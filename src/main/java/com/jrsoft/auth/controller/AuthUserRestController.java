/**
 * 
 */
package com.jrsoft.auth.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.jrsoft.auth.AuthUserStateEnum;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.auth.service.AuthRoleService;
import com.jrsoft.auth.service.AuthUserService;
import com.jrsoft.common.DataGrid;

/**
 * <p>
 * 系统用户控制器类，提供系统用户维护方法接口
 * </p>
 * 
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/users/rest")
public class AuthUserRestController {

	@Resource
	private AuthUserService authUserService;

	@Resource
	private AuthRoleService authRoleService;

	/**
	 * 获取用户列表
	 * 
	 * @since 1.0
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            分页大小
	 * @param searchStr
	 *            模糊查询内容
	 * @return {@link DataGrid }
	 */
	@GetMapping("/list")
	@RequiresPermissions("authUser:list")
	public DataGrid<AuthUser> findAll(@RequestParam(name = "page", defaultValue = "1") int pageIndex,
			@RequestParam(name = "rows", defaultValue = "20") int pageSize,
			@RequestParam(name = "searchValue", defaultValue = "") String searchStr) {
		DataGrid<AuthUser> dg = new DataGrid<AuthUser>();
		PageInfo<AuthUser> pageInfo = this.authUserService.findAll(pageIndex, pageSize, searchStr);
		dg.setTotal(pageInfo.getTotal());
		dg.setRows(pageInfo.getList());
		return dg;
	}

	/**
	 * 将用户状态枚举值转为id与text的键值对型式，返回数据类似
	 * [{"id":"ACTIVE","text":"ACTIVE"},{"id":"LOCKED","text":"LOCKED"},
	 * {"id":"EXPIRED","text":"EXPIRED"},{"id":"INACTIVE","text": "INACTIVE"}]
	 * 
	 * @since 1.0
	 * 
	 * @return List
	 */
	@GetMapping("/states")
	public List<Map<String, String>> getAllUserState() {
		// 将用户状态枚举值转为字符串
		List<Map<String, String>> states = new ArrayList<Map<String, String>>();
		for (AuthUserStateEnum userState : AuthUserStateEnum.values()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", userState.getText().toUpperCase());
			map.put("text", userState.getText().toUpperCase());
			states.add(map);
		}
		return states;
	}

	/**
	 * 更新用户数据
	 * 
	 * @since 1.0
	 * @param id
	 * @param request
	 * @return
	 */
	@PutMapping("/{id}")
	@RequiresPermissions("authUser:edit")
	public AuthUser update(@PathVariable("id") int id, HttpServletRequest request) {
		// this.authUserService.update(authUser);
		return null;
	}

	/**
	 * 删除用户数据
	 * 
	 * @since 1.0
	 * @param id
	 * @param request
	 */
	@DeleteMapping("/{id}")
	@RequiresPermissions("authUser:delete")
	public void delete(@PathVariable("id") int id, HttpServletRequest request) {
		this.authUserService.delete(id);
	}

	/**
	 * 新增用户数据
	 * 
	 * @since 1.0
	 * @param request
	 * @return
	 */
	@PostMapping("/new")
	@RequiresPermissions("authUser:new")
	public AuthUser insert(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		AuthUser authUser = null;
		if (this.authUserService.findOne(authUser) != null) { // 用户名已存在
			// result.rejectValue("userName", "duplicate", "此用户名已被使用，请使用其他用户名");
			return authUser;
		}
		this.authUserService.insert(authUser);
		return authUser;
	}
}
