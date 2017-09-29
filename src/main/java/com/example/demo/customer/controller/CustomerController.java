/**
 * 
 */
package com.example.demo.customer.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.customer.entity.CustomerAccount;
import com.example.demo.customer.service.CusotmerService;
import com.github.pagehelper.PageInfo;

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
	
	@Resource
	private CusotmerService customerService;
	
	@GetMapping("")
	@RequiresPermissions("customer:list")
	public String findAllCustomer(@RequestParam(defaultValue = "1") int pageNum, Model model) {
		PageInfo<CustomerAccount> customers = this.customerService.findAll(pageNum);
		model.addAttribute("customers", customers);
		return "customer/index";
	}
	
	@GetMapping("/{id}")
	@RequiresPermissions("customer:detail")
	public String findCustomer(@PathVariable("id") Integer id, HttpServletRequest request, Model model) {
		CustomerAccount customer = this.customerService.findById(id);
		if (null != customer) {
			model.addAttribute("customer", customer);
		}
		return "customer/detail";
	}

}
