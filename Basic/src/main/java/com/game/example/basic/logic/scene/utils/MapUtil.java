package com.game.example.basic.logic.scene.utils;

import org.qiunet.utils.math.MathUtil;

public class MapUtil {
	/**
	 * 得到一个RegionId.
	 * @param xID x 坐标除完区块长度后的序号
	 * @param zID z 坐标除完区块长度后的序号
	 */
	public static long newBuildRegionId(int xID, int zID) {
		return MathUtil.buildVal(xID, zID);
	}

	/**
	 * 根据 {@link #newBuildRegionId(int, int)}的id. 得到xId
	 * @param id 值
	 * @return xId
	 */
	public static int calX(long id) {
		return MathUtil.calX(id);
	}
	/**
	 * 根据 {@link #newBuildRegionId(int, int)}的id. 得到zId
	 * @param id 值
	 * @return zId
	 */
	public static int calZ(long id) {
		return MathUtil.calY(id);
	}

}
