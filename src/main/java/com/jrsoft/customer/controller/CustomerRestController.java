/**
 * 
 */
package com.jrsoft.customer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jrsoft.common.EasyDataGrid;
import com.jrsoft.common.JsonResult;
import com.jrsoft.customer.entity.Customer;
import com.jrsoft.customer.service.CustomerService;

/**
 * <p>
 * 客户控制器类，提供客户资料维护方法接口
 * <dl>
 * <dt>GET: customers/api/list?page=1&rows=20&searchValue=</dt>
 * <dd>按页码返回（符合查询条件或是全部）客户数据列表，需要拥有<code>customer:list</code>权限</dd>
 * <dt>GET: customers/api/json</dt>
 * <dd>返回全部有效的（available=1）客户数据列表，需要拥有<code>customer:list</code>权限</dd>
 * <dt>POST: customers/api/new</dt>
 * <dd>新建客户数据，需要拥有<code>customer:new</code>权限</dd>
 * <dt>GET: customers/api/{id}</dt>
 * <dd>获取客户数据，无权限控制</dd>
 * <dt>POST: customers/api/{id}</dt>
 * <dd>更新客户数据，需要拥有<code>customer:edit</code>权限</dd>
 * <dt>DELETE: customers/api/{id}</dt>
 * <dd>删除客户数据，需要拥有<code>customer:delete</code>权限</dd>
 * </dl>
 * </p>
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/customers/api")
public class CustomerRestController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
	@RequiresPermissions("customer:list")
	public EasyDataGrid<Customer> findAll(@RequestParam(name = "page", defaultValue = "1") int pageIndex,
			@RequestParam(name = "rows", defaultValue = "20") int pageSize,
			@RequestParam(name = "searchValue", defaultValue = "") String searchStr) {
		return this.customerService.findAll(pageIndex, pageSize, searchStr);
	}

	/**
	 * 返回有效的客户清单
	 * 
	 * @return String
	 */
	@GetMapping("/json")
	@RequiresPermissions("customer:list")
	public List<Customer> jsonData() {
		return this.customerService.findAll(true);
	}

	/**
	 * 新增客户
	 * 
	 * @since 1.0
	 * @param request
	 * @return
	 */
	@PostMapping("/new")
	@RequiresPermissions("customer:new")
	public JsonResult<Customer> insert(HttpServletRequest request) {
		// Customer customer = new Customer();
		// customer.setRoleName(request.getParameter("customerName"));
		// customer.setRoleDescription(request.getParameter("customerDescription"));
		// if (this.customerService.findOne(customer) != null) { // 客户名已存在
		// return new JsonResult<Customer>(JsonResult.ERROR, "客户名【" +
		// customer.getRoleName() + "】已被使用，请使用其他客户名");
		// }
		// if (true == this.customerService.insert(customer)) {
		// return new JsonResult<Customer>();
		// } else {
		// return new JsonResult<Customer>(JsonResult.ERROR, "新增客户出错！");
		// }
		return null;
	}

	/**
	 * 获取客户
	 * 
	 * @since 1.0
	 * @param customerId
	 *            客户编号
	 * @return
	 */
	@GetMapping("/{id}")
	public Customer getRole(@PathVariable("id") int customerId) {
		Customer customer = new Customer();
		return this.customerService.findOne(customer);
	}

	/**
	 * 更新客户
	 * 
	 * @since 1.0
	 * @param customerId
	 *            客户编号
	 * @param request
	 * @return
	 */
	@PostMapping("/{id}")
	@RequiresPermissions("customer:edit")
	public JsonResult<Customer> update(@PathVariable("id") int customerId, HttpServletRequest request) {
		Customer customer = new Customer();
//		customer.setRoleId(customerId);
//		customer.setRoleName(request.getParameter("customerName"));
//		customer.setRoleDescription(request.getParameter("customerDescription"));
//		customer.setAvailable("on".equals(request.getParameter("available")));
		if (true == this.customerService.update(customer)) {
			return new JsonResult<Customer>(customer);
		} else {
			return new JsonResult<Customer>(JsonResult.ERROR, "修改客户出错！");
		}
	}

	/**
	 * 删除客户
	 * 
	 * @since 1.0
	 * @param customerId
	 *            客户编号
	 * @param request
	 * @return
	 */
	@DeleteMapping("/{id}")
	@RequiresPermissions("customer:delete")
	public JsonResult<Customer> delete(@PathVariable("id") int customerId, HttpServletRequest request) {
		if (true == this.customerService.delete(customerId)) {
			return new JsonResult<Customer>();
		} else {
			return new JsonResult<Customer>(JsonResult.ERROR, "删除客户出错！");
		}
	}

}
