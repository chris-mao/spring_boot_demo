/**
 * 
 */
package com.example.demo.app.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.auth.service.AuthUserService;

/**
 * com.example.demo.app.controller HomeController
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
public class HomeController {
	
	@Resource
	private AuthUserService authUserService;
	
	@GetMapping("/")
	public String index(HttpServletRequest request, Map<String, Object> map) {
		Subject subject = SecurityUtils.getSubject();
		System.out.println(subject.isPermitted("/"));
		map.put("userList", this.authUserService.findAll());
		return "index";
	}

}
