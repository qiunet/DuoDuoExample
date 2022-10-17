package com.game.example.basic.logic.scene.proto;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.game.example.basic.logic.scene.domain.Coordinate;
import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

@ChannelData(ID = ProtocolID.Map.SCENE_CHANGE_PUSH, desc="场景切换推送")
public class SceneChangePush extends IChannelData {
	@Protobuf(description = "新的场景ID")
	private String newSceneId;
	@Protobuf(description = "坐标以及朝向")
	private Coordinate coordinate;

	public static SceneChangePush valueOf(String newSceneId, Coordinate coordinate){
		SceneChangePush data = new SceneChangePush();
		data.newSceneId = newSceneId;
		data.coordinate = coordinate;
		return data;
	}

	public String getNewSceneId() {
		return newSceneId;
	}

	public void setNewSceneId(String newSceneId) {
		this.newSceneId = newSceneId;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
}
