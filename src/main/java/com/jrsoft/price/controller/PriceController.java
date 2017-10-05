/**
 * 
 */
package com.jrsoft.price.controller;

import javax.annotation.Resource;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.jrsoft.app.exception.DataNotFoundException;
import com.jrsoft.customer.service.CustomerService;
import com.jrsoft.price.entity.PriceListHeader;
import com.jrsoft.price.service.PriceService;

/**
 * 销售价格数据维护控制器类
 * 
 * com.jrsoft.price.controller PriceController
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/prices")
public class PriceController {

	/**
	 * 
	 */
	@Resource
	private PriceService priceService;

	/**
	 * 
	 */
	@Resource
	private CustomerService customerService;

	/**
	 * 
	 * @param pageNum
	 * @param model
	 * @return
	 */
	@GetMapping("")
	@RequiresPermissions("price:list")
	public String findAllPriceList(@RequestParam(defaultValue = "1") int pageNum, Model model) {
		PageInfo<PriceListHeader> page = this.priceService.findAll(pageNum);
		model.addAttribute("page", page);
		return "price/index";
	}

	/**
	 * 
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 * @throws DataNotFoundException
	 */
	@GetMapping("/{id}")
	@RequiresPermissions("price:detail")
	public String findPriceList(@PathVariable("id") Integer id, Model model) throws DataNotFoundException {
		PriceListHeader h = new PriceListHeader();
		h.setHeaderId(id);
		PriceListHeader priceHeader = this.priceService.findOne(h);
		if (null == priceHeader) {
			throw new DataNotFoundException();
		}
		model.addAttribute("priceHeader", priceHeader);
		model.addAttribute("lines", priceService.findAllPriceLines(priceHeader));
		model.addAttribute("customers", customerService.findAllQualifiedCustomers(priceHeader));
		return "price/detail";
	}

}
