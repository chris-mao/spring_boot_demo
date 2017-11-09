/**
 * 
 */
package com.jrsoft.auth.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 身份代理实体类
 * 
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class AuthUserDelegate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 授予身份用户
	 */
	private AuthUser fromUser;

	/**
	 * 被授予身份用户
	 */
	private AuthUser toUser;

	/**
	 * 生效日期
	 */
	@NotEmpty(message = "代理生效日期不能为空")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;

	/**
	 * 失效日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;

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

	/**
	 * 是否有效
	 */
	private boolean available = true;

	public AuthUserDelegate() {

	}

	public AuthUserDelegate(AuthUser fromUser, AuthUser toUser) {
		this.setFromUser(fromUser);
		this.setToUser(toUser);
		this.setStartDate(Calendar.getInstance().getTime());
	}

	public AuthUserDelegate(AuthUser fromUser, AuthUser toUser, Date startDate, Date endDate) {
		this.setFromUser(fromUser);
		this.setToUser(toUser);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}

	/**
	 * @return the fromUser
	 */
	public AuthUser getFromUser() {
		return fromUser;
	}

	/**
	 * @param fromUser
	 *            the fromUser to set
	 */
	public void setFromUser(AuthUser fromUser) {
		this.fromUser = fromUser;
	}

	/**
	 * @return the toUser
	 */
	public AuthUser getToUser() {
		return toUser;
	}

	/**
	 * @param toUser
	 *            the toUser to set
	 */
	public void setToUser(AuthUser toUser) {
		this.toUser = toUser;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		if ((null != endDate) && (endDate.before(this.startDate))) {
			this.endDate = this.startDate;
		} else {
			this.endDate = endDate;
		}
	}

	/**
	 * @return the createdTime
	 */
	public Date getCreatedTime() {
		return createdTime;
	}

	/**
	 * @param createdTime
	 *            the createdTime to set
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the available
	 */
	public boolean isAvailable() {
		return available;
	}

	/**
	 * @param available
	 *            the available to set
	 */
	public void setAvailable(boolean available) {
		this.available = available;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AuthUserDelegate [fromUser=" + fromUser + ", toUser=" + toUser + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", createdTime=" + createdTime + ", updateTime=" + updateTime
				+ ", available=" + available + "]";
	}

}
