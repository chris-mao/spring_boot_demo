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
	@NotEmpty(message = "")
	private InventoryModel inventoryModel;

	/**
	 * 
	 */
	private String customerModel;

	/**
	 * 
	 */
	@NotEmpty(message = "")
	@Min(value = 1, message = "")
	private int qty;

	/**
	 * 
	 */
	@NotEmpty(message = "")
	private Double sellingPrice;

	/**
	 * 
	 */
	@NotEmpty(message = "")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date requestDate;

	/**
	 * @return the inventoryItem
	 */
	public InventoryModel getInventoryModel() {
		return inventoryModel;
	}

	/**
	 * @param inventoryItem
	 *            the inventoryItem to set
	 */
	public void setInventoryModel(InventoryModel inventoryModel) {
		this.inventoryModel = inventoryModel;
	}

	/**
	 * @return the customerItem
	 */
	public String getCustomerModel() {
		return customerModel;
	}

	/**
	 * @param customerItem
	 *            the customerItem to set
	 */
	public void setCustomerModel(String customerModel) {
		this.customerModel = customerModel;
	}

	/**
	 * @return the qty
	 */
	public int getQty() {
		return qty;
	}

	/**
	 * @param qty
	 *            the qty to set
	 */
	public void setQty(int qty) {
		this.qty = qty;
	}

	/**
	 * @return the sellingPrice
	 */
	public Double getSellingPrice() {
		return sellingPrice;
	}

	/**
	 * @param sellingPrice
	 *            the sellingPrice to set
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
	 * @param requestDate
	 *            the requestDate to set
	 */
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		return "OrderLine [inventoryModel=" + inventoryModel + ", customerModel=" + customerModel + ", qty=" + qty
				+ ", sellingPrice=" + NumberFormat.getInstance().format(sellingPrice) + ", requestDate="
				+ sdf.format(requestDate) + "]";
	}

}
