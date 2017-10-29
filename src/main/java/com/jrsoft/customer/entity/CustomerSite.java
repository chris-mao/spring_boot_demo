/**
 * 
 */
package com.jrsoft.customer.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * com.jrsoft.customer.entity CustomerSite
 *
 * 客户地址实体类
 * 
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class CustomerSite implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private int customerId;

	/**
	 * 
	 */
	private int siteId;

	/**
	 * 
	 */
	@NotEmpty(message = "")
	private int operationUnitId;

	/**
	 * 
	 */
	@NotEmpty(message = "")
	private String operationUnitName;

	/**
	 * 
	 */
	@NotEmpty(message = "")
	@Max(value = 64, message = "")
	private String sitePurpose;

	/**
	 * 
	 */
	@NotEmpty(message = "")
	@Max(value = 200, message = "")
	private String address;

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

	/**
	 * @return the customerId
	 */
	public int getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the siteId
	 */
	public int getSiteId() {
		return siteId;
	}

	/**
	 * @param siteId
	 *            the siteId to set
	 */
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	/**
	 * @return the operationUnitId
	 */
	public int getOperationUnitId() {
		return operationUnitId;
	}

	/**
	 * @param operationUnitId
	 *            the operationUnitId to set
	 */
	public void setOperationUnitId(int operationUnitId) {
		this.operationUnitId = operationUnitId;
	}

	/**
	 * @return the operationUnitName
	 */
	public String getOperationUnitName() {
		return operationUnitName;
	}

	/**
	 * @param operationUnitName
	 *            the operationUnitName to set
	 */
	public void setOperationUnitName(String operationUnitName) {
		this.operationUnitName = operationUnitName;
	}

	/**
	 * @return the sitePurpose
	 */
	public String getSitePurpose() {
		return sitePurpose;
	}

	/**
	 * @param sitePurpose
	 *            the sitePurpose to set
	 */
	public void setSitePurpose(String sitePurpose) {
		this.sitePurpose = sitePurpose;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomerSite [customerId=" + customerId + ", siteId=" + siteId + ", operationUnitId=" + operationUnitId
				+ ", operationUnitName=" + operationUnitName + ", sitePurpose=" + sitePurpose + ", address=" + address
				+ ", createdTime=" + createdTime + ", updateTime=" + updateTime + ", available=" + available + "]";
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
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + customerId;
		result = prime * result + operationUnitId;
		result = prime * result + ((operationUnitName == null) ? 0 : operationUnitName.hashCode());
		result = prime * result + siteId;
		result = prime * result + ((sitePurpose == null) ? 0 : sitePurpose.hashCode());
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
		CustomerSite other = (CustomerSite) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (customerId != other.customerId)
			return false;
		if (operationUnitId != other.operationUnitId)
			return false;
		if (operationUnitName == null) {
			if (other.operationUnitName != null)
				return false;
		} else if (!operationUnitName.equals(other.operationUnitName))
			return false;
		if (siteId != other.siteId)
			return false;
		if (sitePurpose == null) {
			if (other.sitePurpose != null)
				return false;
		} else if (!sitePurpose.equals(other.sitePurpose))
			return false;
		return true;
	}
}
