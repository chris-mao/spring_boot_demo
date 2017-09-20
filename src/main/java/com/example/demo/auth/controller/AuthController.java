/**
 * 
 */
package com.example.demo.auth.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * com.example.demo.auth.controller AuthController
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
public class AuthController {

	@GetMapping("/auth")
	public String index(HttpServletRequest request, Map<String, Object> map) {
		return "auth/login";
	}

	@PostMapping("/auth")
	public String doLogin(HttpServletRequest request, Map<String, Object> map) {
		String msg = "";
		String userName = request.getParameter("user_name");
		String password = request.getParameter("password");
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

		try {
			System.out.println(token);
			// token.setRememberMe(true);
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			if (subject.isAuthenticated()) {
				return "redirect:/";
			}
		} catch (UnknownAccountException e) {
			msg = "您输入的帐号不存在！";
			System.out.println(msg + " >>> " + e.getClass().getName());
		} catch (IncorrectCredentialsException e) {
			msg = "您输入的密码不正确！";
			System.out.println(msg + " >>> " + e.getClass().getName());
		} catch (LockedAccountException e) {
			msg = "您的帐号已被锁定！";
			System.out.println(msg + " >>> " + e.getClass().getName());
		} catch (ExpiredCredentialsException e) {
			msg = "您的帐号已过期！";
			System.out.println(msg + " >>> " + e.getClass().getName());
		} catch (DisabledAccountException e) {
			msg = "您的帐号已被禁用！";
			System.out.println(msg + " >>> " + e.getClass().getName());
		} catch (AuthenticationException e) {
			msg = "登录认证失败，原因不明！";
			// e.printStackTrace();
			map.put("msg", e.getMessage());
		}

		map.put("msg", msg);
		return "auth/login";
	}

	@GetMapping("/logout")
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "auth/logout";
	}

}
