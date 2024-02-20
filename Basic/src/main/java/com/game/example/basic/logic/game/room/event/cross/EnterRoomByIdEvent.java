package com.game.example.basic.logic.game.room.event.cross;

import org.qiunet.cross.event.ToCrossPlayerEvent;

public class EnterRoomByIdEvent extends ToCrossPlayerEvent {
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
