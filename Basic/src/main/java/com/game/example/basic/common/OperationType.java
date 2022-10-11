package com.game.example.basic.common;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import org.qiunet.function.base.IOperationType;

@ProtobufClass(description = "操作来源")
public enum OperationType implements IOperationType {
	@Protobuf(description = "无")
	NONE(0),
	@Protobuf(description = "GM")
	GM(1),
	;

	private final int type;
	/**
	 * 获得奖励是否推送奖励信息
	 * 非真实获得奖励的操作不需给客户端推送
	 */
	private final boolean rewardPush;
	OperationType(int type){
		this(type, true);
	}

	OperationType(int type, boolean rewardPush){
		this.type = type;
		this.rewardPush = rewardPush;
	}

	@Override
	public int getType() {
		return type;
	}

	/**
	 * 获得奖励是否推送奖励信息
	 * @return 获得奖励是否推送奖励信息
	 */
	public boolean isRewardPush() {
		return rewardPush;
	}
}
