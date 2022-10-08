package com.game.example.login.entity;

import org.qiunet.data.support.IEntityBo;

public class LoginBo implements IEntityBo<LoginDo>{
	private final LoginDo aDo;

	public LoginBo (LoginDo aDo) {
		this.aDo = aDo;
		this.deserialize();
	}

	public LoginDo getDo(){
		return aDo;
	}

	@Override
	public void serialize() {

	}

	@Override
	public void deserialize() {

	}
}
