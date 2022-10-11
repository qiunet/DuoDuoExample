package com.game.example.basic.logic.resource.cfg;

import com.game.example.basic.logic.pack.enums.PackType;
import org.qiunet.cfg.annotation.Cfg;
import org.qiunet.cfg.base.ISimpleMapCfg;
import org.qiunet.function.base.IResourceCfg;
import org.qiunet.function.base.IResourceType;

@Cfg("config/resource_data.json")
public class ResourceCfg implements ISimpleMapCfg<Integer>, IResourceCfg {

    private int resId;

    private String name;

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
}
