/**
 * 
 */
package com.jrsoft.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.jrsoft.app.exception.DataNotFoundException;
import com.jrsoft.auth.helper.AuthHelper;
import com.jrsoft.auth.service.AuthRoleService;
import com.jrsoft.customer.entity.CustomerAccount;
import com.jrsoft.customer.service.CustomerService;
import com.jrsoft.employee.entity.Employee;
import com.jrsoft.employee.service.EmployeeService;
import com.jrsoft.price.service.PriceService;

/**
 * com.jrsoft.customer.controller CustomerController
 *
 * 客户数据维护控制器类
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
	@Autowired
	private CustomerService customerService;

	/**
	 * 
	 */
	@Autowired
	private EmployeeService employeeService;

	/**
	 * 
	 */
	@Autowired
	private PriceService priceService;

	/**
	 * 
	 * @param emp
	 * @return
	 */
	private PageInfo<CustomerAccount> findAllByEmployee(Employee emp) {
		if (null == emp) {
			return new PageInfo<CustomerAccount>();
		} else {
			return new PageInfo<CustomerAccount>(customerService.findAllByEmployee(emp));
		}
	}

	/**
	 * 客户列表页面
	 * 
	 * 根据当前登录帐号的角色列出允许查看的客户列表
	 * 
	 * <pre>
	 * 系统管理员：列出所有客户
	 * </pre>
	 * 
	 * <pre>
	 * 销售代表或是客服代表：仅列出该代表负责的客户
	 * </pre>
	 * 
	 * <pre>
	 * 销售客户：仅列出与当前登录帐号绑定的客户，如果仅绑定一个客户，则直接跳转到客户详情页面
	 * </pre>
	 * 
	 * 可以考虑，除了系统管理员，不给其他角色customer:list权限，因为客服代表或是销售代表可以在自己的详情页面中看到自己负责的客户列表，
	 * 无需再到这个页面中查看
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@GetMapping({ "", "/index" })
	@RequiresPermissions("customer:list")
	public String findAllCustomers(@RequestParam(defaultValue = "1") int page, Model model) {
		if (true == AuthHelper.getCredential().hasRole(AuthRoleService.ADMINISTRAOR)) { // 系统管理员，列出所有客户
			PageInfo<CustomerAccount> customers = customerService.findAll(page);
			model.addAttribute("page", customers);
		} else if (true == AuthHelper.getCredential().hasRole(AuthRoleService.CUSTOMER)) { // 销售客户，仅列出与当前登录帐号绑定的客户
			List<CustomerAccount> customers = customerService.findAllByCredential(AuthHelper.getCurrentUser());
			if (customers.size() == 1) { // 如果只有一个客户信息，直接跳转到客户详情页面
				return "redirect:/customers/" + customers.iterator().next().getCustomerId();
			}
			model.addAttribute("page", new PageInfo<CustomerAccount>(customers));

		} else if ((true == AuthHelper.getCredential().hasRole(AuthRoleService.CUSTOMER_SERVICE_REPRESENTATIVE))
				|| (true == AuthHelper.getCredential().hasRole(AuthRoleService.SALES_REPRESENTATIVE))) { // 客服代表或销售代表，仅列出自己负责的客户
			model.addAttribute("page",
					findAllByEmployee(employeeService.findOneByCredential(AuthHelper.getCurrentUser())));
		}

		return "customer/index";
	}

	/**
	 * 查看客户详情页面
	 * 
	 * 包括客户的地址信息，对应的接洽人员信息（客服代表、销售代表）以及可以使用的价格表
	 * 
	 * 根据当前登录帐号的角色判断是否允许查看
	 * 
	 * <pre>
	 * 系统管理员：查看所有客户的详情
	 * </pre>
	 * 
	 * <pre>
	 * 销售代表或是客服代表：仅允许查看该代表负责的客户的详情
	 * </pre>
	 * 
	 * <pre>
	 * 销售客户：仅允许查看与当前登录帐号绑定的客户的详情
	 * </pre>
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws UnauthorizedException,
	 *             DataNotFoundException
	 */
	@GetMapping("/{id}")
	@RequiresPermissions("customer:detail")
	public String viewCustomer(@PathVariable("id") int id, Model model)
			throws UnauthorizedException, DataNotFoundException {
		CustomerAccount customer = null;
		if (true == AuthHelper.getCredential().hasRole(AuthRoleService.ADMINISTRAOR)) { // 系统管理员，查看任一客户
			customer = new CustomerAccount(id);
			customer = customerService.findOne(customer);
			if (null == customer) {
				throw new DataNotFoundException("查询不到指定的客户资料！ID：" + id);
			}
		} else if (true == AuthHelper.getCredential().hasRole(AuthRoleService.CUSTOMER)) { // 销售客户，仅允许查看与当前登录帐号绑定的客户详情
			// TODO:把客户信息写封装在AuthUserDecorator类中，不用再次从数据库中查询
			customer = customerService.isMine(AuthHelper.getCurrentUser(), id);
			if (null == customer) {
				throw new UnauthorizedException("该客户尚未与当前的登录帐号绑定，您无权查看其资料！");
			}
		} else if ((true == AuthHelper.getCredential().hasRole(AuthRoleService.CUSTOMER_SERVICE_REPRESENTATIVE))
				|| (true == AuthHelper.getCredential().hasRole(AuthRoleService.SALES_REPRESENTATIVE))) { // 客服代表或销售代表，仅允许查看自己负责的客户详情
			// TODO:把客户信息写封装在AuthUserDecorator类中，不用再次从数据库中查询
			Employee employee = employeeService.findOneByCredential(AuthHelper.getCurrentUser());
			if (null == employee) {
				throw new DataNotFoundException("当前登录帐号未曾绑定任何员工，无法进一步判断是否有权限查看此客户资料！");
			}
			customer = customerService.isMine(employee, id);
			if (null == customer) {
				throw new UnauthorizedException("该客户尚未分配到您名下，您无权查看其资料！");
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
