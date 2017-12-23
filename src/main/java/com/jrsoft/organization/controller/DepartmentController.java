/**
 * 
 */
package com.jrsoft.organization.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * com.jrsoft.employee.controller DepartmentController
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/departments")
public class DepartmentController {
	
	/**
	 * 部门页面访问入口
	 * 
	 * @return
	 */
	@GetMapping({ "", "/index" })
	@RequiresPermissions("department:list")
	public String index() {
		return "department/index";
	}

}
