/**
 * 
 */
package com.jrsoft.auth.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jrsoft.auth.entity.EasyTreeGridNode;
import com.jrsoft.auth.service.AuthPermissionService;
import com.jrsoft.common.DataGrid;

/**
 * <p>
 * 系统权限控制器类，提供系统权限维护方法接口
 * <dl>
 * <dt>按父节点返回（符合查询条件或是全部）权限数据列表，需要拥有authPermission:list权限</dt>
 * <dd>GET: permissions/rest/list?parentId=1&page=1&rows=20&searchValue=</dd>
 * <dt>返回全部有效的（available=1）权限数据列表，需要拥有authPermission:list权限</dt>
 * <dd>GET: permissions/rest/json</dd>
 * <dt>新建权限数据，需要拥有authPermission:new权限</dt>
 * <dd>POST: permissions/rest/new</dd>
 * <dt>更新权限数据，需要拥有authPermission:edit权限</dt>
 * <dd>POST: permissions/rest/{id}</dd>
 * <dt>删除权限数据，需要拥有authPermission:delete权限</dt>
 * <dd>DELETE: permissions/rest/{id}</dd>
 * </dl>
 * </p>
 * 
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.1
 *
 */
@RestController
@RequestMapping("/permissions/rest")
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
	 * @return {@link DataGrid }
	 */
	@GetMapping("/list")
	@RequiresPermissions("authPermission:list")
	public DataGrid<EasyTreeGridNode> findAll(@RequestParam(name = "id", defaultValue = "0") int parentId,
			@RequestParam(name = "page", defaultValue = "1") int pageIndex,
			@RequestParam(name = "rows", defaultValue = "20") int pageSize,
			@RequestParam(name = "searchValue", defaultValue = "") String searchStr) {
		return authPermissionService.findChildNodes(parentId, pageIndex, pageSize, searchStr);
	}

}
