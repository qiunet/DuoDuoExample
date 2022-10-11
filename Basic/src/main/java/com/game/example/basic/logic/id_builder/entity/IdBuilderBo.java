package com.game.example.basic.logic.id_builder.entity;

import org.qiunet.data.db.loader.DbEntityBo;

import java.util.concurrent.atomic.AtomicInteger;

public class IdBuilderBo extends DbEntityBo<IdBuilderDo> {
	private final IdBuilderDo aDo;
	private AtomicInteger generator;

	public IdBuilderBo(IdBuilderDo aDo) {
		this.aDo = aDo;
		this.deserialize();
	}

	public IdBuilderDo getDo(){
		return aDo;
	}

	public int generatorId(){
		int id = generator.incrementAndGet();
		this.update();
		return id;
	}

	@Override
	public void serialize() {
		aDo.setCurr_id(this.generator.get());
	}

	@Override
	public void deserialize() {
		this.generator = new AtomicInteger(aDo.getCurr_id());
	}
}
