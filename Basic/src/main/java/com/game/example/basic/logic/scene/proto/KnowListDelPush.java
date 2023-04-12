package com.game.example.basic.logic.scene.proto;

import com.baidu.bjf.remoting.protobuf.annotation.Packed;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

import java.util.List;

@ChannelData(ID = ProtocolID.Map.KNOW_LIST_DEL_PUSH, desc = "视野移出对象")
public class KnowListDelPush extends IChannelData {
	@Packed(value = false)
	@Protobuf(description = "消失的对象ID列表")
	private List<Long> objectIds;

	public static KnowListDelPush valueOf(List<Long> objectIds){
		KnowListDelPush data = new KnowListDelPush();
		data.objectIds = objectIds;
		return data;
	}

	public List<Long> getObjectIds() {
		return objectIds;
	}

	public void setObjectIds(List<Long> objectIds) {
		this.objectIds = objectIds;
	}
}
