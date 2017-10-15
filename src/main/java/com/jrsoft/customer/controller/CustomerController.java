/**
 * 
 */
package com.jrsoft.customer.controller;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.jrsoft.app.exception.DataNotFoundException;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.auth.service.AuthRoleService;
import com.jrsoft.customer.entity.CustomerAccount;
import com.jrsoft.customer.service.CustomerService;
import com.jrsoft.employee.entity.Employee;
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
	 * @param page
	 * @param model
	 * @return
	 */
	@GetMapping({ "", "/index" })
	@RequiresPermissions("customer:list")
	public String findAllCustomer(@RequestParam(defaultValue = "1") int page, Model model) {
		Subject credential = SecurityUtils.getSubject();
		AuthUser user = (AuthUser) credential.getPrincipal();
		System.out.println(user);
		if (true == credential.hasRole(AuthRoleService.ADMINISTRAOR)) { // 系统管理员
			PageInfo<CustomerAccount> customers = this.customerService.findAll(page);
			model.addAttribute("page", customers);
		} else if (true == credential.hasRole(AuthRoleService.CUSTOMER)) { // 销售客户
			List<CustomerAccount> customers = this.customerService.findAllByCredential(user);
			if (customers.size() == 1) { // 如果只有一个客户信息，直接跳转到客户详情页面
				return "redirect:/customers/" + customers.iterator().next().getCustomerId();
			}
			model.addAttribute("page", new PageInfo<CustomerAccount>(customers));

		} else if ((true == credential.hasRole(AuthRoleService.CUSTOMER_SERVICE_REPRESENTATIVE))
				|| (true == credential.hasRole(AuthRoleService.SALES_REPRESENTATIVE))) { // 客服代表或销售代表
			Employee employee = this.employeeService.findOneByCredential(user);
			if (null == employee) {
				model.addAttribute("page", new PageInfo<CustomerAccount>());
			} else {
				model.addAttribute("page",
						new PageInfo<CustomerAccount>(this.customerService.findAllByEmployee(employee)));
			}
		} else {
			model.addAttribute("page", new PageInfo<CustomerAccount>());
		}

		return "customer/index";
	}

	/**
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws UnauthorizedException,
	 *             DataNotFoundException
	 */
	@GetMapping("/{id}")
	@RequiresPermissions("customer:detail")
	public String viewCustomer(@PathVariable("id") Integer id, Model model)
			throws UnauthorizedException, DataNotFoundException {

		// 判断客户列表中是否存在某个客户的id与指定的id相匹配
		class CustomerMatcher {
			public boolean isEmpty(List<CustomerAccount> customers) {
				return (null == customers) || (0 == customers.size());
			}

			public CustomerAccount matched(List<CustomerAccount> customers, int id) {
				CustomerAccount customer = null;
				Iterator<CustomerAccount> iterator = customers.iterator();
				while (iterator.hasNext()) {
					customer = iterator.next();
					if (customer.getCustomerId() == id) {
						return customer;
					}
				}
				return null;
			}
		}

		CustomerAccount customer = null;
		CustomerMatcher customerMatcher = new CustomerMatcher();
		Subject credential = SecurityUtils.getSubject();
		AuthUser user = (AuthUser) credential.getPrincipal();

		if (true == credential.hasRole(AuthRoleService.ADMINISTRAOR)) { // 系统管理员
			customer = new CustomerAccount();
			customer.setCustomerId(id);
			customer = this.customerService.findOne(customer);
		} else if (true == credential.hasRole(AuthRoleService.CUSTOMER)) { // 销售客户
			// TODO:把客户信息写封装在AuthUserDecorator类中，不用再次从数据库中读取
			List<CustomerAccount> customers = this.customerService.findAllByCredential(user);
			if (true == customerMatcher.isEmpty(customers)) {
				throw new DataNotFoundException("当前登录帐号未曾绑定任何客户资料！");
			}
			customer = customerMatcher.matched(customers, id);
			if (null == customer) {
				throw new UnauthorizedException("作为我们的销售客户，您仅可以查看自己的资料，无权查看其他客户资料！");
			}
		} else if ((true == credential.hasRole(AuthRoleService.CUSTOMER_SERVICE_REPRESENTATIVE))
				|| (true == credential.hasRole(AuthRoleService.SALES_REPRESENTATIVE))) { // 客服代表或销售代表
			Employee employee = this.employeeService.findOneByCredential(user);
			if (null == employee) {
				throw new DataNotFoundException("当前登录帐号未曾绑定任何员工，无法进一步判断是否有权限查看此客户资料！");
			}
			List<CustomerAccount> customers = this.customerService.findAllByEmployee(employee);
			if (true == customerMatcher.isEmpty(customers)) {
				throw new DataNotFoundException("当前登录帐号未曾绑定任何客户资料！");
			}
			customer = customerMatcher.matched(customers, id);
			if (null == customer) {
				throw new UnauthorizedException("作为客服代表或是销售代表，您仅可以查看分配到您名下的客户资料，无权查看其他客户资料！");
			}
		}

		model.addAttribute("customer", customer);
		// 获取客户对应的客服及销售人员
		model.addAttribute("employeese", employeeService.findAllByCustomer(customer));
		// 获取BILL TO上可用的价格清单
		model.addAttribute("priceList", priceService.findAllAvailablePriceListsByCustomerSite(customer.getBillTo()));
		return "customer/detail";
	}

}
