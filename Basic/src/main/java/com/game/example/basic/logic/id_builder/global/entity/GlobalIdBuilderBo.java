package com.game.example.basic.logic.id_builder.global.entity;

import org.qiunet.data.support.IEntityBo;

public class GlobalIdBuilderBo implements IEntityBo<GlobalIdBuilderDo>{
	private final GlobalIdBuilderDo aDo;

	public GlobalIdBuilderBo (GlobalIdBuilderDo aDo) {
		this.aDo = aDo;

		this.deserialize();
	}

	public GlobalIdBuilderDo getDo(){
		return aDo;
	}

	@Override
	public void serialize() {

	}

	@Override
	public void deserialize() {
	}
}
