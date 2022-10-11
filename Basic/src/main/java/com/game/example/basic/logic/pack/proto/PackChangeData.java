package com.game.example.basic.logic.pack.proto;

import com.baidu.bjf.remoting.protobuf.annotation.Packed;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.game.example.basic.logic.pack.enums.PackType;

import java.util.List;

/***
 * 背包变动
 */
@ProtobufClass(description = "背包数据")
public class PackChangeData {
	@Protobuf(description = "类型")
	private PackType packType;
	@Protobuf(description = "变动的道具")
	private List<PackItemTo> packItems;
	@Packed(value = false)
	@Protobuf(description = "删除的道具")
	private List<Integer> delItems;

	public static PackChangeData valueOf(PackType type, List<PackItemTo> itemList, List<Integer> delItems) {
		PackChangeData data = new PackChangeData();
		data.packItems = itemList;
		data.delItems = delItems;
		data.packType = type;
		return data;
	}

	public PackType getPackType() {
		return packType;
	}

	public void setPackType(PackType packType) {
		this.packType = packType;
	}

	public List<PackItemTo> getPackItems() {
		return packItems;
	}

	public void setPackItems(List<PackItemTo> packItems) {
		this.packItems = packItems;
	}

	public List<Integer> getDelItems() {
		return delItems;
	}

	public void setDelItems(List<Integer> delItems) {
		this.delItems = delItems;
	}
}
