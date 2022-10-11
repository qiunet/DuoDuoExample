package com.game.example.basic.logic.id_builder.enums;

import com.game.example.basic.logic.id_builder.entity.IdBuilderBo;
import com.game.example.basic.logic.id_builder.entity.IdBuilderDo;
import org.qiunet.data.db.loader.IPlayerDataLoader;

import java.util.Map;

/***
 * ID 生成
 */
public enum IDBuilderType {
	/**
	 * 物品
	 * 物品的id builder 只能item storage 使用
	 */
	ITEM(1),
	;

	private final int type;

	IDBuilderType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	/**
	 * 生成一个ID
	 * @param dataLoader
	 * @return
	 */
	public int makeId(IPlayerDataLoader dataLoader) {
		Map<Integer, IdBuilderBo> mapData = dataLoader.getMapData(IdBuilderBo.class);
		IdBuilderBo idBuilderBo = mapData.get(this.getType());
		if (idBuilderBo == null) {
			IdBuilderDo idBuilderDo = new IdBuilderDo(dataLoader.getId(), this.getType());
			idBuilderBo = dataLoader.insertDo(idBuilderDo);
		}
		return idBuilderBo.generatorId();
	}
}
