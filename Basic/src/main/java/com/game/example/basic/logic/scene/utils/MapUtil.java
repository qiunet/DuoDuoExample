package com.game.example.basic.logic.scene.utils;

public class MapUtil {
	/**
	 * 得到一个RegionId.
	 * @param xID x 坐标除完区块长度后的序号
	 * @param zID z 坐标除完区块长度后的序号
	 */
	public static long newBuildRegionId(int xID, int zID) {
		return (((long)xID) << Integer.SIZE) | (zID & 0xffffffffL);
	}

	/**
	 * 根据 {@link #newBuildRegionId(int, int)}的id. 得到xId
	 * @param id 值
	 * @return xId
	 */
	public static int calX(long id) {
		return (int) (id >> Integer.SIZE);
	}
	/**
	 * 根据 {@link #newBuildRegionId(int, int)}的id. 得到zId
	 * @param id 值
	 * @return zId
	 */
	public static int calZ(long id) {
		return (int) id;
	}

}
