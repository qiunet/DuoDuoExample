package com.game.example.basic.protocol;

/***
 * 游戏的协议ID
 *  * 0 - 999 为系统自留
 *  * 请求id       = 模块ID * 1000 + 自增
 *  * 响应ID       = (请求ID) * 1000 + 自增
 *  * 无请求的响应ID = 模块ID * 1000 * 1000 + 自增
 *  *
 *  * 例如: 模块ID: 1
 *  * 请求ID范围: 1001 ~ 1099
 *  * 响应ID范围: 请求ID+000 ~ 请求ID+999
 *  * 无请求响应ID: 1000000 ~ 1000999
 **/
public interface ProtocolID {
    // 玩家数据相关 模块ID: 1
    interface Player {
        // 玩家数据推送
        int PLAYER_DATA_PUSH = 1000001;

        // 登录协议
        int LOGIN_REQ = 1001;
        int LOGIN_RSP = 1001001;
        // 登出协议
        int LOGOUT_REQ = 1002;

        // 注册(名字 形象等设置)
        int REGISTER_REQ = 1003;
        int REGISTER_RSP = 1003001;

        // 请求随机名字
        int RANDOM_NAME_REQ = 1007;
        int RANDOM_NAME_RSP = 1007001;
    }
}
