/**
 * 
 */
package com.jrsoft.auth.service;

import java.util.Date;
import java.util.List;

import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.auth.entity.AuthUserDelegate;

/**
 * com.jrsoft.auth.service AuthUserDelegateService
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface AuthUserDelegateService {
	
	/**
	 * 将身份授予指定用户，从授予当天开始生效，无终止日期
	 * 
	 * @param fromUser 授予身份用户
	 * @param toUser   被授予身份用户
	 */
	public void grantDelegate(AuthUser fromUser, AuthUser toUser);
	
	/**
	 * 将身份授予指定用户，从指定日期开始生效，到指定日期终止
	 * 
	 * @param fromUser
	 * @param toUser
	 * @param startDate 代理开始日期
	 * @param endDate   代理失效日期
	 */
	public void grantDelegate(AuthUser fromUser, AuthUser toUser, Date startDate, Date endDate);
	
	/**
	 * 回收授予身份
	 * 
	 * @param fromUser 授予身份用户
	 * @param toUser   被授予身份用户
	 */
	public void revokeDelegate(AuthUser fromUser, AuthUser toUser);
	
	/**
	 * 判断是否已授予身份
	 * 
	 * @param fromUser 授予身份用户
	 * @param toUser   被授予身份用户
	 * @return
	 */
	public boolean exists(AuthUser fromUser, AuthUser toUser);
	
	/**
	 * 查询所有
	 * @param toUser
	 * @return
	 */
	public List<AuthUserDelegate> findAllByFromUser(AuthUser fromUser);
	
	/**
	 * 
	 * @param fromUser
	 * @return
	 */
	public List<AuthUserDelegate> findAllByToUser(AuthUser toUser);

}
