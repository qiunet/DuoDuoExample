package com.game.example.basic.logic.game.room.proto;

import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

@ChannelData(ID = ProtocolID.Game.Room.JOIN_PUBLIC_ROOM_REQ, desc = "参与公共房间请求")
public class JoinPublicRoomReq extends IChannelData {

}
