package com.jrsoft.auth.controller;

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

import com.github.pagehelper.PageInfo;
import com.jrsoft.app.exception.DataNotFoundException;
import com.jrsoft.auth.entity.AuthPermission;
import com.jrsoft.auth.service.AuthPermissionService;

/**
 * 系统权限管理摈制器类
 * 
 * com.jrsoft.auth.controller AuthPermissionController
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
	 * @param id
	 * @return
	 * @throws DataNotFoundException
	 */
	private AuthPermission findPermission(Integer id) throws DataNotFoundException {
		AuthPermission p = new AuthPermission();
		p.setPermissionId(id);
		AuthPermission permission = this.authPermissionService.findOne(p);
		if (null == permission) {
			throw new DataNotFoundException();
		}
		return permission;
	}

	/**
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@GetMapping({ "", "/index" })
	@RequiresPermissions("authPermission:list")
	public String permissionList(@RequestParam(defaultValue = "1") int page, Model model) {
		PageInfo<AuthPermission> permissions = this.authPermissionService.findAll(page);
		model.addAttribute("page", permissions);
		return "auth/permission/index";
	}

	/**
	 * 
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 * @throws DataNotFoundException
	 */
	@GetMapping("/{id}")
	@RequiresPermissions("authPermission:detail")
	public String viewPermission(@PathVariable("id") Integer id, HttpServletRequest request, Model model)
			throws DataNotFoundException {
		model.addAttribute("authPermission", findPermission(id));
		return "auth/permission/detail";
	}

	@GetMapping("/new")
	@RequiresPermissions("authPermission:new")
	public String newPermission(Model model) {
		model.addAttribute("authPermission", new AuthPermission());
		return "auth/permission/new";
	}

	/**
	 * 
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 * @throws DataNotFoundException
	 */
	@GetMapping("/{id}/edit")
	@RequiresPermissions("authPermission:edit")
	public String editRole(@PathVariable("id") Integer id, HttpServletRequest request, Model model)
			throws DataNotFoundException {
		model.addAttribute("authPermission", findPermission(id));
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
	public String savePermission(@Valid AuthPermission permission, BindingResult result, HttpServletRequest request,
			Model model) {
		model.addAttribute("authPermission", permission);
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				System.out.println(error.getCode() + "---" + error.getArguments() + "---" + error.getDefaultMessage());
			}
			if ("insert".equals(request.getParameter("action"))) {
				return "auth/permission/new";
			}
			if ("update".equals(request.getParameter("action"))) {
				return "auth/permission/edit";
			}
		}
		if ("insert".equals(request.getParameter("action"))) {
			if (this.authPermissionService.findOne(permission) != null) { // 权限名已存在
				result.rejectValue("roleName", "duplicate", "此权限名已被使用，请使用其他权限名称");
				return "auth/permission/new";
			}
			this.authPermissionService.insert(permission);
		}
		if ("update".equals(request.getParameter("action"))) {
			this.authPermissionService.update(permission);
		}
		return "auth/permission/save";
	}

	/**
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@GetMapping("/{id}/del")
	@RequiresPermissions("authPermission:delete")
	public String deletePermission(@PathVariable("id") Integer id, HttpServletRequest request) {
		this.authPermissionService.delete(id);
		return "redirect:/permissions";
	}

}
