/**
 * 
 */
package com.jrsoft.auth.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.jrsoft.auth.AuthUserStateEnum;

/**
 * com.jrsoft.auth.entity AuthUser
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
	 * 初始密码
	 */
	public static final String Initial_Password = "Welcome123";

	/**
	 * 用户编号
	 */
	private int userId;

	/**
	 * 登录名称，不允许重复
	 */
	@NotEmpty(message = "用户名不允许为空")
	@Length(min = 4, max = 64, message = "用户名长度需在4位到64位之间")
	private String userName;

	/**
	 * 昵称
	 */
	@NotEmpty(message = "昵称不允许为空")
	@Length(min = 2, max = 64, message = "昵称长度需在2位到64位之间")
	private String nickName;

	/**
	 * 电子邮件，用于找回密码
	 */
	@NotEmpty(message = "电子邮件不允许为空")
	@Email(message = "请输入正确的电子邮件")
	@Length(max = 64, message = "电子邮件长度不能超过64位")
	private String email;

	/**
	 * 密码，使用MD5加密
	 */
	@NotEmpty(message = "登录密码不允许为空")
	@Length(min = 6, max = 64, message = "登录密码长度需在6位到64位之间")
	private String password;

	/**
	 * 
	 */
	private String salt;

	/**
	 * 是否有效
	 */
	private boolean available = true;

	/**
	 * 用户状态
	 * 
	 * @see AuthUserStateEnum Active 激活状态 Locked 帐户锁定 Expired 帐户过期 Inactive 帐户失效
	 */
	private AuthUserStateEnum state;

	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdTime;

	/**
	 * 更新时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	public AuthUser() {
		super();
		this.password = AuthUser.Initial_Password;
		this.state = AuthUserStateEnum.ACTIVE;
	}

	public AuthUser(int id) {
		this();
		this.setUserId(id);
	}

	public AuthUser(String name) {
		this();
		this.setUserName(name);
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(int userId) {
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
	public AuthUserStateEnum getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(AuthUserStateEnum state) {
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

	public boolean getAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AuthUser [userId=" + userId + ", userName=" + userName + ", nickName=" + nickName + ", email=" + email
				+ ", password=" + password + ", salt=" + salt + ", available=" + available + ", state=" + state
				+ ", createdTime=" + createdTime + ", updateTime=" + updateTime + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + userId;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthUser other = (AuthUser) obj;
		if (userId != other.userId)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

}
