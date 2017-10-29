/**
 * 
 */
package com.jrsoft.price.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.jrsoft.inventory.entity.InventoryModel;

/**
 * com.jrsoft.price.entity PriceListLine
 *
 * 价格表行实体类
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
	 * 价格表行编号
	 */
	private int lineId;

	/**
	 * 工厂型号实体类
	 */
	private InventoryModel inventoryModel;

	/**
	 * 报价单位
	 */
	@NotEmpty(message = "")
	private String uom;

	/**
	 * 销售单价
	 */
	@NotEmpty(message = "")
	private Double unitPrice;

	/**
	 * 最小订购量，-222表示无最小订购量
	 */
	@NotEmpty(message = "")
	private BigInteger minOrderQuantity;

	/**
	 * 最大订购量，-222表示无最大订购量
	 */
	@NotEmpty(message = "")
	private BigInteger maxOrderQuantity;

	/**
	 * 销售价格生效日期
	 */
	@NotEmpty(message = "")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;

	/**
	 * 销售价格失效日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;

	/**
	 * @return the uom
	 */
	public String getUom() {
		return uom;
	}

	/**
	 * @param uom
	 *            the uom to set
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
	 * @param unitPrice
	 *            the unitPrice to set
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
	 * @param minOrderQuantity
	 *            the minOrderQuantity to set
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
	 * @param maxOrderQuantity
	 *            the maxOrderQuantity to set
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
		this.endDate = endDate;
	}

	public Integer getLineId() {
		return lineId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	public InventoryModel getInventoryModel() {
		return inventoryModel;
	}

	public void setInventoryModel(InventoryModel inventoryModel) {
		this.inventoryModel = inventoryModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PriceListLine [lineId=" + lineId + ", inventoryModel=" + inventoryModel + ", uom=" + uom
				+ ", unitPrice=" + unitPrice + ", minOrderQuantity=" + minOrderQuantity + ", maxOrderQuantity="
				+ maxOrderQuantity + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

}
