package com.game.example.basic.logic.scene.controller;


import com.game.example.basic.logic.scene.object.Player;
import com.game.example.basic.logic.scene.object.VisibleObject;
import com.game.example.basic.logic.scene.observer.IPlayerLogout;
import com.game.example.basic.logic.scene.proto.KnowListAddPush;
import com.game.example.basic.logic.scene.proto.KnowListDelPush;
import com.game.example.basic.logic.scene.proto.ObjectTo;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/***
 * 玩家的行为控制器
 */
public class PlayerBehaviorController extends BehaviorController<Player> {

	public void see(List<VisibleObject> creatures) {
		super.see(creatures);

		List<ObjectTo> collect = creatures.stream()
				.map(c -> c.getBehaviorController().buildShowInfo())
				.filter(Objects::nonNull)
				.collect(Collectors.toList());

		owner.sendMessage(KnowListAddPush.valueOf(collect));
	}

	@Override
	public void notSee(List<VisibleObject> creatures) {
		super.notSee(creatures);

		List<Long> collect = creatures.stream()
				.mapToLong(VisibleObject::getObjectId).boxed()
				.collect(Collectors.toList());

		owner.sendMessage(KnowListDelPush.valueOf(collect));
	}

	@Override
	public ObjectTo buildShowInfo() {
		return ObjectTo.valueOf(owner);
	}

	// 登出
	@Override
	public void logout() {
		owner.getPosition().offline();
		owner.getObserverSupport().syncFire(IPlayerLogout.class, i -> i.logout(owner));
		owner.destroy();
	}
}
