/**
 * 
 */
package com.example.demo.auth.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限实体类
 *
 * com.example.demo.auth.entity AuthPermission
 * 
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class AuthPermission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer permissionId;

	/**
	 * 
	 */
	private String permissionName;

	/**
	 * 
	 */
	private String permissionUrl;

	/**
	 * 
	 */
	private Boolean available = Boolean.FALSE;

	/**
	 * 
	 */
	private Date createdTime;

	/**
	 * 
	 */
	private Date updateTime;

	/**
	 * @return the permissionId
	 */
	public Integer getPermissionId() {
		return permissionId;
	}

	/**
	 * @param permissionId
	 *            the permissionId to set
	 */
	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	/**
	 * @return the permissionName
	 */
	public String getPermissionName() {
		return permissionName;
	}

	/**
	 * @param permissionName
	 *            the permissionName to set
	 */
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	/**
	 * @return the permissionUrl
	 */
	public String getPermissionUrl() {
		return permissionUrl;
	}

	/**
	 * @param permissionUrl
	 *            the permissionUrl to set
	 */
	public void setPermissionUrl(String permissionUrl) {
		this.permissionUrl = permissionUrl;
	}

	/**
	 * @return the available
	 */
	public Boolean getAvailable() {
		return available;
	}

	/**
	 * @param available
	 *            the available to set
	 */
	public void setAvailable(Boolean available) {
		this.available = available;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AuthPermission [permissionId=" + permissionId + ", permissionName=" + permissionName
				+ ", permissionUrl=" + permissionUrl + ", available=" + available + ", createdTime=" + createdTime
				+ ", updateTime=" + updateTime + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((permissionId == null) ? 0 : permissionId.hashCode());
		result = prime * result + ((permissionName == null) ? 0 : permissionName.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		AuthPermission other = (AuthPermission) obj;
		if (permissionId == null) {
			if (other.permissionId != null)
				return false;
		} else if (!permissionId.equals(other.permissionId))
			return false;
		if (permissionName == null) {
			if (other.permissionName != null)
				return false;
		} else if (!permissionName.equals(other.permissionName))
			return false;
		return true;
	}

}
