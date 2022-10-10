package com.game.example.basic.logic.player.proto;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.game.example.basic.logic.player.enums.GenderType;
import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

@ChannelData(ID = ProtocolID.Player.RANDOM_NAME_REQ, desc = "随机名字请求")
public class RandomNameReq extends IChannelData {
	@Protobuf(description = "角色性别 1：男，2：女")
	private GenderType gender;

	public GenderType getGender() {
		return gender;
	}

	public void setGender(GenderType gender) {
		this.gender = gender;
	}
}
