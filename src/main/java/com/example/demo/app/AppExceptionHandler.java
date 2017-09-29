/**
 * 
 */
package com.example.demo.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * com.example.demo.app AppExceptionHandler
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
	 * 
	 */
	private final static String DEFAULT_ERROR_VIEW = "error/index";
	
	/**
	 * 
	 */
	private final static String PAGE_NOT_FOUND_ERROR_VIEW = "error/404";
	
	/**
	 * 
	 */
	private final static String UNAUTHORIZED_ERROR_VIEW = "error/403";

	/**
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
		// info.setHttpStatus(HttpServletResponse.SC_FORBIDDEN);
		info.setDeveloperMessage(e.toString());
		model.addAttribute("exceptionInfo", info);

		return AppExceptionHandler.DEFAULT_ERROR_VIEW;
	}

	/**
	 * a
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

}
