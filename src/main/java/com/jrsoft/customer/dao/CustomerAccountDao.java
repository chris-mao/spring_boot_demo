/**
 * 
 */
package com.jrsoft.customer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import com.jrsoft.customer.entity.CustomerAccount;

/**
 * com.jrsoft.customer.dao CustomerAccountDao
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface CustomerAccountDao {

	/**
	 * 
	 * @return
	 */
	@Select("SELECT customer_id, customer_number, customer_name, country, available, created_time, update_time FROM customer")
	@Results({ @Result(property = "customerId", column = "customer_id", id = true),
			@Result(property = "accountNumber", column = "customer_number"),
			@Result(property = "customerName", column = "customer_name"),
			@Result(property = "country", column = "country"), @Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time"),
			@Result(property = "billTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDao.findAllBillTo", fetchType = FetchType.LAZY) ),
			@Result(property = "shipTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDao.findAllShipTo", fetchType = FetchType.LAZY) ),
			@Result(property = "deliverTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDao.findAllDeliverTo", fetchType = FetchType.LAZY) ) })
	public List<CustomerAccount> findAll();

	/**
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT customer_id, customer_number, customer_name, country, available, created_time, update_time FROM customer WHERE customer_id = #{id}")
	@Results({ @Result(property = "customerId", column = "customer_id", id = true),
			@Result(property = "accountNumber", column = "customer_number"),
			@Result(property = "customerName", column = "customer_name"),
			@Result(property = "country", column = "country"), @Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time"),
			@Result(property = "billTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDao.findAllBillTo", fetchType = FetchType.LAZY) ),
			@Result(property = "shipTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDao.findAllShipTo", fetchType = FetchType.LAZY) ),
			@Result(property = "deliverTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDao.findAllDeliverTo", fetchType = FetchType.LAZY) ) })
	public CustomerAccount findById(@Param(value = "id") Integer id);

	/**
	 * 
	 * @param accountNumber
	 * @return
	 */
	@Select("SELECT customer_id, customer_number, customer_name, country, available, created_time, update_time FROM customer WHERE customer_number = #{accountNumber}")
	@Results({ @Result(property = "customerId", column = "customer_id", id = true),
			@Result(property = "accountNumber", column = "customer_number"),
			@Result(property = "customerName", column = "customer_name"),
			@Result(property = "country", column = "country"), @Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time"),
			@Result(property = "billTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDao.findAllBillTo", fetchType = FetchType.LAZY) ),
			@Result(property = "shipTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDao.findAllShipTo", fetchType = FetchType.LAZY) ),
			@Result(property = "deliverTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDao.findAllDeliverTo", fetchType = FetchType.LAZY) ) })
	public CustomerAccount findByAccountNumber(@Param(value = "accountNumber") String accountNumber);

	/**
	 * 
	 * @param credential
	 * @return
	 */
	@Select("CALL sp_findCustomerByCredential(#{user_name})")
	@Results({ @Result(property = "customerId", column = "customer_id", id = true),
			@Result(property = "accountNumber", column = "customer_number"),
			@Result(property = "customerName", column = "customer_name"),
			@Result(property = "country", column = "country"), @Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time"),
			@Result(property = "billTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDao.findAllBillTo", fetchType = FetchType.LAZY) ),
			@Result(property = "shipTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDao.findAllShipTo", fetchType = FetchType.LAZY) ),
			@Result(property = "deliverTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDao.findAllDeliverTo", fetchType = FetchType.LAZY) ) })
	public List<CustomerAccount> findAllByCredential(@Param(value = "user_name") String credential);

	@Select("SELECT customer_id, customer_number, customer_name, country, available, created_time, update_time FROM customer WHERE customer_id IN (SELECT customer_id FROM employee_customer WHERE employee_id= #{emp_id})")
	@Results({ @Result(property = "customerId", column = "customer_id", id = true),
			@Result(property = "accountNumber", column = "customer_number"),
			@Result(property = "customerName", column = "customer_name"),
			@Result(property = "country", column = "country"), @Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time"),
			@Result(property = "billTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDao.findAllBillTo", fetchType = FetchType.LAZY) ),
			@Result(property = "shipTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDao.findAllShipTo", fetchType = FetchType.LAZY) ),
			@Result(property = "deliverTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDao.findAllDeliverTo", fetchType = FetchType.LAZY) ) })
	public List<CustomerAccount> findALlByEmployeeId(@Param(value = "emp_id") Integer employeeId);

	/**
	 * 
	 * @param priceHeaderId
	 * @return
	 */
	@Select("CALL sp_findQualifiedCustomers(#{header_id})")
	@Results({ @Result(property = "customerId", column = "customer_id", id = true),
			@Result(property = "accountNumber", column = "customer_number"),
			@Result(property = "customerName", column = "customer_name"),
			@Result(property = "country", column = "country"), @Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time"),
			@Result(property = "billTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDao.findAllBillTo", fetchType = FetchType.LAZY) ),
			@Result(property = "shipTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDao.findAllShipTo", fetchType = FetchType.LAZY) ),
			@Result(property = "deliverTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDao.findAllDeliverTo", fetchType = FetchType.LAZY) ) })
	public List<CustomerAccount> findAllQualifiedCustomers(@Param(value = "header_id") Integer priceHeaderId);

}
