/**
 * 
 */
package com.example.demo.price.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 价格表行实体类
 * 
 * com.example.demo.price.entity PriceListLine
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
	 * 
	 */
	@NotEmpty(message="")
	private Integer headerId;
	
	/**
	 * 
	 */
	@NotEmpty(message="")
	private Integer inventoryItemId;
	
	/**
	 * 
	 */
	@NotEmpty(message="")
	private String inventoryItemName;
	
	/**
	 * 
	 */
	private String inventoryItemDescription;
	
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
	private Integer minOrderQuantity;
	
	/**
	 * 
	 */
	@NotEmpty(message="")
	private Integer maxOrderQuantity;

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
	 * @return the inventoryItemId
	 */
	public Integer getInventoryItemId() {
		return inventoryItemId;
	}

	/**
	 * @param inventoryItemId the inventoryItemId to set
	 */
	public void setInventoryItemId(Integer inventoryItemId) {
		this.inventoryItemId = inventoryItemId;
	}

	/**
	 * @return the inventoryItemName
	 */
	public String getInventoryItemName() {
		return inventoryItemName;
	}

	/**
	 * @param inventoryItemName the inventoryItemName to set
	 */
	public void setInventoryItemName(String inventoryItemName) {
		this.inventoryItemName = inventoryItemName;
	}

	/**
	 * @return the inventoryItemDescription
	 */
	public String getInventoryItemDescription() {
		return inventoryItemDescription;
	}

	/**
	 * @param inventoryItemDescription the inventoryItemDescription to set
	 */
	public void setInventoryItemDescription(String inventoryItemDescription) {
		this.inventoryItemDescription = inventoryItemDescription;
	}

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
	public Integer getMinOrderQuantity() {
		return minOrderQuantity;
	}

	/**
	 * @param minOrderQuantity the minOrderQuantity to set
	 */
	public void setMinOrderQuantity(Integer minOrderQuantity) {
		this.minOrderQuantity = minOrderQuantity;
	}

	/**
	 * @return the maxOrderQuantity
	 */
	public Integer getMaxOrderQuantity() {
		return maxOrderQuantity;
	}

	/**
	 * @param maxOrderQuantity the maxOrderQuantity to set
	 */
	public void setMaxOrderQuantity(Integer maxOrderQuantity) {
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
		return "PriceListLine [headerId=" + headerId + ", inventoryItemId=" + inventoryItemId + ", inventoryItemName="
				+ inventoryItemName + ", inventoryItemDescription=" + inventoryItemDescription + ", uom=" + uom
				+ ", unitPrice=" + unitPrice + ", minOrderQuantity=" + minOrderQuantity + ", maxOrderQuantity="
				+ maxOrderQuantity + ", startDate=" + startDate + ", endDate=" + endDate + ", createdTime="
				+ createdTime + ", updateTime=" + updateTime + ", available=" + available + "]";
	}

}
