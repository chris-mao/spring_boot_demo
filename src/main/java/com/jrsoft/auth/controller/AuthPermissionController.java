package com.jrsoft.auth.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.jrsoft.app.exception.DataNotFoundException;
import com.jrsoft.auth.entity.AuthPermission;
import com.jrsoft.auth.service.AuthPermissionService;

/**
 * com.jrsoft.auth.controller AuthPermissionController
 *
 * 系统权限管理摈制器类
 * 
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/permissions")
public class AuthPermissionController {

	@Autowired
	private AuthPermissionService authPermissionService;

	/**
	 * 按ID查询权限，如果权限不存在则抛出{@link DataNotFoundException}异常
	 * 
	 * @param id
	 * @return
	 * @throws DataNotFoundException
	 */
	private AuthPermission findPermission(int id) throws DataNotFoundException {
		AuthPermission p = new AuthPermission(id);
		AuthPermission permission = this.authPermissionService.findOne(p);
		if (null == permission) {
			throw new DataNotFoundException("您指定的权限不存在！ID：" + id);
		}
		return permission;
	}

	/**
	 * 系统权限列表页面
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
	 * 查看权限详情页面
	 * 
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 * @throws DataNotFoundException
	 */
	@GetMapping("/{id}")
	@RequiresPermissions("authPermission:detail")
	public String viewPermission(@PathVariable("id") int id, HttpServletRequest request, Model model)
			throws DataNotFoundException {
		model.addAttribute("authPermission", findPermission(id));
		return "auth/permission/detail";
	}

	/**
	 * 创建新权限页面
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/new")
	@RequiresPermissions("authPermission:new")
	public String newPermission(Model model) {
		model.addAttribute("authPermission", new AuthPermission());
		return "auth/permission/new";
	}

	/**
	 * 编辑权限页面
	 * 
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 * @throws DataNotFoundException
	 */
	@GetMapping("/{id}/edit")
	@RequiresPermissions("authPermission:edit")
	public String editRole(@PathVariable("id") int id, HttpServletRequest request, Model model)
			throws DataNotFoundException {
		model.addAttribute("authPermission", findPermission(id));
		return "auth/permission/edit";
	}

	/**
	 * 保存权限页面，新增权限之前会判断权限名称是否已存在
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
	 * 删除权限页面
	 * 
	 * todo:删除之前需要判断权限是否还有角色或是用户使用到该权限
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@GetMapping("/{id}/del")
	@RequiresPermissions("authPermission:delete")
	public String deletePermission(@PathVariable("id") int id, HttpServletRequest request) {
		this.authPermissionService.delete(id);
		return "redirect:/permissions";
	}

	/**
	 * 以JSON格式返回所有有效的系统权限
	 * 
	 * @return
	 */
	@GetMapping("/json")
	@ResponseBody
	public List<AuthPermission> jsonData() {
		return this.authPermissionService.findAllAvailable();
	}

}
