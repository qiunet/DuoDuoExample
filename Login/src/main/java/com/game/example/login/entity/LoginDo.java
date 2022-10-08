package com.game.example.login.entity;

import org.apache.ibatis.type.Alias;
import org.qiunet.data.core.support.db.Column;
import org.qiunet.data.core.support.db.Table;
import org.qiunet.data.redis.entity.RedisEntity;

/**
 * 登录服登录信息
 */
@Alias("LoginDo")
@Table(name = "login", splitTable = true, dbSource = "login")
public class LoginDo extends RedisEntity<Long> {
	@Column(comment = "玩家游戏和平台ID", isKey = true)
	private long player_id;
	@Column(comment = "玩家平台token")
	private String token;
	@Column(comment = "大区ID")
	private int big_group;
	@Column(comment = "玩家所在服务器组")
	private int server_group;
	@Column(comment = "玩家游戏内部交互入场券")
	private String ticket;

	/**默认的构造函数**/
	public LoginDo(){}
	public LoginDo(long player_id){
		this.player_id = player_id;
	}

	public long getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(long player_id) {
		this.player_id = player_id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getBig_group() {
		return big_group;
	}

	public void setBig_group(int big_group) {
		this.big_group = big_group;
	}

	public int getServer_group() {
		return server_group;
	}

	public void setServer_group(int server_group) {
		this.server_group = server_group;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	@Override
	public Long key() {
		return player_id;
	}

	@Override
	public String keyFieldName() {
		return "player_id";
	}
}
