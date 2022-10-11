package com.game.example.basic.logic.resource;

import com.game.example.basic.logic.resource.cfg.ResourceCfg;
import org.qiunet.cfg.wrapper.ISimpleMapCfgWrapper;
import org.qiunet.utils.scanner.anno.AutoWired;

public enum ResourceManager {
    instance;

    @AutoWired
    private static ISimpleMapCfgWrapper<Integer, ResourceCfg> resourceMap;

    public ResourceCfg getResource(int resId) {
        return resourceMap.getCfgById(resId);
    }
}
