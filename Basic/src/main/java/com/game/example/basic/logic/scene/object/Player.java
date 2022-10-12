package com.game.example.basic.logic.scene.object;

import com.game.example.basic.logic.scene.enums.ObjectType;
import org.qiunet.flash.handler.common.player.AbstractUserActor;
import org.qiunet.flash.handler.context.sender.IChannelMessageSender;
import org.qiunet.utils.args.ArgumentKey;

public class Player extends MovableObject<Player> implements IChannelMessageSender {

    public static final ArgumentKey<Player> PLAYER_IN_ACTOR_KEY = new ArgumentKey<>();

    private AbstractUserActor userActor;

    public Player(AbstractUserActor userActor, ObjectType objectType, long objectId) {
        super(objectType, objectId);
        this.userActor = userActor;
    }

    public AbstractUserActor getUserActor() {
        return userActor;
    }

    @Override
    public IChannelMessageSender getSender() {
        return userActor;
    }

}
