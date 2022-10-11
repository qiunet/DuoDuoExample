package com.game.example.basic.gm;

import com.game.example.basic.common.OperationType;
import com.game.example.basic.logic.resource.ResourceManager;
import com.game.example.basic.logic.resource.cfg.ResourceCfg;
import com.game.example.common.constants.GameStatus;
import org.apache.commons.lang3.StringUtils;
import org.qiunet.flash.handler.common.player.PlayerActor;
import org.qiunet.flash.handler.context.status.IGameStatus;
import org.qiunet.function.consume.ConsumeContext;
import org.qiunet.function.consume.Consumes;
import org.qiunet.function.gm.GmCommand;
import org.qiunet.function.gm.GmParamDesc;
import org.qiunet.utils.string.StringUtil;

/**
 * 消耗背包物品gm命令
 */
public class GmCostPackItemCommand {

	@GmCommand(commandName = "消耗背包物品")
	public IGameStatus costPackItem(PlayerActor player,
   		@GmParamDesc(value = "消耗背包物品", example = "资源ID_数量;...") String items
	) {
		if(StringUtil.isEmpty(items))
			return GameStatus.FAIL;

		String[] strings = StringUtils.split(items, ";");
		Consumes<PlayerActor> consumes = new Consumes<>();
		for (String string : strings) {
			if (StringUtil.isEmpty(string)) {
				continue;
			}

			String[] integers = StringUtil.split(string, "_");
			if (integers.length == 2) {
				ResourceCfg resource = ResourceManager.instance.getResource(Integer.parseInt(integers[0]));
				consumes.addConsume(resource.getResId(), Integer.parseInt(integers[1]));
			}
		}

		ConsumeContext<PlayerActor> consumeCtx = consumes.verify(player, OperationType.GM);
		if(consumeCtx.isSuccess()){
			consumeCtx.act();
		}
		return GameStatus.SUCCESS;
	}
}
