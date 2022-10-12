package com.game.example.basic.logic.scene.domain;

import org.qiunet.flash.handler.common.MessageHandler;

/***
 * 地图的实例
 */
public final class SceneInstance extends MessageHandler<SceneInstance> {

    /**
     * 场景的ID
     */
    private final String sceneId;

    public SceneInstance(String sceneId) {
        this.sceneId = sceneId;
    }

    @Override
    public String msgExecuteIndex() {
        return sceneId;
    }
}
