package com.game.example.cross;

import com.game.example.common.utils.redis.RedisGlobalUtil;
import org.qiunet.data.util.ServerConfig;
import org.qiunet.flash.handler.netty.server.BootstrapServer;
import org.qiunet.flash.handler.netty.server.hook.DefaultHook;
import org.qiunet.flash.handler.netty.server.hook.Hook;
import org.qiunet.utils.config.ConfigFileUtil;
import org.qiunet.utils.data.IKeyValueData;

public class CrossServerBootStrap {


	public static void main(String[] args) {
		Hook hook = new DefaultHook();

		String cmd = "start";
		if (args.length >= 1) cmd = args[args.length - 1];

		if ("start".equals(cmd)) {
			BootstrapServer
				.createBootstrap(hook, RedisGlobalUtil::getInstance, "com.game.example")
				.await();
			return;
		}
		IKeyValueData<Object, Object> keyValueData = ConfigFileUtil.loadConfig(ServerConfig.CONFIG_FILE_NAME);
		if ("stop".equals(cmd)) {
			BootstrapServer.sendHookMsg(keyValueData.getInt(ServerConfig.NODE_PORT), hook.getShutdownMsg());
		}else {
			BootstrapServer.sendHookMsg(keyValueData.getInt(ServerConfig.NODE_PORT), cmd);
		}
	}
}
