package com.game.example.basic.logic.scene.enums;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;

/***
 * 地图对象类型
 */
@ProtobufClass(description = "地图上对象类型")
public enum ObjectType {
	@Protobuf(description = "玩家")
	PLAYER,

	@Protobuf(description = "npc")
	NPC,

	@Protobuf(description = "robot")
	ROBOT,

	@Protobuf(description = "交互物体")
	INTERACT_ITEM,

	@Protobuf(description = "家园的房子建筑")
	HOME_BUILD

}
