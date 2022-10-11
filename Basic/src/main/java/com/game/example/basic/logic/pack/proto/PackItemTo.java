package com.game.example.basic.logic.pack.proto;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.game.example.basic.logic.pack.domain.PackItem;
import org.qiunet.flash.handler.util.proto.CommonModuleProto;

@CommonModuleProto
@ProtobufClass(description = "一个道具的描述")
public class PackItemTo {
	@Protobuf(description = "唯一ID")
	private int uid;
	@Protobuf(description = "内容ID")
	private int resId;

	public static PackItemTo valueOf(PackItem packItem) {
		PackItemTo data = new PackItemTo();
		data.resId = packItem.getResId();
		data.uid = packItem.getUid();
		return data;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getResId() {
		return resId;
	}

	public void setResId(int resId) {
		this.resId = resId;
	}
}
