package com.game.example.basic.logic.player.proto;

import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

/***
 * 玩家信息推送
 */
@ChannelData(ID = ProtocolID.Player.NEED_REGISTER_PUSH, desc="玩家需要注册推送")
public class NeedRegisterPush extends IChannelData {


}
