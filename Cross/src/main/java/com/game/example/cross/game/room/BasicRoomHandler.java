package com.game.example.cross.game.room;

import com.game.example.basic.logic.game.room.Room;
import com.game.example.basic.logic.scene.object.Player;
import com.game.example.common.constants.GameStatus;
import org.qiunet.cross.actor.CrossPlayerActor;
import org.qiunet.cross.common.handler.BaseTcpPbTransmitHandler;
import org.qiunet.flash.handler.common.player.PlayerActor;
import org.qiunet.flash.handler.context.request.data.IChannelData;
import org.qiunet.flash.handler.context.request.persistconn.IPersistConnRequest;
import org.qiunet.flash.handler.context.status.StatusResultException;

/***
 * 对房间游戏的玩家操作
 */
public abstract class BasicRoomHandler<REQ extends IChannelData> extends BaseTcpPbTransmitHandler<REQ> {

	@Override
	public final void handler(PlayerActor playerActor, IPersistConnRequest<REQ> context) throws Exception {
		// 不处理
		throw StatusResultException.valueOf(GameStatus.ROOM_OPERATION_NOT_IN_ROOM);
	}

	@Override
	public void crossHandler(CrossPlayerActor actor, REQ req) {
		Player player = actor.getVal(Player.PLAYER_IN_ACTOR_KEY);
		if (player == null) {
			throw StatusResultException.valueOf(GameStatus.ROOM_OPERATION_NOT_IN_SCENE);
		}

		Room room = Room.get(player);
		if (room == null) {
			throw StatusResultException.valueOf(GameStatus.ROOM_OPERATION_NOT_IN_ROOM);
		}

		this.crossHandler0(actor, room, req);
	}

	protected abstract void crossHandler0(CrossPlayerActor actor, Room room, REQ req);
}
