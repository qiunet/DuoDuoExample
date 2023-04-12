package com.game.example.common.server;

import org.qiunet.cross.node.ServerInfo;
import org.qiunet.cross.node.ServerNodeManager;
import org.qiunet.cross.node.ServerNodeTickEvent;
import org.qiunet.data.util.ServerType;
import org.qiunet.flash.handler.common.player.UserOnlineManager;
import org.qiunet.utils.listener.event.EventListener;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum ServerAssigner {

    instance;

    /**
     * 分配一台人少的 cross 服务器
     */
    public ServerInfo assignCrossServer() {
        return assignServer(ServerType.CROSS, null);
    }

    // 分配在线人数最少的组
    public int assignGroupId() {
		int groupId = -1, maxWeight = 0;
		List<ServerInfo> serverInfos = ServerNodeManager.serverList(ServerType.LOGIC);
		Map<Integer, Integer> groups = serverInfos.stream().collect(Collectors.toMap(ServerInfo::getServerGroupId, ServerInfo::weight, Integer::sum));
		for (Map.Entry<Integer, Integer> entry : groups.entrySet()) {
			if (groupId == -1 || maxWeight < entry.getValue()) {
				groupId = entry.getKey();
			}
		}
		if (groupId == -1) {
			throw new IllegalStateException("group id error!");
		}
		return groupId;
    }

     // 在指定服务区分配一台空闲服务器.
    public ServerInfo assignServerByGroupId(int serverGroupId) {
        return ServerNodeManager.assignLogicServerByGroupId(serverGroupId);
    }


    /**
     * 根据类型分配服务器
     */
    public ServerInfo assignServer(ServerType serverType, Predicate<ServerInfo> filter) {
        return ServerNodeManager.assignServer(serverType, filter);
    }

    /**
     * 取得服务器列表
     */
    private List<ServerInfo> getServerInfoList(ServerType serverType){
        return ServerNodeManager.serverList(serverType);
    }


    /**
     * server node 心跳.
     */
    @EventListener
    private void serverNodeTick(ServerNodeTickEvent event) {
        int onlineSize = UserOnlineManager.instance.onlineSize();
		// 用人数作为权重. 人数少. 权重高
        ServerNodeManager.getCurrServerInfo().setWeight(10000000 - onlineSize);
    }
}
