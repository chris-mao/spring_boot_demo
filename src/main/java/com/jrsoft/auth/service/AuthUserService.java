/**
 * 
 */
package com.jrsoft.auth.service;

import java.util.Set;

import com.jrsoft.app.service.AbstractDbService;
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.auth.entity.AuthUserRoleReleation;

/**
 * 系统用户服务接口
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.3
 *
 */
public interface AuthUserService extends AbstractDbService<AuthUser> {

	/**
	 * 更新用户信息，不会修改密码和加密盐值，如果需要修改密码请使用 {@link changePassword}
	 * 
	 * @param user
	 * @return boolean 更新成功返回<code>true</code>,否则返回<code>false</code>
	 * @since 1.0
	 */
	public boolean update(AuthUser user);

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
	 * 锁定用户帐号
	 * 
	 * @since 1.3
	 * @param userId
	 * @throws NotFoundException
	 */
	public void lockUser(final int userId);
	
	/**
	 * 锁定用户帐号
	 * 
	 * @since 1.3
	 * @param userName
	 * @throws NotFoundException
	 */
	public void lockUser(final String userName);

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
	public boolean grantRole(AuthUserRoleReleation releation);

	/**
	 * 更新用户角色关联关系
	 * 
	 * @since 1.2
	 * @param releation
	 * @return
	 */
	public boolean updateGrantedRole(AuthUserRoleReleation releation);

	/**
	 * 删除用户角色关联关系
	 * 
	 * @since 1.2
	 * @param releation
	 * @return
	 */
	public boolean revokeRole(AuthUserRoleReleation releation);

	/**
	 * 添加新角色，此方法此被废弃，请参考{@link #grantRole},{@link #updateGrantedRole},
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
	public boolean grantRole(AuthUser user, AuthRole role);

	/**
	 * 移除角色，此方法此被废弃，请参考{@link #grantRole},{@link #updateGrantedRole},
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
	public boolean revokeRole(AuthUser user, AuthRole role);

	/**
	 * 移除指定用户的所有角色
	 * 
	 * @since 1.0
	 * @param user
	 *            用户对象
	 */
	public void revokeAllRoles(AuthUser user);
}
