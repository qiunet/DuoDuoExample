package com.game.example.basic.logic.player.proto;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

/***
 * 登录协议
 **/
@ChannelData(ID = ProtocolID.Player.LOGIN_REQ, desc = "登录请求协议")
public class LoginReq extends IChannelData {

	@Protobuf(description = "入场券")
	private String ticket;

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getTicket() {
		return ticket;
	}
}
