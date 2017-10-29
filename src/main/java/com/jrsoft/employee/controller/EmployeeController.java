/**
 * 
 */
package com.jrsoft.employee.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.jrsoft.app.exception.DataNotFoundException;
import com.jrsoft.customer.entity.CustomerAccount;
import com.jrsoft.customer.service.CustomerService;
import com.jrsoft.employee.entity.Employee;
import com.jrsoft.employee.service.EmployeeService;

/**
 * com.jrsoft.employee.controller EmployeeController
 *
 * 员工数据维护控制器类
 * 
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/employeese")
public class EmployeeController {

	/**
	 * 
	 */
	@Autowired
	private EmployeeService employeeService;

	/**
	 * 
	 */
	@Autowired
	private CustomerService customerService;

	/**
	 * 
	 * @param id
	 * @return Employee
	 * @throws DataNotFoundException
	 */
	private Employee findEmployee(int id) throws DataNotFoundException {
		Employee emp = new Employee(id);
		Employee employee = this.employeeService.findOne(emp);
		if (null == employee) {
			throw new DataNotFoundException("您指定的员工不存在！ID：" + id);
		}
		return employee;
	}

	/**
	 * 员工列表页面
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@GetMapping({ "", "/index" })
	@RequiresPermissions("employee:list")
	public String findAllEmployee(@RequestParam(defaultValue = "1") int page, Model model) {
		PageInfo<Employee> employeese = this.employeeService.findAll(page);
		model.addAttribute("page", employeese);
		return "employee/index";
	}

	/**
	 * 查看员工明细页面，包括已分配的客户
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws DataNotFoundException
	 */
	@GetMapping("/{id}")
	@RequiresPermissions("employee:detail")
	public String viewEmployee(@PathVariable("id") int id, Model model) throws DataNotFoundException {
		Employee employee = findEmployee(id);
		model.addAttribute("employee", employee);
		model.addAttribute("myCustomers", customerService.findAllByEmployee(employee));
		model.addAttribute("customers", customerService.findAll());

		return "employee/detail";
	}

	/**
	 * 创建新员工页面
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/new")
	@RequiresPermissions("employee:new")
	public String newEmployee(Model model) {
		model.addAttribute("employee", new Employee());
		return "employee/new";
	}

	/**
	 * 编辑员工页面
	 * 
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 * @throws DataNotFoundException
	 */
	@GetMapping("/{id}/edit")
	@RequiresPermissions("employee:edit")
	public String editUser(@PathVariable("id") int id, HttpServletRequest request, Model model)
			throws DataNotFoundException {
		Employee employee = findEmployee(id);
		model.addAttribute("employee", employee);
		return "employee/edit";
	}

	/**
	 * 保存员工页面
	 * 
	 * @param employee
	 * @param result
	 * @param request
	 * @param model
	 * @return
	 */
	@PostMapping("/save")
	@RequiresPermissions("employee:save")
	public String saveEmployee(@Valid Employee employee, BindingResult result, HttpServletRequest request,
			Model model) {
		model.addAttribute("employee", employee);
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				System.out.println(error.getCode() + "---" + error.getArguments() + "---" + error.getDefaultMessage());
			}
			if ("insert".equals(request.getParameter("action"))) {
				return "employee/new";
			}
			if ("update".equals(request.getParameter("action"))) {
				return "employee/edit";
			}
		}
		if ("insert".equals(request.getParameter("action"))) {
			if (employeeService.findOne(employee) != null) { // 员工已存在
				result.rejectValue("employeeName", "duplicate", "此员工名称已存在");
				return "employee/new";
			}
			employeeService.insert(employee);
		}
		if ("update".equals(request.getParameter("action"))) {
			employeeService.update(employee);
			return "redirect:/employeese/" + employee.getEmployeeId();
		}
		return "employee/save";
	}

	/**
	 * 删除员工页面
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@GetMapping("/{id}/del")
	@RequiresPermissions("employee:delete")
	public String deleteUser(@PathVariable("id") int id, HttpServletRequest request) {
		this.employeeService.delete(id);
		return "redirect:/employeese";
	}

	/**
	 * 分配客户到指定员工，先将该员工所有客户删除，再重新分配
	 * 
	 * Ajax调用
	 * 
	 * @param id
	 * @param customerIds
	 * @return
	 */
	@PostMapping("/{id}/customers")
	@ResponseBody
	public String assignCustomers(@PathVariable("id") int id, @RequestBody List<Integer> customerIds) {
		System.out.println("客户分配：EMPLOYEE ==> " + id + "   CUSTOMERS ==>" + customerIds.toString());

		Employee emp = new Employee(id);
		CustomerAccount customer = new CustomerAccount();
		this.employeeService.removeAllCustomers(emp);

		Iterator<Integer> iterator = customerIds.iterator();
		while (iterator.hasNext()) {
			customer.setCustomerId(iterator.next());
			employeeService.addCustomer(emp, customer);
		}

		return "ok";
	}

}
