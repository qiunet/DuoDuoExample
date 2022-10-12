package com.game.example.basic.logic.game.room.proto;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

import java.util.List;

/***
 * 房间的数据
 **/
public class RoomData {
	@Protobuf(description = "房间id", fieldType = FieldType.SFIXED64)
	private long roomId;
	@Protobuf(description = "房间的所有玩家")
	private List<RoomPlayerTo> playerDataList;

	public static RoomData valueOf(long roomId, List<RoomPlayerTo> playerDataList){
		RoomData data = new RoomData();
		data.playerDataList = playerDataList;
		data.roomId = roomId;
		return data;
	}

	public List<RoomPlayerTo> getPlayerDataList() {
		return playerDataList;
	}

	public void setPlayerDataList(List<RoomPlayerTo> playerDataList) {
		this.playerDataList = playerDataList;
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}
}
