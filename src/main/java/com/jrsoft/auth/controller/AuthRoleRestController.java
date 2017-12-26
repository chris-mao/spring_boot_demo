/**
 * 
 */
package com.jrsoft.auth.controller;

import java.util.Iterator;
import java.util.List;
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

import com.jrsoft.auth.entity.AuthPermission;
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.auth.service.AuthPermissionService;
import com.jrsoft.auth.service.AuthRoleService;
import com.jrsoft.common.EasyDataGrid;
import com.jrsoft.common.JsonResult;

/**
 * <p>
 * 系统角色控制器类，提供系统角色维护方法接口
 * <dl>
 * <dt>GET: roles/api/list?page=1&rows=20&searchValue=</dt>
 * <dd>按页码返回（符合查询条件或是全部）角色数据列表，需要拥有<code>authRole:list</code>权限</dd>
 * <dt>GET: roles/api/json</dt>
 * <dd>返回全部有效的（available=1）角色数据列表，需要拥有<code>authRole:list</code>权限</dd>
 * <dt>GET: roles/api/{id}/permissions</dt>
 * <dd>返回角色权限清单，无权限控制。若要返回所有有效的权限，请使用GET方法访问permissions/api/json，参看
 * {@link AuthPermissionRestController#jsonData()}</dd>
 * <dt>POST: roles/api/{id}/permissions</dt>
 * <dd>更新角色权限（新增、删除），需要拥有<code>authRole:edit</code>权限</dd>
 * <dt>GET: roles/api/{id}/permissions/tree</dt>
 * <dd>以树型结构返回角色权限清单，无权限控制。若要返回所有权限的树型结构，请使用GET方法访问permissions/api/tree，参看
 * {@link AuthPermissionRestController#permissionTree()}</dd>
 * <dt>POST: roles/api/new</dt>
 * <dd>新建角色数据，需要拥有<code>authRole:new</code>权限</dd>
 * <dt>GET: roles/api/{id}</dt>
 * <dd>获取角色数据，无权限控制</dd>
 * <dt>POST: roles/api/{id}</dt>
 * <dd>更新角色数据，需要拥有<code>authRole:edit</code>权限</dd>
 * <dt>DELETE: roles/api/{id}</dt>
 * <dd>删除角色数据，需要拥有<code>authRole:delete</code>权限</dd>
 * </dl>
 * </p>
 * 
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/roles/api")
public class AuthRoleRestController {

	/**
	 * 
	 */
	@Autowired
	private AuthRoleService authRoleService;

	/**
	 * 
	 */
	@Autowired
	private AuthPermissionService authPermissionService;

	/**
	 * 获取角色列表
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
	@RequiresPermissions("authRole:list")
	public EasyDataGrid<AuthRole> findAll(@RequestParam(name = "page", defaultValue = "1") int pageIndex,
			@RequestParam(name = "rows", defaultValue = "20") int pageSize,
			@RequestParam(name = "searchValue", defaultValue = "") String searchStr) {
		return this.authRoleService.findAll(pageIndex, pageSize, searchStr);
	}

	/**
	 * 返回有效的角色清单
	 * 
	 * @return String
	 */
	@GetMapping("/json")
	@RequiresPermissions("authRole:list")
	public List<AuthRole> jsonData() {
		return this.authRoleService.findAll(true);
	}

	/**
	 * 返回角色权限清单
	 * 
	 * @since 1.0
	 * @param roleId
	 *            角色编号
	 * @return
	 */
	@GetMapping("/{id}/permissions")
	public List<AuthPermission> findRolePermissions(@PathVariable("id") int roleId) {
		AuthRole role = new AuthRole(roleId);
		return authPermissionService.findRolePermissions(role);
	}

	/**
	 * 更新角色权限
	 * 
	 * @since 1.0
	 * @param roleId
	 *            角色编号
	 * @param permissions
	 *            权限编号列表
	 */
	@PostMapping("/{id}/permissions")
	@RequiresPermissions("authRole:edit")
	public void updateRolePermissions(@PathVariable("id") int roleId, @RequestBody List<Integer> permissions) {
		System.out.println(permissions);
		this.authRoleService.removeAllPermissions(roleId);
		Iterator<Integer> iterator = permissions.iterator();
		while (iterator.hasNext()) {
			this.authRoleService.addPermission(roleId, iterator.next());
		}
	}

	/**
	 * 返回角色权限（树型结构）
	 * 
	 * @since 1.0
	 * @param roleId
	 * @return
	 */
	@GetMapping("/{id}/permissions/tree")
	public List<AuthPermission> getRolePermissionTree(@PathVariable(name = "id") int roleId) {
		AuthRole role = new AuthRole(roleId);
		return authPermissionService.getRolePermissionTree(role);
	}

	/**
	 * 新增角色
	 * 
	 * @since 1.0
	 * @param request
	 * @return
	 */
	@PostMapping("/new")
	@RequiresPermissions("authRole:new")
	public JsonResult<AuthRole> insert(HttpServletRequest request) {
		AuthRole role = new AuthRole();
		role.setRoleName(request.getParameter("roleName"));
		role.setRoleDescription(request.getParameter("roleDescription"));
		if (this.authRoleService.findOne(role) != null) { // 角色名已存在
			return new JsonResult<AuthRole>(JsonResult.ERROR, "角色名【" + role.getRoleName() + "】已被使用，请使用其他角色名");
		}
		if (true == this.authRoleService.insert(role)) {
			return new JsonResult<AuthRole>();
		} else {
			return new JsonResult<AuthRole>(JsonResult.ERROR, "新增角色出错！");
		}
	}

	/**
	 * 获取角色
	 * 
	 * @since 1.0
	 * @param roleId
	 *            角色编号
	 * @return
	 */
	@GetMapping("/{id}")
	public AuthRole getRole(@PathVariable("id") int roleId) {
		AuthRole role = new AuthRole(roleId);
		return this.authRoleService.findOne(role);
	}

	/**
	 * 更新角色
	 * 
	 * @since 1.0
	 * @param roleId
	 *            角色编号
	 * @param request
	 * @return
	 */
	@PostMapping("/{id}")
	@RequiresPermissions("authRole:edit")
	public JsonResult<AuthRole> update(@PathVariable("id") int roleId, HttpServletRequest request) {
		AuthRole role = new AuthRole();
		role.setRoleId(roleId);
		role.setRoleName(request.getParameter("roleName"));
		role.setRoleDescription(request.getParameter("roleDescription"));
		role.setAvailable("on".equals(request.getParameter("available")));
		if (true == this.authRoleService.update(role)) {
			return new JsonResult<AuthRole>(role);
		} else {
			return new JsonResult<AuthRole>(JsonResult.ERROR, "修改角色出错！");
		}
	}

	/**
	 * 删除角色
	 * 
	 * @since 1.0
	 * @param roleId
	 *            角色编号
	 * @param request
	 * @return
	 */
	@DeleteMapping("/{id}")
	@RequiresPermissions("authRole:delete")
	public JsonResult<AuthRole> delete(@PathVariable("id") int roleId, HttpServletRequest request) {
		if (true == this.authRoleService.delete(roleId)) {
			return new JsonResult<AuthRole>();
		} else {
			return new JsonResult<AuthRole>(JsonResult.ERROR, "删除角色出错！");
		}
	}

}
