package com.game.example.common.logger;

import org.qiunet.utils.logger.ILoggerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * 游戏日志类型
 * 可以根据需求划分类型打印
 **/
public enum GameLogger implements ILoggerType {
	/**
	 * 通用日志.
	 */
	COMM_LOGGER("CommLogger"),
	/**
	 * 游戏Handler里面的日志
	 */
	GAME_HANDLER("GameHandler"),
	/**
	 * 游戏监测日志
	 */
	GAME_MONITOR("GameMonitor"),
	/**
	 * 游戏房间日志
	 */
	GAME_ROOM("GameRoom"),
	/**
	 * 游戏中Oss日志
	 */
	GAME_OSS("GameOss"),
	/**
	 * 游戏平台交互日志
	 */
	GAME_PLATFORM("GamePlatform"),
	/**
	 * 后台服日志
	 */
	GAME_BACKSTAGE("GameBackStage"),
	/**
	 * sls日志
	 */
	GAME_SLS_LOG("GameSlsLog"),
	;

    private final String loggerName;

	GameLogger(String loggerName) {
		this.loggerName = loggerName;
	}

	@Override
	public Logger getLogger() {
		return LoggerFactory.getLogger(loggerName);
	}
}
