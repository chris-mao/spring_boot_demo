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
import org.springframework.stereotype.Repository;

import com.jrsoft.customer.entity.CustomerAccount;

/**
 * com.jrsoft.customer.dao CustomerAccountDao
 * 
 * 客户数据访问接口
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Repository
public interface CustomerAccountDAO {

	/**
	 * 查询所有客户
	 * 
	 * @return
	 */
	@Select("SELECT customer_id, customer_number, customer_name, country, available, created_time, update_time FROM customer ORDER BY customer_name")
	@Results({ @Result(property = "customerId", column = "customer_id", id = true),
			@Result(property = "accountNumber", column = "customer_number"),
			@Result(property = "customerName", column = "customer_name"),
			@Result(property = "country", column = "country"), @Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time"),
			@Result(property = "billTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDAO.findAllBillTo", fetchType = FetchType.LAZY) ),
			@Result(property = "shipTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDAO.findAllShipTo", fetchType = FetchType.LAZY) ),
			@Result(property = "deliverTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDAO.findAllDeliverTo", fetchType = FetchType.LAZY) ) })
	public List<CustomerAccount> findAll();

	/**
	 * 按客户编号查询客户信息
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
			@Result(property = "billTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDAO.findAllBillTo", fetchType = FetchType.LAZY) ),
			@Result(property = "shipTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDAO.findAllShipTo", fetchType = FetchType.LAZY) ),
			@Result(property = "deliverTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDAO.findAllDeliverTo", fetchType = FetchType.LAZY) ) })
	public CustomerAccount findById(@Param(value = "id") int id);

	/**
	 * 按客户代码查询客户信息
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
			@Result(property = "billTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDAO.findAllBillTo", fetchType = FetchType.LAZY) ),
			@Result(property = "shipTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDAO.findAllShipTo", fetchType = FetchType.LAZY) ),
			@Result(property = "deliverTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDAO.findAllDeliverTo", fetchType = FetchType.LAZY) ) })
	public CustomerAccount findByAccountNumber(@Param(value = "accountNumber") String accountNumber);

	/**
	 * 按登录用户名查询对应的客户清单
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
			@Result(property = "billTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDAO.findAllBillTo", fetchType = FetchType.LAZY) ),
			@Result(property = "shipTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDAO.findAllShipTo", fetchType = FetchType.LAZY) ),
			@Result(property = "deliverTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDAO.findAllDeliverTo", fetchType = FetchType.LAZY) ) })
	public List<CustomerAccount> findAllByCredential(@Param(value = "user_name") String credential);

	/**
	 * 按员工查询其负责的客户清单
	 * 
	 * @param employeeId
	 * @return
	 */
	@Select("SELECT customer_id, customer_number, customer_name, country, available, created_time, update_time FROM customer WHERE customer_id IN (SELECT customer_id FROM employee_customer WHERE employee_id= #{emp_id}) ORDER BY customer_name")
	@Results({ @Result(property = "customerId", column = "customer_id", id = true),
			@Result(property = "accountNumber", column = "customer_number"),
			@Result(property = "customerName", column = "customer_name"),
			@Result(property = "country", column = "country"), @Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time"),
			@Result(property = "billTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDAO.findAllBillTo", fetchType = FetchType.LAZY) ),
			@Result(property = "shipTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDAO.findAllShipTo", fetchType = FetchType.LAZY) ),
			@Result(property = "deliverTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDAO.findAllDeliverTo", fetchType = FetchType.LAZY) ) })
	public List<CustomerAccount> findAllByEmployeeId(@Param(value = "emp_id") int employeeId);

	/**
	 * 查询可以使用指定价格表的客户清单
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
			@Result(property = "billTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDAO.findAllBillTo", fetchType = FetchType.LAZY) ),
			@Result(property = "shipTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDAO.findAllShipTo", fetchType = FetchType.LAZY) ),
			@Result(property = "deliverTo", column = "customer_id", many = @Many(select = "com.jrsoft.customer.dao.CustomerSiteDAO.findAllDeliverTo", fetchType = FetchType.LAZY) ) })
	public List<CustomerAccount> findAllQualifiedCustomers(@Param(value = "header_id") int priceHeaderId);

}
