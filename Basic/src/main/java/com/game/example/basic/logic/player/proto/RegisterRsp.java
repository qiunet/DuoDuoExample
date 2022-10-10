package com.game.example.basic.logic.player.proto;

import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

/***
 * 响应注册
 */
@ChannelData(ID = ProtocolID.Player.REGISTER_RSP, desc = "注册")
public class RegisterRsp extends IChannelData {
	private boolean success;

	public static RegisterRsp valueOf(boolean success){
		RegisterRsp data = new RegisterRsp();
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
