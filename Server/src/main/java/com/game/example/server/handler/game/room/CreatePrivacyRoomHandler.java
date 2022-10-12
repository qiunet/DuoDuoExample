package com.game.example.server.handler.game.room;

import com.game.example.basic.logic.game.GameService;
import com.game.example.basic.logic.game.room.proto.CreatePrivacyRoomReq;
import com.game.example.server.common.handler.GameHandler;
import org.qiunet.flash.handler.common.player.PlayerActor;
import org.qiunet.flash.handler.context.request.persistconn.IPersistConnRequest;

/***
 * 创建私有房间
 */
public class CreatePrivacyRoomHandler extends GameHandler<CreatePrivacyRoomReq> {

	@Override
	public void handler(PlayerActor playerActor, IPersistConnRequest<CreatePrivacyRoomReq> context) throws Exception {
		GameService.instance.joinOrCreateRoomRequest(playerActor, true);
	}
}
