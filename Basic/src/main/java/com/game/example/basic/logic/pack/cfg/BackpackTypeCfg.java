package com.game.example.basic.logic.pack.cfg;

import com.game.example.basic.logic.pack.enums.PackType;
import org.qiunet.cfg.annotation.Cfg;
import org.qiunet.cfg.base.ISimpleMapCfg;

@Cfg("config/backpack_type.json")
public class BackpackTypeCfg implements ISimpleMapCfg<PackType> {

	private PackType type;

	private String name;

	private int capacity;

	public PackType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public int getCapacity() {
		return capacity;
	}

	@Override
	public PackType getId() {
		return type;
	}
}
