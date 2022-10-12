package com.game.example.basic.logic.game.room.proto;

import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

@ChannelData(ID = ProtocolID.Game.Room.ROOM_QUIT_REQ, desc = "退出房间请求")
public class RoomQuitReq extends IChannelData {
}
