package com.jrsoft.order.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author chrismao
 *
 */
public class OrderHeader {

	/**
	 * 
	 */
	private String oracleOrderNumber;

	/**
	 * 
	 */
	@NotEmpty(message = "")
	private String customerPO;

	/**
	 * 
	 */
	private String accountNumber;

	/**
	 * 
	 */
	private int customerId;

	/**
	 * 
	 */
	private String customerName;

	/**
	 * 
	 */
	private String operatingUnit;

	/**
	 * 
	 */
	private int operatingUnitId;

	/**
	 * 
	 */
	@NotEmpty(message = "")
	private String contact;

	/**
	 * 
	 */
	@NotEmpty(message = "")
	private String creator;

	/**
	 * 
	 */
	@NotEmpty(message = "")
	private String orderType;

	/**
	 * 
	 */
	@NotEmpty(message = "")
	private String currency;

	/**
	 * 
	 */
	@NotEmpty(message = "")
	private String billTo;

	/**
	 * 
	 */
	private String shipTo;

	/**
	 * 
	 */
	private String deliverTo;

	/**
	 * 
	 */
	private List<OrderLine> lines;

	public List<OrderLine> getLines() {
		return lines;
	}

	public String getCustomerPO() {
		return customerPO;
	}

	public void setCustomerPO(String customerPO) {
		this.customerPO = customerPO;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getOperatingUnit() {
		return operatingUnit;
	}

	public void setOperatingUnit(String operatingUnit) {
		this.operatingUnit = operatingUnit;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBillTo() {
		return billTo;
	}

	public void setBillTo(String billTo) {
		this.billTo = billTo;
	}

	public String getShipTo() {
		return shipTo;
	}

	public void setShipTo(String shipTo) {
		this.shipTo = shipTo;
	}

	public String getDeliverTo() {
		return deliverTo;
	}

	public void setDeliverTo(String deliverTo) {
		this.deliverTo = deliverTo;
	}

	public OrderHeader() {
		super();
		lines = new ArrayList<OrderLine>();
	}

	public void addOrderLine(OrderLine line) {
		if (!this.lines.contains(line)) {
			this.lines.add(line);
		}
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getOperatingUnitId() {
		return operatingUnitId;
	}

	public void setOperatingUnitId(int operatingUnitId) {
		this.operatingUnitId = operatingUnitId;
	}

	public String getOracleOrderNumber() {
		return oracleOrderNumber;
	}

	public void setOracleOrderNumber(String oracleOrderNumber) {
		this.oracleOrderNumber = oracleOrderNumber;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("==================== SALES ORDER(" + this.getCustomerPO() + ") ====================").append("\r\n");
		sb.append("  Operating Unit: ").append(this.getOperatingUnit()).append("\r\n");
		sb.append("  Order Type: ").append(this.getOrderType()).append("\r\n");
		sb.append("  Customer account: ").append(this.getAccountNumber()).append("\r\n");
		sb.append("  Customer Name: ").append(this.getCustomerName()).append("\r\n");
		sb.append("  CSR: ").append(this.getContact()).append("\r\n");
		sb.append("  Creator: ").append(this.getCreator()).append("\r\n");
		sb.append("  Bill To: ").append(this.getBillTo()).append("\r\n");
		sb.append("  Ship To: ").append(this.getShipTo()).append("\r\n");
		sb.append("  Deliver To: ").append(this.getDeliverTo()).append("\r\n");
		sb.append("  ==================== ").append("ORDER LINES(" + getLines().size() + ")")
				.append(" ====================").append("\r\n");
		sb.append("\tItem\tQty\tRTD\tPrice").append("\r\n");
		Iterator<OrderLine> iterator = this.getLines().iterator();
		while (iterator.hasNext()) {
			sb.append(iterator.next());
		}
		sb.append("  ======================== LINE END ========================").append("\r\n");
		// sb.append("========== ORDER HEADER(" + this.getCustomerPO() + ")
		// ==========\r\n");
		return sb.toString();
	}
}
