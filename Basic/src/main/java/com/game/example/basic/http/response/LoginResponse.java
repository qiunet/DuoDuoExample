package com.game.example.basic.http.response;


import com.game.example.basic.http.BaseJsonResponseData;

/***
 * 登录的http响应
 */
public class LoginResponse extends BaseJsonResponseData<LoginResponse> {

	private String serverHost;

	private String serverHost6;

	private int serverPort;

	private String ticket;

	public String getServerHost6() {
		return serverHost6;
	}

	public void setServerHost6(String serverHost6) {
		this.serverHost6 = serverHost6;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
}
