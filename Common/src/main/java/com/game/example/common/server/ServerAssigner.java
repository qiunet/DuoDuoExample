package com.game.example.common.server;

import com.game.example.common.utils.redis.RedisDataUtil;
import org.qiunet.cross.node.ServerInfo;
import org.qiunet.cross.node.ServerNodeManager;
import org.qiunet.cross.node.ServerNodeTickEvent;
import org.qiunet.data.util.ServerType;
import org.qiunet.flash.handler.common.player.UserOnlineManager;
import org.qiunet.utils.async.LazyLoader;
import org.qiunet.utils.date.DateUtil;
import org.qiunet.utils.json.JsonUtil;
import org.qiunet.utils.listener.event.EventListener;
import org.qiunet.utils.listener.event.data.ServerShutdownEvent;
import redis.clients.jedis.params.SetParams;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum ServerAssigner {

    instance;

    private static final String ONLINE_USER_COUNT = "ONLINE_USER_COUNT";
    /**
     * 存储当前在线人数统计的 redis key, 使用该key 获取在线人数的map
     * 格式: key + serverType
     */
    private static final String CURRENT_ONLINE_SIZE_REDIS_KEY = "CURRENT_ONLINE_SIZE#";
    /**
     * 存储Map{serverGroupId, 在线人数}
     *
     * 每分钟重新统计 所以key 需要带时间信息
     */
    private static final String ONLINE_SIZE_REDIS_KEY = "ONLINE_SIZE#";
    /**
     * 存储Map{serverId: serverInfo}
     *
     */
    private static final String SERVER_INFO_DATA_REDIS_KEY = "SERVER_INFO_DATA#";

    private static String serverInfoDataRedisKey(ServerType serverType) {
        return SERVER_INFO_DATA_REDIS_KEY + serverType;
    }

    private static String currentOnlineSizeRedisKey(ServerType serverType) {
        return CURRENT_ONLINE_SIZE_REDIS_KEY + serverType;
    }

    private static String onlineSizeRedisKey(ServerType serverType) {
        return ONLINE_SIZE_REDIS_KEY + serverType +"#"+ DateUtil.dateToString(System.currentTimeMillis(), "HH_mm");
    }

    private static final LazyLoader<ServerType> CURRENT_SERVER_TYPE = new LazyLoader<>(ServerNodeManager::getCurrServerType);
    private static final LazyLoader<Integer> CURRENT_SERVER_ID = new LazyLoader<>(ServerNodeManager::getCurrServerId);
    private static final LazyLoader<Integer> CURRENT_SERVER_GROUP_ID = new LazyLoader<>(() -> ServerType.getGroupId(CURRENT_SERVER_ID.get()));

    /**
     * 分配一台人少的 cross 服务器
     */
    public ServerInfo assignCrossServer() {
        return assignServer(ServerType.CROSS, null);
    }

    // 分配在线人数最少的组
    public int assignGroupId() {
        String onlineSizeRedisKey = RedisDataUtil.jedis().get(currentOnlineSizeRedisKey(ServerType.LOGIC));
        Map<String, String> map = RedisDataUtil.jedis().hgetAll(onlineSizeRedisKey);
        AtomicInteger groupNum = new AtomicInteger(-1);
        map.forEach((groupId, count) -> {
            if (groupNum.get() <= 0 || Integer.parseInt(count) < groupNum.get()) {
                groupNum.set(Integer.parseInt(groupId));
            }
        });
        int groupId = groupNum.get();
        if (groupId == -1) {
            throw new IllegalStateException("group id error!");
        }
        return groupId;
    }

     // 在指定服务区分配一台空闲服务器.
    public ServerInfo assignServerByGroupId(int serverGroupId) {
        return this.assignServer(ServerType.LOGIC, info -> ServerType.getGroupId(info.getServerId()) == serverGroupId);
    }


    /**
     * 根据类型分配服务器
     */
    public ServerInfo assignServer(ServerType serverType, Predicate<ServerInfo> filter) {
        List<ServerInfo> serverInfoList = getServerInfoList(serverType);
        if (serverInfoList.isEmpty()) {
            return null;
        }

        // 选择人数最少的服务器
        ServerInfo serverInfo0 = null;
        for (ServerInfo serverInfo : serverInfoList) {
            if (filter != null && ! filter.test(serverInfo)) {
                continue;
            }

            int count0 = (int) serverInfo.get(ONLINE_USER_COUNT);
            if (serverInfo0 == null || count0 < ((int)serverInfo0.get(ONLINE_USER_COUNT))) {
                serverInfo0 = serverInfo;
            }
        }
        if (serverInfo0 == null) {
            throw new IllegalStateException("assign server info error!");
        }
        return serverInfo0;
    }

    /**
     * 取得服务器列表
     */
    private List<ServerInfo> getServerInfoList(ServerType serverType){
        return RedisDataUtil.jedis().hvals(serverInfoDataRedisKey(serverType))
                .stream().map(data -> JsonUtil.getGeneralObj(data, ServerInfo.class)).collect(Collectors.toList());
    }


    /**
     * server node 心跳.
     */
    @EventListener
    private void serverNodeTick(ServerNodeTickEvent event) {
        int onlineSize = UserOnlineManager.instance.onlineSize();
        ServerNodeManager.getCurrServerInfo().put(ONLINE_USER_COUNT, onlineSize);

        RedisDataUtil.getInstance().execCommands(jedis -> {
            jedis.hset(serverInfoDataRedisKey(CURRENT_SERVER_TYPE.get()), String.valueOf(CURRENT_SERVER_ID.get()), ServerNodeManager.getCurrServerInfo().toString());

            // 只有logic 服才需要按照groupId 区分 在线
            if (CURRENT_SERVER_TYPE.get() == ServerType.LOGIC) {
                String onlinePlayerRedisKey = onlineSizeRedisKey(CURRENT_SERVER_TYPE.get());

                jedis.set(currentOnlineSizeRedisKey(CURRENT_SERVER_TYPE.get()), onlinePlayerRedisKey, SetParams.setParams().ex(180L));
                jedis.hincrBy(onlinePlayerRedisKey, String.valueOf(CURRENT_SERVER_GROUP_ID.get()), onlineSize);
                jedis.expire(onlinePlayerRedisKey, 3L * 60);
            }
            return null;
        }, false);
    }

    @EventListener
    private void onShutdown(ServerShutdownEvent eventData) {
        RedisDataUtil.jedis().hdel(serverInfoDataRedisKey(CURRENT_SERVER_TYPE.get()), String.valueOf(CURRENT_SERVER_ID.get()));
    }
}
