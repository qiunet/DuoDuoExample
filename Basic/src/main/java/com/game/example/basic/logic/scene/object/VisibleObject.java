package com.game.example.basic.logic.scene.object;

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

    public VisibleObject(ObjectType objectType, long objectId) {
        super(objectType, objectId);
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
}
