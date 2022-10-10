package com.game.example.server.handler.player;

import com.game.example.basic.logic.player.proto.LogoutReq;
import com.game.example.server.common.handler.GameHandler;
import org.qiunet.flash.handler.common.player.PlayerActor;
import org.qiunet.flash.handler.context.request.persistconn.IPersistConnRequest;

/***
 * 登出
 */
public class LogoutHandler extends GameHandler<LogoutReq> {
	@Override
	public void handler(PlayerActor playerActor, IPersistConnRequest<LogoutReq> context) throws Exception {
		playerActor.logout();
	}
}
