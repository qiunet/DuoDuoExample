package com.game.example.basic.logic.player.proto;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

/***
 * 登录响应
 **/
@ChannelData(ID = ProtocolID.Player.LOGIN_RSP, desc = "登录响应协议")
public class LoginRsp extends IChannelData {

	@Protobuf(description = "需要注册")
	private boolean needRegister;

	@Protobuf(description = "玩家ID", fieldType = FieldType.SFIXED64)
	private long playerId;

	@Protobuf(description = "是否是重连")
	private boolean reconnect;

	public static LoginRsp valueOf(boolean needRegister, long playerId, boolean reconnect){
		LoginRsp data = new LoginRsp();
		data.needRegister = needRegister;
		data.reconnect = reconnect;
		data.playerId = playerId;
		return data;
	}

	public boolean isReconnect() {
		return reconnect;
	}

	public void setReconnect(boolean reconnect) {
		this.reconnect = reconnect;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public boolean isNeedRegister() {
		return needRegister;
	}

	public void setNeedRegister(boolean needRegister) {
		this.needRegister = needRegister;
	}
}
