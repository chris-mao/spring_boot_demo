/**
 * 
 */
package com.example.demo.auth.shiro;

import java.util.Set;

import com.example.demo.auth.entity.AuthRole;
import com.example.demo.auth.entity.AuthUser;

/**
 * com.example.demo.auth.shiro AuthUserDecorator
 * 
 * 系统用户装饰类，封装了系统用户及其对应的OU、客户或是员工信息
 * 用户身份认证成功后，会将此类写入到session中
 * 方便在程序其他地方读取当登录用户的相关信息
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class AuthUserDecorator {
	
	/**
	 * 
	 */
	private final AuthUser authUser;
	
	/**
	 * 
	 * @param theUser
	 */
	public AuthUserDecorator(AuthUser theUser) {
		if (theUser == null) {
            throw new IllegalArgumentException("认证用户对象(AuthUser)不能为空！");   
        }
		this.authUser = theUser;
	}
	
	/**
	 * 
	 * @return
	 */
	public Integer getUserId() {
		return this.authUser.getUserId();
	}
	
	/**
	 * 
	 * @return
	 */
	public String getUserName() {
		return this.authUser.getUserName();
	}
	
	/**
	 * 
	 * @return
	 */
	public String getNickName() {
		return this.authUser.getNickName();
	}
	
	/**
	 * 
	 * @return
	 */
	public String getEmail() {
		return this.authUser.getEmail();
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<AuthRole> getRoles() {
		return this.authUser.getRoles();
	}
	
	/**
	 * 
	 * @return
	 */
	public Object getCustomer() {
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public Object getEmployee() {
		return null;
	}

}
