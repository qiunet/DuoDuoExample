package com.game.example.basic.logic.game.room.proto;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

/***
 * 玩家离开房间推送
 **/
@ChannelData(ID = ProtocolID.Game.Room.ROOM_PLAYER_QUIT_PUSH, desc = "玩家离开房间推送")
public class RoomPlayerQuitPush extends IChannelData {
	@Protobuf(description = "退出玩家的ID", fieldType = FieldType.SFIXED64)
	private long playerId;

	public static RoomPlayerQuitPush valueOf(long playerId) {
		RoomPlayerQuitPush data = new RoomPlayerQuitPush();
		data.playerId = playerId;
		return data;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
}
