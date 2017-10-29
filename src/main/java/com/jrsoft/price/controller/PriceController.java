/**
 * 
 */
package com.jrsoft.price.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

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
import com.jrsoft.customer.entity.CustomerSite;
import com.jrsoft.customer.service.CustomerService;
import com.jrsoft.employee.entity.Employee;
import com.jrsoft.employee.service.EmployeeService;
import com.jrsoft.price.entity.PriceListHeader;
import com.jrsoft.price.service.PriceService;

/**
 * com.jrsoft.price.controller PriceController
 *
 * 销售价格数据维护控制器类
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
	@Autowired
	private PriceService priceService;

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
	 * 根据传入的客户清单，获取这些客户的可用的价格列表
	 * 
	 * @param customers
	 * @return
	 * @throws DataNotFoundException
	 *             如果传入的客户清单为空，则抛出此异常
	 */
	private PageInfo<PriceListHeader> findCustomerPrice(List<CustomerAccount> customers) throws DataNotFoundException {
		if ((null == customers) || (0 == customers.size())) {
			throw new DataNotFoundException("当前登录帐号未曾绑定任何客户资料，无法进一步加载客户的价格表数据！");
		}

		CustomerAccount customer;
		Set<CustomerSite> billTo = null;
		Iterator<CustomerAccount> iter = customers.iterator();
		while (iter.hasNext()) {
			customer = iter.next();
			// 查询客户所有的BILL TO
			if (null == billTo) {
				billTo = customerService.findAllBillTo(customer.getCustomerId());
			} else {
				billTo.addAll(customerService.findAllBillTo(customer.getCustomerId()));
			}
		}
		// 根据BILL TO查询所有可用价格表
		return new PageInfo<PriceListHeader>(priceService.findAllAvailablePriceListsByCustomerSite(billTo));
	}

	/**
	 * 价格表列表页面
	 * 
	 * 根据当前登录帐号的角色列出允许查看的价格表列表
	 * 
	 * <pre>
	 * 系统管理员：列出所有价格表
	 * </pre>
	 * 
	 * <pre>
	 * 销售代表或是客服代表：仅列出该代表负责的客户的价格表
	 * </pre>
	 * 
	 * <pre>
	 * 销售客户：仅列出与当前登录帐号绑定的客户的价格表，如果仅有一个价格表，则直接跳转到价格表详情页面
	 * </pre>
	 * 
	 * @param page
	 * @param model
	 * @return
	 * @throws DataNotFoundException
	 */
	@GetMapping({ "", "/index" })
	@RequiresPermissions("price:list")
	public String findAllPriceList(@RequestParam(defaultValue = "1") int page, Model model)
			throws DataNotFoundException {

		if (true == AuthHelper.getCredential().hasRole(AuthRoleService.ADMINISTRAOR)) { // 系统管理员，查看所有价格表
			PageInfo<PriceListHeader> prices = priceService.findAll(page);
			model.addAttribute("page", prices);
		} else if (true == AuthHelper.getCredential().hasRole(AuthRoleService.CUSTOMER)) { // 销售客户，只看自己的价格表
			// TODO:把客户信息写封装在AuthUserDecorator类中，不用再次从数据库中查询
			List<CustomerAccount> customers = customerService.findAllByCredential(AuthHelper.getCurrentUser());
			PageInfo<PriceListHeader> priceLists = findCustomerPrice(customers);
			if (1 == priceLists.getList().size()) {
				return "redirect:/prices/" + priceLists.getList().iterator().next().getHeaderId();
			}
			model.addAttribute("page", priceLists);
		} else if ((true == AuthHelper.getCredential().hasRole(AuthRoleService.CUSTOMER_SERVICE_REPRESENTATIVE))
				|| (true == AuthHelper.getCredential().hasRole(AuthRoleService.SALES_REPRESENTATIVE))) { // 客服代表或销售代表，只看自己负责的客户的价格表
			Employee emp = employeeService.findOneByCredential(AuthHelper.getCurrentUser());
			if (null == emp) {
				model.addAttribute("page", new PageInfo<PriceListHeader>());
			} else {
				model.addAttribute("page", findCustomerPrice(customerService.findAllByEmployee(emp)));
			}
		}
		return "price/index";
	}

	/**
	 * 价格表详情页面，包括可以使用此价格表的客户清单
	 * 
	 * 根据当前登录帐号的角色判断是否允许查看
	 * 
	 * <pre>
	 * 系统管理员：查看所有客户的价格表详情
	 * </pre>
	 * 
	 * <pre>
	 * 销售代表或是客服代表：仅允许查看该代表负责的客户的价格表详情
	 * </pre>
	 * 
	 * <pre>
	 * 销售客户：仅允许查看与当前登录帐号绑定的客户的价格表详情
	 * </pre>
	 * 
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 * @throws DataNotFoundException
	 */
	@GetMapping("/{id}")
	@RequiresPermissions("price:detail")
	public String viewPriceList(@PathVariable("id") int id, Model model) throws DataNotFoundException {
		PriceListHeader plh = new PriceListHeader(id);
		PriceListHeader priceHeader = priceService.findOne(plh);
		if (null == priceHeader) {
			throw new DataNotFoundException("您指定的价格表不存在！ID：" + plh.getHeaderId());
		}

		model.addAttribute("priceHeader", priceHeader);
		if (true == AuthHelper.getCredential().hasRole(AuthRoleService.ADMINISTRAOR)) { // 系统管理员，查看所有价格表明细
			model.addAttribute("lines", priceService.findAllPriceLines(priceHeader));
			model.addAttribute("customers", customerService.findAllQualifiedCustomers(priceHeader));
		} else if (true == AuthHelper.getCredential().hasRole(AuthRoleService.CUSTOMER)) { // 销售客户，只看自己的价格表明细
			// 获取此用使用此价格表的客户清单，再与当前登录用户绑定的客户进行比较
			List<CustomerAccount> qualifiedCustomers = customerService.findAllQualifiedCustomers(plh);
			Iterator<CustomerAccount> iterator = qualifiedCustomers.iterator();
			while (iterator.hasNext()) {
				if (null != customerService.isMine(AuthHelper.getCurrentUser(), iterator.next().getCustomerId())) {
					model.addAttribute("lines", priceService.findAllPriceLines(priceHeader));
					break;
				}
			}
		} else if ((true == AuthHelper.getCredential().hasRole(AuthRoleService.CUSTOMER_SERVICE_REPRESENTATIVE))
				|| (true == AuthHelper.getCredential().hasRole(AuthRoleService.SALES_REPRESENTATIVE))) { // 客服代表或销售代表，只看自己负责的客户的价格表
			Employee employee = employeeService.findOneByCredential(AuthHelper.getCurrentUser());
			if (null == employee) {
				throw new DataNotFoundException("当前登录帐号未曾绑定任何员工，无法进一步判断是否有权限查看此价格资料！");
			}
			// 获取此用使用此价格表的客户清单，再与当前登录用户绑定的员工名下的客户进行比较
			List<CustomerAccount> qualifiedCustomers = customerService.findAllQualifiedCustomers(plh);
			Iterator<CustomerAccount> iterator = qualifiedCustomers.iterator();
			while (iterator.hasNext()) {
				if (null != customerService.isMine(employee, iterator.next().getCustomerId())) {
					model.addAttribute("lines", priceService.findAllPriceLines(priceHeader));
					model.addAttribute("customers", customerService.findAllQualifiedCustomers(priceHeader));
					break;
				}
			}
		}
		return "price/detail";
	}

}
