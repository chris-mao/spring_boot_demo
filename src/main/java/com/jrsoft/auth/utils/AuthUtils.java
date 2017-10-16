/**
 * 
 */
package com.jrsoft.auth.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.jrsoft.auth.entity.AuthUser;

/**
 * com.jrsoft.auth.utils AuthUtils
 * 
 * 用户认证工具类
 * 
 * 用于获取用户凭证，及以登录的用户相关信息
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class AuthUtils {
	
	private static AuthUser theUser;

	/**
	 * 获取已登录的用户
	 * 
	 * @return Subject
	 */
	public static Subject getCredential() {
		return SecurityUtils.getSubject();
	}

	/**
	 * 获取已登录的用户实例
	 * 如果用户已登录则返回AuthUser对象实例，否则返回null
	 * 
	 * @return AuthUser
	 */
	public static AuthUser getUser() {
		if (getCredential().isAuthenticated()) {
			if (null == theUser) {
				theUser = (AuthUser) getCredential().getPrincipal();
			}
			return theUser;
		}
		return null;
	}

}
