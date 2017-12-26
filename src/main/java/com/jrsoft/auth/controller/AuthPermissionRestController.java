/**
 * 
 */
package com.jrsoft.auth.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jrsoft.auth.AuthPermissionKindEnum;
import com.jrsoft.auth.entity.AuthPermission;
import com.jrsoft.auth.service.AuthPermissionService;
import com.jrsoft.common.JsonResult;
import com.jrsoft.common.EasyDataGrid;

/**
 * <p>
 * 系统权限控制器类，提供系统权限维护方法接口
 * <dl>
 * <dt>GET: permissions/api/list?parentId=1&page=1&rows=20&searchValue=</dt>
 * <dd>按父节点返回（符合查询条件或是全部）权限数据列表，需要拥有<code>authPermission:list</code>权限</dd>
 * <dt>GET: permissions/api/json</dt>
 * <dd>返回全部有效的（available=1）权限数据列表，需要拥有<code>authPermission:list</code>权限</dd>
 * <dt>GET: permissions/api/tree</dt>
 * <dd>以树型结构返回全部有效的（available=1）权限数据列表，需要拥有<code>authPermission:list</code>权限
 * </dd>
 * <dt>POST: permissions/api/new</dt>
 * <dd>新建权限数据，需要拥有<code>authPermission:new</code>权限</dd>
 * <dt>POST: permissions/api/{id}</dt>
 * <dd>更新权限数据，需要拥有<code>authPermission:edit</code>权限</dd>
 * <dt>DELETE: permissions/api/{id}</dt>
 * <dd>删除权限数据，需要拥有<code>authPermission:delete</code>权限</dd>
 * </dl>
 * </p>
 * 
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.1
 *
 */
@RestController
@RequestMapping("/permissions/api")
public class AuthPermissionRestController {

	@Autowired
	private AuthPermissionService authPermissionService;

	/**
	 * 按树结点获取权限列表
	 * 
	 * @since 1.1
	 * @param parentId
	 *            父结点编号
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            分页大小
	 * @param searchStr
	 *            模糊查询内容
	 * @return {@link EasyDataGrid }
	 */
	@GetMapping("/list")
	@RequiresPermissions("authPermission:list")
	public EasyDataGrid<AuthPermission> findAll(@RequestParam(name = "id", defaultValue = "0") int parentId,
			@RequestParam(name = "page", defaultValue = "1") int pageIndex,
			@RequestParam(name = "rows", defaultValue = "20") int pageSize,
			@RequestParam(name = "searchValue", defaultValue = "") String searchStr) {
		return authPermissionService.findChildNodes(parentId, pageIndex, pageSize, searchStr);
	}

	/**
	 * 返回有效的权限清单
	 * 
	 * @since 1.0
	 * @return String
	 */
	@GetMapping("/json")
	@RequiresPermissions("authPermission:list")
	public List<AuthPermission> jsonData() {
		return authPermissionService.findAll(true);
	}

	/**
	 * 以树型结构返回全部有效的（available=1）权限数据列表
	 * 
	 * @since 1.1
	 * @return
	 */
	@GetMapping("/tree")
	@RequiresPermissions("authPermission:list")
	public List<AuthPermission> permissionTree() {
		// authPermissionService.getIndividualPermissionTree(user)
		return authPermissionService.getPermissionTree();
	}

	/**
	 * 新增权限
	 * 
	 * @since 1.0
	 * @param request
	 * @return
	 */
	@PostMapping("/new")
	@RequiresPermissions("authPermission:new")
	public JsonResult<AuthPermission> insert(HttpServletRequest request) {
		AuthPermission permission = new AuthPermission();
		permission.setPermissionName(request.getParameter("permissionName"));
		permission.setPermissionText(request.getParameter("permissionText"));
		permission.setPermissionKind(AuthPermissionKindEnum.valueOf(request.getParameter("permissionKind")));
		permission.setPermissionUrl(request.getParameter("permissionUrl"));
		permission.setWeight(Integer.parseInt(request.getParameter("weight")));
		String parentId = request.getParameter("parentId");
		if ("".equals(parentId)) {
			permission.setParentId(0);
		} else {
			permission.setParentId(Integer.parseInt(parentId));
		}
		if (this.authPermissionService.findOne(permission) != null) { // 权限名已存在
			return new JsonResult<AuthPermission>(JsonResult.ERROR,
					"权限名【" + permission.getPermissionName() + "】已被使用，请使用其他权限名");
		}
		if (true == this.authPermissionService.insert(permission)) {
			return new JsonResult<AuthPermission>();
		} else {
			return new JsonResult<AuthPermission>(JsonResult.ERROR, "新增权限出错！");
		}
	}

	/**
	 * 获取权限
	 * 
	 * @since 1.0
	 * @param permissionId
	 *            权限编号
	 * @return
	 */
	@GetMapping("/{id}")
	public AuthPermission getPermission(@PathVariable("id") int permissionId) {
		AuthPermission role = new AuthPermission(permissionId);
		return this.authPermissionService.findOne(role);
	}

	/**
	 * 编辑权限
	 * 
	 * @since 1.0
	 * @param permissionId
	 * @param request
	 * @return
	 */
	@PostMapping("/{id}")
	@RequiresPermissions("authPermission:edit")
	public JsonResult<AuthPermission> update(@PathVariable("id") int permissionId, HttpServletRequest request) {
		AuthPermission permission = new AuthPermission();
		permission.setPermissionId(permissionId);
		permission.setPermissionName(request.getParameter("roleName"));
		permission.setPermissionName(request.getParameter("permissionName"));
		permission.setPermissionText(request.getParameter("permissionText"));
		permission.setPermissionKind(AuthPermissionKindEnum.valueOf(request.getParameter("permissionKind")));
		permission.setPermissionUrl(request.getParameter("permissionUrl"));
		permission.setWeight(Integer.parseInt(request.getParameter("weight")));
		permission.setParentId(Integer.parseInt(request.getParameter("parentId")));
		permission.setAvailable("on".equals(request.getParameter("available")));
		if (true == this.authPermissionService.update(permission)) {
			return new JsonResult<AuthPermission>(permission);
		} else {
			return new JsonResult<AuthPermission>(JsonResult.ERROR, "修改权限出错！");
		}
	}

	/**
	 * 删除权限
	 * 
	 * @since 1.0
	 * @param permissionId
	 *            权限编号
	 * @param request
	 * @return
	 */
	@DeleteMapping("/{id}")
	@RequiresPermissions("authPermission:delete")
	public JsonResult<AuthPermission> delete(@PathVariable("id") int permissionId, HttpServletRequest request) {
		if (true == this.authPermissionService.delete(permissionId)) {
			return new JsonResult<AuthPermission>();
		} else {
			return new JsonResult<AuthPermission>(JsonResult.ERROR, "删除权限出错！");
		}
	}

}
