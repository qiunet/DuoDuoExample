package com.game.example.basic.logic.scene;

import com.game.example.basic.logic.scene.enums.ObjectType;
import com.game.example.basic.logic.scene.object.Player;
import org.qiunet.cross.actor.CrossPlayerActor;
import org.qiunet.cross.actor.event.CrossPlayerAuthSuccessEventData;
import org.qiunet.utils.listener.event.EventHandlerWeightType;
import org.qiunet.utils.listener.event.EventListener;

public enum SceneService {
    instance;

    @EventListener(EventHandlerWeightType.HIGH)
    private void authBindPlayer(CrossPlayerAuthSuccessEventData eventData) {
        CrossPlayerActor actor = eventData.getPlayer();
        if (! actor.isNull(Player.PLAYER_IN_ACTOR_KEY)) {
            // 不为null 说明是重连过来的
            return;
        }

        Player player = new Player(actor, ObjectType.PLAYER, actor.getId());
        actor.setVal(Player.PLAYER_IN_ACTOR_KEY, player);
    }
}
