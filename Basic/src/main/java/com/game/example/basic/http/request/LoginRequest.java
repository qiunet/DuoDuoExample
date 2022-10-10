package com.game.example.basic.http.request;

/**
 * 登录的http请求
 */
public class LoginRequest {
	/**
	 * 用户uid
	 */
	private String uid;
	/**
	 * 平台给出的token 长度应该大于 20
	 * 如果token长度为空. 认为是unity 登录的, 不会去平台校验.(客户端工具传空字符串)
	 * 否则认为是一个需要校验的token
	 */
	private String token;

	public long getUid() {
		return Long.parseLong(uid);
	}

	public String getToken() {
		return token;
	}
}
