package com.game.example.basic.logic.game.room.proto;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

@ChannelData(ID = ProtocolID.Game.Room.ROOM_QUIT_RSP, desc = "退出房间响应")
public class RoomQuitRsp extends IChannelData {
	@Protobuf(description = "是否退出成功")
	private boolean success;

	public static RoomQuitRsp valueOf(boolean success){
		RoomQuitRsp data = new RoomQuitRsp();
		data.success = success;
		return data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
