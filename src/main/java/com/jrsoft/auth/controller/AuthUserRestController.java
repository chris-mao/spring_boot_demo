/**
 * 
 */
package com.jrsoft.auth.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.SimplePrincipalCollection;
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
import com.jrsoft.auth.entity.AuthPermission;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.auth.entity.AuthUserDelegate;
import com.jrsoft.auth.entity.AuthUserRoleReleation;
import com.jrsoft.auth.service.AuthPermissionService;
import com.jrsoft.auth.service.AuthUserDelegateService;
import com.jrsoft.auth.service.AuthUserService;
import com.jrsoft.auth.utils.AuthUtils;
import com.jrsoft.common.EasyDataGrid;
import com.jrsoft.common.JsonResult;

/**
 * <p>
 * 系统用户控制器类，提供系统用户维护方法接口
 * <dl>
 * <dt>GET: users/rest/list?page=1&rows=20&searchValue=</dt>
 * <dd>按页码返回（符合查询条件或是全部）用户数据列表，需要拥有<code>authUser:list</code>权限</dd>
 * <dt>GET: users/rest/json</dt>
 * <dd>返回全部有效的（available=1）用户数据列表，需要拥有<code>authUser:list</code>权限</dd>
 * <dt>GET: users/rest/{id}/permissions</dt>
 * <dd>获取用户个人权限，无权限控制。若要返回所有有效的权限，请使用GET方法访问permissions/rest/json，参看
 * {@link AuthPermissionRestController#jsonData()}</dd>
 * <dt>GET: users/rest/{id}/permissions/tree</dt>
 * <dd>以树型结构返回用户权限清单，无权限控制。若要返回所有权限的树型结构，请使用GET方法访问permissions/rest/tree，参看
 * {@link AuthPermissionRestController#permissionTree()}</dd>
 * <dt>GET: users/rest/{id}/menu</dt>
 * <dd>获取用户权限菜单，无权限控制</dd>
 * <dt>GET: users/rest/states</dt>
 * <dd>获取用户状态列表，无权限控制</dd>
 * <dt>POST: users/rest/new</dt>
 * <dd>新建用户数据，需要拥有<code>authUser:new</code>权限</dd>
 * <dt>GET: users/rest/{id}</dt>
 * <dd>获取用户数据，需要拥有<code>authUser:list</code>权限</dd>
 * <dt>POST: users/rest/{id}</dt>
 * <dd>更新用户数据，需要拥有<code>authUser:edit</code>权限</dd>
 * <dt>DELETE: users/rest/{id}</dt>
 * <dd>删除用户数据，需要拥有<code>authUser:delete</code>权限</dd>
 * <dt>POST: users/rest/{id}/psd</dt>
 * <dd>修改用户登录密码，无权限控制</dd>
 * <dt>GET: users/rest/{id}/roles</dt>
 * <dd>返回用户关联的角色列表，需要拥有<code>authUser:edit</code>权限</dd>
 * <dt>POST: users/rest/{id}/roles</dt>
 * <dd>修改（新增、编辑、删除）用户关联角色，需要拥有<code>authUser:edit</code>权限</dd>
 * <dt>GET: users/rest/{id}/agents</dt>
 * <dd>返回用户的代理用户清单，需要拥有<code>authUser:delegate</code>权限</dd>
 * <dt>GET: users/rest/{id}/delegates</dt>
 * <dd>返回用户的代理的用户清单，需要拥有<code>authUser:delegate</code>权限</dd>
 * <dt>GET: users/rest/switch-to/{id}</dt>
 * <dd>切换到委托人身份，需要拥有<code>authUser:delegate</code>权限</dd>
 * <dt>GET: users/rest/switch-back</dt>
 * <dd>返回前一个身份，需要拥有<code>authUser:delegate</code>权限</dd>
 * 
 * <dt>POST: users/rest/grant/{id}</dt>
 * <dd>将指定用户设为当前用户的代理，需要拥有<code>authUser:delegate</code>权限</dd>
 * <dt>POST: users/rest/revoke/{id}</dt>
 * <dd>取消代理，需要拥有<code>authUser:delegate</code>权限</dd>
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
	 * 
	 */
	@Autowired
	private AuthPermissionService authPermissionService;

	/**
	 * 
	 */
	@Autowired
	private AuthUserDelegateService authUserDelegateService;

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
	 * 返回用户的个人权限清单
	 * 
	 * @since 1.0
	 * @param userId
	 * @return
	 */
	@GetMapping("/{id}/permissions")
	public List<AuthPermission> findIndividualPermissions(@PathVariable(name = "id") int userId) {
		AuthUser user = new AuthUser(userId);
		return authPermissionService.findIndividualPermissions(user);

	}

	/**
	 * 返回用户权限（树型结构）
	 * 
	 * @since 1.1
	 * @param userId
	 * @return
	 */
	@GetMapping("/{id}/permissions/tree")
	public List<AuthPermission> getIndividualPermissionTree(@PathVariable(name = "id") int userId) {
		AuthUser user = new AuthUser(userId);
		return authPermissionService.getIndividualPermissionTree(user);
	}

	/**
	 * 返回指定用户的菜单树（树型结构）
	 * 
	 * @since 1.1
	 * @param userId
	 * @return
	 */
	@GetMapping("/{id}/menu")
	public List<AuthPermission> getUserMenuTree(@PathVariable(name = "id") int userId) {
		AuthUser user = new AuthUser(userId);
		return authPermissionService.getMenuTreeByUser(user);
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
	 * 获取用户
	 * 
	 * @since 1.0
	 * @param userId
	 *            用户编号
	 * @return
	 */
	@GetMapping("/{id}")
	public AuthUser getUser(@PathVariable("id") int userId) {
		AuthUser user = new AuthUser(userId);
		return this.authUserService.findOne(user);
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
		user.setAvailable("on".equals(request.getParameter("available")));

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
	 * @since 1.0
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

	/**
	 * 获取指定用户的代理用户名单
	 * 
	 * @since 1.0
	 * @param userId
	 *            用户编号
	 * @return
	 */
	@GetMapping("/{id}/agents")
	@RequiresPermissions("authUser:delegate")
	public List<AuthUserDelegate> findAllAgents(@PathVariable("id") int userId) {
		AuthUser fromUser = new AuthUser(userId);
		return authUserDelegateService.findAllByFromUser(fromUser);
	}

	/**
	 * 获取指定用户代理的用户名单
	 * 
	 * @since 1.0
	 * @param userId
	 *            用户编号
	 * @return
	 */
	@GetMapping("/{id}/delegates")
	@RequiresPermissions("authUser:delegate")
	public List<AuthUserDelegate> findAllDelegates(@PathVariable("id") int userId) {
		AuthUser toUser = new AuthUser(userId);
		return authUserDelegateService.findAllByToUser(toUser);
	}

	/**
	 * 将身份委托给指定的用户
	 * 
	 * @param toUserId
	 * @param request
	 * @return {@link JsonResult }
	 */
	@PostMapping("/grant/{toUserId}")
	@RequiresPermissions("authUser:delegate")
	public JsonResult<AuthUser> grant(@PathVariable("toUserId") Integer toUserId, HttpServletRequest request) {
		AuthUser toUser = new AuthUser(toUserId);
		toUser = authUserService.findOne(toUser);
		AuthUser currentUser = AuthUtils.getCurrentUser();
		if (null == toUser) {
			return new JsonResult<AuthUser>(JsonResult.ERROR, String.format("代理帐号不存在！ID：%d", toUserId), toUser);
		} else if (toUser.equals(currentUser)) {
			return new JsonResult<AuthUser>(JsonResult.ERROR, "不允许自已为自己代理！", toUser);
		} else if (this.authUserDelegateService.exists(currentUser, toUser)) {
			return new JsonResult<AuthUser>(JsonResult.ERROR,
					String.format("重复委托：用户 %s 的已是您的代理人，不允许重复设置！", toUser.getNickName()), toUser);
		} else if (true == authUserDelegateService.exists(toUser, currentUser)) {
			return new JsonResult<AuthUser>(JsonResult.ERROR,
					String.format("循环委托：您已是用户 %s 的代理人，不允许再将身份委托给他（她）！", toUser.getNickName()), toUser);
		} else if (toUser.getState() == AuthUserStateEnum.LOCKED) {
			return new JsonResult<AuthUser>(JsonResult.ERROR,
					String.format("代理人 %s 的帐号已被锁，无法设为您的代理人！", toUser.getNickName()), toUser);
		} else if (toUser.getState() == AuthUserStateEnum.EXPIRED) {
			return new JsonResult<AuthUser>(JsonResult.ERROR,
					String.format("代理人 %s 的帐号已过期，无法设为您的代理人！", toUser.getNickName()), toUser);
		} else if (toUser.getState() == AuthUserStateEnum.INACTIVE) {
			return new JsonResult<AuthUser>(JsonResult.ERROR,
					String.format("代理人 %s 的帐号已失效，无法设为您的代理人！", toUser.getNickName()), toUser);
		}

		Date startDate = null, endDate = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			startDate = df.parse(request.getParameter("startDate"));
			if ("" != request.getParameter("endDate")) {
				endDate = df.parse(request.getParameter("endDate"));
			}
			this.authUserDelegateService.grantDelegate(currentUser, toUser, startDate, endDate);
			return new JsonResult<AuthUser>(JsonResult.SUCCESS, String.format("成功将用户 %s 设为您的代理！", toUser.getNickName()),
					toUser);
		} catch (ParseException e) {
			e.printStackTrace();
			return new JsonResult<AuthUser>(JsonResult.ERROR, "设置代理出错！", toUser);
		}
	}

	/**
	 * 取消委托关系
	 * 
	 * @param toUserId
	 * @return
	 */
	@PostMapping("/revoke/{toUserId}")
	@RequiresPermissions("authUser:delegate")
	public JsonResult<AuthUser> revoke(@PathVariable("toUserId") Integer toUserId) {
		AuthUser toUser = new AuthUser(toUserId);
		toUser = authUserService.findOne(toUser);
		this.authUserDelegateService.revokeDelegate(AuthUtils.getCurrentUser(), toUser);
		return new JsonResult<AuthUser>(JsonResult.SUCCESS, String.format("成功收回用户 %s 的代理资格！", toUser.getNickName()),
				toUser);
	}

	/**
	 * 切换身份
	 * 
	 * 在切换身份之前会判断委托关系是否存在，以及委托人的帐号状态（被锁、过期或是失效）是否正常
	 * 
	 * @param toUserId
	 * @return {@link JsonResult }
	 */
	@GetMapping("/switch-to/{id}")
	@RequiresPermissions("authUser:delegate")
	public JsonResult<AuthUser> switchTo(@PathVariable("id") Integer fromUserId) {
		AuthUser fromUser = new AuthUser(fromUserId);
		fromUser = authUserService.findOne(fromUser);
		AuthUser currentUser = AuthUtils.getCurrentUser();
		if (null == fromUser) {
			return new JsonResult<AuthUser>(JsonResult.ERROR, String.format("委托帐号不存在！ID：%d", fromUserId), fromUser);
		} else if (fromUser.equals(currentUser)) {
			return new JsonResult<AuthUser>(JsonResult.ERROR, "不允许自已为自己代理！", fromUser);
		} else if (false == authUserDelegateService.exists(fromUser, currentUser)) {
			return new JsonResult<AuthUser>(JsonResult.ERROR,
					String.format("用户 %s 与 %s 之间不存在委托关系！", fromUser.getNickName(), currentUser.getNickName()),
					fromUser);
		} else if (fromUser.getState() == AuthUserStateEnum.LOCKED) {
			return new JsonResult<AuthUser>(JsonResult.ERROR,
					String.format("委托人 %s 的帐号已被锁，请与系统管理员联系！", fromUser.getNickName()), fromUser);
		} else if (fromUser.getState() == AuthUserStateEnum.EXPIRED) {
			return new JsonResult<AuthUser>(JsonResult.ERROR,
					String.format("委托人 %s 的帐号已过期，请与系统管理员联系！", fromUser.getNickName()), fromUser);
		} else if (fromUser.getState() == AuthUserStateEnum.INACTIVE) {
			return new JsonResult<AuthUser>(JsonResult.ERROR,
					String.format("委托人 %s 的帐号已失效，请与系统管理员联系！", fromUser.getNickName()), fromUser);
		}

		SimplePrincipalCollection spc = new SimplePrincipalCollection(fromUser, "");
		AuthUtils.getCredential().runAs(spc);
		return new JsonResult<AuthUser>(JsonResult.SUCCESS, String.format("成功切换到委托人 %s 的身份！", fromUser.getNickName()),
				fromUser);
	}

	/**
	 * 返回身份
	 * 
	 * 身份委托是以栈的形式保存，所以在返回身份时，只能返回上一个身份，直至栈为空为止
	 * 
	 * @return
	 */
	@GetMapping("/switch-back")
	@RequiresPermissions("authUser:delegate")
	public JsonResult<AuthUser> switchBack() {
		if (AuthUtils.getCredential().isRunAs()) {
			AuthUtils.getCredential().releaseRunAs();
		}
		AuthUser currentUser = AuthUtils.getCurrentUser();
		return new JsonResult<AuthUser>(JsonResult.SUCCESS, String.format("成功返回到 %s 的身份！", currentUser.getNickName()),
				currentUser);
	}
}
