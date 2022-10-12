package com.game.example.basic.logic.game;

import com.game.example.basic.logic.game.room.Room;
import com.game.example.basic.logic.game.room.RoomManager;
import com.game.example.basic.logic.game.room.event.PlayerRoomInfoRspEvent;
import com.game.example.basic.logic.game.room.event.cross.EnterRoomByIdEventData;
import com.game.example.basic.logic.game.room.event.cross.EnterRoomCrossEvent;
import com.game.example.basic.logic.game.room.event.cross.PlayerReconnectEvent;
import com.game.example.basic.logic.game.room.event.cross.PlayerRoomInfoReqEvent;
import com.game.example.basic.logic.game.room.proto.JoinRoomFailRsp;
import com.game.example.basic.logic.player.proto.PlayerBrokenEvent;
import com.game.example.basic.logic.scene.object.Player;
import com.game.example.common.constants.GameStatus;
import org.qiunet.cross.actor.CrossPlayerActor;
import org.qiunet.flash.handler.common.player.AbstractUserActor;
import org.qiunet.flash.handler.netty.server.constants.CloseCause;
import org.qiunet.utils.listener.event.EventListener;

/**
 * 玩法服处理
 */
public enum GameCrossService {
    instance;

    /**
     * 游戏服发到玩法服
     * 进入房间处理
     */
    @EventListener
    private void enterRoomEvent(EnterRoomCrossEvent eventData) {
        Room room = RoomManager.instance.getOrCreateRoom(eventData);
        Player player = eventData.getPlayer().getVal(Player.PLAYER_IN_ACTOR_KEY);
        room.enter(player);
    }

    // 请求玩家当前房间信息
    @EventListener
    private void playerRoomInfoReq(PlayerRoomInfoReqEvent event) {
        CrossPlayerActor crossPlayer = event.getPlayer();
        Player player = crossPlayer.getVal(Player.PLAYER_IN_ACTOR_KEY);
        if (player == null) {
            crossPlayer.fireCrossEvent(PlayerRoomInfoRspEvent.valueOf());
            return;
        }

        Room room = Room.get(player);
        if (room == null) {
            crossPlayer.fireCrossEvent(PlayerRoomInfoRspEvent.valueOf());
            return;
        }

        crossPlayer.fireCrossEvent(PlayerRoomInfoRspEvent.valueOf(room.getRoomInfoData().getRoomId(), event.isPrivacy()));
    }

    @EventListener
    private void enterRoomByIdEvent(EnterRoomByIdEventData data) {
        Room room = RoomManager.instance.getRoom(data.getRoomId());
        CrossPlayerActor player = data.getPlayer();
        if (room == null) {
            player.sendMessage(JoinRoomFailRsp.valueOf(GameStatus.ROOM_JOIN_NOT_EXIST));
            player.logout();
            return;
        }
        room.enter(player.getVal(Player.PLAYER_IN_ACTOR_KEY));
    }

    // 跨服中. 重连事件
    @EventListener
    private void reconnectEvent(PlayerReconnectEvent event) {
        AbstractUserActor actor = event.getPlayer();
        Player player = actor.getVal(Player.PLAYER_IN_ACTOR_KEY);
        Room room = Room.get(player);
        if (room == null) {
            return;
        }
        room.addMessage(r -> r.getHandler().playerReconnect(r, player));
    }

    // 跨服中. 玩家掉线事件
    @EventListener
    private void playerBrokenEvent(PlayerBrokenEvent event) {
        CrossPlayerActor actor = event.getPlayer();
        Player player = actor.getVal(Player.PLAYER_IN_ACTOR_KEY);
        if (player == null) {
            actor.getSession().close(CloseCause.LOGOUT);
            return;
        }
        Room room = Room.get(player);
        if (room != null) {
            room.addMessage(r -> {
                r.getHandler().playerBrokenEvent(r, actor);
            });
        } else {
            // logic 服 拜访之类的.
            // event.getPlayer().getVal(Player.PLAYER_IN_ACTOR_KEY).getPosition().offline();
        }
    }
}
