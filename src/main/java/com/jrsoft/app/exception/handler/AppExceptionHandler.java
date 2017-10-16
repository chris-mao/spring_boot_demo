/**
 * 
 */
package com.jrsoft.app.exception.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jrsoft.app.exception.DataNotFoundException;
import com.jrsoft.app.exception.ExceptionInfo;

/**
 * com.jrsoft.app AppExceptionHandler
 * 
 * 全局异常处理类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@ControllerAdvice
public class AppExceptionHandler {
	
	/**
	 * 默认异常显示模板
	 */
	private final static String DEFAULT_ERROR_VIEW = "error/index";
	
	/**
	 * 页面找不到异常显示模板
	 */
	private final static String PAGE_NOT_FOUND_ERROR_VIEW = "error/404";
	
	/**
	 * 未授权异常显示模板
	 */
	private final static String UNAUTHORIZED_ERROR_VIEW = "error/403";

	/**
	 * 默认异常处理方法
	 * 
	 * @param req
	 * @param res
	 * @param model
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public String defaultErrorHandler(HttpServletRequest req, HttpServletResponse res, Model model, Exception e) {
		ExceptionInfo info = new ExceptionInfo();
		info.setCode(100);
		info.setMessage("出错啦〜〜");
		info.setExMessage(e.getMessage());
		info.setUrl(req.getRequestURL().toString());
		info.setDeveloperMessage(e.toString());
		model.addAttribute("exceptionInfo", info);

		return AppExceptionHandler.DEFAULT_ERROR_VIEW;
	}

	/**
	 * 未授权异常处理方法
	 * 
	 * @param req
	 * @param res
	 * @param model
	 * @param e
	 * @return
	 */
	@ExceptionHandler(UnauthorizedException.class)
	public String unauthorizedErrorHandler(HttpServletRequest req, HttpServletResponse res, Model model,
			UnauthorizedException e) {
		res.setStatus(HttpServletResponse.SC_FORBIDDEN);

		ExceptionInfo info = new ExceptionInfo();
		info.setCode(HttpServletResponse.SC_FORBIDDEN);
		info.setMessage("您没有足够的权限");
		info.setExMessage(e.getMessage());
		info.setUrl(req.getRequestURL().toString());
		info.setHttpStatus(HttpServletResponse.SC_FORBIDDEN);
		info.setDeveloperMessage("请开发人员当前用户是否已在系统中分配相应的操作权限");
		model.addAttribute("exceptionInfo", info);

		return AppExceptionHandler.UNAUTHORIZED_ERROR_VIEW;
	}
	
	/**
	 * 
	 * @param req
	 * @param res
	 * @param model
	 * @param e
	 * @return
	 */
	@ExceptionHandler(UnavailableSecurityManagerException.class)
	public String unauthorizedErrorHandler(HttpServletRequest req, HttpServletResponse res, Model model,
			UnavailableSecurityManagerException e) {
		res.setStatus(HttpServletResponse.SC_NOT_FOUND);

		ExceptionInfo info = new ExceptionInfo();
		info.setCode(HttpServletResponse.SC_NOT_FOUND);
		info.setMessage("您请求的页而不存在");
		info.setExMessage(e.getMessage());
		info.setUrl(req.getRequestURL().toString());
		info.setHttpStatus(HttpServletResponse.SC_NOT_FOUND);
		info.setDeveloperMessage("请确认用户请求的URL是否有效");
		model.addAttribute("exceptionInfo", info);

		return AppExceptionHandler.PAGE_NOT_FOUND_ERROR_VIEW;
	}
	
	/**
	 * 无匹配数据异常处理方法
	 * 
	 * @param req
	 * @param res
	 * @param model
	 * @param e
	 * @return
	 */
	@ExceptionHandler(DataNotFoundException.class)
	public String dataNotFoundErrorHandler(HttpServletRequest req, HttpServletResponse res, Model model, Exception e) {
		ExceptionInfo info = new ExceptionInfo();
		info.setCode(201);
		info.setMessage("Opps〜〜没有相匹配的数据");
		info.setExMessage(e.getMessage());
		info.setUrl(req.getRequestURL().toString());
		info.setHttpStatus(HttpServletResponse.SC_NO_CONTENT);
		info.setDeveloperMessage("请确认用户使用的数据ID是否有效");
		model.addAttribute("exceptionInfo", info);

		return AppExceptionHandler.DEFAULT_ERROR_VIEW;
	}

}
