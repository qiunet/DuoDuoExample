package com.game.example.basic.logic.game.room.proto;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

/***
 * 房间信息推送
 */
@ChannelData(ID = ProtocolID.Game.Room.ROOM_INFO_PUSH, desc = "房间信息推送")
public class RoomInfoPush extends IChannelData {
	@Protobuf(description = "场景")
	private String sceneId;

	@Protobuf(description = "房间数据")
	private RoomData roomData;

	public static RoomInfoPush valueOf(String sceneId, RoomData roomData) {
		RoomInfoPush data = new RoomInfoPush();
		data.sceneId = sceneId;
		data.roomData = roomData;
		return data;
	}

	public RoomData getRoomData() {
		return roomData;
	}

	public void setRoomData(RoomData roomData) {
		this.roomData = roomData;
	}

	public String getSceneId() {
		return sceneId;
	}

	public void setSceneId(String sceneId) {
		this.sceneId = sceneId;
	}
}

