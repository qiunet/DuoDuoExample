package com.game.example.basic.logic.game.room.event.cross;

import org.qiunet.cross.event.BaseCrossPlayerEventData;

public class EnterRoomCrossEvent extends BaseCrossPlayerEventData {
	/**
	 * 私密房
	 */
	private boolean privacy;
	/**
	 * 房间ID
	 */
	private long roomId;

	public static EnterRoomCrossEvent valueOf(boolean privacy, long roomId) {
		EnterRoomCrossEvent data = new EnterRoomCrossEvent();
		data.privacy = privacy;
		data.roomId = roomId;
		return data;
	}

	public boolean isPrivacy() {
		return privacy;
	}

	public long getRoomId() {
		return roomId;
	}
}
