/**
 * 
 */
package com.example.demo.price.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 销售价格数据维护控制器类
 * 
 * com.example.demo.price.controller PriceController
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/prices")
public class PriceController {
	
	@GetMapping("")
	@RequiresPermissions("price:list")
	public String findAllPriceList() {
		return "price/index";
	}
	
	@GetMapping("/{id}")
	@RequiresPermissions("price:detail")
	public String findPriceList(@PathVariable("id") Integer id, HttpServletRequest request, Model model) {
		return "price/detail";
	}

}
