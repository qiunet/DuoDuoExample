package com.game.example.basic.logic.player.proto;

import org.qiunet.cross.event.ToCrossPlayerEvent;

/***
 * 玩家非登出下线处理
 * cross的地方需要处理.
 */
public class PlayerBrokenEvent extends ToCrossPlayerEvent {

	public static PlayerBrokenEvent valueOf(){
		return new PlayerBrokenEvent();
	}
}
