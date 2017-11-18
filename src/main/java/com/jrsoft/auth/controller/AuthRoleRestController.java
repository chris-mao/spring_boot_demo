/**
 * 
 */
package com.jrsoft.auth.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

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

import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.auth.entity.AuthRolePermissionReleation;
import com.jrsoft.auth.service.AuthPermissionService;
import com.jrsoft.auth.service.AuthRoleService;
import com.jrsoft.common.EasyDataGrid;
import com.jrsoft.common.EasyTreeNode;
import com.jrsoft.common.JsonResult;

/**
 * <p>
 * 系统角色控制器类，提供系统角色维护方法接口
 * <dl>
 * <dt>按页码返回（符合查询条件或是全部）角色数据列表，需要拥有authRole:list权限</dt>
 * <dd>GET: roles/rest/list?page=1&rows=20&searchValue=</dd>
 * <dt>返回全部有效的（available=1）角色数据列表，需要拥有authRole:list权限</dt>
 * <dd>GET: roles/rest/json</dd>
 * <dt>新建角色数据，需要拥有authRole:new权限</dt>
 * <dd>POST: roles/rest/new</dd>
 * <dt>更新角色数据，需要拥有authRole:edit权限</dt>
 * <dd>POST: roles/rest/{id}</dd>
 * <dt>删除角色数据，需要拥有authRole:delete权限</dt>
 * <dd>DELETE: roles/rest/{id}</dd>
 * <dt>返回角色关联的权限列表</dt>
 * <dd>GET: roles/rest/{id}/permissions</dd>
 * <dt>修改（新增、编辑、删除）角色关联权限，需要拥有authRole:edit权限</dt>
 * <dd>POST: roles/rest/{id}/permissions</dd>
 * </dl>
 * </p>
 * 
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/roles/rest")
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
	public List<AuthRole> jsonData() {
		return this.authRoleService.findAll(true);
	}

	@PostMapping("/new")
	@RequiresPermissions("authRole:new")
	public JsonResult<AuthRole> insert(HttpServletRequest request) {
		AuthRole user = new AuthRole();
		user.setRoleName(request.getParameter("roleName"));
		if (this.authRoleService.findOne(user) != null) { // 角色名已存在
			return new JsonResult<AuthRole>(JsonResult.ERROR, "角色名【" + user.getRoleName() + "】已被使用，请使用其他角色名");
		}
		if (true == this.authRoleService.insert(user)) {
			return new JsonResult<AuthRole>();
		} else {
			return new JsonResult<AuthRole>(JsonResult.ERROR, "新增角色出错！");
		}
	}

	@PostMapping("/{id}")
	@RequiresPermissions("authRole:edit")
	public JsonResult<AuthRole> update(@PathVariable("id") int roleId, HttpServletRequest request) {
		AuthRole role = new AuthRole();
		role.setRoleId(roleId);
		role.setRoleName(request.getParameter("userName"));
		role.setAvailable(Boolean.parseBoolean(request.getParameter("available")));
		if (true == this.authRoleService.update(role)) {
			return new JsonResult<AuthRole>(role);
		} else {
			return new JsonResult<AuthRole>(JsonResult.ERROR, "修改角色出错！");
		}
	}

	@DeleteMapping("/{id}")
	@RequiresPermissions("authRole:delete")
	public JsonResult<AuthRole> delete(@PathVariable("id") int roleId, HttpServletRequest request) {
		if (true == this.authRoleService.delete(roleId)) {
			return new JsonResult<AuthRole>();
		} else {
			return new JsonResult<AuthRole>(JsonResult.ERROR, "删除角色出错！");
		}
	}

	/**
	 * 保存角色权限关联关系
	 * 
	 * @param roleId
	 *            角色编号
	 * @param userRoleReleations
	 *            角色权限关联应映表，此参数需要包含三个主键<code>inserted</code>,
	 *            <code>updated</code>和<code>deleted</code>
	 *            。每个主键对应一个List对象，其中存有一个或多个角色权限对象
	 *            {@link AuthRolePermissionReleation}
	 * @return
	 */
	@PostMapping("/{id}/permissions")
	@RequiresPermissions("authRole:edit")
	public void assignRolePermissions(@PathVariable("id") int roleId,
			@RequestBody Map<String, List<AuthRolePermissionReleation>> rolePermissionReleations) {
		String operation;
		AuthRolePermissionReleation r;
		Set<Entry<String, List<AuthRolePermissionReleation>>> set = rolePermissionReleations.entrySet();
		for (Iterator<Entry<String, List<AuthRolePermissionReleation>>> iter = set.iterator(); iter.hasNext();) {
			Entry<String, List<AuthRolePermissionReleation>> entry = iter.next();
			operation = entry.getKey();
			List<AuthRolePermissionReleation> list = entry.getValue();
			for (Iterator<AuthRolePermissionReleation> i = list.iterator(); i.hasNext();) {
				r = i.next();
				if ("inserted".equals(operation.toLowerCase())) {
					r.setRoleId(roleId);
					System.out.println("insert: " + r);
					// this.authRoleService.addRoleRelation(r);
				} else if ("updated".equals(operation.toLowerCase())) {
					System.out.println("update: " + r);
					// this.authRoleService.updateRoleRelation(r);
				} else if ("deleted".equals(operation.toLowerCase())) {
					System.out.println("delete: " + r);
					// this.authRoleService.removeRoleRelation(r);
				}
			}
		}
	}
	
	@GetMapping("/{id}/permissions")
	public List<EasyTreeNode> getRolePermissions(@PathVariable(name = "id") int roleId) {
		AuthRole role = new AuthRole(roleId);
		return authPermissionService.getRolePermissionTree(role);
	}

}
