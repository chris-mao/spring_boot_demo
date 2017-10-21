/**
 * 
 */
package com.jrsoft.auth.service;

import java.util.List;
import java.util.Set;

import com.github.pagehelper.PageInfo;
import com.jrsoft.auth.entity.AuthPermission;
import com.jrsoft.auth.entity.AuthRole;

/**
 * com.jrsoft.auth.service AuthPermissionService
 * 
 * 系统权限服务接口
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface AuthPermissionService {

	/**
	 * 查询所有权限，不分页
	 * 
	 * @return List
	 */
	public List<AuthPermission> findAll();

	/**
	 * 查询所有权限，分页
	 * 
	 * @param pageNum
	 * @return PageInfo
	 */
	public PageInfo<AuthPermission> findAll(int pageNum);

	/**
	 * 查询所有有效的权限信息
	 * 
	 * @return List
	 */
	public List<AuthPermission> findAllAvailable();

	/**
	 * 按权限编号或是名称查询
	 * 
	 * @param permission
	 * @return AuthPermission
	 */
	public AuthPermission findOne(AuthPermission permission);

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
	public boolean delete(int id);

	/**
	 * 按角色编号或是角色名称查询其所拥有的权限
	 * 
	 * @param role
	 * @return Set
	 */
	public Set<AuthPermission> findAllByRole(AuthRole role);
}
