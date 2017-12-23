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
import com.jrsoft.organization.entity.Department;
import com.jrsoft.organization.entity.Employee;
import com.jrsoft.organization.service.DepartmentService;
import com.jrsoft.organization.service.EmployeeService;

/**
 * <p>
 * 部门控制器类，提供部门数据的维护方法接口
 * <dl>
 * <dt>GET: departments/rest/list?page=1&rows=20&searchValue=</dt>
 * <dd>按页码返回（符合查询条件或是全部）部门数据列表，需要拥有<code>department:list</code>权限</dd>
 * <dt>GET: departments/rest/json</dt>
 * <dd>返回全部有效的（available=1）部门数据列表，需要拥有<code>department:list</code>权限</dd>
 * <dt>GET: departments/rest/{id}/emoloyees</dt>
 * <dd>获取部门权部门，需要拥有<code>department:list</code>权限</dd>
 * <dt>GET: departments/rest/tree</dt>
 * <dd>以树型结构返回全部有效的（available=1）部门数据列表，需要拥有<code>department:list</code>权限
 * <dt>POST: departments/rest/new</dt>
 * <dd>新建部门数据，需要拥有<code>department:new</code>权限</dd>
 * <dt>GET: departments/rest/{id}</dt>
 * <dd>获取部门数据，需要拥有<code>department:list</code>权限</dd>
 * <dt>POST: departments/rest/{id}</dt>
 * <dd>更新部门数据，需要拥有<code>department:edit</code>权限</dd>
 * <dt>DELETE: departments/rest/{id}</dt>
 * <dd>删除部门数据，需要拥有<code>department:delete</code>权限</dd>
 * </dl>
 * </p>
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/departments/rest")
public class DepartmentRestController {

	/**
	 * 
	 */
	@Autowired
	private DepartmentService departmentService;

	/**
	 * 
	 */
	@Autowired
	private EmployeeService employeeService;

	/**
	 * 获取部门列表
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
	@RequiresPermissions("department:list")
	public EasyDataGrid<Department> findAll(@RequestParam(name = "id", defaultValue = "0") int parentId,
			@RequestParam(name = "page", defaultValue = "1") int pageIndex,
			@RequestParam(name = "rows", defaultValue = "20") int pageSize,
			@RequestParam(name = "searchValue", defaultValue = "") String searchStr) {
		System.out.println("searchStr ==>> " + searchStr);
		return departmentService.findChildNodes(parentId, pageIndex, pageSize, searchStr);
	}
	
	/**
	 * 返回所有有效的部门清单
	 * 
	 * @return String
	 */
	@GetMapping("/json")
	@RequiresPermissions("department:list")
	public List<Department> jsonData() {
		return this.departmentService.findAll(true);
	}
	
	@GetMapping("/{id}/employees")
	public List<Employee> findCustomersByEmployee(@PathVariable(name = "id") int departmentId) {
		Department dept = new Department();
		dept.setDepartmentId(departmentId);
		return this.employeeService.findAllByDepartment(dept);
	}

	/**
	 * 以树型结构返回全部有效的（available=1）权限数据列表
	 * 
	 * @since 1.1
	 * @return
	 */
	@GetMapping("/tree")
	@RequiresPermissions("department:list")
	public List<Department> departmentTree() {
		return departmentService.getDepartmentTree();
	}
	
	/**
	 * 新增部门数据
	 * 
	 * @since 1.0
	 * @param request
	 * @return
	 */
	@PostMapping("/new")
	@RequiresPermissions("department:new")
	public JsonResult<Department> insert(HttpServletRequest request) {
		Department department = new Department();
		department.setDepartmentName(request.getParameter("departmentName"));
		department.setParentId(Integer.parseInt(request.getParameter("parentId")));
		if (this.departmentService.findOne(department) != null) { // 部门名已存在
			return new JsonResult<Department>(JsonResult.ERROR, "部门名【" + department.getDepartmentName() + "】已被使用，请使用其他部门名");
		}
		if (true == this.departmentService.insert(department)) {
			return new JsonResult<Department>();
		} else {
			return new JsonResult<Department>(JsonResult.ERROR, "新增部门出错！");
		}
	}

	/**
	 * 获取部门
	 * 
	 * @since 1.0
	 * @param departmentId
	 *            部门编号
	 * @return
	 */
	@GetMapping("/{id}")
	public Department getDepartment(@PathVariable("id") int departmentId) {
		Department department = new Department();
		department.setDepartmentId(departmentId);
		return this.departmentService.findOne(department);
	}

	/**
	 * 更新部门数据
	 * 
	 * @since 1.0
	 * @param departmentId
	 * @param request
	 * @return
	 */
	@PostMapping("/{id}")
	@RequiresPermissions("department:edit")
	public JsonResult<Department> update(@PathVariable("id") int departmentId, HttpServletRequest request) {
		Department department = new Department();
		department.setDepartmentId(departmentId);
		department.setDepartmentName(request.getParameter("departmentName"));
		department.setParentId(Integer.parseInt(request.getParameter("parentId")));
		department.setAvailable("on".equals(request.getParameter("available")));

		if (true == this.departmentService.update(department)) {
			return new JsonResult<Department>(department);
		} else {
			return new JsonResult<Department>(JsonResult.ERROR, "修改部门出错！");
		}
	}

	/**
	 * 删除部门数据
	 * 
	 * @since 1.0
	 * @param departmentId
	 * @param request
	 */
	@DeleteMapping("/{id}")
	@RequiresPermissions("department:delete")
	public JsonResult<Employee> delete(@PathVariable("id") int departmentId, HttpServletRequest request) {
		if (true == this.departmentService.delete(departmentId)) {
			return new JsonResult<Employee>();
		} else {
			return new JsonResult<Employee>(JsonResult.ERROR, "删除部门出错！");
		}
	}

}
