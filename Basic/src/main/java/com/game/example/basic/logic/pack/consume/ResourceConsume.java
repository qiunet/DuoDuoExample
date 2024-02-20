package com.game.example.basic.logic.pack.consume;

import com.game.example.basic.logic.pack.domain.ItemStorage;
import com.game.example.basic.logic.pack.enums.PackType;
import com.game.example.common.constants.GameStatus;
import org.qiunet.flash.handler.common.player.PlayerActor;
import org.qiunet.flash.handler.context.status.StatusResult;
import org.qiunet.function.consume.BaseCfgConsume;
import org.qiunet.function.consume.BaseConsume;
import org.qiunet.function.consume.ConsumeConfig;
import org.qiunet.function.consume.ConsumeContext;

public class ResourceConsume extends BaseCfgConsume<PlayerActor> {

    public ResourceConsume(int cfgId, long value) {
        super(cfgId, value);
    }

    public ResourceConsume(ConsumeConfig consumeConfig) {
        super(consumeConfig);
    }

    @Override
    protected StatusResult doVerify(ConsumeContext<PlayerActor> context) {
        ItemStorage itemStorage = PackType.getItemStorage(context.getObj(), getId());
        if (itemStorage.getType() != PackType.RESOURCE) {
            return StatusResult.valueOf(GameStatus.PARAMS_ERROR);
        }
        if (itemStorage.getItemNum(getId()) < getCount()) {
            return StatusResult.valueOf(GameStatus.PARAMS_ERROR);
        }
        return StatusResult.SUCCESS;
    }

    @Override
    protected void doConsume(ConsumeContext<PlayerActor> context) {
        ItemStorage itemStorage = PackType.getItemStorage(context.getObj(), getId());
        int realValue = (int) Math.max(0, getCount());
        itemStorage.consumeItem(getId(), realValue);
    }

    @Override
    protected BaseConsume<PlayerActor> doCopy(int multi) {
        return null;
    }
}
