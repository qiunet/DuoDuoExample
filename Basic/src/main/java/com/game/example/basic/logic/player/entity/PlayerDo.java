package com.game.example.basic.logic.player.entity;

import org.apache.ibatis.type.Alias;
import org.qiunet.data.core.enums.ColumnJdbcType;
import org.qiunet.data.core.support.db.Column;
import org.qiunet.data.core.support.db.Table;
import org.qiunet.data.db.entity.DbEntity;

/**
 * 玩家数据表
 */
@Alias("PlayerDo")
@Table(name = "player", keyName = "player_id", comment="玩家表", dbSource = "basic")
public class PlayerDo extends DbEntity<Long> {
	@Column(comment = "平台的玩家id", isKey = true)
	private long player_id;
	@Column(comment = "玩家平台token")
	private String token;
	@Column(comment = "登录服下发的ticket")
	private String ticket;
	@Column(comment = "玩家名称")
	private String name;
	@Column(comment = "玩家形象", jdbcType = ColumnJdbcType.MEDIUMTEXT)
	private String avatar;
	@Column(comment = "玩家普通货币")
	private long m1;
	@Column(comment = "玩家人民币代币")
	private long m2;
	@Column(comment = "注册时间戳（毫秒）")
	private long register_date;

	/**默认的构造函数**/
	public PlayerDo(){}
	public PlayerDo(long player_id){
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

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public long getM1() {
		return m1;
	}

	public void setM1(long m1) {
		this.m1 = m1;
	}

	public long getM2() {
		return m2;
	}

	public void setM2(long m2) {
		this.m2 = m2;
	}

	public long getRegister_date() {
		return register_date;
	}

	public void setRegister_date(long register_date) {
		this.register_date = register_date;
	}

	@Override
	public Long key() {
		return player_id;
	}
}
