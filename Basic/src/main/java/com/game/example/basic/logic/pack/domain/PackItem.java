package com.game.example.basic.logic.pack.domain;


import com.alibaba.fastjson2.annotation.JSONField;
import com.baidu.bjf.remoting.protobuf.annotation.Ignore;
import com.game.example.basic.logic.pack.enums.PackType;
import com.game.example.basic.logic.pack.proto.PackItemTo;
import org.qiunet.function.reward.IRealReward;

/***
 * 背包物品
 */
public class PackItem implements IRealReward {
	// 唯一id
	private int uid;
	// 资源id
	private int resId;
	// 数量
	private int num;

	@Ignore
	@JSONField(deserialize = false, serialize = false)
	transient ItemStorage itemStorage;

	/**
	 * 该构造方法仅 {@link ItemStorage#addItem(int, int)} 使用
	 */
	PackItem(int resId, int uid, int num) {
		this.resId = resId;
		this.uid = uid;
		this.num = num;
	}

	public PackItem() {}

	public int getResId() {
		return resId;
	}

	public int getUid() {
		return uid;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public PackType getPackType() {
		return itemStorage.getType();
	}

	/**
	 * 构造to
	 */
	public PackItemTo buildPackItemTo() {
		return PackItemTo.valueOf(this);
	}

	/**
	 * 直接修改PackItem 里面的非count字段.
	 * count修改走Consumes Rewards
	 */
	public void update() {
		itemStorage.updateItems.add(this.getUid());
		itemStorage.update();
	}

	@Override
	public int getCfgId() {
		return resId;
	}

	@Override
	public long getValue() {
		return num;
	}
}
