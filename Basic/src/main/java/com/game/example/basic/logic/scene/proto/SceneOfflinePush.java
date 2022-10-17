package com.game.example.basic.logic.scene.proto;

import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

@ChannelData(ID = ProtocolID.Map.SCENE_OFFLINE_PUSH, desc="场景下线推送")
public class SceneOfflinePush extends IChannelData {

	public static SceneOfflinePush valueOf(){
		return new SceneOfflinePush();
	}


}
