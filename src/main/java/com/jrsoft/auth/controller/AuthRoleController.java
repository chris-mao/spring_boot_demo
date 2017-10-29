package com.jrsoft.auth.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.jrsoft.app.exception.DataNotFoundException;
import com.jrsoft.auth.entity.AuthPermission;
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.auth.service.AuthPermissionService;
import com.jrsoft.auth.service.AuthRoleService;

/**
 * com.jrsoft.auth.controller AuthRoleController
 *
 * 系统角色控制器类
 * 
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/roles")
public class AuthRoleController {

	@Autowired
	private AuthRoleService authRoleService;

	@Autowired
	private AuthPermissionService authPermissionService;

	/**
	 * 按ID查询角色，如果角色不存在则抛出{@link DataNotFoundException}异常
	 * 
	 * @param id
	 * @return
	 * @throws DataNotFoundException
	 */
	private AuthRole findRole(int id) throws DataNotFoundException {
		AuthRole r = new AuthRole(id);
		AuthRole role = this.authRoleService.findOne(r);
		if (null == role) {
			throw new DataNotFoundException("您指定的角色不存在！ID：" + id);
		}
		return role;
	}

	/**
	 * 系统角色列表页面
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
	 * 查看角色详情页面
	 * 
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 * @throws DataNotFoundException
	 */
	@GetMapping("/{id}")
	@RequiresPermissions("authRole:detail")
	public String viewRole(@PathVariable("id") int id, HttpServletRequest request, Model model)
			throws DataNotFoundException {
		AuthRole role = findRole(id);
		model.addAttribute("role", role);
		// 获取角色权限
		model.addAttribute("myPermissions", authPermissionService.findAllByRole(role));
		// 获取所有有效权限
		model.addAttribute("permissions", authPermissionService.findAllAvailable());
		return "auth/role/detail";
	}

	/**
	 * 创建新角色页面
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
	 * 编辑角色页面
	 * 
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 * @throws DataNotFoundException
	 */
	@GetMapping("/{id}/edit")
	@RequiresPermissions("authRole:edit")
	public String editRole(@PathVariable("id") int id, HttpServletRequest request, Model model)
			throws DataNotFoundException {
		model.addAttribute("authRole", findRole(id));
		return "auth/role/edit";
	}

	/**
	 * 保存角色页面，新增角色之前会判断角色名称是否已存在
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
	 * 分配权限到指定角色，先将该角色所有权限删除，再重新分配
	 * 
	 * Ajax调用
	 * 
	 * @param id
	 * @param permissionIds
	 * @return
	 */
	@PostMapping("/{id}/permissions")
	@ResponseBody
	public String assignRoles(@PathVariable("id") int id, @RequestBody List<Integer> permissionIds) {
		System.out.println("权限分配：ROLE ==> " + id + "   PERMISSIONS ==>" + permissionIds.toString());

		AuthRole role = new AuthRole(id);
		AuthPermission permission = new AuthPermission();
		this.authRoleService.removeAllPermissions(role);

		Iterator<Integer> iterator = permissionIds.iterator();
		while (iterator.hasNext()) {
			permission.setPermissionId(iterator.next());
			authRoleService.addPermission(role, permission);
		}
		return "ok";
	}

	/**
	 * 删除角色页面
	 * 
	 * todo: 删除之前需要判断角色是否还有用户使用该权限
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@GetMapping("/{id}/del")
	@RequiresPermissions("authRole:delete")
	public String deleteRole(@PathVariable("id") int id, HttpServletRequest request) {
		this.authRoleService.delete(id);
		return "redirect:/roles";
	}

	/**
	 * 以JSON格式返回所有有效的系统角色
	 * 
	 * @return
	 */
	@ResponseBody
	@GetMapping("/json")
	public List<AuthRole> jsonData() {
		return this.authRoleService.findAllAvailable();
	}

}
