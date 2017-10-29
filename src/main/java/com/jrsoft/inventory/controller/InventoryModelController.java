/**
 * 
 */
package com.jrsoft.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.jrsoft.inventory.entity.InventoryModel;
import com.jrsoft.inventory.service.InventoryModelService;

/**
 * com.jrsoft.inventory.controller ItemController
 *
 * 工厂型号控制器类
 * 
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/models")
public class InventoryModelController {

	@Autowired
	private InventoryModelService inventoryModelService;

	@GetMapping({ "", "/index" })
	@RequiresPermissions("model:list")
	public String findAllPriceList(@RequestParam(defaultValue = "1") int page, Model model) {
		PageInfo<InventoryModel> items = inventoryModelService.findAll(page);
		model.addAttribute("page", items);
		return "model/index";
	}

}
