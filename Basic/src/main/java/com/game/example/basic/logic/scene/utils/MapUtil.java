package com.game.example.basic.logic.scene.utils;

import org.qiunet.utils.exceptions.CustomException;
import org.qiunet.utils.string.StringUtil;

public class MapUtil {
	/**
	 * 得到一个RegionId.
	 * @param xID x 坐标除完区块长度后的序号
	 * @param zID z 坐标除完区块长度后的序号
	 */
	public static String buildRegionId(int xID, int zID) {
		return xID +"_"+ zID;
	}

	/**
	 * 拆分regionId
	 */
	public static Integer [] splitRegionId(String regionId) {
		String [] strings = StringUtil.split(regionId, "_");
		if (strings.length != 2) {
			throw new CustomException("{} not a regionId", regionId);
		}
		return StringUtil.conversion(strings, Integer.class);
	}

}
