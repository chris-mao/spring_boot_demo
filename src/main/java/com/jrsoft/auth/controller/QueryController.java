/**
 * 
 */
package com.jrsoft.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;
import com.jrsoft.auth.entity.AuthPermission;
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.auth.service.AuthPermissionService;
import com.jrsoft.auth.service.AuthRoleService;

/**
 * com.jrsoft.auth.controller QueryController
 * 
 * 查询控制器类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/query")
public class QueryController {

	/**
	 * 
	 */
	@Autowired
	private AuthRoleService authRoleService;

	/**
	 * 
	 */
	@Autowired
	private AuthPermissionService authPermissionService;

	@GetMapping("/users/quick-search")
	public String userQuickSearch(Model model) {
		// List<AuthUser> users = this.authUserService.findByNameFuzzily("cm%");
		// System.out.println(users.size());
		// model.addAttribute("page", users);
		return "auth/user/index";
	}

	@GetMapping("/roles/quick-search")
	public String roleQuickSearch(Model model) {
		PageInfo<AuthRole> roles = this.authRoleService.findAll(1);
		model.addAttribute("page", roles);
		return "auth/role/index";
	}

	@GetMapping("/permissions/quick-search")
	public String permissionQuickSearch(Model model) {
		PageInfo<AuthPermission> permissions = this.authPermissionService.findAll(1);
		model.addAttribute("page", permissions);
		return "auth/permission/index";
	}

}
