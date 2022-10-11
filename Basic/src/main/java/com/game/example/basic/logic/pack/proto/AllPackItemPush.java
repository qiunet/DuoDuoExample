package com.game.example.basic.logic.pack.proto;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

import java.util.List;

@ChannelData(ID = ProtocolID.Pack.ALL_ITEM_PUSH, desc = "所有道具推送")
public class AllPackItemPush extends IChannelData {
	@Protobuf(description = "道具列表.没有的类型不下发")
	private List<PackStorageData> packItems;

	public static AllPackItemPush valueOf(List<PackStorageData> packItems) {
		AllPackItemPush data = new AllPackItemPush();
		data.packItems = packItems;
		return data;
	}

	public List<PackStorageData> getPackItems() {
		return packItems;
	}

	public void setPackItems(List<PackStorageData> packItems) {
		this.packItems = packItems;
	}
}
