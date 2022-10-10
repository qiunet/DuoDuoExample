package com.game.example.basic.logic.player.entity;

import org.qiunet.data.db.loader.DbEntityBo;

public class PlayerBo extends DbEntityBo<PlayerDo> {
	private final PlayerDo aDo;

	public PlayerBo (PlayerDo aDo) {
		this.aDo = aDo;
		this.deserialize();
	}

	public PlayerDo getDo(){
		return aDo;
	}

	@Override
	public void serialize() {

	}

	@Override
	public void deserialize() {

	}

	public void updateAvatar(){

	}

}
