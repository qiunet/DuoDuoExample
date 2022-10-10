package com.game.example.server.handler.player;

import com.game.example.basic.logic.player.proto.LoginReq;
import com.game.example.common.constants.GameStatus;
import com.game.example.common.data.PlayerPlatformData;
import com.game.example.server.common.handler.GameHandler;
import com.game.example.server.logic.player.PlayerService;
import org.qiunet.data.util.ServerConfig;
import org.qiunet.flash.handler.common.player.PlayerActor;
import org.qiunet.flash.handler.common.player.proto.ReconnectInvalidPush;
import org.qiunet.flash.handler.context.request.persistconn.IPersistConnRequest;
import org.qiunet.flash.handler.context.status.StatusResultException;
import org.qiunet.utils.string.StringUtil;

import java.util.Objects;

/***
 * 登录游戏服
 */
public class LoginHandler extends GameHandler<LoginReq> {
	@Override
	public void handler(PlayerActor playerActor, IPersistConnRequest<LoginReq> context) throws Exception {
		LoginReq req = context.getRequestData();
		String ticket = req.getTicket();

		if (StringUtil.isEmpty(ticket)) {
			// 请求必须带ticket
			playerActor.sendMessage(ReconnectInvalidPush.getInstance());
			return;
		}

		String[] strings = StringUtil.split(ticket, "_");
		PlayerPlatformData data = PlayerPlatformData.readData(Long.parseLong(strings[1]));
		if (data == null || !Objects.equals(data.getTicket(), ticket)) {
			// redis中PlayerPlatformData的ticket必须与客户端请求的一致，否则重新请求登录服登录
			playerActor.sendMessage(ReconnectInvalidPush.getInstance());
			return;
		}

		if (data.getServerId() != ServerConfig.getServerId()) {
			throw StatusResultException.valueOf(GameStatus.LOGIN_SERVER_ERROR);
		}

		PlayerService.instance.doLogin(playerActor, data, ticket);
	}

	@Override
	public boolean needAuth() {
		return false;
	}

	public static long version(String cliVersion) {
		if (StringUtil.isEmpty(cliVersion)) {
			return 0;
		}
		Integer[] integers = StringUtil.conversion(cliVersion, ".", Integer.class);
		if(integers.length < 3){
			return 0;
		}
		return integers[0] * 10_000_000_000L + integers[1] * 10_000_000L + integers[2];
	}
}
