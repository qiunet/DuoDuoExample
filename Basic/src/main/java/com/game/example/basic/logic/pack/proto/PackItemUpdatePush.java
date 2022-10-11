package com.game.example.basic.logic.pack.proto;

import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

import java.util.List;

@ChannelData(ID = ProtocolID.Pack.ITEM_CHANGE_PUSH, desc = "道具变动更新推送")
public class PackItemUpdatePush extends IChannelData {
	private List<PackChangeData> updateList;

	public static PackItemUpdatePush valueOf(List<PackChangeData> updateList) {
		PackItemUpdatePush data = new PackItemUpdatePush();
		data.updateList = updateList;
		return data;
	}

	public List<PackChangeData> getUpdateList() {
		return updateList;
	}

	public void setUpdateList(List<PackChangeData> updateList) {
		this.updateList = updateList;
	}
}
