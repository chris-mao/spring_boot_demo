/**
 * 
 */
package com.jrsoft.order.entity;

import java.io.Serializable;
import java.util.Date;

import com.jrsoft.customer.entity.CustomerSite;
import com.jrsoft.inventory.entity.InventoryModel;
import com.jrsoft.price.entity.PriceListHeader;

/**
 * 购物车实体类
 * 
 * com.jrsoft.order.entity ShoppingCartLine
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class ShoppingCartLine implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private CustomerSite customerSite;

	/**
	 * 
	 */
	private InventoryModel inventoryItem;

	/**
	 * 
	 */
	private int quantity;

	/**
	 * 
	 */
	private Date requestDate;

	/**
	 * 
	 */
	private PriceListHeader priceHeader;

	/**
	 * 
	 */
	private Double sellingPrice;

	/**
	 * 
	 */
	private boolean checkOut = true;

	/**
	 * @return the customerSite
	 */
	public CustomerSite getCustomerSite() {
		return customerSite;
	}

	/**
	 * @param customerSite
	 *            the customerSite to set
	 */
	public void setCustomerSite(CustomerSite customerSite) {
		this.customerSite = customerSite;
	}

	/**
	 * @return the inventoryItem
	 */
	public InventoryModel getInventoryItem() {
		return inventoryItem;
	}

	/**
	 * @param inventoryItem
	 *            the inventoryItem to set
	 */
	public void setInventoryItem(InventoryModel inventoryItem) {
		this.inventoryItem = inventoryItem;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
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

	/**
	 * @return the priceHeader
	 */
	public PriceListHeader getPriceHeader() {
		return priceHeader;
	}

	/**
	 * @param priceHeader
	 *            the priceHeader to set
	 */
	public void setPriceHeader(PriceListHeader priceHeader) {
		this.priceHeader = priceHeader;
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
	 * @return the checkOut
	 */
	public boolean getCheckOut() {
		return checkOut;
	}

	/**
	 * @param checkOut
	 *            the checkOut to set
	 */
	public void setCheckOut(boolean checkOut) {
		this.checkOut = checkOut;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ShoppingCartLine [customerSite=" + customerSite + ", inventoryItem=" + inventoryItem + ", quantity="
				+ quantity + ", requestDate=" + requestDate + ", priceHeader=" + priceHeader + ", sellingPrice="
				+ sellingPrice + ", checkOut=" + checkOut + "]";
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
		result = prime * result + ((customerSite == null) ? 0 : customerSite.hashCode());
		result = prime * result + ((inventoryItem == null) ? 0 : inventoryItem.hashCode());
		result = prime * result + ((priceHeader == null) ? 0 : priceHeader.hashCode());
		result = prime * result + ((requestDate == null) ? 0 : requestDate.hashCode());
		result = prime * result + ((sellingPrice == null) ? 0 : sellingPrice.hashCode());
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
		ShoppingCartLine other = (ShoppingCartLine) obj;
		if (customerSite == null) {
			if (other.customerSite != null)
				return false;
		} else if (!customerSite.equals(other.customerSite))
			return false;
		if (inventoryItem == null) {
			if (other.inventoryItem != null)
				return false;
		} else if (!inventoryItem.equals(other.inventoryItem))
			return false;
		if (priceHeader == null) {
			if (other.priceHeader != null)
				return false;
		} else if (!priceHeader.equals(other.priceHeader))
			return false;
		if (requestDate == null) {
			if (other.requestDate != null)
				return false;
		} else if (!requestDate.equals(other.requestDate))
			return false;
		if (sellingPrice == null) {
			if (other.sellingPrice != null)
				return false;
		} else if (!sellingPrice.equals(other.sellingPrice))
			return false;
		return true;
	}

}
