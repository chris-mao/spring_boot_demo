/**
 * 
 */
package com.jrsoft.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * com.jrsoft.order.controller ShoppingCartController
 *
 * 购物车管理控制器类
 * 
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

	/**
	 * 
	 * @return
	 */
	@GetMapping({ "", "/index" })
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
