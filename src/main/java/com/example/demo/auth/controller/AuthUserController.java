/**
 * 
 */
package com.example.demo.auth.controller;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	@RequiresPermissions("authUser:list")
	public String findAllUser(@RequestParam(defaultValue="1")int pageNum, Model model) {
		PageInfo<AuthUser> page = this.authUserService.findAll(pageNum);
		model.addAttribute("page", page);
		return "auth/user/index";
	}
	
	@GetMapping("/{id}")
	@RequiresPermissions("authUser:detail")
	public String findUser(@PathVariable("id")Integer id, Model model) {
		model.addAttribute("user", this.authUserService.findById(id));
		return "auth/user/detail";
	}
	
	@GetMapping("/new")
	@RequiresPermissions("authUser:new")
	public String newUser() {
		return "auth/user/new";
	}
	
	@GetMapping("/edit/{id}")
	public String editUser(@PathVariable("id")Integer id, Model model) {
		model.addAttribute("user", this.authUserService.findById(id));
		return "auth/user/edit";
	}
	
	@PostMapping("/save")
	@RequiresPermissions("authUser:save")
	public String saveUser() {
		return "auth/user/save";
	}
	
	@PostMapping("/del/{id}")
	@RequiresPermissions("authUser:delete")
	public String deleteUser(@PathVariable("id")Integer id) {
		return "auth/user/delete";
	}

}
