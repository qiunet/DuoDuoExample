package com.game.example.basic.logic.resource.cfg;

import com.game.example.basic.logic.pack.enums.PackType;
import org.qiunet.cfg.annotation.Cfg;
import org.qiunet.cfg.base.ICfgCustomInit;
import org.qiunet.cfg.manager.base.ILoadSandbox;
import org.qiunet.flash.handler.common.player.PlayerActor;
import org.qiunet.function.base.IResourceCfg;
import org.qiunet.function.base.IResourceType;
import org.qiunet.function.reward.Rewards;

@Cfg("config/resource_data.json")
public class ResourceCfg implements IResourceCfg, ICfgCustomInit {

    private int resId;

    private String name;

	private Rewards<PlayerActor> reward;

    @Override
    public Integer getId() {
        return resId;
    }

    public int getResId() {
        return resId;
    }

    public String getName() {
        return name;
    }

    @Override
    public <Type extends Enum<Type> & IResourceType> Type type() {
        return (Type) PackType.RESOURCE;
    }

	@Override
	public void init(ILoadSandbox sandBox) {
	}
}
