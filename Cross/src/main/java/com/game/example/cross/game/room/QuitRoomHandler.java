package com.game.example.cross.game.room;

import com.game.example.basic.logic.game.room.Room;
import com.game.example.basic.logic.game.room.proto.RoomQuitReq;
import com.game.example.basic.logic.game.room.proto.RoomQuitRsp;
import org.qiunet.cross.actor.CrossPlayerActor;
import org.qiunet.flash.handler.common.player.PlayerActor;
import org.qiunet.flash.handler.context.request.persistconn.IPersistConnRequest;

/***
 * 请求退出房间
 */
public class QuitRoomHandler extends BasicRoomHandler<RoomQuitReq> {

	@Override
	public void handler(PlayerActor playerActor, IPersistConnRequest<RoomQuitReq> context) throws Exception {
		playerActor.sendMessage(RoomQuitRsp.valueOf(true));
	}

	@Override
	public void crossHandler0(CrossPlayerActor actor, Room room, RoomQuitReq roomQuitReq) {
		room.quit(actor);
	}
}
