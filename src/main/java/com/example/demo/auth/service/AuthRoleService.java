/**
 * 
 */
package com.example.demo.auth.service;

import java.util.List;
import java.util.Set;

import com.example.demo.auth.entity.AuthPermission;
import com.example.demo.auth.entity.AuthRole;
import com.example.demo.auth.entity.AuthUser;
import com.github.pagehelper.PageInfo;

/**
 * com.example.demo.auth.service AuthRoleService
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface AuthRoleService {
	
	/**
	 * 查询所有角色信息，不分页
	 * 
	 * @return List
	 */
	public List<AuthRole> findAll();
	
	/**
	 * 查询所有角色信息，分页
	 * 
	 * @param pageNum
	 * @return PageInfo
	 */
	public PageInfo<AuthRole> findAll(int pageNum);
	
	/**
	 * 按角色编号查询
	 * 
	 * @param id
	 * @return AuthRole
	 */
	public AuthRole findById(Integer id);
	
	/**
	 * 按角色名称查询
	 * 
	 * @param roleName
	 * @return AuthRole
	 */
	public AuthRole findByName(String roleName);
	
	/**
	 * 根据用户查询其所拥有的角色清单
	 * 
	 * @param user
	 * @return Set
	 */
	public Set<AuthRole> findAllByUser(AuthUser user);
	
	/**
	 * 根据用户名称查询其所拥有的角色清单
	 * 
	 * @param userName
	 * @return Set
	 */
	public Set<AuthRole> findAllByUser(String userName);
	
	/**
	 * 创建新角色
	 * 
	 * @param role
	 * @return 成功返回true,否则返回false
	 */
	public boolean insert(AuthRole role);
	
	/**
	 * 更新角色
	 * 
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
	public boolean delete(Integer id);
	
	/**
	 * 添加新权限
	 * 
	 * @param role
	 * @param permission
	 * @return 成功返回true,否则返回false
	 */
	public boolean addPermission(AuthRole role, AuthPermission permission);
	
	/**
	 * 移除权限
	 * 
	 * @param role
	 * @param permission
	 * @return 成功返回true,否则返回false
	 */
	public boolean removePermission(AuthRole role, AuthPermission permission);
}
