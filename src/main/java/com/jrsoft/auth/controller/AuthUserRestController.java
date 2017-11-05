/**
 * 
 */
package com.jrsoft.auth.controller;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.auth.service.AuthUserService;
import com.jrsoft.common.DataGrid;

/**
 * com.jrsoft.auth.controller AuthUserRestController
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/rest/users")
public class AuthUserRestController {

	@Resource
	private AuthUserService authUserService;

	/**
	 * 系统用户列表
	 * 
	 * /rest/users?page=5&size=30
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@GetMapping({ "", "/" })
	@RequiresPermissions("authUser:list")
	public DataGrid<AuthUser> findAll(@RequestParam(name = "page", defaultValue = "1") int pageIndex,
			@RequestParam(name = "rows", defaultValue = "20") int pageSize) {
		PageInfo<AuthUser> pageInfo = this.authUserService.findAll(pageIndex, pageSize);
		DataGrid<AuthUser> dg = new DataGrid<AuthUser>();
		dg.setTotal(pageInfo.getTotal());
		dg.setRows(pageInfo.getList());
		return dg;
	}

}
