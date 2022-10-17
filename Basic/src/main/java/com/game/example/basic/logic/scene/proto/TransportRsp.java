package com.game.example.basic.logic.scene.proto;

import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

@ChannelData(ID = ProtocolID.Map.TRANSPORT_RSP, desc="地图切换响应")
public class TransportRsp extends IChannelData {
	public static TransportRsp valueOf() {
		return new TransportRsp();
	}
}
