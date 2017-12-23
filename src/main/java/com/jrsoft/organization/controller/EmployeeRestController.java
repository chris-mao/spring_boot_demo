/**
 * 
 */
package com.jrsoft.organization.controller;

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
import com.jrsoft.organization.entity.Employee;
import com.jrsoft.organization.service.EmployeeService;

/**
 * <p>
 * 员工控制器类，提供员工数据的维护方法接口
 * <dl>
 * <dt>GET: employees/rest/list?page=1&rows=20&searchValue=</dt>
 * <dd>按页码返回（符合查询条件或是全部）员工数据列表，需要拥有<code>employee:list</code>权限</dd>
 * <dt>GET: employees/rest/json</dt>
 * <dd>返回全部有效的（available=1）员工数据列表，需要拥有<code>employee:list</code>权限</dd>
 * <dt>GET: employees/rest/{id}/customers</dt>
 * <dd>获取员工权限菜单，需要拥有<code>customer:list</code>权限</dd>
 * <dt>POST: employees/rest/new</dt>
 * <dd>新建员工数据，需要拥有<code>employee:new</code>权限</dd>
 * <dt>GET: employees/rest/{id}</dt>
 * <dd>获取员工数据，需要拥有<code>employee:list</code>权限</dd>
 * <dt>POST: employees/rest/{id}</dt>
 * <dd>更新员工数据，需要拥有<code>employee:edit</code>权限</dd>
 * <dt>DELETE: employees/rest/{id}</dt>
 * <dd>删除员工数据，需要拥有<code>employee:delete</code>权限</dd>
 * </dl>
 * </p>
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/employees/rest")
public class EmployeeRestController {

	/**
	 * 
	 */
	@Autowired
	private EmployeeService employeeService;

	/**
	 * 获取员工列表
	 * 
	 * @since 1.0
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            分页大小
	 * @param searchStr
	 *            模糊查询内容
	 * @return {@link EasyDataGrid }
	 */
	@GetMapping("/list")
	@RequiresPermissions("employee:list")
	public EasyDataGrid<Employee> findAll(@RequestParam(name = "page", defaultValue = "1") int pageIndex,
			@RequestParam(name = "rows", defaultValue = "20") int pageSize,
			@RequestParam(name = "searchValue", defaultValue = "") String searchStr) {
		return employeeService.findAll(pageIndex, pageSize, searchStr);
	}
	
	/**
	 * 返回所有有效的员工清单
	 * 
	 * @return String
	 */
	@GetMapping("/json")
	@RequiresPermissions("employee:list")
	public List<Employee> jsonData() {
		return this.employeeService.findAll(true);
	}
	
	@GetMapping("/{id}/customers")
	public List<Customer> findCustomersByEmployee(@PathVariable(name = "id") int employeeId) {
		return null;
	}
	
	/**
	 * 新增员工数据
	 * 
	 * @since 1.0
	 * @param request
	 * @return
	 */
	@PostMapping("/new")
	@RequiresPermissions("employee:new")
	public JsonResult<Employee> insert(HttpServletRequest request) {
		Employee employee = new Employee();
		employee.setEmployeeName(request.getParameter("employeeName"));
		employee.setPhone(request.getParameter("phone"));
		employee.setFax(request.getParameter("fax"));
		employee.setEmail(request.getParameter("email"));
		employee.setOracleAccount(request.getParameter("oracleAccount"));
		employee.setReportTo(Integer.parseInt(request.getParameter("reportTo")));
//		employee.setUserName(request.getParameter("userName"));
//		employee.setNickName(request.getParameter("nickName"));
		employee.setEmail(request.getParameter("email"));
		if (this.employeeService.findOne(employee) != null) { // 员工名已存在
			return new JsonResult<Employee>(JsonResult.ERROR, "员工名【" + employee.getEmployeeName() + "】已被使用，请使用其他员工名");
		}
		if (true == this.employeeService.insert(employee)) {
			return new JsonResult<Employee>();
		} else {
			return new JsonResult<Employee>(JsonResult.ERROR, "新增员工出错！");
		}
	}

	/**
	 * 获取员工
	 * 
	 * @since 1.0
	 * @param employeeId
	 *            员工编号
	 * @return
	 */
	@GetMapping("/{id}")
	public Employee getUser(@PathVariable("id") int employeeId) {
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		return this.employeeService.findOne(employee);
	}

	/**
	 * 更新员工数据
	 * 
	 * @since 1.0
	 * @param employeeId
	 * @param request
	 * @return
	 */
	@PostMapping("/{id}")
	@RequiresPermissions("employee:edit")
	public JsonResult<Employee> update(@PathVariable("id") int employeeId, HttpServletRequest request) {
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
//		employee.setUserName(request.getParameter("userName"));
//		employee.setNickName(request.getParameter("nickName"));
		employee.setEmail(request.getParameter("email"));
		employee.setAvailable("on".equals(request.getParameter("available")));

		if (true == this.employeeService.update(employee)) {
			return new JsonResult<Employee>(employee);
		} else {
			return new JsonResult<Employee>(JsonResult.ERROR, "修改员工出错！");
		}
	}

	/**
	 * 删除员工数据
	 * 
	 * @since 1.0
	 * @param employeeId
	 * @param request
	 */
	@DeleteMapping("/{id}")
	@RequiresPermissions("employee:delete")
	public JsonResult<Employee> delete(@PathVariable("id") int employeeId, HttpServletRequest request) {
		if (true == this.employeeService.delete(employeeId)) {
			return new JsonResult<Employee>();
		} else {
			return new JsonResult<Employee>(JsonResult.ERROR, "删除员工出错！");
		}
	}

}
