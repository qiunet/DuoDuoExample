package com.game.example.basic.logic.game.room.proto;

import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

@ChannelData(ID = ProtocolID.Game.Room.CREATE_PRIVACY_ROOM_REQ, desc = "创建隐私房间请求")
public class CreatePrivacyRoomReq extends IChannelData {

}
