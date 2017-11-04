/**
 * 
 */
package com.jrsoft.order.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jrsoft.app.exception.DataNotFoundException;

/**
 * com.jrsoft.order.controller OrderController
 *
 * 订单管理控制器类
 * 
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/orders")
public class OrderController {

	@GetMapping({ "", "/index" })
	public String findUnclosedOrders() {
		return "order/index";
	}

	@GetMapping("/{id}")
	@RequiresPermissions("order:detail")
	public String viewOrder(@PathVariable("id") int id, Model model) throws DataNotFoundException {
		return "order/detail";
	}

	@GetMapping("/confirmation")
	@RequiresPermissions("order:save")
	public String ConfirmOrder() {
		return "order/confirmation";
	}

	@PostMapping("/save")
	@RequiresPermissions("order:save")
	public String saveOrder() {
		return "order/save";
	}

	@GetMapping("/query")
	@RequiresPermissions("order:query")
	public String queryForm() {
		return "order/query";
	}

	@GetMapping("/import")
	@RequiresPermissions("order:import")
	public String importOrder() {
		return "order/import";
	}

	@GetMapping("/export")
	@RequiresPermissions("order:export")
	public String exportOrder() {
		return "order/export";
	}

	@GetMapping("/acknowledgement")
	@RequiresPermissions("order:acknowledgement")
	public String downloadAcknowledgement() {
		return "order/acknowledgement";
	}
}
