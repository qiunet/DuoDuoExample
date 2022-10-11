package com.game.example.basic.logic.pack.consume;

import com.game.example.basic.logic.pack.domain.ItemStorage;
import com.game.example.basic.logic.pack.enums.PackType;
import com.game.example.common.constants.GameStatus;
import org.qiunet.flash.handler.common.player.PlayerActor;
import org.qiunet.flash.handler.context.status.StatusResult;
import org.qiunet.function.consume.BaseConsume;
import org.qiunet.function.consume.ConsumeConfig;
import org.qiunet.function.consume.ConsumeContext;

public class ResourceConsume extends BaseConsume<PlayerActor> {

    public ResourceConsume(int cfgId, long value) {
        super(cfgId, value);
    }

    public ResourceConsume(ConsumeConfig consumeConfig) {
        super(consumeConfig);
    }

    @Override
    protected StatusResult doVerify(ConsumeContext<PlayerActor> context) {
        ItemStorage itemStorage = PackType.getItemStorage(context.getObj(), cfgId);
        if (itemStorage.getType() != PackType.RESOURCE) {
            return StatusResult.valueOf(GameStatus.PARAMS_ERROR);
        }
        if (itemStorage.getItemNum(cfgId) < value) {
            return StatusResult.valueOf(GameStatus.PARAMS_ERROR);
        }
        return StatusResult.SUCCESS;
    }

    @Override
    protected void doConsume(ConsumeContext<PlayerActor> context) {
        ItemStorage itemStorage = PackType.getItemStorage(context.getObj(), cfgId);
        int realValue = (int) Math.max(0, value);
        itemStorage.consumeItem(cfgId, realValue);
    }

    @Override
    protected BaseConsume<PlayerActor> doCopy(int multi) {
        return null;
    }
}
