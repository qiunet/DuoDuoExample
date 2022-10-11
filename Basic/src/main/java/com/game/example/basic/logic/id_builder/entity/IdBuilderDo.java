package com.game.example.basic.logic.id_builder.entity;

import org.apache.ibatis.type.Alias;
import org.qiunet.data.core.support.db.Column;
import org.qiunet.data.core.support.db.Table;
import org.qiunet.data.db.entity.DbEntityList;
/**
*, comment="个人相关的id生成表"
*
* 对象为自动创建 不要修改
*/
@Alias("IdBuilderDo")
@Table(name = "id_builder", comment="个人相关的id生成表", dbSource = "basic")
public class IdBuilderDo extends DbEntityList<Long, Integer> {
	@Column(comment = "玩家id", isKey = true)
	private long player_id;
	@Column(comment = "类型", isKey = true)
	private int type;
	@Column(comment = "当前的id值")
	private int curr_id;

	/**默认的构造函数**/
	public IdBuilderDo(){}
	public IdBuilderDo(long player_id, int type){
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

	public int getCurr_id() {
		return curr_id;
	}

	public void setCurr_id(int curr_id) {
		this.curr_id = curr_id;
	}

	@Override
	public Long key() {
		return player_id;
	}

	@Override
	public String keyFieldName() {
		return "player_id";
	}

	@Override
	public Integer subKey() {
		return type;
	}

	@Override
	public String subKeyFieldName() {
		return "type";
	}
}
