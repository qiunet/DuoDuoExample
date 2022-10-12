package com.game.example.basic.logic.game.room.proto;

import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;
import org.qiunet.flash.handler.context.request.data.InterestedChannelData;

@InterestedChannelData
@ChannelData(ID = ProtocolID.Game.Room.ROOM_DESTROY_PUSH, desc = "房间销毁推送")
public class RoomDestroyPush extends IChannelData {

	public static RoomDestroyPush valueOf(){
		return new RoomDestroyPush();
	}
}
