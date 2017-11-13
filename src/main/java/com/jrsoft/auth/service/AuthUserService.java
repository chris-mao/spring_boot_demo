/**
 * 
 */
package com.jrsoft.auth.service;

import java.util.List;
import java.util.Set;

import com.github.pagehelper.PageInfo;
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.auth.entity.AuthUserRoleReleation;
import com.jrsoft.common.DataGrid;

/**
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
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            分页大小
	 * @return {@link PageInfo}
	 * @since 1.0
	 */
	public PageInfo<AuthUser> findAll(int pageIndex, int pageSize);

	/**
	 * 根据传入的查询条件查询数据，具有分页功能 如果参数searchStr为空，则查询所有用户数据，否则查询在<code>userName</code>
	 * 或是<code>nickName</code>中含有其内容的用户数据
	 * 
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            分页大小
	 * @param searchStr
	 *            模糊查询内容
	 * @return
	 * @since 1.2
	 */
	public DataGrid<AuthUser> findAll(int pageIndex, int pageSize, String searchStr);

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
	 *            用户编号
	 * @param oldPassword
	 *            旧密码
	 * @param newPassword
	 *            新密码
	 * @return boolean 更新成功返回<code>true</code>,否则返回<code>false</code>
	 * @since 1.0
	 */
	public boolean changePassword(int id, String oldPassword, String newPassword);

	/**
	 * 查询用户所拥有的角色关系
	 * 
	 * @since 1.2
	 * @param id
	 *            用户编号
	 * @return List
	 */
	public Set<AuthUserRoleReleation> findUserRoles(int id);

	/**
	 * 新增用户角色关联关系
	 * 
	 * @since 1.2
	 * @param releation
	 * @return
	 */
	public boolean addRoleRelation(AuthUserRoleReleation releation);

	/**
	 * 更新用户角色关联关系
	 * 
	 * @since 1.2
	 * @param releation
	 * @return
	 */
	public boolean updateRoleRelation(AuthUserRoleReleation releation);

	/**
	 * 删除用户角色关联关系
	 * 
	 * @since 1.2
	 * @param releation
	 * @return
	 */
	public boolean removeRoleRelation(AuthUserRoleReleation releation);

	/**
	 * 添加新角色，此方法此被废弃，请参考{@link #addRoleRelation},{@link #updateRoleRelation},
	 * {@link #removeRoleRelation}
	 * 
	 * @deprecated
	 * @since 1.0
	 * @param user
	 *            用户对象
	 * @param role
	 *            角色对象
	 * @return boolean 成功返回<code>true</code>,否则返回<code>false</code>
	 */
	public boolean addRole(AuthUser user, AuthRole role);

	/**
	 * 移除角色，此方法此被废弃，请参考{@link #addRoleRelation},{@link #updateRoleRelation},
	 * {@link #removeRoleRelation}
	 * 
	 * @deprecated
	 * @since 1.0
	 * @param user
	 *            用户对象
	 * @param role
	 *            角色对象
	 * @return boolean 成功返回<code>true</code>,否则返回<code>false</code>
	 */
	public boolean removeRole(AuthUser user, AuthRole role);

	/**
	 * 移除指定用户的所有角色，此方法此被废弃，请参考{@link #addRoleRelation},
	 * {@link #updateRoleRelation},{@link #removeRoleRelation}
	 * 
	 * @deprecated
	 * @since 1.0
	 * @param user
	 *            用户对象
	 */
	public void removeAllRoles(AuthUser user);
}
