package com.game.example.basic.logic.scene.object;

import com.game.example.basic.logic.scene.enums.ObjectType;
import org.qiunet.cross.actor.CrossPlayerActor;
import org.qiunet.flash.handler.common.IMessage;
import org.qiunet.flash.handler.common.player.AbstractUserActor;
import org.qiunet.flash.handler.common.player.observer.IPlayerDestroy;
import org.qiunet.flash.handler.context.request.data.IChannelData;
import org.qiunet.flash.handler.context.session.IPlayerSender;
import org.qiunet.flash.handler.context.session.ISession;
import org.qiunet.utils.args.ArgumentKey;

import java.util.function.Predicate;

public class Player extends MovableObject<Player> implements IPlayerSender {

    public static final ArgumentKey<Player> PLAYER_IN_ACTOR_KEY = new ArgumentKey<>();

    private final AbstractUserActor userActor;

    public Player(AbstractUserActor userActor, ObjectType objectType, long objectId) {
        super(objectType, objectId);
        this.userActor = userActor;
    }


    @Override
    public boolean addMessage(IMessage<Player> msg) {
        if (userActor.isDestroyed()){
            ((CrossPlayerActor) userActor).getObserverSupport().syncFire(IPlayerDestroy.class, i -> i.destroyActor(this.userActor));
            this.getPosition().offline();
            this.destroy();
        }
        return userActor.addMessage(p -> msg.execute(this));
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
	public ISession getKcpSession() {
		return userActor.getKcpSession();
	}

	@Override
	public ISession getSession() {
		return userActor.getSession();
	}
}
