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
 * @version 1.2
 *
 */
public interface AuthUserService {
	
	/**
	 * 查询所有用户数据，不具备分页功能
	 * 
	 * @return List
	 * @since 1.0
	 */
	public List<AuthUser> findAll();

	/**
	 * 查询所有数据，具有分页功能
	 * 
	 * @param pageIndex 页码
	 * @param pageSize  分页大小
	 * @return {@link PageInfo}
	 * @since 1.0
	 */
	public PageInfo<AuthUser> findAll(int pageIndex, int pageSize);
	
	/**
	 * 根据传入的查询条件查询数据，肯有分页功能
	 * 如果参数searchStr为空，则查询所有用户数据，否则查询在<code>userName</code>或是<code>nickName</code>中含有其内容的用户数据
	 * 
	 * @param pageIndex 页码
	 * @param pageSize  分页大小
	 * @param searchStr 模糊查询内容
	 * @return
	 * @since 1.2
	 */
	public PageInfo<AuthUser> findAll(int pageIndex, int pageSize, String searchStr);
	
	/**
	 * 查询所有有效的用户信息
	 * 
	 * @return List
	 * @since 1.1
	 */
	public List<AuthUser> findAllAvailableUser();
	
	/**
	 * 按用户编号或是名称查询
	 * 
	 * @param user
	 * @return AuthUser
	 * @since 1.0
	 */
	public AuthUser findOne(AuthUser user);
	
	/**
	 * 创建新用户
	 * 
	 * @param user
	 * @return boolean 数据保存成功返回<code>true</code>,否则返回<code>false</code>
	 * @since 1.0
	 */
	public boolean insert(AuthUser user);
	
	/**
	 * 更新用户信息，不会修改密码和加密盐值，如果需要修改密码请使用 {@link changePassword}
	 * 
	 * @param user
	 * @return boolean 更新成功返回<code>true</code>,否则返回<code>false</code>
	 * @since 1.0
	 */
	public boolean update(AuthUser user); 
	
	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return boolean 删除成功返回<code>true</code>,否则返回<code>false</code>
	 * @since 1.0
	 */
	public boolean delete(int id);
	
	/**
	 * 修改登录密码
	 * 
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 * @return boolean 更新成功返回<code>true</code>,否则返回<code>false</code>
	 * @since 1.0
	 */
	public boolean changePassword(int id, String oldPassword, String newPassword);
	
	/**
	 * 添加新角色
	 * 
	 * @param user
	 * @param role
	 * @return boolean 成功返回<code>true</code>,否则返回<code>false</code>
	 * @since 1.0
	 */
	public boolean addRole(AuthUser user, AuthRole role);
	
	/**
	 * 移除角色
	 * 
	 * @param user
	 * @param role
	 * @return boolean 成功返回<code>true</code>,否则返回<code>false</code>
	 * @since 1.0
	 */
	public boolean removeRole(AuthUser user, AuthRole role);
	
	/**
	 * 移除指定用户的所有角色
	 * 
	 * @param user
	 * @since 1.0
	 */
	public void removeAllRoles(AuthUser user);
}
