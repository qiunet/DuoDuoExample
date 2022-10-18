package com.game.example.basic.logic.game.room.domain;

import com.game.example.basic.logic.game.room.Room;
import com.game.example.common.utils.redis.RedisDataUtil;
import org.qiunet.cross.node.ServerNodeManager;
import org.qiunet.data.util.RedisMapUtil;

import java.util.Map;

/***
 * 房间信息
 */
public class RoomInfoData {

	public static final String ROOM_INFO_SET_REDIS_KEY = "GAME_PLAY_ROOM_INFO_SET";
	private static final String ROOM_INFO_DATA_REDIS_KEY_PREFIX = "GAME_PLAY_ROOM_INFO_DATA_";

	private long roomId;
	/**
	 * Room 人数
	 */
	private int playerNum;
	/**
	 * 房间所在服务器ID
	 */
	private int serverId;
	/**
	 * 私密房间
	 */
	private boolean privacy;
	/**
	 * 场景ID
	 */
	private String sceneId;

	private int maxPlayerLimit = 5;

	public RoomInfoData() {
	}

	public RoomInfoData(Room room, String sceneId, boolean privacy) {
		this.serverId = ServerNodeManager.getCurrServerId();
		this.playerNum = room.playerSize();
		this.privacy = privacy;
		this.roomId = room.getId();
		this.sceneId = sceneId;
	}

	/**
	 * 对房间人数进行调整.
	 * 正 加
	 * 负 减
	 */
	public synchronized void alterPlayerNum(int num) {
		RedisDataUtil.executorCommands(jedis -> {
			String roomInfoRedisKey = getRoomInfoDataRedisKey(roomId);
			if (jedis.exists(roomInfoRedisKey)) {
				Long playerNum = jedis.hincrBy(roomInfoRedisKey, "playerNum", num);
				if (playerNum >= maxPlayerLimit) {
					// 如果满员. 就移除出去.
					jedis.srem(ROOM_INFO_SET_REDIS_KEY, String.valueOf(this.roomId));
				}else {
					// 否则添加进来. 让人进入.
					if (!isPrivacy()) {
						jedis.sadd(ROOM_INFO_SET_REDIS_KEY, String.valueOf(this.roomId));
					}
				}
			}
			return null;
		});
	}

	/**
	 * 从redis 读取一个数据
	 * @param roomId	房间ID
	 */
	public static RoomInfoData readFromRedis(long roomId) {
		Map<String, String> stringMap = RedisDataUtil.jedis().hgetAll(getRoomInfoDataRedisKey(roomId));
		if(stringMap.isEmpty()){
			return null;
		}

		if (!stringMap.containsKey("roomId") || !stringMap.containsKey("serverId")) {
			RedisDataUtil.jedis().del(getRoomInfoDataRedisKey(roomId));
			return null;
		}
		return RedisMapUtil.toObj(stringMap, RoomInfoData.class);
	}

	//发送到redis
	public void sendToRedis() {
		Map<String, String> params = RedisMapUtil.toMap(this);
		RedisDataUtil.jedis().hmset(getRoomInfoDataRedisKey(roomId), params);

		if (! isPrivacy()) {
			RedisDataUtil.jedis().sadd(ROOM_INFO_SET_REDIS_KEY, String.valueOf(this.roomId));
		}
	}

	//清除房间信息
	public void remove() {
		RedisDataUtil.jedis().del(getRoomInfoDataRedisKey(roomId));
		if (! isPrivacy()) {
			RedisDataUtil.jedis().srem(ROOM_INFO_SET_REDIS_KEY, String.valueOf(this.roomId));
		}
	}

	private static String getRoomInfoDataRedisKey(long roomId) {
		return ROOM_INFO_DATA_REDIS_KEY_PREFIX + roomId;
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public int getPlayerNum() {
		return playerNum;
	}

	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public boolean isPrivacy() {
		return privacy;
	}

	public void setPrivacy(boolean privacy) {
		this.privacy = privacy;
	}

	public int getMaxPlayerLimit() {
		return maxPlayerLimit;
	}

	public void setMaxPlayerLimit(int maxPlayerLimit) {
		this.maxPlayerLimit = maxPlayerLimit;
	}

	public String getSceneId() {
		return sceneId;
	}

	public void setSceneId(String sceneId) {
		this.sceneId = sceneId;
	}
}
