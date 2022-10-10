###游戏服
> 启动类ServerBootstrap

####登录游戏服流程
1. 客户端发送登录的http请求到登录服，登陆服将PlayerPlatformData[含有ticket(登录服在每次请求登录时生成新的)]保存到redis中，
并响应客户端LoginResponse[包含ip、port、ticket]
2. 客户端根据登录服响应的ip、端口等用tcp协议连接对应的游戏服
3. 客户端发送ConnectionReq协议包给游戏服，服务器响应ConnectionRsp
4. 客户端发送LoginReq协议给游戏服处理登录

```
游戏服响应给客户端LoginRsp

1.如果需要注册，客户端发送RandomNameReq协议包给游戏服，服务器响应RandomNameRsp
客户端发送RegisterReq协议包给游戏服，服务器处理注册逻辑，并响应RegisterRsp注册成功，
同时在游戏服中直接登录成功并推送玩家信息PlayerDataPush给客户端，至此登录成功。
（注册时的ticket从redis获取并保存到PlayerDo）

2.已经注册账号，直接登录成功并推送玩家信息PlayerDataPush给客户端，至此登录成功
（ticket从客户端请求获得，如果与PlayerBo中原先的ticket相同，会走重连逻辑）
登录会保存新的ticket到PlayerDo

（客户端持有的ticket是登录服生成的。所以会与redis中相同，redis中PlayerPlatformData是登录凭证）
```

