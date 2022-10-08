package com.game.example.login.handler;

import com.game.example.basic.http.request.LoginRequest;
import com.game.example.basic.http.response.LoginResponse;
import com.game.example.common.constants.GameStatus;
import com.game.example.common.constants.LoginUri;
import com.game.example.common.http.AsyncHttpJsonHandler;
import com.game.example.common.utils.redis.RedisDataUtil;
import com.game.example.login.service.LoginService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.qiunet.flash.handler.common.annotation.UriPathHandler;
import org.qiunet.flash.handler.context.status.IGameStatus;
import org.qiunet.flash.handler.handler.http.async.HttpAsyncTask;
import org.qiunet.flash.handler.netty.server.http.handler.HttpServerHandler;

/***
 * 登录. 分配 server ticket
 * @see HttpServerHandler#handlerOtherUriPathRequest(ChannelHandlerContext, FullHttpRequest, String) 调用到此Handler
 **/
@UriPathHandler(LoginUri.login)
public class LoginHandler extends AsyncHttpJsonHandler<LoginRequest, LoginResponse> {
	public static final String USER_REDIS_LOCK_KEY = "user_redis_lock_key";

	@Override
	public void handler(HttpAsyncTask<LoginRequest, LoginResponse> asyncTask) throws Exception {
		LoginRequest requestData = asyncTask.getRequest();
		LoginResponse responseData = new LoginResponse();

		if (requestData.getUid() <= 0) {
			asyncTask.response(responseData.setFail(IGameStatus.PARAMS_ERROR));
			return;
		}

		String userLock = USER_REDIS_LOCK_KEY + requestData.getUid();
		Long result = RedisDataUtil.jedis().incr(userLock);
		if (result != 1) {
			asyncTask.response(responseData.setFail(IGameStatus.FAST_REQUEST));
			return;
		}
		RedisDataUtil.jedis().expire(userLock, 3L);

		try {
			asyncTask.response(LoginService.instance.doLogin(requestData));
		} catch (Exception e) {
			logger.error("login exception: ", e);
			asyncTask.response(responseData.setFail(GameStatus.EXCEPTION));
		} finally {
			RedisDataUtil.jedis().del(userLock);
		}
	}
}
