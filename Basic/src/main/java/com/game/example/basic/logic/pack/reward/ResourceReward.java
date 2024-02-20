package com.game.example.basic.logic.pack.reward;

import com.game.example.basic.logic.pack.domain.ItemStorage;
import com.game.example.basic.logic.pack.domain.PackItem;
import com.game.example.basic.logic.pack.enums.PackType;
import com.game.example.common.constants.GameStatus;
import org.qiunet.flash.handler.common.player.PlayerActor;
import org.qiunet.flash.handler.context.status.StatusResult;
import org.qiunet.function.reward.BaseCfgReward;
import org.qiunet.function.reward.BaseReward;
import org.qiunet.function.reward.RewardConfig;
import org.qiunet.function.reward.RewardContext;

import java.util.List;

public class ResourceReward extends BaseCfgReward<PlayerActor> {

	public ResourceReward(int resId, long value) {
		super(resId, value);
	}

	public ResourceReward(RewardConfig rewardConfig) {
		super(rewardConfig);
	}

	/**
	 * 校验
	 */
	@Override
	public StatusResult doVerify(RewardContext<PlayerActor> context) {
		ItemStorage itemStorage = PackType.getItemStorage(context.getPlayer(), getId());
		if (itemStorage.getType() != PackType.RESOURCE) {
			return StatusResult.valueOf(GameStatus.PARAMS_ERROR);
		}
		return StatusResult.SUCCESS;
	}

	/**
	 * 发放奖励
	 */
	@Override
	public void grant(RewardContext<PlayerActor> context) {
		ItemStorage itemStorage = PackType.getItemStorage(context.getPlayer(), getId());
		int realValue = (int) Math.max(0, getCount());
		List<PackItem> packItems = itemStorage.addItem(getId(), realValue);
		context.getRealRewards().addAll(packItems);
	}

	@Override
	protected BaseReward<PlayerActor> doCopy(int multi) {
		return null;
	}

}
