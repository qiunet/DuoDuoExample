package com.game.example.basic.logic.pack.enums;

import com.baidu.bjf.remoting.protobuf.EnumReadable;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.game.example.basic.logic.pack.PackService;
import com.game.example.basic.logic.pack.consume.ResourceConsume;
import com.game.example.basic.logic.pack.domain.ItemStorage;
import com.game.example.basic.logic.pack.reward.ResourceReward;
import com.game.example.basic.logic.resource.ResourceManager;
import org.qiunet.flash.handler.common.player.PlayerActor;
import org.qiunet.function.base.IResourceCfg;
import org.qiunet.function.base.IResourceType;
import org.qiunet.function.consume.ConsumeConfig;
import org.qiunet.function.reward.RewardConfig;
import org.qiunet.utils.exceptions.EnumParseException;

/***
 * 玩家包裹类型
 */
public enum PackType implements EnumReadable, IResourceType {
	@Protobuf(description = "资源背包")
	RESOURCE(1, true) {
		@Override
		public ResourceConsume createConsume(ConsumeConfig consumeConfig) {
			return new ResourceConsume(consumeConfig);
		}

		@Override
		public ResourceReward createRewardItem(RewardConfig rewardConfig) {
			return new ResourceReward(rewardConfig);
		}
	}
	;

	private final int value;
	private final boolean haveStorage;

	PackType(int value, boolean haveStorage) {
		this.value = value;
		this.haveStorage = haveStorage;
	}

	public boolean haveStorage() {
		return haveStorage;
	}

	public int getValue() {
		return value;
	}

	/**
	 * 获取背包存储对象
	 */
	public ItemStorage getItemStorage(PlayerActor playerActor) {
		return PackService.instance.getItemStorage(playerActor, this);
	}

	public static ItemStorage getItemStorage(PlayerActor playerActor, int cfgId) {
		IResourceCfg resource = ResourceManager.instance.getResource(cfgId);
		return ((PackType) resource.type()).getItemStorage(playerActor);
	}

	public static final PackType [] values = values();

	public static PackType parse(int val){
		for(PackType t : values)
			if(t.getValue() == val)
				return t;

		throw new EnumParseException(val);
	}

	@Override
	public int value() {
		return value;
	}
}
