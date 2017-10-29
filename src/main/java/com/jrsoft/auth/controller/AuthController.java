package com.jrsoft.auth.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * com.jrsoft.auth.controller AuthController
 * 
 * 身份认证控制器类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
public class AuthController {

	private final static Logger logger = LoggerFactory.getLogger(AuthController.class);

	/**
	 * 登录页面
	 * 
	 * @return
	 */
	@GetMapping("/login")
	public String index() {
		return "auth/login";
	}

	/**
	 * 用户登录POST页面，判断用户名及密码是否正确，如果返回的错误异常名称是
	 * 
	 * <pre>
	 * {@link UnknownAccountException} 表示登录帐号不存在
	 * </pre>
	 * 
	 * <pre>
	 * {@link IncorrectCredentialsException} 表示登录帐号存在，但输入的密码不正确
	 * </pre>
	 * 
	 * <pre>
	 * {@link LockedAccountException} 表示登录帐号被锁
	 * </pre>
	 * 
	 * <pre>
	 * {@link ExpiredCredentialsException} 表示登录帐号已过期
	 * </pre>
	 * 
	 * <pre>
	 * {@link DisabledAccountException} 表示登录帐号被禁用
	 * </pre>
	 * 
	 * <pre>
	 * {@link AuthenticationException} 表示其他未知和登录错误
	 * </pre>
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@PostMapping("/login")
	public String doLogin(HttpServletRequest request, Model model) {
		String msg = "";
		String exception = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		if (exception != null) {
			if (UnknownAccountException.class.getName().equals(exception)) {
				msg = "您输入的帐号不存在！";
			} else if (IncorrectCredentialsException.class.getName().equals(exception)) {
				msg = "您输入的密码不正确！";
			} else if (LockedAccountException.class.getName().equals(exception)) {
				msg = "您的帐号已被锁定！";
			} else if (ExpiredCredentialsException.class.getName().equals(exception)) {
				msg = "您的帐号已过期！";
			} else if (DisabledAccountException.class.getName().equals(exception)) {
				msg = "您的帐号已被禁用！";
			} else if (AuthenticationException.class.getName().equals(exception)) {
				msg = "登录认证失败，原因不明！";
			} else {
				msg = "登录异常！";
			}
			logger.error(msg + " >>> " + exception);
		}

		model.addAttribute("msg", msg);
		return "auth/login";
	}

	/**
	 * 退出系统页面
	 * 
	 * @return
	 */
	@GetMapping("/logout")
	public String logout() {
		SecurityUtils.getSubject().logout();
		return "auth/logout";
	}

}
