package com.game.example.basic.logic.scene.proto;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.game.example.basic.logic.scene.domain.Coordinate;
import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

@ChannelData(ID = ProtocolID.Map.TRANSPORT_REQ, desc="地图切换请求")
public class TransportReq extends IChannelData {
	@Protobuf(description = "场景ID")
	private String sceneId;
	@Protobuf(description = "场景目标位置，若是未传就使用配置的出生点位置，出生点位置也未配置时则是(0,0)位置")
	private Coordinate coordinate;

	public String getSceneId() {
		return sceneId;
	}

	public void setSceneId(String sceneId) {
		this.sceneId = sceneId;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
}
