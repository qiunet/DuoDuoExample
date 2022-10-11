package com.game.example.basic.logic.pack;

import com.game.example.basic.logic.pack.domain.ItemStorage;
import com.game.example.basic.logic.pack.entity.PackBo;
import com.game.example.basic.logic.pack.entity.PackDo;
import com.game.example.basic.logic.pack.enums.PackType;
import com.game.example.basic.logic.pack.proto.*;
import com.google.common.collect.Lists;
import org.qiunet.data.db.loader.DataLoader;
import org.qiunet.data.support.DbDataListSupport;
import org.qiunet.flash.handler.common.IThreadSafe;
import org.qiunet.flash.handler.common.player.IPlayer;
import org.qiunet.flash.handler.common.player.PlayerActor;
import org.qiunet.function.consume.ConsumeEventData;
import org.qiunet.function.reward.GainRewardEventData;
import org.qiunet.utils.listener.event.EventListener;

import java.util.List;
import java.util.Map;

public enum PackService {
	instance;
	@DataLoader(PackBo.class)
	private final DbDataListSupport<Long, Integer, PackDo, PackBo> dataSupport = new DbDataListSupport<>(PackDo.class, PackBo::new);

	@EventListener
	private void consumeEvent(ConsumeEventData eventData) {
		IThreadSafe obj = eventData.getContext().getObj();
		if (obj instanceof PlayerActor) {
			this.collectPackUpdate(((PlayerActor) obj));
		}
	}
	@EventListener
	private void gainReward(GainRewardEventData eventData) {
		IPlayer player = eventData.getPlayer();
		if (player instanceof PlayerActor) {
			this.collectPackUpdate(((PlayerActor) player));
		}
	}

	/**
	 * 收集更新. 然后推送给客户端
	 */
	private void collectPackUpdate(PlayerActor playerActor) {
		List<PackChangeData> list = Lists.newArrayListWithCapacity(PackType.values.length);

		for (PackType packType : PackType.values) {
			if (! packType.haveStorage()) {
				continue;
			}
			List<PackItemTo> updateList = Lists.newArrayListWithCapacity(5);
			List<Integer> delList = Lists.newArrayListWithCapacity(5);

			getItemStorage(playerActor, packType).collectUpdate(updateList, delList);

			if(updateList.isEmpty() && delList.isEmpty()){
				continue;
			}

			list.add(PackChangeData.valueOf(packType, updateList, delList));
		}

		if (list.isEmpty()) {
			return;
		}

		playerActor.sendMessage(PackItemUpdatePush.valueOf(list));
	}


	/***
	 * 获得一个 ItemStorage 对象
	 * @param playerActor 获取对象的加载器
	 * @return PackBo bo对象
	 **/
	public ItemStorage getItemStorage(PlayerActor playerActor, PackType packType) {
		Map<Integer, PackBo> packBoMap = playerActor.getMapData(PackBo.class);
		PackBo packBo = packBoMap.get(packType.getValue());
		if(packBo == null){
			PackDo packDo = new PackDo(playerActor.getId(), packType.getValue());
			packBo = playerActor.insertDo(packDo);
		}
		return packBo.getItemStorage(playerActor);
	}
}
