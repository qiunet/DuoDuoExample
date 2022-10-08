package com.game.example.login.service;


import com.game.example.basic.http.request.LoginRequest;
import com.game.example.basic.http.response.LoginResponse;
import com.game.example.common.constants.GameStatus;
import com.game.example.common.data.PlayerPlatformData;
import com.game.example.common.server.ServerAssigner;
import com.game.example.common.utils.redis.RedisDataUtil;
import com.game.example.login.entity.LoginBo;
import com.game.example.login.entity.LoginDo;
import org.qiunet.cross.node.ServerInfo;
import org.qiunet.data.support.RedisDataSupport;
import org.qiunet.utils.string.StringUtil;

import java.util.StringJoiner;

/**
 * 登录服使用.
 */
public enum LoginService {
    instance;

    // 缓存和持久化支持. 在获取缓存的线程、Redis(有过期时间)、DB中都有保存数据
    private final RedisDataSupport<Long, LoginDo, LoginBo> dataSupport = new RedisDataSupport<>(RedisDataUtil.getInstance(), LoginDo.class, LoginBo::new);

    /**
     * 登录处理
     */
    public LoginResponse doLogin(LoginRequest requestData) {
        LoginResponse response = new LoginResponse();

        LoginBo loginBo = this.getOrRegister(requestData);
        ServerInfo serverInfo = this.assignServer(loginBo);
        if (serverInfo == null || serverInfo.isOffline()) {
            return response.setFail(GameStatus.SERVER_ABSENT_NOW);
        }

        // 杀进程重进. 不算重连
        PlayerPlatformData data = PlayerPlatformData.valueOf(requestData.getUid(), requestData.getToken(), serverInfo.getServerId(), loginBo.getDo().getTicket());
        data.sendToRedis();

        response.setServerHost6(serverInfo.getPublicHost6());
        response.setServerHost(serverInfo.getPublicHost());
        response.setServerPort(serverInfo.getServerPort());
        response.setTicket(data.getTicket());
        return response;
    }

    /**
     * 分配服务器
     */
    private ServerInfo assignServer(LoginBo loginBo) {
        int groupId = loginBo.getDo().getServer_group();
        if (groupId <= 0) {
            // 新用户
            groupId = ServerAssigner.instance.assignGroupId();
            loginBo.getDo().setServer_group(groupId);
            loginBo.update();
        }
        return ServerAssigner.instance.assignServerByGroupId(groupId);
    }

    /**
     * 返回已有. 或者 注册新的login
     */
    private LoginBo getOrRegister(LoginRequest requestData) {
        long playerId = requestData.getUid();
        LoginBo loginBo = LoginService.instance.getLoginBo(playerId);
        String ticket = this.buildTicket(playerId);
        if (loginBo == null) {
            LoginDo loginDo = new LoginDo(playerId);
            loginDo.setBig_group(0);
            loginDo.setToken(requestData.getToken());
            loginDo.setTicket(ticket);
            loginBo = dataSupport.insert(loginDo);
        }else {
            loginBo.getDo().setToken(requestData.getToken());
            loginBo.getDo().setTicket(ticket);
            loginBo.update();
        }

        return loginBo;
    }

    /***
     * 获得一个 LoginBo 对象
     */
    public LoginBo getLoginBo(long playerId) {
        return dataSupport.getBo(playerId);
    }

    /**
     * 构造一个server入场券
     */
    public String buildTicket(long playerId) {
        return new StringJoiner("_")
                .add(StringUtil.randomString(10))
                .add(String.valueOf(playerId))
                .toString();
    }
}
