package com.game.example.basic.http;

import org.qiunet.flash.handler.context.request.http.json.IResultResponse;
import org.qiunet.flash.handler.context.status.IGameStatus;

/***
 * Json response 的父类
 */
public abstract class BaseJsonResponseData<T extends BaseJsonResponseData<T>> implements IResultResponse {

	private HttpStatus status = IResultResponse.SUCCESS;

	@Override
	public HttpStatus getStatus() {
		return status;
	}

	public T setStatus(int status, String desc) {
		this.setStatus(new HttpStatus(status, desc));
		return (T) this;
	}

	public T setFail(IGameStatus status) {
		return this.setStatus(status.getStatus(), status.getDesc());
	}

	@Override
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}
