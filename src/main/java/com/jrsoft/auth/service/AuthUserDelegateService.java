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
 * 身份代理服务接口
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface AuthUserDelegateService {

	/**
	 * 将身份委托给指定用户，从授予当天开始生效，无终止日期
	 * 
	 * @param fromUser
	 *            委托人
	 * @param toUser
	 *            受托人（代理人）
	 */
	public void grantDelegate(AuthUser fromUser, AuthUser toUser);

	/**
	 * 将身份授予指定用户，从指定日期开始生效，到指定日期终止
	 * 
	 * @param fromUser
	 *            委托人
	 * @param toUser
	 *            受托人（代理人）
	 * @param startDate
	 *            代理开始日期
	 * @param endDate
	 *            代理失效日期
	 */
	public void grantDelegate(AuthUser fromUser, AuthUser toUser, Date startDate, Date endDate);

	/**
	 * 回收授予身份
	 * 
	 * @param fromUser
	 *            委托人
	 * @param toUser
	 *            受托人（代理人）
	 */
	public void revokeDelegate(AuthUser fromUser, AuthUser toUser);

	/**
	 * 判断是否已授予身份
	 * 
	 * @param fromUser
	 *            委托人
	 * @param toUser
	 *            受托人（代理人）
	 * @return
	 */
	public boolean exists(AuthUser fromUser, AuthUser toUser);

	/**
	 * 查询受托人／代理人（我把身份委托给了谁）
	 * 
	 * @param fromUser
	 *            委托人
	 * @return
	 */
	public List<AuthUserDelegate> findAllByFromUser(AuthUser fromUser);

	/**
	 * 查询委托人（谁把身份委托给了我）
	 * 
	 * @param toUser
	 *            受托人（代理人）
	 * @return
	 */
	public List<AuthUserDelegate> findAllByToUser(AuthUser toUser);

}
