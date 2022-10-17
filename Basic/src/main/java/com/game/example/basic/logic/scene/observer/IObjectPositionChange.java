package com.game.example.basic.logic.scene.observer;

import com.game.example.basic.logic.scene.object.VisibleObject;
import org.qiunet.flash.handler.common.observer.IObserver;

/***
 * 物品位置变动. 这里触发
 */
public interface IObjectPositionChange extends IObserver {
	/**
	 * 位置变动
	 * @param object 变动的物体.
	 */
	void change(VisibleObject<?> object);
}
