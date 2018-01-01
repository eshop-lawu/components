package com.lawu.weixinapi.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.lawu.weixinapi.base.HttpCode;
import com.lawu.weixinapi.base.Result;
import com.lawu.weixinapi.base.ResultCode;

/**
 * @author Leach
 * @date 2017/3/13
 */
public abstract class BaseController {

	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	public HttpServletResponse getResponse() {
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		return response;
	}

	public <T> Result<T> response(int httpCode, int retCode, String message, String debug, T model) {
		getResponse().setStatus(httpCode);
		Result<T> result = new Result();
		result.setRet(retCode);
		result.setMsg(message);
		result.setDebug(debug);
		result.setModel(model);
		return result;
	}

	public Result response(int httpCode, Result result) {
		return response(httpCode, result.getRet(), result.getMsg(), result.getDebug(), result.getModel());
	}

	public <T> Result<T> successGet(T model) {
		return response(HttpCode.SC_OK, ResultCode.SUCCESS, ResultCode.get(ResultCode.SUCCESS), null, model);
	}

	public <T> Result<T> successGet() {
		return response(HttpCode.SC_OK, ResultCode.SUCCESS, ResultCode.get(ResultCode.SUCCESS), null, null);
	}

	public <T> Result<T> successGet(int resultCode) {
		return response(HttpCode.SC_OK, resultCode, ResultCode.get(resultCode), null, null);
	}

	public <T> Result<T> successGet(int resultCode, String message) {
		return response(HttpCode.SC_OK, resultCode, message, null, null);
	}

	/**
	 * 主要用于直接返回service层数据
	 * 
	 * @param result
	 * @return
	 */
	public Result successGet(Result result) {
		return response(HttpCode.SC_OK, result);
	}

	public <T> Result<T> successCreated(T model) {
		return response(HttpCode.SC_CREATED, ResultCode.SUCCESS, ResultCode.get(ResultCode.SUCCESS), null, model);
	}

	public <T> Result<T> successCreated() {
		return response(HttpCode.SC_CREATED, ResultCode.SUCCESS, ResultCode.get(ResultCode.SUCCESS), null, null);
	}

	public <T> Result<T> successCreated(int resultCode) {
		return response(HttpCode.SC_CREATED, resultCode, ResultCode.get(resultCode), null, null);
	}
	
	public <T> Result<T> successCreated(int resultCode, T model) {
		return response(HttpCode.SC_CREATED, resultCode, ResultCode.get(resultCode), null, model);
	}

	public <T> Result<T> successCreated(int resultCode, String message) {
		return response(HttpCode.SC_CREATED, resultCode, message, null, null);
	}

	public Result successCreated(Result result) {
		return response(HttpCode.SC_CREATED, result);
	}

	public <T> Result<T> successAccepted(T model) {
		return response(HttpCode.SC_ACCEPTED, ResultCode.SUCCESS, ResultCode.get(ResultCode.SUCCESS), null, model);
	}

	public <T> Result<T> successAccepted() {
		return response(HttpCode.SC_ACCEPTED, ResultCode.SUCCESS, ResultCode.get(ResultCode.SUCCESS), null, null);

	}

	public <T> Result<T> successAccepted(int resultCode) {
		return response(HttpCode.SC_ACCEPTED, resultCode, ResultCode.get(resultCode), null, null);
	}

	public <T> Result<T> successAccepted(int resultCode, String message) {
		return response(HttpCode.SC_ACCEPTED, resultCode, message, null, null);
	}

	public Result successAccepted(Result result) {
		return response(HttpCode.SC_ACCEPTED, result);
	}
	
	/**
	 * @deprecated
	 */
	@Deprecated
	public <T> Result<T> successDelete(T model) {
		return response(HttpCode.SC_NO_CONTENT, ResultCode.SUCCESS, ResultCode.get(ResultCode.SUCCESS), null, model);
	}

	public <T> Result<T> successDelete() {
		return successDelete(null);
	}

	public <T> Result<T> failUnauthorized(int retCode, T model) {
		return response(HttpCode.SC_UNAUTHORIZED, retCode, ResultCode.get(retCode), null, model);
	}

	public <T> Result<T> failUnauthorized(int retCode) {
		return failUnauthorized(retCode, null);
	}

	public <T> Result<T> failServerError(String debug) {
		return response(HttpCode.SC_INTERNAL_SERVER_ERROR, HttpCode.SC_INTERNAL_SERVER_ERROR, HttpCode.get(HttpCode.SC_INTERNAL_SERVER_ERROR), debug, null);
	}

	public ModelAndView createMav(String viewName, Map<String, ?> model) {
		return new ModelAndView(viewName, model);
	}

	/**
	 * 内部模块返回接口成功判断
	 *
	 * @param result
	 * @return
	 */
	public boolean isSuccess(Result result) {
		return result.getRet() == ResultCode.SUCCESS;
	}
}
