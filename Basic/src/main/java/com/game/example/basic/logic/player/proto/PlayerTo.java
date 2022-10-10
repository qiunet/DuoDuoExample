package com.game.example.basic.logic.player.proto;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.game.example.basic.logic.player.entity.PlayerBo;
import com.game.example.basic.logic.scene.proto.ObjectTo;

/***
 * 玩家传输对象
 */
@ProtobufClass(description = "玩家详细数据. 一般都是自己数据下发用")
public class PlayerTo {
	@Protobuf(description = "玩家的objectTo对象")
	private ObjectTo objectTo;

	@Protobuf(description = "游戏普通货币", fieldType = FieldType.SFIXED64)
	private long m2;

	@Protobuf(description = "游戏人民币代币", fieldType = FieldType.SFIXED64)
	private long m1;

	public PlayerTo() {}

	public static PlayerTo valueOf(PlayerBo playerBo){
		PlayerTo playerTo = new PlayerTo();
		playerTo.objectTo = ObjectTo.valueOf(playerBo.getDo().getPlayer_id(), playerBo.getDo().getName());
		playerTo.m1 = playerBo.getDo().getM1();
		playerTo.m2 = playerBo.getDo().getM2();
		return playerTo;
	}

	public ObjectTo getObjectTo() {
		return objectTo;
	}

	public void setObjectTo(ObjectTo objectTo) {
		this.objectTo = objectTo;
	}

	public void setM2(long m2) {
		this.m2 = m2;
	}

	public long getM2() {
		return m2;
	}

	public long getM1() {
		return m1;
	}

	public void setM1(long m1) {
		this.m1 = m1;
	}
}
