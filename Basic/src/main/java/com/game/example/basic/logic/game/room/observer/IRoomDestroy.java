package com.game.example.basic.logic.game.room.observer;

import com.game.example.basic.logic.game.room.Room;
import org.qiunet.flash.handler.common.observer.IObserver;

/***
 * 房间销毁
 **/
public interface IRoomDestroy extends IObserver {
	/**
	 * 销毁
	 * @param room 需要销毁的房间
	 */
	void destroy(Room room);
}
