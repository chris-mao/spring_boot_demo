/**
 * 
 */
package com.example.demo.auth.service;

import java.util.List;

import com.example.demo.auth.AuthUserState;
import com.example.demo.auth.entity.AuthRole;
import com.example.demo.auth.entity.AuthUser;

/**
 * com.example.demo.auth.service AuthUserService
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface AuthUserService { 
	
	/**
	 * 查询所有用户信息
	 * 
	 * @return
	 */
	public List<AuthUser> findAll();
	
	/**
	 * 根据用户编号查询用户信息
	 * 
	 * @param id
	 * @return
	 */
	public AuthUser findById(Integer id);
  
	/**
	 * 根据用户名称查询用户信息
	 * 
	 * @param userName
	 * @return
	 */
	public AuthUser findByName(String userName);
	
	/**
	 * 创建新用户
	 * 
	 * @param user
	 * @return boolean 数据保存存成功返回true,否则返回false
	 */
	public boolean insert(AuthUser user);
	
	/**
	 * 更新用户信息，不会修改密码和加密盐值
	 * 如果需要修改密码请使用 changePassword
	 * 
	 * @param user
	 * @return boolean 更新成功返回true，否则返回false
	 */	public boolean update(AuthUser user);
	
	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return boolean 删除成功返回true，否则返回false
	 */
	public boolean delete(Integer id);
	
	/**
	 * 修改登录密码
	 * 
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 * @return boolean 更新成功返回true，否则返回false
	 */
	public boolean changePassword(Integer id, String oldPassword, String newPassword);
	
	/**
	 * 更新用户状态
	 * 
	 * @see AuthUserState
	 * 
	 * @param id
	 * @param state
	 * @return boolean 更新成功返回true，否则返回false
	 */
	public boolean changeState(Integer id, AuthUserState state);
	
	/**
	 * 添加新角色
	 * 
	 * @param user
	 * @param role
	 * @return
	 */
	public boolean addRole(AuthUser user, AuthRole role);
	
	/**
	 * 移除角色
	 * 
	 * @param user
	 * @param role
	 * @return
	 */
	public boolean removeRole(AuthUser user, AuthRole role);
}
