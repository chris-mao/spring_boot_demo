/**
 * 
 */
package com.example.demo.auth.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * com.example.demo.auth.controller AuthRoleController
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/roles")
public class AuthRoleController {
	
	@GetMapping("")
	@RequiresPermissions("authRole:list")
	public String findAllRole() {
		return "auth/Role/index";
	}
	
	@GetMapping("/{id}")
	@RequiresPermissions("authRole:detail")
	public String findRole(@PathVariable("id")Integer id) {
		return null;
		//
	}
	
	@GetMapping("/new")
	@RequiresPermissions("authRole:new")
	public String newRole() {
		return "";
	}
	
	@PostMapping("/save")
	@RequiresPermissions("authRole:save")
	public String saveRole() {
		return "";
	}
	
	@PostMapping("/del/{id}")
	@RequiresPermissions("authRole:delete")
	public String deleteRole(@PathVariable("id")Integer id) {
		return "";
	}

}
