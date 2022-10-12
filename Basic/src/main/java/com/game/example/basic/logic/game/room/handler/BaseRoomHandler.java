package com.game.example.basic.logic.game.room.handler;

import com.game.example.basic.logic.game.room.Room;
import com.game.example.basic.logic.game.room.proto.RoomInfoPush;
import com.game.example.basic.logic.scene.object.Player;
import org.qiunet.cross.actor.CrossPlayerActor;
import org.qiunet.utils.logger.LoggerType;
import org.slf4j.Logger;

/***
 * 基础的房间服务
 */
public abstract class BaseRoomHandler implements IRoomHandler {
    protected final Logger logger = LoggerType.DUODUO_CROSS.getLogger();
    protected final String sceneId;

    public BaseRoomHandler(String sceneId) {
        this.sceneId = sceneId;
    }

    @Override
    public void playerBrokenEvent(Room room, CrossPlayerActor playerActor) {
        logger.debug("玩家[{}]退出跨服！！！", playerActor.getPlayerId());
        // 先推送房间状态.
        Player player = Player.PLAYER_IN_ACTOR_KEY.get(playerActor);
        // player.getPosition().offline();
    }

    @Override
    public void playerReconnect(Room room, Player player) {
        logger.debug("玩家[{}]重连跨服！！！", player.getId());
        // 场景下线
        // player.getPosition().offline();
        // 玩家重连房间，状态等信息不变，推送房间状态即可.
        player.sendMessage(RoomInfoPush.valueOf(room.getSceneId(), room.buildRoomData()));
        // 推送视野
        // player.getPosition().online();
    }
}
