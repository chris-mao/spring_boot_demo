/**
 * 
 */
package com.example.demo.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	@ExceptionHandler(UnauthorizedException.class)
	public String defaultErrorHandler(HttpServletRequest req, HttpServletResponse res, Model model, UnauthorizedException e) {
		res.setStatus(HttpServletResponse.SC_FORBIDDEN);
		
		ExceptionInfo info = new ExceptionInfo();
		info.setCode(HttpServletResponse.SC_FORBIDDEN);
		info.setMessage("您没有足够的权限");
		info.setExMessage(e.getMessage());
		info.setUrl(req.getRequestURL().toString());
		info.setHttpStatus(HttpServletResponse.SC_FORBIDDEN);
		info.setDeveloperMessage("请开发人员当前用户是否已在系统中分配相应的操作权限");
		model.addAttribute("exceptionInfo", info);

		System.out.println("status = " + res.getStatus());

		return "error/403";
	}

}
