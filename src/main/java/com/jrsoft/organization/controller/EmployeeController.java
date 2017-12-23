/**
 * 
 */
package com.jrsoft.organization.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * com.jrsoft.employee.controller EmployeeController
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/employees")
public class EmployeeController {
	
	/**
	 * 员工页面访问入口
	 * 
	 * @return
	 */
	@GetMapping({ "", "/index" })
	@RequiresPermissions("employee:list")
	public String index() {
		return "employee/index";
	}

}
