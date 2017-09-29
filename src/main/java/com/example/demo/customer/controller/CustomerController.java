/**
 * 
 */
package com.example.demo.customer.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 客户数据维护控制器类
 * 
 * com.example.demo.customer.controller CustomerController
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/customers")
public class CustomerController {
	
	@GetMapping("")
	@RequiresPermissions("customer:list")
	public String findAllCustomer() {
		return "customer/index";
	}
	
	@GetMapping("/{id}")
	@RequiresPermissions("customer:detail")
	public String findCustomer(@PathVariable("id") Integer id, HttpServletRequest request, Model model) {
		return "customer/detail";
	}

}
