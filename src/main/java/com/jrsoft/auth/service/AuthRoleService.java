/**
 * 
 */
package com.jrsoft.auth.service;

import java.util.Set;

import com.jrsoft.app.service.AbstractDbService;
import com.jrsoft.auth.entity.AuthPermission;
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.auth.entity.AuthRolePermissionReleation;
import com.jrsoft.auth.entity.AuthUser;

/**
 * 系统角色服务接口
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.2
 *
 */
public interface AuthRoleService extends AbstractDbService<AuthRole> {

	/**
	 * 根据用户查询其所拥有的有效角色清单
	 * 
	 * @since 1.0
	 * @param user
	 * @return Set
	 */
	public Set<AuthRole> findAllByUser(AuthUser user);

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
