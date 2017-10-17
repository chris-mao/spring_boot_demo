/**
 * 
 */
package com.jrsoft.order.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jrsoft.order.service.ShoppingCartService;

/**
 * 购物车管理控制器类
 * 
 * com.jrsoft.order.controller ShoppingCartController
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {
	
	@Resource
	private ShoppingCartService shoppingCartService;
	
	/**
	 * 
	 * @return
	 */
	@GetMapping({"", "/index"})
	public String showShoppingCart() {
		return "order/cart/index";
	}
	
	/**
	 * 
	 * @return
	 */
	@PostMapping("/add")
	public String putItemIntoShoppingCart() {
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public String removeItemFromShoppingCart() {
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public String updateExistItem() {
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public String checkOutFromShoppingCart() {
		return null;
	}

}
