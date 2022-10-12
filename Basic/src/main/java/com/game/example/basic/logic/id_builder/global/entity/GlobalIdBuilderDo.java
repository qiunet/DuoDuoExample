package com.game.example.basic.logic.id_builder.global.entity;

import org.apache.ibatis.type.Alias;
import org.qiunet.data.core.support.db.Column;
import org.qiunet.data.core.support.db.Table;
import org.qiunet.data.db.entity.DbEntity;

/**
*, comment="全局id生成表"
*
* 对象为自动创建 不要修改
*/
@Alias("GlobalIdBuilderDo")
@Table(name = "global_id_builder", comment="全局id生成表", dbSource = "basic")
public class GlobalIdBuilderDo extends DbEntity<Integer> {
	@Column(comment = "类型", isKey = true)
	private int type;
	@Column(comment = "id值")
	private long id_val;

	/**默认的构造函数**/
	public GlobalIdBuilderDo(){}
	public GlobalIdBuilderDo(int type, long id_val){
		this.type = type;
		this.id_val = id_val;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getId_val() {
		return id_val;
	}

	public void setId_val(long id_val) {
		this.id_val = id_val;
	}

	@Override
	public Integer key() {
		return type;
	}

	@Override
	public String keyFieldName() {
		return "type";
	}
}
