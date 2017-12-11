package com.jrsoft.auth.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	/**
	 * 系统权限页面访问入口
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@GetMapping({ "", "/index" })
	@RequiresPermissions("authPermission:list")
	public String permissionList(@RequestParam(defaultValue = "1") int page, Model model) {
		return "auth/permission";
	}
}
