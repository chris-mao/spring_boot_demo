/**
 * 
 */
package com.example.demo.auth.service;

import java.util.List;
import java.util.Set;

import com.example.demo.auth.entity.AuthPermission;
import com.example.demo.auth.entity.AuthRole;

/**
 * com.example.demo.auth.service AuthPermissionService
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface AuthPermissionService {

	/**
	 * 查询所有权限
	 * 
	 * @return List
	 */
	public List<AuthPermission> findAll();

	/**
	 * 按权限编号查询
	 * 
	 * @param id
	 * @return AuthPermission
	 */
	public AuthPermission findById(Integer id);

	/**
	 * 按权限名称查询
	 * 
	 * @param permissionName
	 * @return AuthPermission
	 */
	public AuthPermission findByName(String permissionName);

	/**
	 * 创建新权限
	 * 
	 * @param permission
	 * @return 成功返回true,否则返回false
	 */
	public boolean insert(AuthPermission permission);

	/**
	 * 更新权限
	 * 
	 * @param permission
	 * @return 成功返回true,否则返回false
	 */
	public boolean update(AuthPermission permission);

	/**
	 * 删除权限
	 * 
	 * @param permission
	 * @return 成功返回true,否则返回false
	 */
	public boolean delete(Integer id);

	/**
	 * 按角色查询其所拥有的权限
	 * 
	 * @param role
	 * @return Set
	 */
	public Set<AuthPermission> findAllByRole(AuthRole role);

	/**
	 * 按角色名称查询其所拥有的权限
	 * 
	 * @param roleName
	 * @return Set
	 */
	public Set<AuthPermission> findAllByRole(String roleName);

	// public Set<AuthPermission> findAllByUser(AuthUser user);

	// public Set<AuthPermission> findAllByUser(String userName);

}
