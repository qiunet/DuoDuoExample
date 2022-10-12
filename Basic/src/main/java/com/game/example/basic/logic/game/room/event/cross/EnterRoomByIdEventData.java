package com.game.example.basic.logic.game.room.event.cross;

import org.qiunet.cross.event.BaseCrossPlayerEventData;

public class EnterRoomByIdEventData extends BaseCrossPlayerEventData {
	/**
	 * 房间ID
	 */
	private long roomId;

	public static EnterRoomByIdEventData valueOf(long roomId) {
		EnterRoomByIdEventData data = new EnterRoomByIdEventData();
		data.roomId = roomId;
		return data;
	}

	public long getRoomId() {
		return roomId;
	}
}
