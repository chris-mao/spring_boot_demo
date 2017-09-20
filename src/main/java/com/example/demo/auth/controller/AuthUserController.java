/**
 * 
 */
package com.example.demo.auth.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * com.example.demo.auth.controller AuthUserController
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/user")
public class AuthUserController {
	
	@GetMapping("/")
	@RequiresPermissions("authUser:list")
	public String index() {
		return "auth/user/index";
	}

}
