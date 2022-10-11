package com.game.example.basic.logic.pack.entity;

import com.game.example.basic.logic.pack.domain.ItemStorage;
import com.game.example.basic.logic.pack.enums.PackType;
import org.qiunet.data.db.loader.DbEntityBo;
import org.qiunet.flash.handler.common.player.PlayerActor;

public class PackBo extends DbEntityBo<PackDo>{
	private final PackDo aDo;
	private PackType type;
	private ItemStorage itemStorage;

	public PackBo (PackDo aDo) {
		this.aDo = aDo;
		this.deserialize();
	}

	public PackDo getDo(){
		return aDo;
	}

	public PackType getType() {
		return type;
	}

	public ItemStorage getItemStorage(PlayerActor playerActor) {
		if (itemStorage == null) {
			itemStorage = new ItemStorage(playerActor, this);
		}
		return itemStorage;
	}

	@Override
	public void serialize() {
		if (itemStorage != null) {
			itemStorage.serialize();
		}
	}

	@Override
	public void deserialize() {
		this.type = PackType.parse(aDo.getType());
	}
}
