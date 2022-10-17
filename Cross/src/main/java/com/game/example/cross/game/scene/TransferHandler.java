package com.game.example.cross.game.scene;

import com.game.example.basic.logic.scene.domain.Coordinate;
import com.game.example.basic.logic.scene.object.Player;
import com.game.example.basic.logic.scene.proto.TransportReq;
import org.qiunet.cross.actor.CrossPlayerActor;
import org.qiunet.cross.common.handler.BaseTcpPbTransmitHandler;
import org.qiunet.flash.handler.common.player.AbstractUserActor;
import org.qiunet.flash.handler.common.player.PlayerActor;
import org.qiunet.flash.handler.context.request.persistconn.IPersistConnRequest;

/***
 * 传送
 */
public class TransferHandler extends BaseTcpPbTransmitHandler<TransportReq> {

	@Override
	public void handler(PlayerActor playerActor, IPersistConnRequest<TransportReq> context) throws Exception {

	}

	@Override
	public void crossHandler(CrossPlayerActor actor, TransportReq transportReq) {
		transfer(actor, transportReq);
	}

	private void transfer(AbstractUserActor actor, TransportReq transportReq){
		Coordinate coordinate = transportReq.getCoordinate();

		if(coordinate == null){
			coordinate = new Coordinate();
		} {
			// 临时写定1米半径范围
			coordinate = coordinate.rand(1);
		}

		Player player = actor.getVal(Player.PLAYER_IN_ACTOR_KEY);
		player.transfer(transportReq.getSceneId(), coordinate);
	}
}
