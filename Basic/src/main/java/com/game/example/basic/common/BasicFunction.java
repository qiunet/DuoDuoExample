package com.game.example.basic.common;

import org.qiunet.function.attr.enums.IAttrEnum;
import org.qiunet.function.base.basic.IBasicFunction;

/***
 * 基础功能提供
 */
public class BasicFunction implements IBasicFunction {

	@Override
	public <Type extends Enum<Type> & IAttrEnum<Type>> Type parse(String attrName) {
		return null;
	}
}
