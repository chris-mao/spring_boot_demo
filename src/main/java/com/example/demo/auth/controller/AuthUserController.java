/**
 * 
 */
package com.example.demo.auth.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.auth.entity.AuthUser;
import com.example.demo.auth.service.AuthUserService;
import com.github.pagehelper.PageInfo;

/**
 * com.example.demo.auth.controller AuthUserController
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/users")
public class AuthUserController {
	
	@Resource
	private AuthUserService authUserService;
	
	@GetMapping("")
//	@RequiresPermissions("authUser:list")
	public String findAllUser(@RequestParam(defaultValue="1")int pageNum, Map<String, Object> map) {
		final int pageSize = 20;
		PageInfo<AuthUser> page = this.authUserService.findAll(pageNum, pageSize);
		map.put("page", page);
		return "auth/users/index";
	}
	
	@GetMapping("/{id}")
	@RequiresPermissions("authUser:detail")
	public String findUser(@PathVariable("id")Integer id) {
		return null;
	}
	
	@GetMapping("/new")
	@RequiresPermissions("authUser:new")
	public String newUser() {
		return "";
	}
	
	@PostMapping("/save")
	@RequiresPermissions("authUser:save")
	public String saveUser() {
		return "";
	}
	
	@PostMapping("/del/{id}")
	@RequiresPermissions("authUser:delete")
	public String deleteUser(@PathVariable("id")Integer id) {
		return "";
	}

}
