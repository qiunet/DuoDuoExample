package com.game.example.basic.logic.game.room.player;

import com.game.example.basic.logic.scene.object.MovableObject;
import com.game.example.basic.logic.scene.object.Player;
import org.qiunet.flash.handler.context.session.IPlayerSender;
import org.qiunet.flash.handler.context.session.ISession;

/**
 * 房间的玩家对象
 * 保存玩家在房间中的一些状态，如加入房间时间、房间游戏状态等
 *
 * @param player 玩家跨服对象
 */
public record RoomPlayer(MovableObject player) implements IPlayerSender {

	public long getId() {
		return player.getId();
	}

	/**
	 * 是否不是自己
	 */
	public boolean isNotSelf(long playerId) {
		return !isSelf(playerId);
	}

	/**
	 * 是否是自己
	 */
	public boolean isSelf(long playerId) {
		return player.getId() == playerId;
	}

	@Override
	public ISession getSession() {
		return player.isPlayer() ? ((Player) player).getSession() : null;
	}

	@Override
	public ISession getKcpSession() {
		return player.isPlayer() ? ((Player) player).getKcpSession() : null;
	}
}
