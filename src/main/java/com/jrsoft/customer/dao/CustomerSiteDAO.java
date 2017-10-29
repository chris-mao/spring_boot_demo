/**
 * 
 */
package com.jrsoft.customer.dao;

import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.jrsoft.customer.entity.CustomerSite;

/**
 * com.jrsoft.customer.dao CustomerSiteDao
 * 
 * 客户地址数据访问接口
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Repository
public interface CustomerSiteDAO {

	/**
	 * 查询客户所有的BillTo地址
	 * 
	 * @param customerId
	 * @return
	 */
	@Select("SELECT customer_id, site_id, ou_id, ou_name, site_purpose, site_address, available, created_time, update_time FROM customer_sites WHERE site_purpose = 'BILL_TO' AND customer_id = #{id}")
	@Results({ @Result(property = "customerId", column = "customer_id"),
			@Result(property = "siteId", column = "site_id"), @Result(property = "operationUnitId", column = "ou_id"),
			@Result(property = "operationUnitName", column = "ou_name"),
			@Result(property = "sitePurpose", column = "site_purpose"),
			@Result(property = "address", column = "site_address"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public Set<CustomerSite> findAllBillTo(@Param(value = "id") int customerId);

	/**
	 * 查询客户所有的ShipTo地址
	 * 
	 * @param customerId
	 * @return
	 */
	@Select("SELECT customer_id, site_id, ou_id, ou_name, site_purpose, site_address, available, created_time, update_time FROM customer_sites WHERE site_purpose = 'SHIP_TO' AND customer_id = #{id}")
	@Results({ @Result(property = "customerId", column = "customer_id"),
			@Result(property = "siteId", column = "site_id"), @Result(property = "operationUnitId", column = "ou_id"),
			@Result(property = "operationUnitName", column = "ou_name"),
			@Result(property = "sitePurpose", column = "site_purpose"),
			@Result(property = "address", column = "site_address"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public Set<CustomerSite> findAllShipTo(@Param(value = "id") int customerId);

	/**
	 * 查询客户所有DeliveryTo地址
	 * 
	 * @param customerId
	 * @return
	 */
	@Select("SELECT customer_id, site_id, ou_id, ou_name, site_purpose, site_address, available, created_time, update_time FROM customer_sites WHERE site_purpose = 'DELIVER_TO' AND customer_id = #{id}")
	@Results({ @Result(property = "customerId", column = "customer_id"),
			@Result(property = "siteId", column = "site_id"), @Result(property = "operationUnitId", column = "ou_id"),
			@Result(property = "operationUnitName", column = "ou_name"),
			@Result(property = "sitePurpose", column = "site_purpose"),
			@Result(property = "address", column = "site_address"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public Set<CustomerSite> findAllDeliverTo(@Param(value = "id") int customerId);

	/**
	 * 按OU查询客户BillTo地址
	 * 
	 * @param customerId
	 * @param operationUnitId
	 * @return
	 */
	@Select("SELECT customer_id, site_id, ou_id, ou_name, site_purpose, site_address, available, created_time, update_time FROM customer_sites WHERE site_purpose = 'BILL_TO' AND customer_id = #{customer_id} AND ou_id = #{ou_id}")
	@Results({ @Result(property = "customerId", column = "customer_id"),
			@Result(property = "siteId", column = "site_id"), @Result(property = "operationUnitId", column = "ou_id"),
			@Result(property = "operationUnitName", column = "ou_name"),
			@Result(property = "sitePurpose", column = "site_purpose"),
			@Result(property = "address", column = "site_address"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public CustomerSite findOperationUnitBillTo(@Param(value = "customer_id") int customerId,
			@Param(value = "ou_id") int operationUnitId);

	/**
	 * 按OU查询客户ShipTo地址
	 * 
	 * @param customerId
	 * @param operationUnitId
	 * @return
	 */
	@Select("SELECT customer_id, site_id, ou_id, ou_name, site_purpose, site_address, available, created_time, update_time FROM customer_sites WHERE site_purpose = 'SHIP_TO' AND customer_id = #{customer_id} AND ou_id = #{ou_id}")
	@Results({ @Result(property = "customerId", column = "customer_id"),
			@Result(property = "siteId", column = "site_id"), @Result(property = "operationUnitId", column = "ou_id"),
			@Result(property = "operationUnitName", column = "ou_name"),
			@Result(property = "sitePurpose", column = "site_purpose"),
			@Result(property = "address", column = "site_address"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public Set<CustomerSite> findOperationUnitShipTo(@Param(value = "customer_id") int customerId,
			@Param(value = "ou_id") int operationUnitId);

	/**
	 * 按OU查询客户DeliveryTo地址
	 * 
	 * @param customerId
	 * @param operationUnitId
	 * @return
	 */
	@Select("SELECT customer_id, site_id, ou_id, ou_name, site_purpose, site_address, available, created_time, update_time FROM customer_sites WHERE site_purpose = 'DELIVER_TO' AND customer_id = #{customer_id} AND ou_id = #{ou_id}")
	@Results({ @Result(property = "customerId", column = "customer_id"),
			@Result(property = "siteId", column = "site_id"), @Result(property = "operationUnitId", column = "ou_id"),
			@Result(property = "operationUnitName", column = "ou_name"),
			@Result(property = "sitePurpose", column = "site_purpose"),
			@Result(property = "address", column = "site_address"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public Set<CustomerSite> findOperationUnitDeliverTo(@Param(value = "customer_id") int customerId,
			@Param(value = "ou_id") int operationUnitId);

}
