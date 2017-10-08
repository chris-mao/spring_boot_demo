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
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.auth.service.AuthRoleService;

/**
 * 系统角色控制器类
 * 
 * com.jrsoft.auth.controller AuthRoleController
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/roles")
public class AuthRoleController {

	@Resource
	private AuthRoleService authRoleService;

	/**
	 * @param id
	 * @return
	 * @throws DataNotFoundException
	 */
	private AuthRole findRole(Integer id) throws DataNotFoundException {
		AuthRole r = new AuthRole();
		r.setRoleId(id);
		AuthRole role = this.authRoleService.findOne(r);
		if (null == role) {
			throw new DataNotFoundException();
		}
		return role;
	}

	/**
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@GetMapping({ "", "/index" })
	@RequiresPermissions("authRole:list")
	public String roleList(@RequestParam(defaultValue = "1") int page, Model model) {
		PageInfo<AuthRole> roles = this.authRoleService.findAll(page);
		model.addAttribute("page", roles);
		return "auth/role/index";
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
	@RequiresPermissions("authRole:detail")
	public String viewRole(@PathVariable("id") Integer id, HttpServletRequest request, Model model)
			throws DataNotFoundException {
		model.addAttribute("role", findRole(id));
		return "auth/role/detail";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/new")
	@RequiresPermissions("authRole:new")
	public String newRole(Model model) {
		model.addAttribute("authRole", new AuthRole());
		return "auth/role/new";
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
	@RequiresPermissions("authRole:edit")
	public String editRole(@PathVariable("id") Integer id, HttpServletRequest request, Model model)
			throws DataNotFoundException {
		model.addAttribute("authRole", findRole(id));
		return "auth/role/edit";
	}

	/**
	 * 
	 * @param authRole
	 * @param request
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping("/save")
	@RequiresPermissions("authRole:save")
	public String saveRole(@Valid AuthRole authRole, BindingResult result, HttpServletRequest request, Model model) {
		model.addAttribute("authRole", authRole);
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
			if (this.authRoleService.findOne(authRole) != null) { // 角色名已存在
				result.rejectValue("roleName", "duplicate", "此角色名已被使用，请使用其他角色名称");
				return "auth/role/new";
			}
			this.authRoleService.insert(authRole);
		}
		if ("update".equals(request.getParameter("action"))) {
			this.authRoleService.update(authRole);
		}
		return "auth/role/save";
	}

	/**
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@GetMapping("/{id}/del")
	@RequiresPermissions("authRole:delete")
	public String deleteRole(@PathVariable("id") Integer id, HttpServletRequest request) {
		this.authRoleService.delete(id);
		return "redirect:/roles";
	}

}
