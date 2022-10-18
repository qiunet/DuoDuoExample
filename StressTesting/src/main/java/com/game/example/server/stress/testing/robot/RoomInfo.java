package com.game.example.server.stress.testing.robot;

import com.game.example.basic.logic.game.room.proto.RoomData;

public class RoomInfo {

    private static int needMoveCount = 5;

    private String sceneId;

    private RoomData roomData;

    private int moveCount;

    public final void clear() {
        this.roomData = null;
        this.sceneId = "";
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public RoomData getRoomData() {
        return roomData;
    }

    public void setRoomData(RoomData roomData) {
        this.roomData = roomData;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }

    public static int getNeedMoveCount() {
        return needMoveCount;
    }
}
