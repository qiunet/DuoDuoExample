package com.game.example.basic.logic.game.room.proto;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.game.example.basic.logic.game.room.player.RoomPlayer;

@ProtobufClass
public class RoomPlayerTo {
	@Protobuf(description = "玩家ID")
	private long playerId;

	public static RoomPlayerTo valueOf(RoomPlayer roomPlayer){
		RoomPlayerTo to = new RoomPlayerTo();
		to.playerId = roomPlayer.getId();
		return to;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
}
