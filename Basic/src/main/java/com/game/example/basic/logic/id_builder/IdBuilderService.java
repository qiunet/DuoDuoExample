package com.game.example.basic.logic.id_builder;

import com.game.example.basic.logic.id_builder.entity.IdBuilderBo;
import com.game.example.basic.logic.id_builder.entity.IdBuilderDo;
import org.qiunet.data.db.loader.DataLoader;
import org.qiunet.data.support.DbDataListSupport;

public enum IdBuilderService {
	instance;

	@DataLoader(IdBuilderBo.class)
	private final DbDataListSupport<Long, Integer, IdBuilderDo, IdBuilderBo> dataSupport = new DbDataListSupport<>(IdBuilderDo.class, IdBuilderBo::new);
}
