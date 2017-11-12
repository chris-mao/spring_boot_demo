/**
 * 
 */
package com.jrsoft.auth.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.auth.service.AuthRoleService;
import com.jrsoft.common.DataGrid;

/**
 *<p>
 * 系统角色控制器类，提供系统角色维护方法接口
 * <dl>
 * <dt>按页码返回（符合查询条件或是全部）角色数据列表，需要拥有authRole:list权限</dt>
 * <dd>GET: users/rest/list?page=1&rows=20&searchValue=</dd>
 * <dt>返回全部有效的（available=1）角色数据列表，需要拥有authRole:list权限</dt>
 * <dd>GET: users/rest/json</dd>
 * <dt>新建角色数据，需要拥有authRole:new权限</dt>
 * <dd>POST: users/rest/new</dd>
 * <dt>更新角色数据，需要拥有authRole:edit权限</dt>
 * <dd>POST: users/rest/{id}</dd>
 * <dt>删除角色数据，需要拥有authRole:delete权限</dt>
 * <dd>DELETE: users/rest/{id}</dd>
 * <dt>返回角色关联的权限列表，需要拥有authRole:edit权限</dt>
 * <dd>GET: users/rest/{id}/roles</dd>
 * <dt>修改（新增、编辑、删除）角色关联权限，需要拥有authRole:edit权限</dt>
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
@RequestMapping("/roles/rest")
public class AuthRoleRestController {

	@Resource
	private AuthRoleService authRoleService;
	
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
	 * @return {@link DataGrid }
	 */
	@GetMapping("/list")
	@RequiresPermissions("authRole:list")
	public DataGrid<AuthRole> findAll(@RequestParam(name = "page", defaultValue = "1") int pageIndex,
			@RequestParam(name = "rows", defaultValue = "20") int pageSize,
			@RequestParam(name = "searchValue", defaultValue = "") String searchStr) {
		DataGrid<AuthRole> dg = new DataGrid<AuthRole>();
		PageInfo<AuthRole> pageInfo = this.authRoleService.findAll(pageIndex, pageSize, searchStr);
		dg.setTotal(pageInfo.getTotal());
		dg.setRows(pageInfo.getList());
		return dg;
	}
	
	/**
	 * 以Json格式返回角色清单
	 * 
	 * @return String
	 */
	@ResponseBody
	@GetMapping("/json")
	public List<AuthRole> jsonData() {
		return this.authRoleService.findAllAvailable();
	}

}
