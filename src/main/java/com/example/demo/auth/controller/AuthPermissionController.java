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
 * com.example.demo.auth.controller AuthPermissionController
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/permissions")
public class AuthPermissionController {
	
	@GetMapping("")
	@RequiresPermissions("authPermission:list")
	public String findAllPermission() {
		return "auth/Permission/index";
	}
	
	@GetMapping("/{id}")
	@RequiresPermissions("authPermission:detail")
	public String findPermission(@PathVariable("id")Integer id) {
		return null;
		//
	}
	
	@GetMapping("/new")
	@RequiresPermissions("authPermission:new")
	public String newPermission() {
		return "";
	}
	
	@PostMapping("/save")
	@RequiresPermissions("authPermission:save")
	public String savePermission() {
		return "";
	}
	
	@PostMapping("/del/{id}")
	@RequiresPermissions("authPermission:delete")
	public String deletePermission(@PathVariable("id")Integer id) {
		return "";
	}

}
