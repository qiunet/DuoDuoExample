package com.game.example.basic.logic.scene.domain;

import com.game.example.basic.logic.scene.object.MObject;
import com.game.example.basic.logic.scene.object.Player;
import com.game.example.basic.logic.scene.object.VisibleObject;
import com.google.common.collect.Maps;
import org.qiunet.flash.handler.context.request.data.IChannelData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;

/***
 * 视野人员
 */
public class KnowList<Owner extends VisibleObject<Owner>> {
	// 广播最大人数
	private static final int KNOW_LIST_BROADCAST_NUMBER = 50;
	private static final ThreadLocal<NumFilter> numFilter = ThreadLocal.withInitial(() -> new NumFilter(KNOW_LIST_BROADCAST_NUMBER));
	private static final ThreadLocal<Map<Long, VisibleObject>> objMap = ThreadLocal.withInitial(HashMap::new);
	private static final ThreadLocal<List<VisibleObject>> objList = ThreadLocal.withInitial(ArrayList::new);
	/**
	 * 所属对象
	 */
	private final Owner owner;
	/**
	 * 所属对象可见的玩家
	 */
	private final Map<Long, VisibleObject> knowList = Maps.newConcurrentMap();


	public KnowList(Owner owner) {
		this.owner = owner;
	}

	// 是否可见
	public boolean currentSee(long objectId) {
		return knowList.containsKey(objectId);
	}

	// 区块变更 导致视野变更.
	public void update() {
		Map<Long, VisibleObject> tempKnowListMap = objMap.get();

		// 重新计算可见视野
		owner.getPosition().getSceneInstance().calAroundRegionId(owner.getPosition(), mapRegion -> {
			if (mapRegion == null) {
				return;
			}
			mapRegion.walk(obj -> {
				if (obj == owner) {
					return;
				}
				// 在这个点我可见的对象
				tempKnowListMap.put(obj.getObjectId(), obj);
			});
		});

		// 处理视野消失逻辑
		List<VisibleObject> notSeeList = objList.get();
		knowList.forEach((id, obj) -> {	// 遍历原先的视野，如果新视野不存在原先可见即消失了
			if (! tempKnowListMap.containsKey(id)) {
				obj.getBehaviorController().notSee(owner);
				notSeeList.add(obj);
			}
		});
		if (!notSeeList.isEmpty()) {
			owner.getBehaviorController().notSee(notSeeList);
			notSeeList.clear();
		}

		// 处理视野新增逻辑
		List<VisibleObject> seeList = notSeeList;
		tempKnowListMap.forEach((id, obj) -> { // 遍历新视野，如果旧视野不存在即新进入
			if (!this.currentSee(id)) {
				obj.getBehaviorController().see(owner);
				seeList.add(obj);
			}
		});
		if (!seeList.isEmpty()) {
			owner.getBehaviorController().see(seeList);
		}
		tempKnowListMap.clear();
		seeList.clear();
	}

	// 删除
	public void del(long objectId) {
		knowList.remove(objectId);
	}
	// 加入
	public void add(VisibleObject creature) {
		knowList.put(creature.getObjectId(), creature);
	}

	// 获取
	public VisibleObject get(long objectId){
		return knowList.get(objectId);
	}

	public void walk(Consumer<VisibleObject> consumer) {
		this.walk(null, consumer);
	}

	public void walk(Predicate<VisibleObject> filter, Consumer<VisibleObject> consumer) {
		knowList.values().forEach(creature -> {
			if (filter == null || filter.test(creature)) {
				consumer.accept(creature);
			}
		});
	}

	// 清除所有
	public void clean(){
		knowList.clear();
	}

	// 广播
	public void broadcast(IChannelData channelData) {
		this.broadcast(channelData, false);
	}

	public void broadcast(IChannelData channelData, boolean kcp) {
		broadcast(null, channelData, kcp);
	}

	// 广播
	public void broadcast(Predicate<VisibleObject> filter, IChannelData channelData, boolean kcp) {
		walk(MObject::isPlayer, creature -> {
			if (filter != null && ! filter.test(creature)) {
				return;
			}
			if (!numFilter.get().test(creature)) {
				return;
			}
			if (kcp && ((Player) creature).isKcpSessionPrepare()) {
				((Player) creature).sendKcpMessage(channelData);
			}else {
				((Player) creature).sendMessage(channelData);
			}
		});
		numFilter.get().counter.set(0);
	}

	// 视野人数
	public int size(){
		return knowList.size();
	}

	// 根据数量过滤
	private static class NumFilter implements Predicate<VisibleObject> {
		private final AtomicInteger counter = new AtomicInteger();
		private final int num;
		public NumFilter(int num) {
			this.num = num;
		}
		@Override
		public boolean test(VisibleObject creature) {
			return counter.incrementAndGet() < num;
		}
	}
}
