package com.game.example.basic.logic.scene.object;

import com.game.example.basic.logic.scene.enums.ObjectType;
import org.qiunet.flash.handler.common.MessageHandler;
import org.qiunet.utils.args.ArgsContainer;
import org.qiunet.utils.args.Argument;
import org.qiunet.utils.args.ArgumentKey;
import org.qiunet.utils.args.IArgsContainer;

/***
 * 地图里面所有的物体. 可见不可见都包括
 */
public abstract class MObject<Owner extends MObject<Owner>> extends MessageHandler<Owner> implements IArgsContainer {

    private final ArgsContainer container = new ArgsContainer();

    private final ObjectType objectType;

    private final long objectId;

    public MObject(ObjectType objectType, long objectId) {
        this.objectType = objectType;
        this.objectId = objectId;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public long getId() {
        return objectId;
    }

    public long getObjectId() {
        return objectId;
    }

    public boolean isPlayer(){
        return ObjectType.PLAYER == objectType;
    }

    public boolean isNpc() {
        return ObjectType.NPC == objectType;
    }

    public boolean isRobot() {
        return ObjectType.ROBOT == objectType;
    }

    @Override
    public String msgExecuteIndex() {
        return String.valueOf(objectId);
    }

    @Override
    public <T> Argument<T> getArgument(ArgumentKey<T> key, boolean computeIfAbsent) {
        return container.getArgument(key, computeIfAbsent);
    }
}
