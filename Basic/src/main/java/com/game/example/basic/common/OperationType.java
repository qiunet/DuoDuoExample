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

	OperationType(int type){
		this.type = type;
	}

	@Override
	public int getType() {
		return type;
	}
}
