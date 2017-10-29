/**
 * 
 */
package com.jrsoft.customer.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * com.jrsoft.customer.entity Customer
 *
 * 客户实体类
 * 
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class CustomerAccount implements Serializable {

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
	@NotEmpty(message = "")
	@Max(value = 10, message = "")
	private String accountNumber;

	/**
	 * 
	 */
	@NotEmpty(message = "")
	private String customerName;

	/**
	 * 
	 */
	@NotEmpty(message = "")
	@Length(min = 2, max = 32, message = "")
	private String country;

	/**
	 * 
	 */
	private Set<CustomerSite> billTo;

	/**
	 * 
	 */
	private Set<CustomerSite> shipTo;

	/**
	 * 
	 */
	private Set<CustomerSite> deliverTo;

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

	public CustomerAccount() {
		super();
	}

	public CustomerAccount(int id) {
		this();
		this.setCustomerId(id);
	}

	public CustomerAccount(String accountNumber) {
		this();
		this.setAccountNumber(accountNumber);
	}

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
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber
	 *            the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the custoemrName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param custoemrName
	 *            the custoemrName to set
	 */
	public void setCustomerName(String custoemrName) {
		this.customerName = custoemrName;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the billTo
	 */
	public Set<CustomerSite> getBillTo() {
		return billTo;
	}

	/**
	 * @param billTo
	 *            the billTo to set
	 */
	public void setBillTo(Set<CustomerSite> billTo) {
		this.billTo = billTo;
	}

	/**
	 * @return the shipTo
	 */
	public Set<CustomerSite> getShipTo() {
		return shipTo;
	}

	/**
	 * @param shipTo
	 *            the shipTo to set
	 */
	public void setShipTo(Set<CustomerSite> shipTo) {
		this.shipTo = shipTo;
	}

	/**
	 * @return the deliverTo
	 */
	public Set<CustomerSite> getDeliverTo() {
		return deliverTo;
	}

	/**
	 * @param deliverTo
	 *            the deliverTo to set
	 */
	public void setDeliverTo(Set<CustomerSite> deliverTo) {
		this.deliverTo = deliverTo;
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
		return "CustomerAccount [customerId=" + customerId + ", accountNumber=" + accountNumber + ", custoemrName="
				+ customerName + ", country=" + country + ", billTo=" + billTo + ", shipTo=" + shipTo + ", deliverTo="
				+ deliverTo + ", createdTime=" + createdTime + ", updateTime=" + updateTime + ", available=" + available
				+ "]";
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
		result = prime * result + customerId;
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
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
		CustomerAccount other = (CustomerAccount) obj;
		if (customerId != other.customerId)
			return false;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		return true;
	}
}
