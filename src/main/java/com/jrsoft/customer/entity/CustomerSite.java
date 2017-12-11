/**
 * 
 */
package com.jrsoft.customer.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * com.jrsoft.customer.entity CustomerSite
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
	public static final String BILL_TO = "BILL_TO";

	/**
	 * 
	 */
	public static final String SHIP_TO = "SHIP_TO";

	/**
	 * 
	 */
	public static final String DELIVER_TO = "DELIVER_TO";

	/**
	 * 
	 */
	private int siteId;

	/**
	 * 
	 */
	private String sitePurpose;

	/**
	 * 
	 */
	private String address;

	/**
	 * 是否有效
	 */
	private boolean available = true;

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
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return "";
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return "";
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return "";
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return "";
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

}
