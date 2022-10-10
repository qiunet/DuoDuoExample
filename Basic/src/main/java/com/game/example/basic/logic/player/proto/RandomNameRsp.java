package com.game.example.basic.logic.player.proto;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

@ChannelData(ID = ProtocolID.Player.RANDOM_NAME_RSP, desc = "随机名字返回")
public class RandomNameRsp extends IChannelData {
	@Protobuf(description = "随机名字")
	private String name;

	/**
	 * 创建一个随机名字返回命令
	 * @param name	随机名字
	 * @return	随机名字返回命令
	 */
	public static RandomNameRsp valueOf(String name){
		RandomNameRsp rsp = new RandomNameRsp();
		rsp.name = name;
		return rsp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
