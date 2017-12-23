/**
 * 
 */
package com.jrsoft.organization.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 员工实体类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class Employee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String SALES_PERSON = "";

	/**
	 * 
	 */
	public static final String CSR = "";

	/**
	 * 员工编号
	 */
	private int employeeId;

	/**
	 * 员工姓名
	 */
	@NotEmpty(message = "员工姓名不能为空")
	@Length(min = 3, max = 64, message = "员工姓名长度需在3位到64位之间")
	private String employeeName;

	/**
	 * 电子邮件
	 */
	@Length(min = 10, max = 100, message = "电子邮件长度需在10位到100位之间")
	private String email;

	/**
	 * 电话号码
	 */
	@Length(min = 8, max = 32, message = "电话号码长度需要8位到32位之间")
	private String phone;
	
	
	/**
	 * 传真号码
	 */
	@Length(min = 8, max = 32, message = "传真号码长度需要8位到32位之间")
	private String fax;

	/**
	 * 员工类型
	 */
	private String employeeType;

	/**
	 * oracle帐号
	 */
	@Length(min = 4, max = 32, message = "Oracle帐号长度需要4位到32位之间")
	private String oracleAccount;

	/**
	 * 汇报主管、经理
	 */
	private int reportTo;
	
	/**
	 * 部门编号
	 */
	@NotEmpty(message = "请指定员工隶属部门")
	private int departmentId;

	/**
	 * 员工对应的系统用户编号
	 */
	private int userId;

	/**
	 * 是否有效
	 */
	private boolean available = true;

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
	 * @return the employeeId
	 */
	public int getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the employeeName
	 */
	public String getEmployeeName() {
		return employeeName;
	}

	/**
	 * @param employeeName the employeeName to set
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the employeeType
	 */
	public String getEmployeeType() {
		return employeeType;
	}

	/**
	 * @param employeeType the employeeType to set
	 */
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	/**
	 * @return the oracleAccount
	 */
	public String getOracleAccount() {
		return oracleAccount;
	}

	/**
	 * @param oracleAccount the oracleAccount to set
	 */
	public void setOracleAccount(String oracleAccount) {
		this.oracleAccount = oracleAccount;
	}

	/**
	 * @return the reportTo
	 */
	public int getReportTo() {
		return reportTo;
	}

	/**
	 * @param reportTo the reportTo to set
	 */
	public void setReportTo(int reportTo) {
		this.reportTo = reportTo;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the available
	 */
	public boolean isAvailable() {
		return available;
	}

	/**
	 * @param available the available to set
	 */
	public void setAvailable(boolean available) {
		this.available = available;
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
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", employeeName=" + employeeName + ", email=" + email + ", phone="
				+ phone + ", fax=" + fax + ", employeeType=" + employeeType + ", oracleAccount=" + oracleAccount
				+ ", reportTo=" + reportTo + ", userId=" + userId + ", available=" + available + ", createdTime="
				+ createdTime + ", updateTime=" + updateTime + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + employeeId;
		result = prime * result + ((employeeName == null) ? 0 : employeeName.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		Employee other = (Employee) obj;
		if (employeeId != other.employeeId)
			return false;
		if (employeeName == null) {
			if (other.employeeName != null)
				return false;
		} else if (!employeeName.equals(other.employeeName))
			return false;
		return true;
	}

}
