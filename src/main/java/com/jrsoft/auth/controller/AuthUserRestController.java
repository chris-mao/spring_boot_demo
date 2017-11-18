/**
 * 
 */
package com.jrsoft.auth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jrsoft.auth.AuthUserStateEnum;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.auth.entity.AuthUserRoleReleation;
import com.jrsoft.auth.service.AuthUserService;
import com.jrsoft.common.EasyDataGrid;
import com.jrsoft.common.JsonResult;

/**
 * <p>
 * 系统用户控制器类，提供系统用户维护方法接口
 * <dl>
 * <dt>按页码返回（符合查询条件或是全部）用户数据列表，需要拥有authUser:list权限</dt>
 * <dd>GET: users/rest/list?page=1&rows=20&searchValue=</dd>
 * <dt>返回全部有效的（available=1）用户数据列表，需要拥有authUser:list权限</dt>
 * <dd>GET: users/rest/json</dd>
 * <dt>获取用户状态列表，无权限控制</dt>
 * <dd>GET: users/rest/states</dd>
 * <dt>新建用户数据，需要拥有authUser:new权限</dt>
 * <dd>POST: users/rest/new</dd>
 * <dt>更新用户数据，需要拥有authUser:edit权限</dt>
 * <dd>POST: users/rest/{id}</dd>
 * <dt>删除用户数据，需要拥有authUser:delete权限</dt>
 * <dd>DELETE: users/rest/{id}</dd>
 * <dt>修改用户登录密码，无权限控制</dt>
 * <dd>POST: users/rest/{id}/psd</dd>
 * <dt>返回用户关联的角色列表，需要拥有authUser:edit权限</dt>
 * <dd>GET: users/rest/{id}/roles</dd>
 * <dt>修改（新增、编辑、删除）用户关联角色，需要拥有authUser:edit权限</dt>
 * <dd>POST: users/rest/{id}/roles</dd>
 * </dl>
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

	@Autowired
	private AuthUserService authUserService;

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
	 * @return {@link EasyDataGrid }
	 */
	@GetMapping("/list")
	@RequiresPermissions("authUser:list")
	public EasyDataGrid<AuthUser> findAll(@RequestParam(name = "page", defaultValue = "1") int pageIndex,
			@RequestParam(name = "rows", defaultValue = "20") int pageSize,
			@RequestParam(name = "searchValue", defaultValue = "") String searchStr) {
		return authUserService.findAll(pageIndex, pageSize, searchStr);
	}

	/**
	 * 返回所有有效的用户清单
	 * 
	 * @return String
	 */
	@GetMapping("/json")
	@RequiresPermissions("authUser:list")
	public List<AuthUser> jsonData() {
		return this.authUserService.findAll(true);
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
	 * 新增用户数据
	 * 
	 * @since 1.0
	 * @param request
	 * @return
	 */
	@PostMapping("/new")
	@RequiresPermissions("authUser:new")
	public JsonResult<AuthUser> insert(HttpServletRequest request) {
		AuthUser user = new AuthUser();
		user.setUserName(request.getParameter("userName"));
		user.setNickName(request.getParameter("nickName"));
		user.setEmail(request.getParameter("email"));
		user.setState(AuthUserStateEnum.valueOf(request.getParameter("state")));
		if (this.authUserService.findOne(user) != null) { // 用户名已存在
			return new JsonResult<AuthUser>(JsonResult.ERROR, "用户名【" + user.getUserName() + "】已被使用，请使用其他用户名");
		}
		if (true == this.authUserService.insert(user)) {
			return new JsonResult<AuthUser>();
		} else {
			return new JsonResult<AuthUser>(JsonResult.ERROR, "新增用户出错！");
		}
	}

	/**
	 * 更新用户数据
	 * 
	 * @since 1.0
	 * @param userId
	 * @param request
	 * @return
	 */
	@PostMapping("/{id}")
	@RequiresPermissions("authUser:edit")
	public JsonResult<AuthUser> update(@PathVariable("id") int userId, HttpServletRequest request) {
		AuthUser user = new AuthUser();
		user.setUserId(userId);
		user.setUserName(request.getParameter("userName"));
		user.setNickName(request.getParameter("nickName"));
		user.setEmail(request.getParameter("email"));
		user.setState(AuthUserStateEnum.valueOf(request.getParameter("state")));
		user.setAvailable(Boolean.parseBoolean(request.getParameter("available")));
		if (true == this.authUserService.update(user)) {
			return new JsonResult<AuthUser>(user);
		} else {
			return new JsonResult<AuthUser>(JsonResult.ERROR, "修改用户出错！");
		}
	}

	/**
	 * 修改用户密码
	 * 
	 * @since 1.0
	 * @param userId
	 * @param request
	 * @return
	 */
	@PostMapping("/{id}/psd")
	public JsonResult<AuthUser> changePassword(@PathVariable("id") int userId, HttpServletRequest request) {
		String newPassword = request.getParameter("newPassword");
		AuthUser user = new AuthUser(userId);
		user = authUserService.findOne(user);
		if (null == user) {
			return new JsonResult<AuthUser>(JsonResult.ERROR, "编号为【" + userId + "】的用户不存在");
		}
		if (true == authUserService.changePassword(userId, user.getPassword(), newPassword)) {
			return new JsonResult<AuthUser>(user);
		} else {
			return new JsonResult<AuthUser>(JsonResult.ERROR, "修改密码出错！");
		}
	}

	/**
	 * 删除用户数据
	 * 
	 * @since 1.0
	 * @param userId
	 * @param request
	 */
	@DeleteMapping("/{id}")
	@RequiresPermissions("authUser:delete")
	public JsonResult<AuthUser> delete(@PathVariable("id") int userId, HttpServletRequest request) {
		if (true == this.authUserService.delete(userId)) {
			return new JsonResult<AuthUser>();
		} else {
			return new JsonResult<AuthUser>(JsonResult.ERROR, "删除用户出错！");
		}
	}

	/**
	 * 获取用户关联的角色清单
	 * 
	 * @since 1.0
	 * @param userId
	 *            用户编号
	 * @return
	 */
	@GetMapping("/{id}/roles")
	@RequiresPermissions("authUser:edit")
	public Set<AuthUserRoleReleation> findUserRoles(@PathVariable("id") int userId) {
		return authUserService.findUserRoles(userId);
	}

	/**
	 * 修改用户角色关联关系（包括新增、更新和删除）
	 * 
	 * @param userId
	 *            用户编号
	 * @param userRoleReleations
	 *            用户角色关联应映表，此参数需要包含三个主键<code>inserted</code>,
	 *            <code>updated</code>和<code>deleted</code>
	 *            。每个主键对应一个List对象，其中存有一个或多个用户角色对象{@link AuthUserRoleReleation}
	 * @return
	 */
	@PostMapping("/{id}/roles")
	@RequiresPermissions("authUser:edit")
	public void updateUserRoles(@PathVariable("id") int userId,
			@RequestBody Map<String, List<AuthUserRoleReleation>> userRoleReleations) {
		String operation;
		AuthUserRoleReleation r;
		Set<Entry<String, List<AuthUserRoleReleation>>> set = userRoleReleations.entrySet();
		for (Iterator<Entry<String, List<AuthUserRoleReleation>>> iter = set.iterator(); iter.hasNext();) {
			Entry<String, List<AuthUserRoleReleation>> entry = iter.next();
			operation = entry.getKey();
			List<AuthUserRoleReleation> list = entry.getValue();
			for (Iterator<AuthUserRoleReleation> i = list.iterator(); i.hasNext();) {
				r = i.next();
				if ("inserted".equals(operation.toLowerCase())) {
					r.setUserId(userId);
					System.out.println("insert: " + r);
					this.authUserService.grantRole(r);
				} else if ("updated".equals(operation.toLowerCase())) {
					System.out.println("update: " + r);
					this.authUserService.updateGrantedRole(r);
				} else if ("deleted".equals(operation.toLowerCase())) {
					System.out.println("delete: " + r);
					this.authUserService.revokeRole(r);
				}
			}
		}
	}
}
