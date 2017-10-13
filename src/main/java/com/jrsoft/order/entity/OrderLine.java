/**
 * 
 */
package com.jrsoft.order.entity;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.jrsoft.inventory.entity.InventoryModel;

/**
 * @author chrismao
 *
 */
public class OrderLine implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@NotEmpty(message="")
	private InventoryModel inventoryItem;

	/**
	 * 
	 */
	private String customerItem;

	/**
	 * 
	 */
	@NotEmpty(message="")
	@Min(value=1, message="")
	private Integer qty;

	/**
	 * 
	 */
	@NotEmpty(message="")
	private Double sellingPrice;

	/**
	 * 
	 */
	@NotEmpty(message="")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date requestDate;

	/**
	 * @return the inventoryItem
	 */
	public InventoryModel getInventoryItem() {
		return inventoryItem;
	}

	/**
	 * @param inventoryItem the inventoryItem to set
	 */
	public void setInventoryItem(InventoryModel inventoryItem) {
		this.inventoryItem = inventoryItem;
	}

	/**
	 * @return the customerItem
	 */
	public String getCustomerItem() {
		return customerItem;
	}

	/**
	 * @param customerItem the customerItem to set
	 */
	public void setCustomerItem(String customerItem) {
		this.customerItem = customerItem;
	}

	/**
	 * @return the qty
	 */
	public Integer getQty() {
		return qty;
	}

	/**
	 * @param qty the qty to set
	 */
	public void setQty(Integer qty) {
		this.qty = qty;
	}

	/**
	 * @return the sellingPrice
	 */
	public Double getSellingPrice() {
		return sellingPrice;
	}

	/**
	 * @param sellingPrice the sellingPrice to set
	 */
	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	/**
	 * @return the requestDate
	 */
	public Date getRequestDate() {
		return requestDate;
	}

	/**
	 * @param requestDate the requestDate to set
	 */
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		return "OrderLine [inventoryItem=" + inventoryItem + ", customerItem=" + customerItem + ", qty=" + qty
				+ ", sellingPrice=" + NumberFormat.getInstance().format(sellingPrice) + ", requestDate=" + sdf.format(requestDate) + "]";
	}

}
