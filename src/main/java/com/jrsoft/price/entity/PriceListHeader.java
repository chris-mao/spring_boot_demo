/**
 * 
 */
package com.jrsoft.price.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * com.jrsoft.price.entity PriceListHeader
 *
 * 价格表头实体类
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
	 * 价格表头编号
	 */
	@NotEmpty(message = "")
	private int headerId;

	/**
	 * 价格表名称
	 */
	@NotEmpty(message = "")
	@Max(value = 128, message = "")
	private String name;

	/**
	 * 价格表币别
	 */
	@NotEmpty(message = "")
	@Length(min = 3, max = 10, message = "")
	private String currency;

	/**
	 * 价格表生效日期
	 */
	@NotEmpty(message = "")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startDate;

	/**
	 * 价格表失效日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endDate;

	/**
	 * 价格表中所行信息，考虑到性能问题，没有在DAO层实现加载价格行数据
	 * 请使用PriceSerivce中的findAllPriceLinesByHeaderId 或是
	 * findAllPriceLinesByName方法获取价格行数据
	 * 
	 * @see findAllPriceLinesByHeaderId
	 * @see findAllPriceLinesByName
	 */
	private List<PriceListLine> lines;

	public PriceListHeader() {
		super();
	}

	public PriceListHeader(int headerId) {
		this();
		this.setHeaderId(headerId);
	}

	public PriceListHeader(String priceListName) {
		this();
		this.setName(priceListName);
	}

	/**
	 * @return the headerId
	 */
	public int getHeaderId() {
		return headerId;
	}

	/**
	 * @param headerId
	 *            the headerId to set
	 */
	public void setHeaderId(int headerId) {
		this.headerId = headerId;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *            the currency to set
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

	/**
	 * @return the lines
	 */
	public List<PriceListLine> getLines() {
		return lines;
	}

	/**
	 * @param lines
	 *            the lines to set
	 */
	public void setLines(List<PriceListLine> lines) {
		this.lines = lines;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PriceListHeader [headerId=" + headerId + ", priceListName=" + name + ", currency=" + currency
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", lines=" + lines + "]";
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
		result = prime * result + headerId;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		PriceListHeader other = (PriceListHeader) obj;
		if (headerId != other.headerId)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
