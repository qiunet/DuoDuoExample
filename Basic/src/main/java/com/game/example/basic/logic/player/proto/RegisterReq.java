package com.game.example.basic.logic.player.proto;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

/***
 * 请求注册
 */
@ChannelData(ID = ProtocolID.Player.REGISTER_REQ, desc = "注册")
public class RegisterReq extends IChannelData {
	@Protobuf(description = "名字")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
