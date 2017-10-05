/**
 * 
 */
package com.jrsoft.employee.controller;

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
import com.jrsoft.employee.entity.Employee;
import com.jrsoft.employee.service.EmployeeService;

/**
 * 员工数据维护控制器类
 * 
 * com.jrsoft.employee.controller EmployeeController
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
	@Resource
	private EmployeeService employeeService;
	
	@Resource
	private CustomerService customerService;

	/**
	 * 
	 * @param pageNum
	 * @param model
	 * @return
	 */
	@GetMapping("")
	@RequiresPermissions("employee:list")
	public String findAllEmployee(@RequestParam(defaultValue = "1") int pageNum, Model model) {
		PageInfo<Employee> employeese = this.employeeService.findAll(pageNum);
		model.addAttribute("page", employeese);
		return "employee/index";
	}

	/**
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws DataNotFoundException
	 */
	@GetMapping("/{id}")
	@RequiresPermissions("employee:detail")
	public String findEmployee(@PathVariable("id") Integer id, Model model) throws DataNotFoundException {
		Employee e = new Employee();
		e.setEmployeeId(id);
		Employee employee = this.employeeService.findOne(e);
		if (null == employee) {
			throw new DataNotFoundException();
		}
		model.addAttribute("employee", employee);
		model.addAttribute("customers", customerService.findAllByEmployee(employee));
		
		return "employee/detail";
	}

}
