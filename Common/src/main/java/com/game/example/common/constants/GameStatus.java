package com.game.example.common.constants;

import org.qiunet.flash.handler.context.status.IGameStatus;

public enum GameStatus implements IGameStatus {

	SERVER_ABSENT_NOW(1000001, "服务器当前繁忙"),

	;
	private final int status;
	private final String desc;

	GameStatus(int status, String desc) {
		this.status = status;
		this.desc = desc;
	}

	@Override
	public int getStatus() {
		return status;
	}

	@Override
	public String getDesc() {
		return desc;
	}
}
