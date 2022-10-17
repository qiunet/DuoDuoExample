package com.game.example.basic.logic.scene.object;

import com.game.example.basic.logic.scene.controller.BehaviorController;
import com.game.example.basic.logic.scene.controller.PlayerBehaviorController;
import com.game.example.basic.logic.scene.domain.KnowList;
import com.game.example.basic.logic.scene.domain.Position;
import com.game.example.basic.logic.scene.enums.ObjectType;
import org.qiunet.flash.handler.common.observer.IObserverSupportOwner;
import org.qiunet.flash.handler.common.observer.ObserverSupport;

/**
 * 可见的对象
 */
public abstract class VisibleObject <Owner extends VisibleObject<Owner>> extends MObject<Owner> implements IObserverSupportOwner<Owner> {
    /**
     * 观察者
     */
    private final ObserverSupport<Owner> observerSupport = new ObserverSupport<>((Owner) this);
    /**
     * 视野管理
     */
    private final KnowList<Owner> knowList = new KnowList<>((Owner) this);
    /**
     * 行为控制器
     * 
     */
    private final BehaviorController<Owner> behaviorController;
    /**
     * 位置
     */
    private final Position<Owner> position;

    public VisibleObject(ObjectType objectType, long objectId) {
        super(objectType, objectId);
        this.behaviorController = (BehaviorController<Owner>) new PlayerBehaviorController();
        this.position = new Position<>((Owner) this);
    }

    @Override
    public void destroy() {
        this.observerSupport.clear();
        super.destroy();
    }

    @Override
    public ObserverSupport<Owner> getObserverSupport() {
        return observerSupport;
    }

    public KnowList<Owner> getKnowList() {
        return knowList;
    }

    public Position<Owner> getPosition() {
        return position;
    }

    public BehaviorController<Owner> getBehaviorController() {
        return behaviorController;
    }
}
