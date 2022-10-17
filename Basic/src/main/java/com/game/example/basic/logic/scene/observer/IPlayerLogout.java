package com.game.example.basic.logic.scene.observer;

import com.game.example.basic.logic.scene.object.Player;
import org.qiunet.flash.handler.common.observer.IObserver;

/***
 * 玩家登出处理
 */
public interface IPlayerLogout extends IObserver {
	/**
	 * 登出
	 */
	void logout(Player player);
}
