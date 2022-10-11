package com.game.example.basic.logic.pack.proto;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.game.example.basic.logic.pack.enums.PackType;

import java.util.List;

@ProtobufClass(description = "背包数据")
public class PackStorageData {
	@Protobuf(description = "类型")
	private PackType type;
	@Protobuf(description = "背包物品")
	private List<PackItemTo> itemList;

	public static PackStorageData valueOf(PackType type, List<PackItemTo> itemList) {
		PackStorageData data = new PackStorageData();
		data.itemList = itemList;
		data.type = type;
		return data;
	}

	public PackType getType() {
		return type;
	}

	public void setType(PackType type) {
		this.type = type;
	}

	public List<PackItemTo> getItemList() {
		return itemList;
	}

	public void setItemList(List<PackItemTo> itemList) {
		this.itemList = itemList;
	}
}
