/**
 * 
 */
package com.jrsoft.auth.entity;

import java.util.Date;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.jrsoft.auth.AuthPermissionKindEnum;
import com.jrsoft.common.EasyTreeNode;

/**
 * 权限实体类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class AuthPermission extends EasyTreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 权限编号
	 * 
	 * @since 1.0
	 */
	private int permissionId;

	/**
	 * 权限名称
	 * 
	 * @since 1.0
	 */
	@NotEmpty(message = "权限名称不允许为空")
	@Length(min = 6, max = 64, message = "权限名称长度需在6位到64位之间")
	private String permissionName;

	/**
	 * 对应的访问地址
	 * 
	 * @since 1.0
	 */
	@Length(max = 128, message = "资源路径长度不能超过128位")
	private String permissionUrl;

	/**
	 * 权限显示名称
	 * 
	 * @since 1.1
	 */
	@NotEmpty(message = "权限显示名称不允许为空")
	@Length(min = 4, max = 64, message = "权限显示名称长度需在4位到64位之间")
	private String permissionText;

	/**
	 * 权重，用于指定排序，数字小的排在前面
	 * 
	 * @since 1.1
	 */
	private int weight;

	/**
	 * 父节点名称
	 * 
	 * @since 1.1
	 */
	private int parentId;

	/**
	 * 权限类型
	 * 
	 * @since 1.1
	 */
	private AuthPermissionKindEnum permissionKind;

	/**
	 * @since 1.0
	 */
	private boolean available = true;

	/**
	 * @since 1.0
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdTime;

	/**
	 * @since 1.0
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	public AuthPermission() {
		super();
	}

	public AuthPermission(int id) {
		this();
		this.setPermissionId(id);
	}

	public AuthPermission(String name) {
		this();
		this.setPermissionName(name);
	}

	/**
	 * @return the permissionId
	 */
	public int getPermissionId() {
		return permissionId;
	}

	/**
	 * @param permissionId
	 *            the permissionId to set
	 */
	public void setPermissionId(int permissionId) {
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
	public boolean getAvailable() {
		return available;
	}

	/**
	 * @param available
	 *            the available to set
	 */
	public void setAvailable(boolean available) {
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

	/**
	 * @return the permissionText
	 */
	public String getPermissionText() {
		return permissionText;
	}

	/**
	 * @param permissionText
	 *            the permissionText to set
	 */
	public void setPermissionText(String permissionText) {
		this.permissionText = permissionText;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * @return the parentId
	 */
	public int getParentId() {
		return this.parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the permissionKind
	 */
	public AuthPermissionKindEnum getPermissionKind() {
		return permissionKind;
	}

	/**
	 * @param permissionKind
	 *            the permissionKind to set
	 */
	public void setPermissionKind(AuthPermissionKindEnum permissionKind) {
		this.permissionKind = permissionKind;
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
		result = prime * result + permissionId;
		result = prime * result + ((permissionName == null) ? 0 : permissionName.hashCode());
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
		AuthPermission other = (AuthPermission) obj;
		if (permissionId != other.permissionId)
			return false;
		if (permissionName == null) {
			if (other.permissionName != null)
				return false;
		} else if (!permissionName.equals(other.permissionName))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AuthPermission [permissionId=" + permissionId + ", permissionName=" + permissionName
				+ ", permissionText=" + permissionText + ", permissionUrl=" + permissionUrl + ", weight=" + weight
				+ ", parentId=" + parentId + ", permissionKind=" + permissionKind + ", available=" + available
				+ ", createdTime=" + createdTime + ", updateTime=" + updateTime + "]";
	}

	/**
	 * <p>
	 * 针对EasyUI Tree组件要求，id字段是必须的
	 * </p>
	 * 
	 * @since 1.0
	 */
	@Override
	public int getId() {
		return this.getPermissionId();
	}
	
	/**
	 * <p>
	 * 针对EasyUI Tree组件要求，text字段是必须的
	 * </p>
	 * 
	 * @since 1.0
	 */
	@Override
	public String getText() {
		return this.getPermissionText();
	}

}
