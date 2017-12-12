package com.jrsoft.auth.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
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
 * 身份认证控制器类
 * 
 * com.jrsoft.auth.controller AuthController
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
public class AuthController {

	private final static Logger logger = LoggerFactory.getLogger(AuthController.class);

	@GetMapping("/login")
	public String index() {
		return "auth/login";
	}

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
			}
			else if (AuthenticationException.class.getName().equals(exception)) {
				msg = "登录认证失败，请联系管理员检查数据库是否连接正常！";
			}
			else if (ExcessiveAttemptsException.class.getName().equals(exception)) {
				msg = "连续5次登录失败，帐号已被锁定，请与系统管理员联系！";
			}
			else {
				msg = "登录异常！";
			}
			logger.error(msg + " >>> " + exception);
		}

		model.addAttribute("msg", msg);
		return "auth/login";
	}

	@GetMapping("/logout")
	public String logout() {
		SecurityUtils.getSubject().logout();
		return "auth/logout";
	}
	
	@GetMapping("/403")
	public String unauthorized() {
		return "auth/unauthorized";
	}

}
