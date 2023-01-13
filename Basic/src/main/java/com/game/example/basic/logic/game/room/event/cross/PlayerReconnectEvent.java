package com.game.example.basic.logic.game.room.event.cross;

import org.qiunet.cross.event.BaseCrossPlayerEvent;

/***
 * 玩家重连处理
 */
public class PlayerReconnectEvent extends BaseCrossPlayerEvent {

	public static PlayerReconnectEvent valueOf(){
		return new PlayerReconnectEvent();
	}
}
