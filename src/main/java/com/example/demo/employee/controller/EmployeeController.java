/**
 * 
 */
package com.example.demo.employee.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 员工数据维护控制器类
 * 
 * com.example.demo.employee.controller EmployeeController
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/employees")
public class EmployeeController {
	
	@GetMapping("")
	@RequiresPermissions("employee:list")
	public String findAllEmployee() {
		return "employee/index";
	}
	
	@GetMapping("/{id}")
	@RequiresPermissions("employee:detail")
	public String findEmployee(@PathVariable("id") Integer id, HttpServletRequest request, Model model) {
		return "employee/detail";
	}

}
