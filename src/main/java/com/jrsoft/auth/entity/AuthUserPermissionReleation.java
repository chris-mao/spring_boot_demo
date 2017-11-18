/**
 * 
 */
package com.jrsoft.auth.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 用户与权限关联实体类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class AuthUserPermissionReleation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户编号
	 */
	private int userId;

	/**
	 * 权限编号
	 */
	private int permissionId;

	/**
	 * 生效日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;

	/**
	 * 终止日期
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
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the permissionId
	 */
	public int getPermissionId() {
		return permissionId;
	}

	/**
	 * @param permissionId the permissionId to set
	 */
	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
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
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the createdTime
	 */
	public Date getCreatedTime() {
		return createdTime;
	}

	/**
	 * @param createdTime the createdTime to set
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
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AuthUserPermissionReleation [userId=" + userId + ", permissionId=" + permissionId + ", startDate="
				+ startDate + ", endDate=" + endDate + ", createdTime=" + createdTime + ", updateTime=" + updateTime
				+ "]";
	}

}
