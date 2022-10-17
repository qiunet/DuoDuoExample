package com.game.example.basic.logic.scene.proto;

import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

import java.util.List;

@ChannelData(ID = ProtocolID.Map.KNOW_LIST_ADD_PUSH, desc = "视野增加对象")
public class KnowListAddPush extends IChannelData {

	private List<ObjectTo> objectTos;

	public static KnowListAddPush valueOf(List<ObjectTo> objectTos){
		KnowListAddPush data = new KnowListAddPush();
		data.objectTos = objectTos;
		return data;
	}

	public List<ObjectTo> getObjectTos() {
		return objectTos;
	}

	public void setObjectTos(List<ObjectTo> objectTos) {
		this.objectTos = objectTos;
	}
}
