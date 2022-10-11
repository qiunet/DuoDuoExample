package com.game.example.basic.logic.pack.domain;

import com.alibaba.fastjson.JSON;
import com.game.example.basic.logic.id_builder.enums.IDBuilderType;
import com.game.example.basic.logic.pack.entity.PackBo;
import com.game.example.basic.logic.pack.enums.PackType;
import com.game.example.basic.logic.pack.proto.PackItemTo;
import com.google.common.collect.Sets;
import org.qiunet.flash.handler.common.player.PlayerActor;
import org.qiunet.utils.json.JsonUtil;
import org.qiunet.utils.string.StringUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/***
 * 背包存储
 */
public class ItemStorage {
	final Set<Integer> updateItems = Sets.newHashSet(); // 需要推送客户端有变化的uid列表
	private final Map<Integer, Integer> itemCounts;	// resId -> num
	private final Map<Integer, PackItem> items;	// uid -> packItem

	final PlayerActor playerActor;
	private final PackBo packBo;

	public ItemStorage(PlayerActor playerActor, PackBo packBo) {
		this.playerActor = playerActor;
		this.packBo = packBo;

		List<PackItem> list = Collections.emptyList();
		if (! StringUtil.isEmpty(packBo.getDo().getData())) {
			list = JSON.parseArray(packBo.getDo().getData(), PackItem.class, JsonUtil.DEFAULT_PARSER_CONFIG);
		}
		this.items = list.stream().peek(item -> item.itemStorage = this).collect(Collectors.toMap(PackItem::getUid, Function.identity()));
		this.itemCounts = this.items.values().stream().collect(Collectors.groupingBy(
				PackItem::getResId, Collectors.reducing(0, item -> (int)item.getValue(), Integer::sum))
		);
	}

	/**
	 * 获得物品数量
	 */
	public int getItemNum(int resId) {
		return itemCounts.getOrDefault(resId, 0);
	}

	/**
	 * 消耗物品
	 */
	public void consumeItem(int resId, int num) {
		if (getItemNum(resId) < num) {
			throw new RuntimeException("consume over!");
		}

		Iterator<PackItem> iterator = items.values().iterator();
		while (iterator.hasNext()) {
			if (num <= 0) {
				break;
			}
			PackItem item = iterator.next();
			if (item.getValue() > 0) {
				if (item.getValue() >= num) {
					item.setNum((int) (item.getValue() - num));
					num = 0;
				} else {
					num -= item.getValue();
					item.setNum(0);
				}
				if (item.getValue() == 0) {
					iterator.remove(); // 移除
					itemCounts.merge(item.getResId(), -num, Integer::sum);
					updateItems.add(item.getUid());
				}
			}
		}
	}

	/**
	 * 消耗物品
	 */
	public void consumeItem(int uid) {
		PackItem packItem = items.get(uid);
		if (packItem == null) {
			throw new NullPointerException(StringUtil.slf4jFormat("PackItem id: {} null!", uid));
		}
		items.remove(packItem.getUid());
		itemCounts.merge(packItem.getResId(), -1, Integer::sum);
		updateItems.add(packItem.getUid());
	}

	/**
	 * 获得指定背包物品
	 */
	public PackItem getPackItem(int uid) {
		return items.get(uid);
	}

	/**
	 * 增加道具
	 */
	public List<PackItem> addItem(int resId, int count) {
		List<PackItem> list = new ArrayList<>(count);
		itemCounts.merge(resId, count, Integer::sum);
		for (int i = 0; i < count; i++) {
			int uid = IDBuilderType.ITEM.makeId(playerActor);
			PackItem packItem = new PackItem(resId, uid, 1);
			items.put(packItem.getUid(), packItem);
			updateItems.add(packItem.getUid());
			packItem.itemStorage = this;
			list.add(packItem);
		}
		return list;
	}

	/**
	 * 添加一个 他人创建的 PackItem (用于以后交易)
	 */
	public PackItem addItem(PackItem packItem) {
		if (items.put(packItem.getCfgId(), packItem) == null){
			itemCounts.merge(packItem.getResId(), 1, Integer::sum);
			updateItems.add(packItem.getUid());
		};
		return packItem;
	}

	public PackType getType() {
		return packBo.getType();
	}

	/**
	 * 更新推送
	 */
	public void collectUpdate(List<PackItemTo> updateList, List<Integer> delList) {
		if (updateItems.isEmpty()) {
			return;
		}

		for (int uid : updateItems) {
			PackItem packItem = items.get(uid);
			if (packItem != null) {
				updateList.add(packItem.buildPackItemTo());
			}else {
				delList.add(uid);
			}
		}

		updateItems.clear();
		packBo.update();

	}

	/**
	 * 发送全量推送
	 */
	public List<PackItemTo> allItemTo() {
		return items.values().stream().map(PackItem::buildPackItemTo).collect(Collectors.toList());
	}

	/**
	 * 序列化数据
	 */
	public void update() {
		packBo.update();
	}

	public String serialize() {
		String jsonString = JsonUtil.toJsonString(items.values());
		packBo.getDo().setData(jsonString);
		return jsonString;
	}
}
