package com.game.example.basic.gm;

import com.game.example.basic.common.OperationType;
import com.game.example.basic.logic.resource.ResourceManager;
import com.game.example.basic.logic.resource.cfg.ResourceCfg;
import com.game.example.common.constants.GameStatus;
import org.apache.commons.lang3.StringUtils;
import org.qiunet.flash.handler.common.player.PlayerActor;
import org.qiunet.flash.handler.context.status.IGameStatus;
import org.qiunet.function.gm.GmCommand;
import org.qiunet.function.gm.GmParamDesc;
import org.qiunet.function.reward.RewardContext;
import org.qiunet.function.reward.Rewards;
import org.qiunet.utils.string.StringUtil;

/**
 * 增加背包物品gm命令
 */
public class GmAddPackItemCommand {

	@GmCommand(commandName = "增加背包物品")
	public IGameStatus addPackItem(PlayerActor player,
   		@GmParamDesc(value = "增加背包物品", example = "资源ID_数量;...") String items) {
		if(StringUtil.isEmpty(items))
			return GameStatus.FAIL;

		String[] strings = StringUtils.split(items, ";");
		Rewards<PlayerActor> rewards = new Rewards<>();
		for (String string : strings) {
			if (StringUtil.isEmpty(string)) {
				continue;
			}

			String[] integers = StringUtil.split(string, "_");
			if (integers.length == 2) {
				ResourceCfg resource = ResourceManager.instance.getResource(Integer.parseInt(integers[0]));
				rewards.addRewardItem(resource.getResId(), Integer.parseInt(integers[1]));
			}
		}

		RewardContext<PlayerActor> rewardContext = rewards.verify(player, OperationType.GM);
		if(rewardContext.isSuccess()){
			rewardContext.grant();
		}
		return GameStatus.SUCCESS;
	}
}
