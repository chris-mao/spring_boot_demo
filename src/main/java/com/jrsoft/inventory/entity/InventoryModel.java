/**
 * 
 */
package com.jrsoft.inventory.entity;

import java.io.Serializable;
import java.math.BigInteger;
import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * com.jrsoft.inventory.entity model
 * 
 * 库存型号实体类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class InventoryModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 工厂型号编号
	 */
	private BigInteger modelId;

	/**
	 * 工厂型号名称
	 */
	@NotEmpty(message = "工厂型号名称不允许为空")
	@Max(value = 64, message = "工厂型号最大长度不能超过64位")
	private String modelName;

	/**
	 * 工厂型号短描述
	 */
	@Max(value = 128, message = "工厂型号描述最大长度不能超过128位")
	private String modelDescription;

	/**
	 * 工厂型号长描述
	 */
	@Max(value = 1024, message = "工厂型号中文描述最大长度不能超过1024位")
	private String modelChineseDescription;

	/**
	 * 创建时间
	 */
	// @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	// private Date createdTime;

	/**
	 * 更新时间
	 */
	// @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	// private Date updateTime;

	/**
	 * 是否有效
	 */
	private boolean available = true;

	/**
	 * @return the modelId
	 */
	public BigInteger getModelId() {
		return modelId;
	}

	/**
	 * @param modelId
	 *            the modelId to set
	 */
	public void setModelId(BigInteger modelId) {
		this.modelId = modelId;
	}

	/**
	 * @return the modelName
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * @param modelName
	 *            the modelName to set
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * @return the modelDescription
	 */
	public String getModelDescription() {
		return modelDescription;
	}

	/**
	 * @param modelDescription
	 *            the modelDescription to set
	 */
	public void setModelDescription(String modelDescription) {
		this.modelDescription = modelDescription;
	}

	/**
	 * @return the modelChineseDescription
	 */
	public String getModelChineseDescription() {
		return modelChineseDescription;
	}

	/**
	 * @param modelChineseDescription
	 *            the modelChineseDescription to set
	 */
	public void setModelChineseDescription(String modelChineseDescription) {
		this.modelChineseDescription = modelChineseDescription;
	}

	/**
	 * @return the available
	 */
	public boolean getAvailable() {
		return available;
	}

	/**
	 * @param available
	 *            the available to set
	 */
	public void setAvailable(boolean available) {
		this.available = available;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InventoryModel [modelId=" + modelId + ", modelName=" + modelName + ", modelDescription="
				+ modelDescription + ", modelChineseDescription=" + modelChineseDescription + ", available=" + available
				+ "]";
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
		result = prime * result + ((modelId == null) ? 0 : modelId.hashCode());
		result = prime * result + ((modelName == null) ? 0 : modelName.hashCode());
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
		InventoryModel other = (InventoryModel) obj;
		if (modelId == null) {
			if (other.modelId != null)
				return false;
		} else if (!modelId.equals(other.modelId))
			return false;
		if (modelName == null) {
			if (other.modelName != null)
				return false;
		} else if (!modelName.equals(other.modelName))
			return false;
		return true;
	}

}
