package com.game.example.basic.logic.game.room.event.cross;

import org.qiunet.cross.event.BaseCrossPlayerEvent;

/***
 * 请求玩法服房间信息.
 */
public class PlayerRoomInfoReqEvent extends BaseCrossPlayerEvent {
	/**
	 * 是否私密
	 */
	private boolean privacy;

	public static PlayerRoomInfoReqEvent valueOf(boolean privacy){
		PlayerRoomInfoReqEvent data = new PlayerRoomInfoReqEvent();
		data.privacy = privacy;
		return data;
	}

	public boolean isPrivacy() {
		return privacy;
	}
}
