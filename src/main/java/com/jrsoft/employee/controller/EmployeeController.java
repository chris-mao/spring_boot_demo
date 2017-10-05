/**
 * 
 */
package com.jrsoft.employee.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
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
	
	@Resource
	private EmployeeService employeeService;
	
	@GetMapping("")
	@RequiresPermissions("employee:list")
	public String findAllEmployee(@RequestParam(defaultValue = "1") int pageNum, Model model) {
		PageInfo<Employee> employeese = this.employeeService.findAll(pageNum);
		model.addAttribute("page", employeese);
		return "employee/index";
	}
	
	@GetMapping("/{id}")
	@RequiresPermissions("employee:detail")
	public String findEmployee(@PathVariable("id") Integer id, HttpServletRequest request, Model model) {
		Employee employee = this.employeeService.findById(id);
		if (null != employee) {
			model.addAttribute("employee", employee);
		}
		return "employee/detail";
	}

}
