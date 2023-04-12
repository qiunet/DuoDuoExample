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
        // 需要注册
		int NEED_REGISTER_PUSH = 1000002;

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

    //背包相关  模块ID: 4
    interface Pack {
        // 所有物品推送
        int ALL_ITEM_PUSH = 4000000;
        // 物品变动推送(包含删除)
        int ITEM_CHANGE_PUSH = 4000001;
    }

    // 游戏玩法相关 占用 [8 - 10] 后面的业务从11开始
    interface Game {
        // 匹配等通用协议 8001 ~ 8499
        interface Room {
            // 房间信息推送
            int ROOM_INFO_PUSH = 8000009;

            //  玩家进入房间推送
            int ROOM_PLAYER_ENTER_PUSH = 8000012;
            // 玩家离开房间推送
            int ROOM_PLAYER_QUIT_PUSH = 8000013;
            // 房间销毁推送
            int ROOM_DESTROY_PUSH = 8000014;
            // 加入房间失败返回
            int JOIN_ROOM_FAIL_RSP = 8000015;

            // 退出房间请求.
            int ROOM_QUIT_REQ = 8001;
            int ROOM_QUIT_RSP = 8001001;

            // 加入公共房间
            int JOIN_PUBLIC_ROOM_REQ = 8002;
            // 创建私密房间
            int CREATE_PRIVACY_ROOM_REQ = 8003;

            // 加入指定ID房间
            int JOIN_ROOM_REQ = 8004;
        }
    }

    //地图相关  模块ID: 3
    interface Map {
        // 视野变化: 有人移出
        int KNOW_LIST_DEL_PUSH = 3000000;
        // 视野变化: 有人进入
        int KNOW_LIST_ADD_PUSH = 3000001;

        // 场景切换推送
        int SCENE_CHANGE_PUSH = 3000004;
        // 在场景下线
        int SCENE_OFFLINE_PUSH = 3000007;

        // 移动指定位置
        int SCENE_POSITION_REQ = 3001;
        int SCENE_POSITION_RSP = 3001001;

        // 地图切换请求
        int TRANSPORT_REQ = 3002;
        int TRANSPORT_RSP = 3002001;
    }
}
