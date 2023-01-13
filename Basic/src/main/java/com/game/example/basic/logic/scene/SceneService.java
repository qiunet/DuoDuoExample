package com.game.example.basic.logic.scene;

import com.game.example.basic.logic.scene.domain.Coordinate;
import com.game.example.basic.logic.scene.domain.SceneInstance;
import com.game.example.basic.logic.scene.enums.ObjectType;
import com.game.example.basic.logic.scene.object.MovableObject;
import com.game.example.basic.logic.scene.object.Player;
import com.game.example.common.logger.GameLogger;
import org.qiunet.cross.actor.CrossPlayerActor;
import org.qiunet.cross.actor.event.CrossPlayerAuthSuccessEvent;
import org.qiunet.utils.listener.event.EventHandlerWeightType;
import org.qiunet.utils.listener.event.EventListener;
import org.slf4j.Logger;

public enum SceneService {
    instance;

    private static final Logger logger = GameLogger.COMM_LOGGER.getLogger();

    @EventListener(EventHandlerWeightType.HIGH)
    private void authBindPlayer(CrossPlayerAuthSuccessEvent eventData) {
        CrossPlayerActor actor = eventData.getPlayer();
        if (! actor.isNull(Player.PLAYER_IN_ACTOR_KEY)) {
            // 不为null 说明是重连过来的
            return;
        }

        Player player = new Player(actor, ObjectType.PLAYER, actor.getId());
        actor.setVal(Player.PLAYER_IN_ACTOR_KEY, player);
    }

    // 对象在 SceneInstance 上线
    public <T extends MovableObject> T bindSceneInstance(T player, SceneInstance sceneInstance, Coordinate birthCoordinate) {
        if(player.isPlayer()){
            logger.info("PlayerActor {} online in scene {}", player.getId(), sceneInstance.getSceneId());
        }
        player.getPosition().offline();
        player.getPosition().onlineInSceneInstance(sceneInstance, birthCoordinate);
        player.getPosition().online();
        return player;
    }
}
