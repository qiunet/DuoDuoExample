package com.game.example.cross.game.scene;

import com.game.example.basic.logic.game.room.Room;
import com.game.example.basic.logic.scene.domain.Coordinate;
import com.game.example.basic.logic.scene.object.Player;
import com.game.example.basic.logic.scene.proto.ScenePositionReq;
import com.game.example.basic.logic.scene.proto.ScenePositionRsp;
import com.google.common.base.Preconditions;
import org.qiunet.cross.actor.CrossPlayerActor;
import org.qiunet.cross.common.handler.BaseTcpPbTransmitHandler;
import org.qiunet.flash.handler.common.player.AbstractUserActor;
import org.qiunet.flash.handler.common.player.PlayerActor;
import org.qiunet.flash.handler.context.request.persistconn.IPersistConnRequest;

public class ScenePositionHandler extends BaseTcpPbTransmitHandler<ScenePositionReq> {

	@Override
	public void handler(PlayerActor playerActor, IPersistConnRequest<ScenePositionReq> context) throws Exception {

	}

	@Override
	public void crossHandler(CrossPlayerActor actor, ScenePositionReq resetPositionReq) {
		handler(actor, resetPositionReq);
	}

	private void handler(AbstractUserActor actor, ScenePositionReq req){
		Player player = actor.getVal(Player.PLAYER_IN_ACTOR_KEY);
		Preconditions.checkState(player != null && player.getPosition().isOnline());
		Room room = Room.get(player);
		if (room == null) {
			return;
		}
		Coordinate coordinate = new Coordinate(req.getX(), req.getY(), 0);
		player.getPosition().update(coordinate.getX(), coordinate.getY(), coordinate.getDir());

		player.sendMessage(ScenePositionRsp.valueOf(player.getPosition().getCurrPos()));
	}
}
