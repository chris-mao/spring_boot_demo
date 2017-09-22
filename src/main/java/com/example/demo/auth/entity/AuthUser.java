/**
 * 
 */
package com.example.demo.auth.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.demo.auth.AuthUserState;

/**
 * com.example.demo.auth.entity AuthUser
 * 
 * 系统用户实体类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class AuthUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户编号
	 */
	private Integer userId;

	/**
	 * 登录名称，不允许重复
	 */
	private String userName;

	/**
	 * 昵称
	 */
	private String nickName;
	
	/**
	 * 电子邮件，用于找回密码
	 */
	private String email;

	/**
	 * 密码，使用MD5加密
	 */
	private String password;

	/**
	 * 
	 */
	private String salt;

	/**
	 * 用户状态
	 * @see AuthUserState 
	 * Active     激活状态
	 * Locked     帐户锁定
	 * Expired    帐户过期
	 * Inactive   帐户失效
	 */
	private AuthUserState state;

	/**
	 * 创建时间
	 */
	private Date createdTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 用户拥有的角色
	 */
	private Set<AuthRole> roles = new HashSet<AuthRole>();

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the state
	 */
	public AuthUserState getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(AuthUserState state) {
		this.state = state;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * @param salt
	 *            the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getCredentialsSalt() {
		if (null == this.salt) {
			return "";
		}
		return this.userName + this.salt;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Set<AuthRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<AuthRole> roles) {
		this.roles = roles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AuthUser [userId=" + userId + ", userName=" + userName + ", nickName=" + nickName + ", email=" + email
				+ ", password=" + password + ", salt=" + salt + ", state=" + state + ", createdTime=" + createdTime
				+ ", updateTime=" + updateTime + ", roles=" + roles + "]";
	}

}
