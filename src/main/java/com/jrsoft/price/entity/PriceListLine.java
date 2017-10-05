/**
 * 
 */
package com.jrsoft.price.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.jrsoft.inventory.entity.Item;

/**
 * 价格表行实体类
 * 
 * com.jrsoft.price.entity PriceListLine
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class PriceListLine implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 工厂型号
	 */
	private Item inventoryItem;
	
	/**
	 * 
	 */
	@NotEmpty(message="")
	private String uom;
	
	/**
	 * 
	 */
	@NotEmpty(message="")
	private Double unitPrice;
	
	/**
	 * 
	 */
	@NotEmpty(message="")
	private BigInteger minOrderQuantity;
	
	/**
	 * 
	 */
	@NotEmpty(message="")
	private BigInteger maxOrderQuantity;

	/**
	 * 
	 */
	@NotEmpty(message = "")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startDate;

	/**
	 * 
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
	 * @return the uom
	 */
	public String getUom() {
		return uom;
	}

	/**
	 * @param uom the uom to set
	 */
	public void setUom(String uom) {
		this.uom = uom;
	}

	/**
	 * @return the unitPrice
	 */
	public Double getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the minOrderQuantity
	 */
	public BigInteger getMinOrderQuantity() {
		return minOrderQuantity;
	}

	/**
	 * @param minOrderQuantity the minOrderQuantity to set
	 */
	public void setMinOrderQuantity(BigInteger minOrderQuantity) {
		this.minOrderQuantity = minOrderQuantity;
	}

	/**
	 * @return the maxOrderQuantity
	 */
	public BigInteger getMaxOrderQuantity() {
		return maxOrderQuantity;
	}

	/**
	 * @param maxOrderQuantity the maxOrderQuantity to set
	 */
	public void setMaxOrderQuantity(BigInteger maxOrderQuantity) {
		this.maxOrderQuantity = maxOrderQuantity;
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

	public Item getInventoryItem() {
		return inventoryItem;
	}

	public void setInventoryItem(Item inventoryItem) {
		this.inventoryItem = inventoryItem;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PriceListLine [inventoryItem=" + inventoryItem + ", uom=" + uom + ", unitPrice=" + unitPrice
				+ ", minOrderQuantity=" + minOrderQuantity + ", maxOrderQuantity=" + maxOrderQuantity + ", startDate="
				+ startDate + ", endDate=" + endDate + ", createdTime=" + createdTime + ", updateTime=" + updateTime
				+ "]";
	}

}
