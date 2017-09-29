/**
 * 
 */
package com.example.demo.price.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 价格表头实体类
 * 
 * com.example.demo.price.entity PriceListHeader
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class PriceListHeader implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer headerId;

	/**
	 * 
	 */
	@NotEmpty(message = "")
	@Max(value = 128, message = "")
	private String priceListName;

	/**
	 * 
	 */
	@NotEmpty(message = "")
	@Length(min = 3, max = 10, message = "")
	private String currency;

	/**
	 * 
	 */
	@NotEmpty(message = "")
	private Date startDate;

	/**
	 * 
	 */
	private Date endDate;

	/**
	 * 
	 */
	private List<PriceListLine> lines;

	/**
	 * 创建时间
	 */
	private Date createdTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 是否有效
	 */
	private Boolean available;

	/**
	 * @return the headerId
	 */
	public Integer getHeaderId() {
		return headerId;
	}

	/**
	 * @param headerId the headerId to set
	 */
	public void setHeaderId(Integer headerId) {
		this.headerId = headerId;
	}

	/**
	 * @return the priceListName
	 */
	public String getPriceListName() {
		return priceListName;
	}

	/**
	 * @param priceListName the priceListName to set
	 */
	public void setPriceListName(String priceListName) {
		this.priceListName = priceListName;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
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
	 * @return the lines
	 */
	public List<PriceListLine> getLines() {
		return lines;
	}

	/**
	 * @param lines the lines to set
	 */
	public void setLines(List<PriceListLine> lines) {
		this.lines = lines;
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

	/**
	 * @return the available
	 */
	public Boolean getAvailable() {
		return available;
	}

	/**
	 * @param available the available to set
	 */
	public void setAvailable(Boolean available) {
		this.available = available;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PriceListHeader [headerId=" + headerId + ", priceListName=" + priceListName + ", currency=" + currency
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", lines=" + lines + ", createdTime="
				+ createdTime + ", updateTime=" + updateTime + ", available=" + available + "]";
	}

}
