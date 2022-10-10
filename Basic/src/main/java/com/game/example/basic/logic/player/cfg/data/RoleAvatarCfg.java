package com.game.example.basic.logic.player.cfg.data;

import com.game.example.basic.logic.player.enums.GenderType;
import org.qiunet.cfg.annotation.Cfg;
import org.qiunet.cfg.base.ICfgCheck;
import org.qiunet.cfg.base.ISimpleMapCfg;
import org.qiunet.utils.collection.generics.StringList;

@Cfg("config/role_avatar.json")
public class RoleAvatarCfg implements ISimpleMapCfg<Integer>, ICfgCheck {
	/**
	 * ID
	 */
	private int id;
	/**
	 * 性别
	 */
	private GenderType gender;
	/**
	 * 裸体部位资源
	 */
	private StringList intPartRes;
	/**
	 * 裸体部位资源替换
	 */
	private StringList parts;

	@Override
	public Integer getId() {
		return id;
	}

	public GenderType getGender() {
		return gender;
	}

	public StringList getIntPartRes() {
		return intPartRes;
	}

	public StringList getParts() {
		return parts;
	}

	/**
	 * 检查配置
	 */
	@Override
	public void check() {
		// 忽略检查
	}
}
