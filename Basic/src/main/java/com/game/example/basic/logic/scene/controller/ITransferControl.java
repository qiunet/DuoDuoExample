package com.game.example.basic.logic.scene.controller;


import com.game.example.basic.logic.scene.SceneService;
import com.game.example.basic.logic.scene.domain.Coordinate;
import com.game.example.basic.logic.scene.domain.SceneInstance;
import com.game.example.basic.logic.scene.object.MovableObject;
import com.game.example.basic.logic.scene.object.Player;
import com.game.example.basic.logic.scene.proto.TransportRsp;

public interface ITransferControl {
	/**
	 * 传送
	 */
	default void transfer(MovableObject owner, String sceneId, Coordinate coordinate) {
		enterScene(owner, sceneId, coordinate);
		if (owner.isPlayer()) {
			((Player) owner).sendMessage(TransportRsp.valueOf());
		}
	}

	/**
	 * 进入场景
	 */
	default void enterScene(MovableObject owner, String sceneId, Coordinate birthCoordinate) {
		enterScene(owner, this.getSceneInstance(sceneId), birthCoordinate);
		owner.setTransferControl(this);
	}

	/**
	 * 进入场景
	 */
	default void enterScene(MovableObject owner, SceneInstance sceneInstance, Coordinate birthCoordinate) {
		SceneService.instance.bindSceneInstance(owner, sceneInstance, birthCoordinate);
	}

	/**
	 * 获得对应的场景ID
	 */
	SceneInstance getSceneInstance(String sceneId);
}
