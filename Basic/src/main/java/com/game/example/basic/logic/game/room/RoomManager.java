package com.game.example.basic.logic.game.room;

import com.game.example.basic.logic.game.room.event.cross.EnterRoomCrossEvent;
import com.game.example.basic.logic.game.room.handler.EmptyRoomHandler;
import com.game.example.basic.logic.game.room.handler.IRoomHandler;
import com.game.example.basic.logic.game.room.observer.IRoomDestroy;
import com.google.common.collect.Maps;
import org.qiunet.utils.listener.event.EventHandlerWeightType;
import org.qiunet.utils.listener.event.EventListener;
import org.qiunet.utils.listener.event.data.ServerShutdownEvent;

import java.util.Map;

public enum RoomManager {
    instance;

    /**
     * 所有的场景
     */
    private final Map<Long, Room> roomMap = Maps.newConcurrentMap();

    public Room getRoom(long roomId) {
        return roomMap.get(roomId);
    }

    /**
     * 获取一个房间. 如果没有. 就使用该ID 创建
     */
    public Room getOrCreateRoom(EnterRoomCrossEvent eventData) {
        Room room = roomMap.get(eventData.getRoomId());
        if (room == null) {
            return this.makeRoom(eventData);
        }
        return room;
    }

    private Room makeRoom(EnterRoomCrossEvent eventData) {
        String sceneId = "DEFAULT_SCENE";
        long roomId = eventData.getRoomId();
        IRoomHandler handler = new EmptyRoomHandler(sceneId);
        Room room = this.roomMap.computeIfAbsent(roomId, key -> new Room(handler, roomId, sceneId, eventData.isPrivacy()));
        room.getObserverSupport().attach(IRoomDestroy.class, this::removeRoom);
        return room;
    }

    private void removeRoom(Room room) {
        roomMap.remove(room.getId());
    }

    @EventListener(EventHandlerWeightType.HIGHEST)
    private void onShutdown(ServerShutdownEvent eventData) {
        roomMap.values().forEach(Room::destroy);
    }
}
