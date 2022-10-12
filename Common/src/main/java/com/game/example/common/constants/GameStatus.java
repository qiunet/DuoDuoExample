package com.game.example.common.constants;

import org.qiunet.flash.handler.context.status.IGameStatus;

public enum GameStatus implements IGameStatus {

	SERVER_ABSENT_NOW(1000001, "服务器当前繁忙"),

	LOGIN_TICKET_ERROR(1001001, "登录券错误"),
	LOGIN_SERVER_ERROR(1001002, "登录serverId错误"),
	LOGIN_ALREADY_REGISTER(1001003, "已经有注册了"),
	LOGIN_REQUEST_REPEATED(1001012, "重复请求LoginReq"),

	AVATAR_ROLE_ABSENT(5000010, "角色模型不存在"),

	ROOM_OPERATION_NOT_IN_SCENE(8001000, "当前不在场景!"),
	ROOM_OPERATION_NOT_IN_ROOM(8001001, "当前不在房间!"),
	ROOM_ENTER_BUT_IN_CROSS(8001002, "请求进入房间,但是在跨服状态"),

	ROOM_JOIN_NOT_EXIST(8002001, "房间ID不存在"),
	ROOM_JOIN_FAIL(8002005, "进入房间失败"),

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
