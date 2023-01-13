package com.game.example.basic.logic.game.room.event;

import org.qiunet.flash.handler.common.player.event.BasePlayerEvent;

/***
 * 请求玩法服房间信息.
 */
public class PlayerRoomInfoRspEvent extends BasePlayerEvent {
	/**
	 * 房间ID
	 */
	private long roomId;
	/**
	 * 私密
	 */
	private boolean privacy;

	public static PlayerRoomInfoRspEvent valueOf() {
		return valueOf(0, false);
	}

	public static PlayerRoomInfoRspEvent valueOf(long roomId, boolean privacy){
		PlayerRoomInfoRspEvent data = new PlayerRoomInfoRspEvent();
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
