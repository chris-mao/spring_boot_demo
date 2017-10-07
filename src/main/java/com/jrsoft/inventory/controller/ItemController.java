/**
 * 
 */
package com.jrsoft.inventory.controller;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.jrsoft.inventory.entity.Item;
import com.jrsoft.inventory.service.ItemService;

/**
 * 工厂型号控制器类
 * 
 * com.jrsoft.inventory.controller ItemController
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/items")
public class ItemController {
	
	@Resource
	private ItemService itemService;
	
	@GetMapping({"", "/index"})
	@RequiresPermissions("item:list")
	public String findAllPriceList(@RequestParam(defaultValue = "1") int page, Model model) {
		PageInfo<Item> items = this.itemService.findAll(page);
		model.addAttribute("page", items);
		return "item/index";
	}

}
