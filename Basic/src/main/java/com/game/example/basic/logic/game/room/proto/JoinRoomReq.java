package com.game.example.basic.logic.game.room.proto;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

@ChannelData(ID = ProtocolID.Game.Room.JOIN_ROOM_REQ, desc = "参与指定ID房间请求")
public class JoinRoomReq extends IChannelData {
	@Protobuf(description = "房间ID", fieldType = FieldType.SFIXED64)
	private long roomID;

	public long getRoomID() {
		return roomID;
	}

	public void setRoomID(long roomID) {
		this.roomID = roomID;
	}
}
