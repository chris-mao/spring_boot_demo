/**
 * 
 */
package com.example.demo.price.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.price.entity.PriceListHeader;
import com.example.demo.price.service.PriceService;
import com.github.pagehelper.PageInfo;

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
	
	@Resource
	private PriceService priceService;
	
	@GetMapping("")
	@RequiresPermissions("price:list")
	public String findAllPriceList(@RequestParam(defaultValue = "1") int pageNum, Model model) {
		PageInfo<PriceListHeader> customers = this.priceService.findAll(pageNum);
		model.addAttribute("customers", customers);
		return "price/index";
	}
	
	@GetMapping("/{id}")
	@RequiresPermissions("price:detail")
	public String findPriceList(@PathVariable("id") Integer id, HttpServletRequest request, Model model) {
		PriceListHeader customer = this.priceService.findById(id);
		if (null != customer) {
			model.addAttribute("customer", customer);
		}
		return "price/detail";
	}

}
