package com.game.example.basic.logic.game.room.player;

import com.game.example.basic.logic.scene.object.MovableObject;
import com.game.example.basic.logic.scene.object.Player;
import org.qiunet.flash.handler.context.sender.IChannelMessageSender;

public class RoomPlayer implements IChannelMessageSender {
    // 玩家跨服对象
    private final MovableObject player;

    public RoomPlayer(MovableObject player) {
        this.player = player;
    }

    public MovableObject getPlayer(){
        return player;
    }

    public long getId(){
        return player.getId();
    }

    /**
     * 是否不是自己
     */
    public boolean isNotSelf(long playerId) {
        return !isSelf(playerId);
    }

    /**
     * 是否是自己
     */
    public boolean isSelf(long playerId) {
        return player.getId() == playerId;
    }


    @Override
    public IChannelMessageSender getSender() {
        return player.isPlayer() ? ((Player)player).getUserActor() : null;
    }
}
