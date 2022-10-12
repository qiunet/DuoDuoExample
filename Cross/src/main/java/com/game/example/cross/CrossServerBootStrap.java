package com.game.example.cross;

import com.game.example.common.utils.redis.RedisGlobalUtil;
import org.qiunet.cross.common.contants.ScannerParamKey;
import org.qiunet.data.util.ServerConfig;
import org.qiunet.flash.handler.context.header.ProtocolHeaderType;
import org.qiunet.flash.handler.netty.server.BootstrapServer;
import org.qiunet.flash.handler.netty.server.hook.DefaultHook;
import org.qiunet.flash.handler.netty.server.hook.Hook;
import org.qiunet.flash.handler.netty.server.param.TcpBootstrapParams;
import org.qiunet.flash.handler.netty.server.param.adapter.IStartupContext;
import org.qiunet.utils.config.ConfigFileUtil;
import org.qiunet.utils.data.IKeyValueData;
import org.qiunet.utils.scanner.ClassScanner;
import org.qiunet.utils.scanner.ScannerType;

public class CrossServerBootStrap {


	public static void main(String[] args) {
		Hook hook = new DefaultHook();

		String cmd = "start";
		if (args.length >= 1) cmd = args[args.length - 1];

		if ("start".equals(cmd)) {
			ClassScanner.getInstance(ScannerType.SERVER)
					.addParam(ScannerParamKey.SERVER_NODE_REDIS_INSTANCE_SUPPLIER, RedisGlobalUtil::getInstance)
					.scanner("com.game.example");

			BootstrapServer.createBootstrap(hook)
					.tcpListener(TcpBootstrapParams.custom().setStartupContext(IStartupContext.DEFAULT_CROSS_START_CONTEXT)
							.setProtocolHeaderType(ProtocolHeaderType.cross)
							.setPort(ServerConfig.getServerPort()).
							build())
					.tcpListener(TcpBootstrapParams.custom().setStartupContext(IStartupContext.DEFAULT_CROSS_NODE_START_CONTEXT)
							.setProtocolHeaderType(ProtocolHeaderType.node)
							.setPort(ServerConfig.getNodePort())
							.build())
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