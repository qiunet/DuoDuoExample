package com.game.example.basic.logic.game;

import com.game.example.basic.logic.game.room.domain.RoomInfoData;
import com.game.example.basic.logic.game.room.event.PlayerRoomInfoRspEvent;
import com.game.example.basic.logic.game.room.event.cross.EnterRoomByIdEvent;
import com.game.example.basic.logic.game.room.event.cross.EnterRoomCrossEvent;
import com.game.example.basic.logic.game.room.event.cross.PlayerReconnectEvent;
import com.game.example.basic.logic.game.room.event.cross.PlayerRoomInfoReqEvent;
import com.game.example.basic.logic.game.room.proto.JoinRoomFailRsp;
import com.game.example.basic.logic.game.room.proto.RoomQuitReq;
import com.game.example.basic.logic.id_builder.enums.GlobalIdBuilderType;
import com.game.example.common.server.ServerAssigner;
import com.game.example.common.utils.redis.RedisDataUtil;
import org.qiunet.cross.node.ServerInfo;
import org.qiunet.flash.handler.common.player.PlayerActor;
import org.qiunet.flash.handler.context.status.IGameStatus;
import org.qiunet.utils.listener.event.EventListener;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 游戏服处理
 */
public enum GameService {
    instance;

    @EventListener
    private void playerRoomInfoRsp(PlayerRoomInfoRspEvent event) {
        if (event.getRoomId() > 0) {    // 理论上在跨服状态都会有roomId,重连就好
            event.getPlayer().fireCrossEvent(PlayerReconnectEvent.valueOf());
            return;
        }
        // 退出重进的逻辑暂时不会调用到
        // if (event.getGameId() != 0 && event.getGameId() != event.getReqGameId()) {
            // 先退出房间
            event.getPlayer().sendCrossMessage(new RoomQuitReq());
            // 预留点时间给退出房间
            event.getPlayer().scheduleMessage((p) -> {
                this.joinOrCreateRoomRequest(p, event.isPrivacy());
            }, 10, TimeUnit.MILLISECONDS);
        // }
    }

    /**
     * 加入或创建房间玩法
     */
    public void joinOrCreateRoomRequest(PlayerActor playerActor, boolean privacy) {
        if (playerActor.isCrossStatus()) {
            this.handlerCross(playerActor, privacy);
            return;
        }
        this.joinOrCreateRoomRequest0(playerActor, privacy);
    }

    /**
     * @see GameCrossService#playerRoomInfoReq(PlayerRoomInfoReqEvent)
     * @see #playerRoomInfoRsp(PlayerRoomInfoRspEvent)
     */
    private void handlerCross(PlayerActor playerActor, boolean privacy) {
        if (!playerActor.isCrossStatus()) {
            return;
        }
        playerActor.fireCrossEvent(PlayerRoomInfoReqEvent.valueOf(privacy));
    }

    private void joinOrCreateRoomRequest0(PlayerActor playerActor, boolean privacy) {
        if (privacy) {
            // 自己创建房间
            ServerInfo serverInfo = ServerAssigner.instance.assignCrossServer();
            this.joinRoom(playerActor, serverInfo.getServerId(), true, 0);
            return;
        }
        // 随机加入
        RoomInfoData roomInfoData = randRoom();
        if (roomInfoData == null) {
            ServerInfo serverInfo = ServerAssigner.instance.assignCrossServer();
            this.joinRoom(playerActor, serverInfo.getServerId(), false, 0);
            return;
        }

        this.joinRoom(playerActor, roomInfoData.getServerId(), false, roomInfoData.getRoomId());
    }

    private RoomInfoData randRoom() {
        Set<String> rooms = RedisDataUtil.jedis().smembers(RoomInfoData.ROOM_INFO_SET_REDIS_KEY);
        if (rooms.isEmpty()) {
            return null;
        }

        for (String roomIdString : rooms) {
            long roomId = Long.parseLong(roomIdString);
            RoomInfoData roomInfoData = RoomInfoData.readFromRedis(roomId);
            if (roomInfoData != null) {
                return roomInfoData;
            }

            RedisDataUtil.jedis().srem(RoomInfoData.ROOM_INFO_SET_REDIS_KEY, roomIdString);
        }
        return null;
    }

    /**
     * 前往房间
     * @param roomId  房间id == 0 说明要新建
     */
    public void joinRoom(PlayerActor playerActor, int serverId, boolean privacy, long roomId) {
        if (roomId == 0) {
            // 房间ID 在游戏服build. 可以序列化到数据库
            roomId = GlobalIdBuilderType.ROOM.generateId();
        }
        long finalRoomId = roomId;
        playerActor.addMessage((p) -> {
            EnterRoomCrossEvent event = EnterRoomCrossEvent.valueOf(privacy, finalRoomId);
            playerActor.crossToServer(serverId);
            playerActor.fireCrossEvent(event);
        });
    }

    //加入指定房间
    public void joinRoomById(PlayerActor playerActor, RoomInfoData roomInfoData) {
        playerActor.crossToServer(roomInfoData.getServerId());
        playerActor.fireCrossEvent(EnterRoomByIdEvent.valueOf(roomInfoData.getRoomId()));
    }

    public void sendEnterRoomFailMessage(PlayerActor playerActor, IGameStatus status){
        playerActor.sendMessage(JoinRoomFailRsp.valueOf(status));
    }
}
