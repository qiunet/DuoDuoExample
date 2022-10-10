package com.game.example.basic.logic.player.proto;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.game.example.basic.protocol.ProtocolID;
import org.qiunet.flash.handler.context.request.data.ChannelData;
import org.qiunet.flash.handler.context.request.data.IChannelData;

/***
 * 玩家信息推送
 */
@ChannelData(ID = ProtocolID.Player.PLAYER_DATA_PUSH, desc="玩家数据推送")
public class PlayerDataPush extends IChannelData {
	@Protobuf(description = "玩家的详细数据")
	private PlayerTo playerTo;

	public static PlayerDataPush valueOf(PlayerTo playerTo){
		PlayerDataPush data = new PlayerDataPush();
		data.playerTo = playerTo;
		return data;
	}

	public PlayerTo getPlayerTo() {
		return playerTo;
	}

	public void setPlayerTo(PlayerTo playerTo) {
		this.playerTo = playerTo;
	}
}
