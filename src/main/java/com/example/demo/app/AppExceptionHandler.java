/**
 * 
 */
package com.example.demo.app;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ExceptionInfo defaultErrorHandler(HttpServletRequest req, Exception e) {
		ExceptionInfo info = new ExceptionInfo();
		info.setCode(100);
		info.setExMessage(e.getMessage());
		info.setMessage("您没有足够的权限");
		info.setUrl(req.getRequestURL().toString());
		return info;
		
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("exception", e);
//		mav.addObject("url", req.getRequestURL());
//		mav.setViewName("error/index");
//		return mav;
	}

}
