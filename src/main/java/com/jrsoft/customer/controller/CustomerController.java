/**
 * 
 */
package com.jrsoft.customer.controller;

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
import com.jrsoft.customer.entity.CustomerAccount;
import com.jrsoft.customer.service.CustomerService;
import com.jrsoft.employee.service.EmployeeService;
import com.jrsoft.price.service.PriceService;

/**
 * 客户数据维护控制器类
 * 
 * com.jrsoft.customer.controller CustomerController
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/customers")
public class CustomerController {

	/**
	 * 
	 */
	@Resource
	private CustomerService customerService;

	/**
	 * 
	 */
	@Resource
	private EmployeeService employeeService;

	/**
	 * 
	 */
	@Resource
	private PriceService priceService;

	/**
	 * 
	 * @param pageNum
	 * @param model
	 * @return
	 */
	@GetMapping("")
	@RequiresPermissions("customer:list")
	public String findAllCustomer(@RequestParam(defaultValue = "1") int pageNum, Model model) {
		PageInfo<CustomerAccount> customers = this.customerService.findAll(pageNum);
		model.addAttribute("page", customers);
		return "customer/index";
	}

	/**
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws DataNotFoundException
	 */
	@GetMapping("/{id}")
	@RequiresPermissions("customer:detail")
	public String findCustomer(@PathVariable("id") Integer id, Model model) throws DataNotFoundException {
		CustomerAccount c = new CustomerAccount();
		c.setCustomerId(id);
		CustomerAccount customer = this.customerService.findOne(c);
		if (null == customer) {
			throw new DataNotFoundException();
		}

		model.addAttribute("customer", customer);
		// 获取客户对应的客服及销售人员
		model.addAttribute("employeese", employeeService.findAllByCustomer(customer));
		// 获取BILL TO上可用的价格清单
		model.addAttribute("priceList", priceService.findAllAvailablePriceListsByCustomerSite(customer.getBillTo()));
		return "customer/detail";
	}

}
