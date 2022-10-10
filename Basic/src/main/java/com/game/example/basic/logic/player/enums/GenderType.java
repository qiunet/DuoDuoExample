package com.game.example.basic.logic.player.enums;

import com.baidu.bjf.remoting.protobuf.EnumReadable;

public enum GenderType implements EnumReadable {
	/**
	 * 无
	 */
	NONE(0),
	/**
	 * 男性
	 */
	MALE(1),
	/**
	 * 女性
	 */
	FEMALE(2),
	/**
	 * 通用
	 */
	COMMON(3)
	;

	private int value;
	GenderType(int value){
		this.value = value;
	}
	/**
	 * @return the value of Enum element
	 */
	@Override
	public int value() {
		return value;
	}
}
