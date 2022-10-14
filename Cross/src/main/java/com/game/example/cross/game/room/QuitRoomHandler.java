package com.game.example.cross.game.room;

import com.game.example.basic.logic.game.room.Room;
import com.game.example.basic.logic.game.room.proto.RoomQuitReq;
import org.qiunet.cross.actor.CrossPlayerActor;

/***
 * 请求退出房间
 */
public class QuitRoomHandler extends BasicRoomHandler<RoomQuitReq> {

	@Override
	public void crossHandler0(CrossPlayerActor actor, Room room, RoomQuitReq roomQuitReq) {
		room.quit(actor);
	}

}
