package com.game.example.server;

import com.game.example.common.utils.redis.RedisGlobalUtil;
import org.qiunet.data.util.ServerConfig;
import org.qiunet.flash.handler.netty.server.BootstrapServer;
import org.qiunet.flash.handler.netty.server.config.ServerBootStrapConfig;
import org.qiunet.flash.handler.netty.server.hook.DefaultHook;
import org.qiunet.flash.handler.netty.server.hook.Hook;
import org.qiunet.utils.config.ConfigFileUtil;
import org.qiunet.utils.data.IKeyValueData;

/***
 * 游戏服务启动
 **/
public class ServerBootStrap {

    public static void main(String[] args) {
        Hook hook = new DefaultHook();

        String cmd = "start";
        if (args.length >= 1) cmd = args[args.length - 1];

        if ("start".equals(cmd)) {
            BootstrapServer server = BootstrapServer.createBootstrap(hook, RedisGlobalUtil::getInstance, "com.game.example");
            server.listener(ServerBootStrapConfig.newBuild("游戏服", ServerConfig.getServerPort())
					.setTcpBootStrapConfig(
						ServerBootStrapConfig.TcpBootstrapConfig.newBuild()
						.setUdpOpen(ServerBootStrapConfig.KcpBootstrapConfig.newBuild().setPortCount(1).build())
						.build()
					).build())
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
