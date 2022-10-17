package com.game.example.basic.logic.scene.observer;

import com.game.example.basic.logic.scene.object.Player;
import org.qiunet.flash.handler.common.observer.IObserver;

public interface IPlayerSceneChange extends IObserver {
    /**
     * 玩家场景切换
	 * @param player
     */
	void change(Player player, String newSceneId);
}
