package com.game.example.server.handler.player;

import com.game.example.basic.logic.player.proto.RandomNameReq;
import com.game.example.basic.logic.player.proto.RandomNameRsp;
import com.game.example.common.constants.GameStatus;
import com.game.example.server.common.handler.GameHandler;
import org.qiunet.flash.handler.common.player.PlayerActor;
import org.qiunet.flash.handler.context.request.persistconn.IPersistConnRequest;
import org.qiunet.flash.handler.context.status.StatusResultException;
import org.qiunet.utils.string.StringUtil;

/**
 * 获取随机名字
 */
public class RandomNameHandler extends GameHandler<RandomNameReq> {
	@Override
	public void handler(PlayerActor playerActor, IPersistConnRequest<RandomNameReq> context) throws Exception {
		RandomNameReq requestData = context.getRequestData();
		if (requestData.getGender() == null) {
			throw StatusResultException.valueOf(GameStatus.PARAMS_ERROR);
		}

		String name = StringUtil.randomString(6);
		if (StringUtil.isEmpty(name)) {
			return;
		}

		playerActor.sendMessage(RandomNameRsp.valueOf(name));
	}

	@Override
	public boolean needAuth() {
		return false;
	}
}
