package com.game.example.basic.logic.game.room.proto;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

/***
 * 新玩家进入房间推送
 **/
@ChannelData(ID = ProtocolID.Game.Room.ROOM_PLAYER_ENTER_PUSH, desc = "新玩家进入房间推送")
public class RoomPlayerEnterPush extends IChannelData {
	@Protobuf(description = "进入的玩家信息")
	private RoomPlayerTo roomPlayerTo;

	public static RoomPlayerEnterPush valueOf(RoomPlayerTo roomPlayerTo) {
		RoomPlayerEnterPush data = new RoomPlayerEnterPush();
		data.roomPlayerTo = roomPlayerTo;
		return data;
	}

	public RoomPlayerTo getRoomPlayerTo() {
		return roomPlayerTo;
	}

	public void setRoomPlayerTo(RoomPlayerTo roomPlayerTo) {
		this.roomPlayerTo = roomPlayerTo;
	}
}
