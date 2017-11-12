package com.jrsoft.auth.controller;

import javax.annotation.Resource;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jrsoft.auth.service.AuthUserService;

/**
 * <p>
 * 系统用户控制器类，提供系统用户维护页面入口
 * </p>
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

	/**
	 * 系统用户页面访问入口
	 * 
	 */
	@GetMapping({ "", "/", "/index" })
	@RequiresPermissions("authUser:list")
	public String userList() {
		return "auth/user";
	}	
}
