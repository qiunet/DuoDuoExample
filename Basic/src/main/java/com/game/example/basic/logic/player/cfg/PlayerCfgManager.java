package com.game.example.basic.logic.player.cfg;

import com.game.example.basic.logic.player.cfg.data.RoleAvatarCfg;
import org.qiunet.cfg.event.CfgLoadCompleteEvent;
import org.qiunet.cfg.manager.base.ISimpleMapCfgWrapper;
import org.qiunet.utils.listener.event.EventListener;
import org.qiunet.utils.scanner.anno.AutoWired;

import java.util.Optional;

public enum PlayerCfgManager {
    instance;
    @AutoWired
    private ISimpleMapCfgWrapper<Integer, RoleAvatarCfg> roleAvatarMap;
    /**
     * 注册未上传角色信息时默认角色ID
     */
    private int defaultRoleId;

    @EventListener
    private void completeLoader(CfgLoadCompleteEvent data) {
        Optional<Integer> first = roleAvatarMap.list().stream()
                .map(RoleAvatarCfg::getId)
                .filter(id -> id > 0)
                .min(Integer::compare);
        this.defaultRoleId = first.orElse(0);
    }

    public RoleAvatarCfg getRoleAvatarCfg(int id){
        return roleAvatarMap.getCfgById(id);
    }

    public int getDefaultRoleId() {
        return defaultRoleId;
    }
}
