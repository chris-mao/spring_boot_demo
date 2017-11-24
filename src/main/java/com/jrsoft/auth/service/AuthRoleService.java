/**
 * 
 */
package com.jrsoft.auth.service;

import java.util.List;
import java.util.Set;

import com.github.pagehelper.PageInfo;
import com.jrsoft.auth.entity.AuthPermission;
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.auth.entity.AuthRolePermissionReleation;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.common.EasyDataGrid;

/**
 * 系统角色服务接口
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.2
 *
 */
public interface AuthRoleService {

	/**
	 * 查询所有角色信息，不具备分页功能 等同于findAll(false)
	 * 
	 * @since 1.0
	 * @return List
	 */
	public List<AuthRole> findAll();

	/**
	 * 查询所有角色信息，不具备分页功能
	 * 
	 * @param onlyAvailable
	 *            仅返回有效的角色
	 * 
	 * @since 1.1
	 * @return List
	 */
	public List<AuthRole> findAll(boolean onlyAvailable);

	/**
	 * 查询所有数据，具有分页功能
	 * 
	 * @since 1.0
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            分页大小
	 * @return {@link PageInfo}
	 */
	public PageInfo<AuthRole> findAll(int pageNum, int pageSize);

	/**
	 * 根据传入的查询条件查询数据，具有分页功能 如果参数searchStr为空，则查询所有角色数据，否则查询<code>roleName</code>
	 * 中含有其内容的角色数据
	 * 
	 * @since 1.2
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            分页大小
	 * @param searchStr
	 *            模糊查询内容
	 * @return
	 */
	public EasyDataGrid<AuthRole> findAll(int pageIndex, int pageSize, String searchStr);

	/**
	 * 按角色编号或是名称查询
	 * 
	 * @since 1.0
	 * @param role
	 * @return AuthRole
	 */
	public AuthRole findOne(AuthRole role);

	/**
	 * 根据用户查询其所拥有的有效角色清单
	 * 
	 * @since 1.0
	 * @param user
	 * @return Set
	 */
	public Set<AuthRole> findAllByUser(AuthUser user);

	/**
	 * 创建新角色
	 * 
	 * @since 1.0
	 * @param role
	 * @return 成功返回true,否则返回false
	 */
	public boolean insert(AuthRole role);

	/**
	 * 更新角色
	 * 
	 * @since 1.0
	 * @param role
	 * @return 成功返回true,否则返回false
	 */
	public boolean update(AuthRole role);

	/**
	 * 删除角色
	 * 
	 * @param id
	 * @return 成功返回true,否则返回false
	 */
	public boolean delete(int id);

	/**
	 * 新增角色权限关联关系
	 * 
	 * @since 1.2
	 * @param rolePermission
	 * @return
	 */
	public boolean grantPermission(AuthRolePermissionReleation rolePermission);

	/**
	 * 更新角色权限关联关系
	 * 
	 * @since 1.2
	 * @param rolePermission
	 * @return
	 */
	public boolean updateGrantedPermission(AuthRolePermissionReleation rolePermission);

	/**
	 * 删除角色权限关联关系
	 * 
	 * @since 1.2
	 * @param rolePermission
	 * @return
	 */
	public boolean revokePermission(AuthRolePermissionReleation rolePermission);

	/**
	 * 添加新权限
	 * 
	 * @since 1.0
	 * @param role
	 * @param permission
	 * @return 成功返回true,否则返回false
	 */
	public boolean addPermission(AuthRole role, AuthPermission permission);

	/**
	 * 移除权限
	 * 
	 * @since 1.0
	 * @param role
	 * @param permission
	 * @return 成功返回true,否则返回false
	 */
	public boolean removePermission(AuthRole role, AuthPermission permission);

	/**
	 * 移除指定角色上所有权限
	 * 
	 * @since 1.0
	 * @param role
	 */
	public void removeAllPermissions(AuthRole role);

	/**
	 * 添加新权限
	 * 
	 * @since 1.0
	 * @param roleId
	 * @param permissionId
	 * @return 成功返回true,否则返回false
	 */
	public boolean addPermission(Integer roleId, Integer permissionId);

	/**
	 * 移除权限
	 * 
	 * @since 1.0
	 * @param roleId
	 * @param permissionId
	 * @return 成功返回true,否则返回false
	 */
	public boolean removePermission(Integer roleId, Integer permissionId);

	/**
	 * 移除指定角色上所有权限
	 * 
	 * @since 1.0
	 * @param roleId
	 */
	public void removeAllPermissions(Integer roleId);
}
