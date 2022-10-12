package com.game.example.basic.logic.scene.enums;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;

/***
 * 地图对象类型
 */
@ProtobufClass(description = "地图上对象类型")
public enum ObjectType {
	/**
	 * 玩家
	 */
	@Protobuf(description = "玩家")
	PLAYER,
	/**
	 * npc
	 */
	@Protobuf(description = "npc")
	NPC,
	/**
	 * robot
	 */
	@Protobuf(description = "robot")
	ROBOT,
}
