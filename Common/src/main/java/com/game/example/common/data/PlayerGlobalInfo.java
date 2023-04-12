package com.game.example.common.data;

import com.alibaba.fastjson2.annotation.JSONField;
import com.game.example.common.utils.redis.RedisGlobalUtil;
import org.qiunet.utils.date.DateUtil;
import org.qiunet.utils.exceptions.CustomException;
import org.qiunet.utils.json.JsonUtil;
import redis.clients.jedis.params.SetParams;

/***
 * 玩家的全服数据
 * 确保全球用户可以获取
 * 主要是好友间状态的同步 查看使用. 大批量的直接查询不现实. 使用Redis的mget比较好点.
 */
public class PlayerGlobalInfo {
	private static final String PLAYER_GLOBAL_INFO_KEY = "PLAYER_GLOBAL_INFO_";
	/**
	 * 玩家名称
	 */
	@JSONField(name = "na")
	private String name;
	/**
	 * 头像
	 */
	@JSONField(name = "ic")
	private String icon;
	/**
	 * 简介
	 */
	@JSONField(name = "in")
	private String intro;
	/**
	 * 玩家ID
	 */
	@JSONField(serialize = false, deserialize = false)
	private long playerId;
	/**
	 * 最后登录时间 秒
	 */
	@JSONField(name = "ld")
	private long lastLoginDt;
	/**
	 * 是否在线.
	 * 不在线. 说明离线数据在哪个服务器.
	 * 玩家登录时候, 需要查看key是否存在. 有offline数据时候.
	 * 需要发送清除离线数据事件
	 */
	@JSONField(name = "ol")
	private boolean offline;
	/**
	 * 虚拟形象地址
	 */
	@JSONField(name = "av")
	private String avatar;

	public void setName(String name) {
		this.name = name;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		// 特殊处理. 因为显示只需要几个字符. 这里截取一部分.
		if (intro != null && intro.length() > 10) {
			this.intro = intro.substring(0, 10);
		}else {
			this.intro = intro;
		}
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public long getLastLoginDt() {
		return lastLoginDt;
	}

	public void setLastLoginDt(long lastLoginDt) {
		this.lastLoginDt = lastLoginDt;
	}

	public String getName() {
		return name;
	}
	/**
	 * 设置是否在线
	 */
	public PlayerGlobalInfo setOffline(boolean offline) {
		this.offline = offline;
		return this;
	}

	public boolean isOffline() {
		return offline;
	}

	public long getPlayerId() {
		return playerId;
	}
	/**
	 * 获得redis Key
	 */
	private static String getRedisKey(long playerId) {
		if (playerId == 0) {
			throw new CustomException("playerId can not be zero");
		}
		return PLAYER_GLOBAL_INFO_KEY + playerId;
	}

	/**
	 * 失效用户的server存储信息
	 * 目前设计不失效对象. 有最后登录时间
	 */
	private static void expire(long playerId) {
		RedisGlobalUtil.jedis().del(getRedisKey(playerId));
	}

	/**
	 * 是否存在
	 */
	public static boolean exist(long playerId) {
		return RedisGlobalUtil.jedis().exists(getRedisKey(playerId));
	}

	/**
	 * 删除数据，游戏中不会使用，仅用于gm等用户管理用处
	 */
	public static boolean delete(long playerId) {
		return RedisGlobalUtil.jedis().del(getRedisKey(playerId)) > 0;
	}
	/**
	 * 从redis 读取该对象
	 * @return 没有 返回null
	 */
	public static PlayerGlobalInfo readFromRedis(long playerId) {
		String json = RedisGlobalUtil.jedis().get(getRedisKey(playerId));
		PlayerGlobalInfo playerGlobalInfo = JsonUtil.getGeneralObj(json, PlayerGlobalInfo.class);
		if (playerGlobalInfo != null) {
			playerGlobalInfo.setPlayerId(playerId);
		}
		return playerGlobalInfo;
	}
	/**
	 * 更新到redis
	 */
	public void sendToRedis(){
		// 失效后. 如果上线. 或者 被人访问. 会重新加载到redis. 不然redis 沉淀数据会很多
		RedisGlobalUtil.jedis().set(getRedisKey(playerId), toJsonString(), SetParams.setParams().ex(30L * DateUtil.DAY_SECONDS));
	}

	public String toJsonString(){
		return JsonUtil.toJsonString(this);
	}

	/**
	 * 更改名称
	 */
	public void alterName(String name){
		this.setName(name);
		sendToRedis();
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * 修改头像
	 */
	public void alterIcon(String icon) {
		this.setIcon(icon);
		this.sendToRedis();
	}
}
