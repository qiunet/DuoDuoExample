package com.game.example.basic.logic.scene.proto;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import org.qiunet.flash.handler.util.proto.CommonModuleProto;

/***
 * 简单的玩家传输对象
 * 主要传输 其它 玩家的信息
 */
@CommonModuleProto
@ProtobufClass(description = "简单的玩家传输对象, 一般是查看其它玩家数据")
public class ObjectTo {
	@Protobuf(description = "对象id", fieldType = FieldType.SFIXED64)
	private long objectId;

	@Protobuf(description = "名称")
	private String name;

	public static ObjectTo valueOf(long objectId, String name) {
		ObjectTo data = new ObjectTo();
		data.objectId = objectId;
		data.name = name;
		return  data;
	}

	public long getObjectId() {
		return objectId;
	}

	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
