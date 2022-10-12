package com.game.example.basic.logic.game.room.observer;

import com.game.example.basic.logic.game.room.Room;
import com.game.example.basic.logic.game.room.player.RoomPlayer;
import org.qiunet.flash.handler.common.observer.IObserver;

/***
 * 房间人退出了
 */
public interface IRoomQuitPlayer extends IObserver {
	/**
     * 退出
	 * @param room
     * @param roomPlayer 退出的玩家
	 */
	void quit(Room room, RoomPlayer roomPlayer);
}
