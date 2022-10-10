package com.game.example.common.data;

import com.game.example.common.utils.redis.RedisDataUtil;
import org.qiunet.utils.json.JsonUtil;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/***
 * 玩家信息
 */
public class PlayerPlatformData {
	private static final String PLAYER_PLATFORM_DATA_KEY = "player_platform_data_key#";
	/**
	 * 玩家id
	 */
	private long playerId;
	/**
	 * 玩家平台token
	 */
	private String token;
	/**
	 * ticket
	 */
	private String ticket;
	/**
	 *
	 * 玩家当前进入的服务器id
	 */
	private int serverId;
	/**
	 * 玩家昵称
	 */
	private String name;
	/**
	 * 性别
	 */
	private int sex;

	public static PlayerPlatformData valueOf(long playerId, String token, int serverId, String ticket){
		PlayerPlatformData data = new PlayerPlatformData();
		data.playerId = playerId;
		data.token = token;
		data.name = String.valueOf(playerId);
		data.sex = 0;
		data.serverId = serverId;
		data.ticket = ticket;
		return data;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public void sendToRedis(){
		RedisDataUtil.jedis().setex(redisKey(this.playerId), TimeUnit.HOURS.toSeconds(24), JsonUtil.toJsonString(this));
	}

	/**
	 * 获得玩家的数据
	 * @param playerId
	 * @return
	 */
	public static PlayerPlatformData readData(long playerId) {
		return RedisDataUtil.getObject(redisKey(playerId), PlayerPlatformData.class);
	}

	public void extensionExpireTime() {
		RedisDataUtil.jedis().expire(redisKey(playerId), TimeUnit.HOURS.toSeconds(24));
	}

	public static String redisKey(long playerId) {
		return PLAYER_PLATFORM_DATA_KEY + playerId;
	}

	public void expire(String originTicket) {
		del(getPlayerId(), originTicket);
	}

	public static void del(long playerId) {
		RedisDataUtil.jedis().del(redisKey(playerId));
	}

	public static void del(long playerId, String originTicket) {
		PlayerPlatformData playerPlatformData = PlayerPlatformData.readData(playerId);
		if(playerPlatformData != null && !Objects.equals(playerPlatformData.getTicket(), originTicket)){
			return;
		}
		del(playerId);
	}
}
