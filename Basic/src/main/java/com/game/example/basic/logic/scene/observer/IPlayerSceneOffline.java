package com.game.example.basic.logic.scene.observer;

import com.game.example.basic.logic.scene.object.Player;
import org.qiunet.flash.handler.common.observer.IObserver;

public interface IPlayerSceneOffline extends IObserver {
	/**
	 * 玩家场景下线
	 */
	void offline(Player player);
}
