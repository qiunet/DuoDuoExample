package com.game.example.basic.logic.scene.object;

import com.game.example.basic.logic.scene.enums.ObjectType;

/***
 * 移动的对象
 */
public class MovableObject<Owner extends MovableObject<Owner>> extends VisibleObject<Owner> {

    public MovableObject(ObjectType objectType, long objectId) {
        super(objectType, objectId);
    }
}
