package com.game.example.basic.logic.game.room;

import com.game.example.basic.logic.game.room.handler.IRoomHandler;
import org.qiunet.flash.handler.common.MessageHandler;
import org.qiunet.utils.args.ArgsContainer;
import org.qiunet.utils.args.Argument;
import org.qiunet.utils.args.ArgumentKey;
import org.qiunet.utils.args.IArgsContainer;

public abstract class BaseRoom extends MessageHandler<Room> implements IArgsContainer {

    private final ArgsContainer argsContainer = new ArgsContainer();
    /**
     * 房间的处理
     */
    protected final IRoomHandler handler;

    protected final long roomId;

    protected BaseRoom(IRoomHandler handler, long roomId) {
        this.handler = handler;
        this.roomId = roomId;
    }

    public IRoomHandler getHandler() {
        return handler;
    }

    @Override
    public <T> Argument<T> getArgument(ArgumentKey<T> key, boolean computeIfAbsent) {
        return argsContainer.getArgument(key, computeIfAbsent);
    }

    public long getId() {
        return roomId;
    }
}
