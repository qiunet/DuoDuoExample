package com.game.example.server;

import com.game.example.common.utils.redis.RedisGlobalUtil;
import org.qiunet.cross.common.contants.ScannerParamKey;
import org.qiunet.data.util.ServerConfig;
import org.qiunet.flash.handler.context.header.CompatibleProtocolHeader;
import org.qiunet.flash.handler.netty.server.BootstrapServer;
import org.qiunet.flash.handler.netty.server.config.adapter.IStartupContext;
import org.qiunet.flash.handler.netty.server.hook.DefaultHook;
import org.qiunet.flash.handler.netty.server.hook.Hook;
import org.qiunet.flash.handler.netty.server.config.ServerBootStrapConfig;
import org.qiunet.utils.config.ConfigFileUtil;
import org.qiunet.utils.data.IKeyValueData;
import org.qiunet.utils.scanner.ClassScanner;
import org.qiunet.utils.scanner.ScannerType;

/***
 * 游戏服务启动
 **/
public class ServerBootStrap {

    public static void main(String[] args) {
        Hook hook = new DefaultHook();

        String cmd = "start";
        if (args.length >= 1) cmd = args[args.length - 1];

        if ("start".equals(cmd)) {
            ClassScanner.getInstance(ScannerType.SERVER)
                    .addParam(ScannerParamKey.SERVER_NODE_REDIS_INSTANCE_SUPPLIER, RedisGlobalUtil::getInstance)
                    .scanner("com.game.example");

            BootstrapServer server = BootstrapServer.createBootstrap(hook);
            server.listener(ServerBootStrapConfig.newBuild("游戏服", ServerConfig.getServerPort())
					.setTcpBootStrapConfig(
						ServerBootStrapConfig.TcpBootstrapConfig.newBuild()
						.setUdpOpen(ServerBootStrapConfig.KcpBootstrapConfig.newBuild().setPortCount(1).build())
						.build()
					).setStartupContext(IStartupContext.SERVER_STARTUP_CONTEXT)
					// 方便老工具使用. 实际可以不用下面这行. 会默认使用 DefaultProtocolHeader
					.setProtocolHeader(CompatibleProtocolHeader.instance)
                    .build())
                    // 节点交互
                    .listener(ServerBootStrapConfig.newBuild("节点通讯服务", ServerConfig.getNodePort())
                            .setStartupContext(IStartupContext.DEFAULT_SERVER_NODE_START_CONTEXT)
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
