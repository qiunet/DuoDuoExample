package com.game.example.common.constants;

import org.qiunet.flash.handler.context.status.IGameStatus;

public enum GameStatus implements IGameStatus {

	SERVER_ABSENT_NOW(1000001, "服务器当前繁忙"),

	LOGIN_TICKET_ERROR(1001001, "登录券错误"),
	LOGIN_SERVER_ERROR(1001002, "登录serverId错误"),
	LOGIN_ALREADY_REGISTER(1001003, "已经有注册了"),
	LOGIN_REQUEST_REPEATED(1001012, "重复请求LoginReq"),

	AVATAR_ROLE_ABSENT(5000010, "角色模型不存在"),

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
