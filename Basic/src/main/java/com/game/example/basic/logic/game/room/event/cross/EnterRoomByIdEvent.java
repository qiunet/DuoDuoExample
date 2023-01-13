package com.game.example.basic.logic.game.room.event.cross;

import org.qiunet.cross.event.BaseCrossPlayerEvent;

public class EnterRoomByIdEvent extends BaseCrossPlayerEvent {
	/**
	 * 房间ID
	 */
	private long roomId;

	public static EnterRoomByIdEvent valueOf(long roomId) {
		EnterRoomByIdEvent data = new EnterRoomByIdEvent();
		data.roomId = roomId;
		return data;
	}

	public long getRoomId() {
		return roomId;
	}
}
