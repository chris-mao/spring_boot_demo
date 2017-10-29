/**
 * 
 */
package com.jrsoft.auth.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.auth.entity.AuthUser;

/**
 * com.jrsoft.auth.service AuthUserService
 * 
 * 系统用户服务接口
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface AuthUserService {

	/**
	 * 查询所有数据，不分页
	 * 
	 * @return List
	 */
	public List<AuthUser> findAll();

	/**
	 * 查询所有数据，分页
	 * 
	 * @param pageNum
	 * @return PageInfo
	 */
	public PageInfo<AuthUser> findAll(int pageNum);

	/**
	 * 查询所有有效的用户信息
	 * 
	 * @return List
	 */
	public List<AuthUser> findAllAvailableUser();

	/**
	 * 按用户编号或是名称查询
	 * 
	 * @param user
	 * @return AuthUser
	 */
	public AuthUser findOne(AuthUser user);

	/**
	 * 创建新用户
	 * 
	 * @param user
	 * @return boolean 数据保存存成功返回true,否则返回false
	 */
	public boolean insert(AuthUser user);

	/**
	 * 更新用户信息，不会修改密码和加密盐值，如果需要修改密码请使用 {@link changePassword}
	 * 
	 * @param user
	 * @return boolean 更新成功返回true，否则返回false
	 */
	public boolean update(AuthUser user);

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return boolean 删除成功返回true，否则返回false
	 */
	public boolean delete(int id);

	/**
	 * 修改登录密码
	 * 
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 * @return boolean 更新成功返回true，否则返回false
	 */
	public boolean changePassword(int id, String oldPassword, String newPassword);

	/**
	 * 添加新角色
	 * 
	 * @param user
	 * @param role
	 * @return boolean 成功返回true，否则返回false
	 */
	public boolean addRole(AuthUser user, AuthRole role);

	/**
	 * 移除角色
	 * 
	 * @param user
	 * @param role
	 * @return boolean 成功返回true，否则返回false
	 */
	public boolean removeRole(AuthUser user, AuthRole role);

	/**
	 * 移除指定用户的所有角色
	 * 
	 * @param user
	 */
	public void removeAllRoles(AuthUser user);
}
