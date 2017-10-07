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
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@GetMapping({"", "/index"})
	@RequiresPermissions("authRole:list")
	public String findAllRole(@RequestParam(defaultValue = "1") int page, Model model) {
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
	public String findRole(@PathVariable("id") Integer id, HttpServletRequest request, Model model) throws DataNotFoundException {
		AuthRole r = new AuthRole();
		r.setRoleId(id);
		AuthRole role = this.authRoleService.findOne(r);
		if (null == role) {
			throw new DataNotFoundException();
		}
		
			model.addAttribute("role", role);
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
	public String editRole(@PathVariable("id") Integer id, HttpServletRequest request, Model model) throws DataNotFoundException {
		AuthRole r = new AuthRole();
		r.setRoleId(id);
		AuthRole role = this.authRoleService.findOne(r);
		if (null == role) {
			throw new DataNotFoundException();
		}
			model.addAttribute("authRole", role);
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
	@PostMapping("/del/{id}")
	@RequiresPermissions("authRole:delete")
	public String deleteRole(@PathVariable("id") Integer id, HttpServletRequest request) {
		this.authRoleService.delete(id);
		return "redirect:/roles";
	}

}
