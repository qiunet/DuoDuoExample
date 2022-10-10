package com.game.example.server;

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
            server.tcpListener(TcpBootstrapParams.custom()
                    .setStartupContext(IStartupContext.SERVER_STARTUP_CONTEXT)
                    .setProtocolHeaderType(ProtocolHeaderType.server)
                    .setPort(ServerConfig.getServerPort())
                    .setServerName("游戏服")
                    .setEncryption(false)
                    .setUdpOpen()
                    .build())
                    // 节点交互
                    .tcpListener(TcpBootstrapParams.custom()
                            .setStartupContext(IStartupContext.DEFAULT_CROSS_NODE_START_CONTEXT)
                            .setProtocolHeaderType(ProtocolHeaderType.node)
                            .setPort(ServerConfig.getNodePort())
                            .setServerName("节点通讯")
                            .build())
                    //// Cross 交互
                    //.tcpListener(TcpBootstrapParams.custom()
                    //		.setStartupContext(IStartupContext.DEFAULT_CROSS_START_CONTEXT)
                    //		.setPort(ServerConfig.getInstance().getInt(ServerConfig.CROSS_PORT))
                    //		.setProtocolHeaderType(ProtocolHeaderType.node)
                    //		.setServerName("跨服玩法")
                    //		.build())
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
