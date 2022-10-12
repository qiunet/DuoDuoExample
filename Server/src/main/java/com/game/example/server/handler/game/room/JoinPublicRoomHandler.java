package com.game.example.server.handler.game.room;

import com.game.example.basic.logic.game.GameService;
import com.game.example.basic.logic.game.room.proto.JoinPublicRoomReq;
import com.game.example.server.common.handler.GameHandler;
import org.qiunet.flash.handler.common.player.PlayerActor;
import org.qiunet.flash.handler.context.request.persistconn.IPersistConnRequest;

/***
 * 加入公共房间
 */
public class JoinPublicRoomHandler extends GameHandler<JoinPublicRoomReq> {

	@Override
	public void handler(PlayerActor playerActor, IPersistConnRequest<JoinPublicRoomReq> context) throws Exception {
		GameService.instance.joinOrCreateRoomRequest(playerActor, false);
	}
}
