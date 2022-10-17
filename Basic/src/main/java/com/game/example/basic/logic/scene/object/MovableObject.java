package com.game.example.basic.logic.scene.object;

import com.game.example.basic.logic.scene.controller.ITransferControl;
import com.game.example.basic.logic.scene.domain.Coordinate;
import com.game.example.basic.logic.scene.enums.ObjectType;

/***
 * 移动的对象
 */
public class MovableObject<Owner extends MovableObject<Owner>> extends VisibleObject<Owner> {
    /**
     * 传送的控制
     */
    private ITransferControl transferControl;

    public MovableObject(ObjectType objectType, long objectId) {
        super(objectType, objectId);
    }

    public ITransferControl getTransferControl() {
        return transferControl;
    }

    public void setTransferControl(ITransferControl transferControl) {
        this.transferControl = transferControl;
    }

    /**
     * 传送
     * @param sceneId		场景ID
     * @param coordinate	目标位置
     */
    public void transfer(String sceneId, Coordinate coordinate) {
        transferControl.transfer(this, sceneId, coordinate);
    }
}
