package com.game.example.basic.logic.player;

import com.game.example.basic.logic.game.room.event.cross.PlayerReconnectEvent;
import com.game.example.basic.logic.player.entity.PlayerBo;
import com.game.example.basic.logic.player.entity.PlayerDo;
import com.game.example.basic.logic.player.proto.*;
import com.game.example.common.constants.GameStatus;
import com.game.example.common.data.AvatarInfo;
import com.game.example.common.data.PlayerAvatarData;
import com.game.example.common.data.PlayerPlatformData;
import com.game.example.common.logger.GameLogger;
import org.qiunet.data.db.loader.DataLoader;
import org.qiunet.data.support.DbDataSupport;
import org.qiunet.flash.handler.common.player.PlayerActor;
import org.qiunet.flash.handler.common.player.UserOnlineManager;
import org.qiunet.flash.handler.common.player.event.CrossPlayerDestroyEvent;
import org.qiunet.flash.handler.common.player.event.PlayerActorLogoutEvent;
import org.qiunet.flash.handler.common.player.observer.IPlayerDestroy;
import org.qiunet.flash.handler.context.status.StatusResultException;
import org.qiunet.flash.handler.netty.server.constants.CloseCause;
import org.qiunet.utils.date.DateUtil;
import org.qiunet.utils.listener.event.EventListener;
import org.slf4j.Logger;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public enum PlayerService {
    instance;

    private static final Logger logger = GameLogger.COMM_LOGGER.getLogger();

    @DataLoader(PlayerBo.class)
    private final DbDataSupport<Long, PlayerDo, PlayerBo> dataSupport = new DbDataSupport<>(PlayerDo.class, PlayerBo::new);


    ////////////////////////////////////////////////////以下是注册////////////////////////////////////////////////////////////////
    public void doRegister(PlayerActor actor, PlayerPlatformData data, AvatarInfo avatarInfo) {
		long playerId = data.getPlayerId();

		// 注册Player
		PlayerDo playerDo = new PlayerDo(playerId);
		playerDo.setAvatar(PlayerAvatarData.valueOf(avatarInfo).serialize());
		playerDo.setToken(data.getToken());
		playerDo.setName(data.getName());
		playerDo.setRegister_date(DateUtil.currentTimeMillis());
		actor.insertDo(playerDo);

        actor.sendMessage(RegisterRsp.valueOf(true));
        this.loginSuccess(actor, data);
    }

    ////////////////////////////////////////////////////以下是登录////////////////////////////////////////////////////////////////
    public void doLogin(PlayerActor actor, PlayerPlatformData data) {
        if (actor.isAuth()) {
            throw StatusResultException.valueOf(GameStatus.LOGIN_REQUEST_REPEATED);
        }

        this.repeatedLoginCheck(data.getPlayerId(), data.getTicket());
        actor.auth(data.getPlayerId());

        PlayerBo playerBo = dataSupport.getBo(data.getPlayerId());
        boolean needRegister = playerBo == null;
        actor.setOpenId(String.valueOf(data.getPlayerId()));
        if (needRegister) {
            // refreshPlayerInfo(actor);
            actor.sendMessage(new NeedRegisterPush());
            return;
        }

        this.loginSuccess(actor, data);
    }

    public void loginSuccess(PlayerActor playerActor, PlayerPlatformData data) {
        PlayerBo playerBo = playerActor.getData(PlayerBo.class);
        String preTicket = playerBo.getDo().getTicket();
		String ticket = data.getTicket();
        boolean reconnect = false;

        if (Objects.equals(preTicket, ticket)) {   // 如果本次客户端带的ticket与服务端持有的ticket一致就走重连逻辑
            // 玩家ticket进入, 重连处理.
            playerActor = UserOnlineManager.instance.reconnect(data.getPlayerId(), playerActor);
            if (playerActor == null) {
                // 重连没有成功. 数据已经失效. 客户端应该重走登录流程
                data.expire(ticket);
                return;
            }
            this.reconnect(playerActor);
            reconnect = true;
        }else {
            // 如果本机有waiter的. 可能里面保留了cross的连接. 需要销毁掉.
            UserOnlineManager.instance.destroyWaiter(playerActor.getPlayerId());
        }

        playerActor.sendMessage(LoginRsp.valueOf(data.getPlayerId(), reconnect));
        playerActor.setOpenId(String.valueOf(data.getPlayerId()));
        playerActor.loginSuccess();

        playerBo = playerActor.getData(PlayerBo.class);
        playerBo.getDo().setToken(data.getToken());
        playerBo.getDo().setTicket(ticket);
        playerBo.getDo().setName(data.getName());
        playerBo.update();

        PlayerDataPush playerDataPush = PlayerDataPush.valueOf(PlayerTo.valueOf(playerBo));
        playerActor.sendMessage(playerDataPush);
    }

    private void repeatedLoginCheck(long playerId, String ticket) {
		PlayerActor playerActor = UserOnlineManager.instance.getPlayerActor(playerId);
		if (playerActor == null) {
			return;
		}

		logger.info("=====PlayerActor {} repeated login=====", playerId);
		PlayerBo playerBo = playerActor.getData(PlayerBo.class);
		boolean sameTicket = Objects.equals(playerBo.getDo().getTicket(), ticket);
		playerActor.getSession().close(sameTicket ? CloseCause.LOGIN_RECONNECTION : CloseCause.LOGIN_REPEATED);
	}

    /**
     * 重连处理
     */
    private void reconnect(PlayerActor actor) {
        if (actor.isCrossStatus()) {
            // 通知连接的服务器处理
            actor.fireCrossEvent(PlayerReconnectEvent.valueOf());
        }
    }

    ////////////////////////////////////////////////////以下是登出////////////////////////////////////////////////////////////////
    @EventListener
    public void playerLogout(PlayerActorLogoutEvent data) {
        PlayerActor playerActor = data.getPlayer();

        // 省略redis全局玩家信息的登录状态、登出时间修改

        if (data.getCause().needWaitConnect()) {
            PlayerPlatformData playerPlatformData = PlayerPlatformData.readData(playerActor.getPlayerId());
            String ticket = playerPlatformData == null ? null : playerPlatformData.getTicket();
            playerActor.getObserverSupport().attach(IPlayerDestroy.class, actor -> {
                PlayerPlatformData.del(playerActor.getPlayerId(), ticket);
            });
            if (playerActor.isCrossStatus()) {
                // 告诉跨服服务. 玩家这里断线了.
                playerActor.fireCrossEvent(PlayerBrokenEvent.valueOf());
            }
            return;
        }
        PlayerPlatformData.del(playerActor.getPlayerId());
    }
	/**
     * 处理跨服销毁
     * CrossPlayer已经销毁. 通知player这.
     */
    @EventListener
    private void crossDestroy(CrossPlayerDestroyEvent event) {
        // 延缓一秒关闭. 给玩法服一些时间处理事情
        event.getPlayer().scheduleMessage(p -> {
            event.getPlayer().quitCross(event.getServerId(), CloseCause.DESTROY);
        }, 1, TimeUnit.SECONDS);
    }

}
