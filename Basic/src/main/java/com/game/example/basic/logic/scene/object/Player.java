package com.game.example.basic.logic.scene.object;

import com.game.example.basic.logic.scene.enums.ObjectType;
import io.netty.channel.ChannelFuture;
import org.qiunet.cross.actor.CrossPlayerActor;
import org.qiunet.flash.handler.common.IMessage;
import org.qiunet.flash.handler.common.player.AbstractUserActor;
import org.qiunet.flash.handler.common.player.observer.IPlayerDestroy;
import org.qiunet.flash.handler.context.request.data.IChannelData;
import org.qiunet.flash.handler.context.response.push.IChannelMessage;
import org.qiunet.flash.handler.context.sender.IChannelMessageSender;
import org.qiunet.utils.args.ArgumentKey;

import java.util.function.Predicate;

public class Player extends MovableObject<Player> implements IChannelMessageSender {

    public static final ArgumentKey<Player> PLAYER_IN_ACTOR_KEY = new ArgumentKey<>();

    private AbstractUserActor userActor;

    public Player(AbstractUserActor userActor, ObjectType objectType, long objectId) {
        super(objectType, objectId);
        this.userActor = userActor;
    }

    @Override
    public ChannelFuture sendMessage(IChannelData message) {
        return userActor.sendMessage(message);
    }

    @Override
    public ChannelFuture sendMessage(IChannelData message, boolean flush) {
        return userActor.sendMessage(message, flush);
    }

    @Override
    public ChannelFuture sendKcpMessage(IChannelMessage<?> message, boolean flush) {
        return userActor.sendKcpMessage(message, flush);
    }

    @Override
    public ChannelFuture sendKcpMessage(IChannelData message, boolean flush) {
        return userActor.sendKcpMessage(message, flush);
    }

    @Override
    public void addMessage(IMessage<Player> msg) {
        if (userActor.isDestroyed()){
            ((CrossPlayerActor) userActor).getObserverSupport().syncFire(IPlayerDestroy.class, i -> i.destroyActor(this.userActor));
            this.getPosition().offline();
            this.destroy();
        }
        userActor.addMessage(p -> msg.execute(this));
    }

    /**
     * 给玩家广播消息
     */
    public void knowListBroadcast(Predicate<VisibleObject> filter, IChannelData channelData, boolean kcp) {
        getKnowList().broadcast(filter, channelData, kcp);
    }

    public void knowListBroadcast(IChannelData channelData) {
        getKnowList().broadcast(channelData);
    }

    public void logout() {
        getBehaviorController().logout();
    }

    public AbstractUserActor getUserActor() {
        return userActor;
    }

    @Override
    public IChannelMessageSender getSender() {
        return userActor;
    }

}
