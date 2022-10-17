package com.game.example.basic.logic.scene.controller;

import com.game.example.basic.logic.scene.object.VisibleObject;
import com.game.example.basic.logic.scene.proto.ObjectTo;
import com.game.example.common.logger.GameLogger;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;

/***
 * 地图对象控制器
 */
public class BehaviorController<Owner extends VisibleObject<Owner>> {
	private static final Logger logger = GameLogger.COMM_LOGGER.getLogger();
	/***
	 * 对象
	 */
	protected Owner owner;

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	// 进入视野
	public void see(VisibleObject... creatures) {
		this.see(Arrays.asList(creatures));
	}

	public void see(List<VisibleObject> creatures) {
		for (VisibleObject creature : creatures) {
			if (logger.isInfoEnabled() && owner.isPlayer()) {
				logger.info("==Object id[{}] see Object id [{}]", owner.getObjectId(), creature.getObjectId());
			}
			owner.getKnowList().add(creature);
		}
	}

	// 从视野消失
	public void notSee(VisibleObject... creatures) {
		this.notSee(Arrays.asList(creatures));
	}

	public void notSee(List<VisibleObject> creatures) {
		for (VisibleObject creature : creatures) {
			if (logger.isInfoEnabled() && owner.isPlayer()) {
				logger.info("==Object id[{}] notSee Object id [{}]", owner.getObjectId(), creature.getObjectId());
			}
			owner.getKnowList().del(creature.getObjectId());
		}
	}

	/**
	 * 展示信息.
	 * 因为游戏简单. 所以用一个对象.
	 * 否则需要每个类型单独出来. 单发.
	 */
	public ObjectTo buildShowInfo() {
		return ObjectTo.valueOf(owner);
	}

	/**
	 * 登出
	 */
	public void logout() {
		this.owner.destroy();
	}
}
