package com.game.example.basic.logic.player;

import com.game.example.basic.logic.player.entity.PlayerBo;
import com.game.example.basic.logic.player.entity.PlayerDo;
import com.game.example.basic.logic.player.proto.*;
import com.game.example.common.constants.GameStatus;
import com.game.example.common.data.AvatarInfo;
import com.game.example.common.data.PlayerAvatarData;
import com.game.example.common.data.PlayerPlatformData;
import com.game.example.common.logger.GameLogger;
import com.google.common.collect.Sets;
import org.qiunet.data.db.loader.DataLoader;
import org.qiunet.data.support.DbDataSupport;
import org.qiunet.flash.handler.common.player.PlayerActor;
import org.qiunet.flash.handler.common.player.UserOnlineManager;
import org.qiunet.flash.handler.common.player.event.PlayerActorLogoutEvent;
import org.qiunet.flash.handler.common.player.observer.IPlayerDestroy;
import org.qiunet.flash.handler.context.status.StatusResultException;
import org.qiunet.flash.handler.netty.server.constants.CloseCause;
import org.qiunet.utils.date.DateUtil;
import org.qiunet.utils.listener.event.EventListener;
import org.slf4j.Logger;

import java.util.Objects;
import java.util.Set;

public enum PlayerService {
    instance;

    private static final Logger logger = GameLogger.COMM_LOGGER.getLogger();

    @DataLoader(PlayerBo.class)
    private final DbDataSupport<Long, PlayerDo, PlayerBo> dataSupport = new DbDataSupport<>(PlayerDo.class, PlayerBo::new);
    /**
     * openId -> PlayerActor 防止openID 多客户端登录
     * 一个openId 上线. 下线会添加 删除.
     */
    private static final Set<Long> playerId2Actor = Sets.newConcurrentHashSet();


    ////////////////////////////////////////////////////以下是注册////////////////////////////////////////////////////////////////
    public void doRegister(PlayerActor actor, PlayerPlatformData data, AvatarInfo avatarInfo) {
        this.register(actor, data, data.getTicket(), avatarInfo);
        actor.sendMessage(RegisterRsp.valueOf(true));
        this.loginSuccess(actor, data, data.getTicket(), true);
    }

    private void register(PlayerActor playerActor, PlayerPlatformData data, String ticket, AvatarInfo avatarInfo) {
        long playerId = data.getPlayerId();

        // 注册Player
        PlayerDo playerDo = new PlayerDo(playerId);
        playerDo.setAvatar(PlayerAvatarData.valueOf(avatarInfo).serialize());
        playerDo.setToken(data.getToken());
        playerDo.setName(data.getName());
        playerDo.setTicket(ticket);
        playerDo.setRegister_date(DateUtil.currentTimeMillis());

        playerActor.insertDo(playerDo);
    }
    ////////////////////////////////////////////////////以下是登录////////////////////////////////////////////////////////////////
    public void doLogin(PlayerActor actor, PlayerPlatformData data, String ticket) {
        if (actor.isAuth()) {
            throw StatusResultException.valueOf(GameStatus.LOGIN_REQUEST_REPEATED);
        }

        this.repeatedLoginCheck(data.getPlayerId(), ticket);
        actor.auth(data.getPlayerId());

        PlayerBo playerBo = dataSupport.getBo(data.getPlayerId());
        boolean needRegister = playerBo == null;
        actor.setOpenId(String.valueOf(data.getPlayerId()));
        if (needRegister) {
            // refreshPlayerInfo(actor);
            actor.sendMessage(LoginRsp.valueOf(true, data.getPlayerId(), false));
            return;
        }

        this.loginSuccess(actor, data, ticket, false);
    }

    public void loginSuccess(PlayerActor playerActor, PlayerPlatformData data, String ticket, boolean register) {
        playerId2Actor.add(data.getPlayerId());

        PlayerBo playerBo = playerActor.getData(PlayerBo.class);
        String preTicket = playerBo.getDo().getTicket();

        boolean reconnect = false;
        if (!register && Objects.equals(preTicket, ticket)) {   // 如果本次客户端带的ticket与服务端持有的ticket一致就走重连逻辑
            // 玩家ticket进入, 重连处理.
            playerActor = UserOnlineManager.instance.reconnect(data.getPlayerId(), playerActor);
            if (playerActor == null) {
                // 重连没有成功. 数据已经失效. 客户端应该重走登录流程
                data.expire(ticket);
                return;
            }
            reconnect = true;
        }else {
            // 如果本机有waiter的. 可能里面保留了cross的连接. 需要销毁掉.
            UserOnlineManager.instance.destroyWaiter(playerActor.getPlayerId());
        }

        if (!register) {
            playerActor.sendMessage(LoginRsp.valueOf(false, data.getPlayerId(), reconnect));
        }

        playerActor.setOpenId(String.valueOf(data.getPlayerId()));
        playerActor.loginSuccess();

        playerBo = playerActor.getData(PlayerBo.class);
        playerBo.getDo().setToken(data.getToken());
        playerBo.getDo().setTicket(ticket);
        playerBo.getDo().setName(data.getName());
        playerBo.update();

        // refreshPlayerInfo(playerActor);

        PlayerDataPush playerDataPush = PlayerDataPush.valueOf(PlayerTo.valueOf(playerBo));
        playerActor.sendMessage(playerDataPush);
    }

    private void repeatedLoginCheck(long playerId, String ticket) {
        if (!playerId2Actor.contains(playerId)) {
            return;
        }

        playerId2Actor.remove(playerId);
        logger.info("=====PlayerActor {} repeated login=====", playerId);
        PlayerActor playerActor = UserOnlineManager.instance.getPlayerActor(playerId);
        if (playerActor == null) {
            logger.error("playerId2Actor not null! but UserOnlineManager is null");
            return;
        }

        PlayerBo playerBo = playerActor.getData(PlayerBo.class);
        boolean sameTicket = Objects.equals(playerBo.getDo().getTicket(), ticket);
        playerActor.getSession().close(sameTicket ? CloseCause.LOGIN_RECONNECTION : CloseCause.LOGIN_REPEATED);

    }

    ////////////////////////////////////////////////////以下是登出////////////////////////////////////////////////////////////////
    @EventListener
    public void playerLogout(PlayerActorLogoutEvent data) {
        PlayerActor playerActor = data.getPlayer();
        playerId2Actor.remove(playerActor.getPlayerId());

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

    /*private static PlayerGlobalInfo refreshPlayerInfo(PlayerActor player) {
        PlayerBo playerBo = player.getData(PlayerBo.class);
        PlayerGlobalInfo data = new PlayerGlobalInfo();

        data.setCurrServerId(ServerNodeManager.getCurrServerId());
        if(playerBo == null){
            data.setPlayerId(player.getPlayerId());
        }else{
            data.setIcon(String.valueOf(playerBo.getDo().getIcon()));
            data.setPlayerId(playerBo.getDo().getPlayer_id());
            data.setIntro(playerBo.getDo().getIntro());
            data.setName(playerBo.getDo().getName());
            data.setAvatar(playerBo.getDo().getAvatar());
        }
        data.setLastLoginDt((int) DateUtil.currSeconds());
        data.setServerId(ServerConfig.getServerId());
        data.setOffline(false);
        data.sendToRedis();
        return data;
    }*/
}
