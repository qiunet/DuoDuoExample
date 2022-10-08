package com.game.example.common.http;

import org.qiunet.flash.handler.context.request.http.json.IResultResponse;
import org.qiunet.flash.handler.handler.http.HttpJsonHandler;
import org.qiunet.flash.handler.handler.http.async.IAsyncHttpHandler;

/***
 * 异步关系的HttpJson Handler 父类
 */
public abstract class AsyncHttpJsonHandler<RequestData, ResponseData extends IResultResponse>
		extends HttpJsonHandler<RequestData>
		implements IAsyncHttpHandler<RequestData, ResponseData> {
}
