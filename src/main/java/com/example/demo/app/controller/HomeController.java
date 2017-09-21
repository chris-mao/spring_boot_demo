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
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.auth.entity.AuthUser;
import com.example.demo.auth.service.AuthUserService;
import com.github.pagehelper.PageInfo;

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
	
	@GetMapping("/")
	public String index(HttpServletRequest request, Map<String, Object> map) {
		return "index";
	}

}
