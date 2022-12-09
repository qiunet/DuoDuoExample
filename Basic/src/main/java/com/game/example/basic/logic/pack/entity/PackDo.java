package com.game.example.basic.logic.pack.entity;

import org.apache.ibatis.type.Alias;
import org.qiunet.data.core.enums.ColumnJdbcType;
import org.qiunet.data.core.support.db.Column;
import org.qiunet.data.core.support.db.Table;
import org.qiunet.data.db.entity.DbEntityList;
/**
*
*
* 对象为自动创建 不要修改
*/
@Alias("PackDo")
@Table(name = "pack", keyName = "player_id", subKeyName = "type", dbSource = "basic")
public class PackDo extends DbEntityList<Long, Integer> {
	@Column(comment = "玩家id", isKey = true)
	private long player_id;
	@Column(comment = "背包类型", isKey = true)
	private int type;
	@Column(comment = "背包的json数据", jdbcType = ColumnJdbcType.MEDIUMTEXT)
	private String data;

	/**默认的构造函数**/
	public PackDo(){}
	public PackDo(long player_id, int type){
		this.player_id = player_id;
		this.type = type;
	}

	public long getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(long player_id) {
		this.player_id = player_id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public Long key() {
		return player_id;
	}

	@Override
	public Integer subKey() {
		return type;
	}
}
