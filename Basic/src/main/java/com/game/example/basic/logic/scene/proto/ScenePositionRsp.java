package com.game.example.basic.logic.scene.proto;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.game.example.basic.logic.scene.domain.Coordinate;
import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

@ChannelData(ID = ProtocolID.Map.SCENE_POSITION_RSP, desc = "到地图指定位置返回")
public class ScenePositionRsp extends IChannelData {
	@Protobuf(description = "是否成功")
	private boolean success;
	@Protobuf(description = "坐标以及朝向")
	private Coordinate coordinate;

	public static ScenePositionRsp valueOf(Coordinate coordinate){
		ScenePositionRsp rsp = new ScenePositionRsp();
		rsp.success = true;
		rsp.coordinate = coordinate;
		return rsp;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
}
