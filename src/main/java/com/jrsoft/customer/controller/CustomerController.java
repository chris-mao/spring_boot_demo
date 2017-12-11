package com.jrsoft.customer.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>客户控制器类，提供客户维护页面入口</p>
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/customers")
public class CustomerController {

	/**
	 * 客户管理页面访问入口
	 * 
	 * @return
	 */
	@GetMapping({ "", "/index" })
	@RequiresPermissions("customer:list1")
	public String customerList() {
		return "customer/index";
	}
}
