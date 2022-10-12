package com.game.example.basic.logic.game.room.handler;

import com.game.example.basic.logic.game.room.Room;

/**
 * 空房间
 */
public class EmptyRoomHandler extends BaseRoomHandler {

    public EmptyRoomHandler(String sceneId) {
        super(sceneId);
    }

    @Override
    public void initialize(Room room) {

    }

    @Override
    public void frameLogic(Room room, float ptf) {

    }
}
