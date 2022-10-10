package com.game.example.basic.logic.pack.entity;

import org.qiunet.data.db.loader.DbEntityBo;

public class PackBo extends DbEntityBo<PackDo>{
	private final PackDo aDo;

	public PackBo (PackDo aDo) {
		this.aDo = aDo;
		this.deserialize();
	}

	public PackDo getDo(){
		return aDo;
	}

	@Override
	public void serialize() {

	}

	@Override
	public void deserialize() {

	}
}
