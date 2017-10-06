/**
 * 
 */
package com.jrsoft.order.controller;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jrsoft.app.exception.DataNotFoundException;
import com.jrsoft.order.service.OrderService;

/**
 * 订单管理控制器类
 * 
 * com.jrsoft.order.controller OrderController
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/orders")
public class OrderController {
	
	@Resource
	private OrderService orderService;

	@GetMapping({ "", "/index" })
	public String showUnclosedOrders() {
		return null;
	}

	@GetMapping("/{id}")
	@RequiresPermissions("order:detail")
	public String findOrder(@PathVariable("id") Integer id, Model model) throws DataNotFoundException {
		return null;
	}
	
	@RequiresPermissions("order:save")
	public String orderConfirmation() {
		return null;
	}
	
	@PostMapping("/save")
	@RequiresPermissions("order:save")
	public String saveOrder() {
		return null;
	}
	
	@GetMapping("/query")
	@RequiresPermissions("order:query")
	public String queryForm() {
		return null;
	}
}
