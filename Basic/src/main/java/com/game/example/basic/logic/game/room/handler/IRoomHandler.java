package com.game.example.basic.logic.game.room.handler;

import com.game.example.basic.logic.game.room.Room;
import com.game.example.basic.logic.scene.domain.SceneInstance;
import com.game.example.basic.logic.scene.object.Player;
import org.qiunet.cross.actor.CrossPlayerActor;

/***
 * 房间的处理
 **/
public interface IRoomHandler {
	/**
	 * 一帧的逻辑.
	 * 不能放处理太久的逻辑
	 */
	default void frameLogic(Room room, float ptf){}

	/**
	 * 初始化房间场景
	 * 添加场景模型等。
	 */
	void initialize(Room room);

	/**
	 * 玩家断线
	 */
	void playerBrokenEvent(Room room, CrossPlayerActor playerActor);

	/**
	 * 玩家重连
	 */
	void playerReconnect(Room room, Player player);

	/**
	 * 构造一个房间的场景.
	 * @param sceneId 场景ID
	 * @return 实例
	 */
	default SceneInstance createSceneInstance(String sceneId) {
		return new SceneInstance(sceneId);
	}
}
