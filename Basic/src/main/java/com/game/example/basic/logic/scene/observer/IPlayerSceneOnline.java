package com.game.example.basic.logic.scene.observer;

import com.game.example.basic.logic.scene.object.Player;
import org.qiunet.flash.handler.common.observer.IObserver;

public interface IPlayerSceneOnline extends IObserver {
	/**
	 * 玩家场景上线
	 */
	void online(Player player);
}
