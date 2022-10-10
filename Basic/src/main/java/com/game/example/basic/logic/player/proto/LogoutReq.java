package com.game.example.basic.logic.player.proto;

import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

/***
 *
 * @author qiunet
 * 2021/11/5 09:26
 */
@ChannelData(ID = ProtocolID.Player.LOGOUT_REQ, desc = "登出请求")
public class LogoutReq extends IChannelData {
}
