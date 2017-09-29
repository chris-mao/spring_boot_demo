package com.example.demo.auth.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.auth.entity.AuthPermission;
import com.example.demo.auth.service.AuthPermissionService;
import com.github.pagehelper.PageInfo;

/**
 * 系统权限管理摈制器类
 * 
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
	
	@Resource
	private AuthPermissionService authPermissionService;
	
	/**
	 * 
	 * @param pageNum
	 * @param model
	 * @return
	 */
	@GetMapping("")
	@RequiresPermissions("authPermission:list")
	public String findAllPermission(@RequestParam(defaultValue = "1") int pageNum, Model model) {
		PageInfo<AuthPermission> page = this.authPermissionService.findAll(pageNum);
		model.addAttribute("page", page);
		return "auth/permission/index";
	}
	
	/**
	 * 
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping("/{id}")
	@RequiresPermissions("authPermission:detail")
	public String findPermission(@PathVariable("id") Integer id, HttpServletRequest request, Model model) {
		AuthPermission permission = this.authPermissionService.findById(id);
		if (null != permission) {
			model.addAttribute("permission", permission);
		}
		return "auth/permission/detail";
	}
	
	@GetMapping("/new")
	@RequiresPermissions("authPermission:new")
	public String newPermission(Model model) {
		model.addAttribute("permission", new AuthPermission());
		return "auth/permission/new";
	}
	
	/**
	 * 
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping("/{id}/edit")
	@RequiresPermissions("authPermission:edit")
	public String editRole(@PathVariable("id") Integer id, HttpServletRequest request, Model model) {
		AuthPermission permission = this.authPermissionService.findById(id);
		if (null != permission) {
			model.addAttribute("permission", permission);
		}
		return "auth/permission/edit";
	}
	
	/**
	 * 
	 * @param permission
	 * @param request
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping("/save")
	@RequiresPermissions("authPermission:save")
	public String savePermission(@Valid AuthPermission permission, HttpServletRequest request, BindingResult result, Model model) {
		model.addAttribute("permission", permission);
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				System.out.println(error.getCode() + "---" + error.getArguments() + "---" + error.getDefaultMessage());
			}
			if ("insert".equals(request.getParameter("action"))) {
				return "auth/role/new";
			}
			if ("update".equals(request.getParameter("action"))) {
				return "auth/role/edit";
			}
		}
		if ("insert".equals(request.getParameter("action"))) {
			this.authPermissionService.insert(permission);
		}
		if ("update".equals(request.getParameter("action"))) {
			this.authPermissionService.update(permission);
		}
		return "auth/role/save";
	}
	
	/**
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@PostMapping("/{id}/del")
	@RequiresPermissions("authPermission:delete")
	public String deletePermission(@PathVariable("id") Integer id, HttpServletRequest request) {
		this.authPermissionService.delete(id);
		return "redirect:/permissions";
	}

}
