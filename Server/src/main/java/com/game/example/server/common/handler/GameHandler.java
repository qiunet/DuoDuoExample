package com.game.example.server.common.handler;

import org.qiunet.flash.handler.common.player.PlayerActor;
import org.qiunet.flash.handler.context.request.data.IChannelData;
import org.qiunet.flash.handler.handler.persistconn.PersistConnPbHandler;

/**
 * 基础handler
 * 继承长连接ProtoBuf的处理器父类
 **/
public abstract class GameHandler<REQ extends IChannelData> extends PersistConnPbHandler<PlayerActor, REQ> {
}
