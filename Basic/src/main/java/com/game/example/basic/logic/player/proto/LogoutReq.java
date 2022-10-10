package com.game.example.basic.logic.player.proto;

import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

@ChannelData(ID = ProtocolID.Player.LOGOUT_REQ, desc = "登出请求")
public class LogoutReq extends IChannelData {

}
