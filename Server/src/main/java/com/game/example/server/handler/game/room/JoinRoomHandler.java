package com.game.example.server.handler.game.room;

import com.game.example.basic.logic.game.GameService;
import com.game.example.basic.logic.game.room.domain.RoomInfoData;
import com.game.example.basic.logic.game.room.proto.JoinRoomReq;
import com.game.example.common.constants.GameStatus;
import com.game.example.server.common.handler.GameHandler;
import org.qiunet.flash.handler.common.player.PlayerActor;
import org.qiunet.flash.handler.context.request.persistconn.IPersistConnRequest;

/***
 * 加入指定ID的房间
 */
public class JoinRoomHandler extends GameHandler<JoinRoomReq> {

	@Override
	public void handler(PlayerActor playerActor, IPersistConnRequest<JoinRoomReq> context) throws Exception {
		if (context.getRequestData().getRoomID() <= 0) {
			GameService.instance.sendEnterRoomFailMessage(playerActor, GameStatus.PARAMS_ERROR);
			return;
		}

		if (playerActor.isCrossStatus()) {
			GameService.instance.sendEnterRoomFailMessage(playerActor, GameStatus.ROOM_ENTER_BUT_IN_CROSS);
			return;
		}

		RoomInfoData roomInfoData = RoomInfoData.readFromRedis(context.getRequestData().getRoomID());
		if (roomInfoData == null) {
			GameService.instance.sendEnterRoomFailMessage(playerActor, GameStatus.ROOM_JOIN_NOT_EXIST);
			return;
		}

		GameService.instance.joinRoomById(playerActor, roomInfoData);
	}
}
