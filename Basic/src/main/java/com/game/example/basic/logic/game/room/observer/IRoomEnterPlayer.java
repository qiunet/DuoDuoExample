package com.game.example.basic.logic.game.room.observer;

import com.game.example.basic.logic.game.room.Room;
import com.game.example.basic.logic.game.room.player.RoomPlayer;
import org.qiunet.flash.handler.common.observer.IObserver;

/***
 * 房间进人了
 */
public interface IRoomEnterPlayer extends IObserver {
	/**
	 * 玩家进入房间.
	 * @param room
	 * @param roomPlayer 进入的玩家
	 */
	void enter(Room room, RoomPlayer roomPlayer);
}
