package com.game.example.basic.logic.game.room.proto;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;
import org.qiunet.flash.handler.context.status.IGameStatus;

@ChannelData(ID = ProtocolID.Game.Room.JOIN_ROOM_FAIL_RSP, desc = "加入房间失败返回")
public class JoinRoomFailRsp extends IChannelData {
	@Protobuf(description = "错误状态，10:参数错误，8002001:房间ID不存在，8002002:房间当前满员，8002003:房间邀请码错误，8002004:房间场景不存在，8002005:进入房间失败")
	private int status;
	@Protobuf(description = "描述")
	private String desc;

	public static JoinRoomFailRsp valueOf(IGameStatus gameStatus){
		JoinRoomFailRsp rsp = new JoinRoomFailRsp();
		rsp.status = gameStatus.getStatus();
		rsp.desc = gameStatus.getDesc();
		return rsp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
