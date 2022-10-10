package com.game.example.common.data;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Maps;
import org.qiunet.utils.json.JsonUtil;

import java.util.Map;

public class PlayerAvatarData {
	/**
	 * 当前选择的角色ID
	 */
	@JSONField(name = "id")
	private int currentId;
	/**
	 * 角色对应的捏脸数据
	 */
	private Map<Integer, AvatarInfo> map = Maps.newHashMap();

	public static PlayerAvatarData valueOf(AvatarInfo avatarInfo) {
		PlayerAvatarData data = new PlayerAvatarData();
		if (avatarInfo != null) {
			data.currentId = avatarInfo.getId();
			data.map.put(avatarInfo.getId(), avatarInfo);
		}
		return data;
	}

	public String serialize() {
		return JsonUtil.toJsonString(this);
	}

	/**
	 * 当前角色的虚拟形象数据
	 * @return 当前角色的虚拟形象数据
	 */
	public AvatarInfo current() {
		if(currentId == 0){
			return null;
		}

		return map.computeIfAbsent(currentId, k -> AvatarInfo.valueOf(currentId, null));
	}

	public int getCurrentId() {
		return currentId;
	}

	public void setCurrentId(int currentId) {
		this.currentId = currentId;
	}

	public Map<Integer, AvatarInfo> getMap() {
		return map;
	}

	public void setMap(Map<Integer, AvatarInfo> map) {
		this.map = map;
	}
}
