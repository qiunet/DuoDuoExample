package com.game.example.basic.logic.pack;

import com.game.example.basic.logic.pack.cfg.BackpackTypeCfg;
import com.game.example.basic.logic.pack.enums.PackType;
import org.qiunet.cfg.manager.base.ISimpleMapCfgWrapper;
import org.qiunet.utils.scanner.anno.AutoWired;

public enum PackManager {
	instance;

	@AutoWired
	private static ISimpleMapCfgWrapper<PackType, BackpackTypeCfg> backPackTypeCfgs;

	/**
	 * 类型相关配置
	 */
	public BackpackTypeCfg typeCfg(PackType packType) {
		return backPackTypeCfgs.getCfgById(packType);
	}
	/**
	 * 获得背包类型容量
	 */
	public int capacity(PackType packType) {
		return typeCfg(packType).getCapacity();
	}
}
