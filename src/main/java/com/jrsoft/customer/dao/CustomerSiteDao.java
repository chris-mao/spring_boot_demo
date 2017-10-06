/**
 * 
 */
package com.jrsoft.customer.dao;

import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.jrsoft.customer.entity.CustomerSite;

/**
 * com.jrsoft.customer.dao CustomerSiteDao
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface CustomerSiteDAO {

	@Select("SELECT customer_id, site_id, ou_id, ou_name, site_purpose, site_address, available, created_time, update_time FROM customer_sites WHERE site_purpose = 'BILL_TO' AND customer_id = #{id}")
	@Results({ @Result(property = "customerId", column = "customer_id"),
			@Result(property = "siteId", column = "site_id"), @Result(property = "operationUnitId", column = "ou_id"),
			@Result(property = "operationUnitName", column = "ou_name"),
			@Result(property = "sitePurpose", column = "site_purpose"),
			@Result(property = "address", column = "site_address"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public Set<CustomerSite> findAllBillTo(@Param(value = "id") Integer customerId);

	@Select("SELECT customer_id, site_id, ou_id, ou_name, site_purpose, site_address, available, created_time, update_time FROM customer_sites WHERE site_purpose = 'SHIP_TO' AND customer_id = #{id}")
	@Results({ @Result(property = "customerId", column = "customer_id"),
			@Result(property = "siteId", column = "site_id"), @Result(property = "operationUnitId", column = "ou_id"),
			@Result(property = "operationUnitName", column = "ou_name"),
			@Result(property = "sitePurpose", column = "site_purpose"),
			@Result(property = "address", column = "site_address"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public Set<CustomerSite> findAllShipTo(@Param(value = "id") Integer customerId);

	@Select("SELECT customer_id, site_id, ou_id, ou_name, site_purpose, site_address, available, created_time, update_time FROM customer_sites WHERE site_purpose = 'DELIVER_TO' AND customer_id = #{id}")
	@Results({ @Result(property = "customerId", column = "customer_id"),
			@Result(property = "siteId", column = "site_id"), @Result(property = "operationUnitId", column = "ou_id"),
			@Result(property = "operationUnitName", column = "ou_name"),
			@Result(property = "sitePurpose", column = "site_purpose"),
			@Result(property = "address", column = "site_address"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public Set<CustomerSite> findAllDeliverTo(@Param(value = "id") Integer customerId);

	@Select("SELECT customer_id, site_id, ou_id, ou_name, site_purpose, site_address, available, created_time, update_time FROM customer_sites WHERE site_purpose = 'BILL_TO' AND customer_id = #{customer_id} AND ou_id = #{ou_id}")
	@Results({ @Result(property = "customerId", column = "customer_id"),
			@Result(property = "siteId", column = "site_id"), @Result(property = "operationUnitId", column = "ou_id"),
			@Result(property = "operationUnitName", column = "ou_name"),
			@Result(property = "sitePurpose", column = "site_purpose"),
			@Result(property = "address", column = "site_address"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public CustomerSite findOperationUnitBillTo(@Param(value = "customer_id") Integer customerId, @Param(value = "ou_id") Integer operationUnitId);
	
	@Select("SELECT customer_id, site_id, ou_id, ou_name, site_purpose, site_address, available, created_time, update_time FROM customer_sites WHERE site_purpose = 'SHIP_TO' AND customer_id = #{customer_id} AND ou_id = #{ou_id}")
	@Results({ @Result(property = "customerId", column = "customer_id"),
			@Result(property = "siteId", column = "site_id"), @Result(property = "operationUnitId", column = "ou_id"),
			@Result(property = "operationUnitName", column = "ou_name"),
			@Result(property = "sitePurpose", column = "site_purpose"),
			@Result(property = "address", column = "site_address"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public Set<CustomerSite> findOperationUnitShipTo(@Param(value = "customer_id") Integer customerId, @Param(value = "ou_id") Integer operationUnitId);
	
	@Select("SELECT customer_id, site_id, ou_id, ou_name, site_purpose, site_address, available, created_time, update_time FROM customer_sites WHERE site_purpose = 'DELIVER_TO' AND customer_id = #{customer_id} AND ou_id = #{ou_id}")
	@Results({ @Result(property = "customerId", column = "customer_id"),
			@Result(property = "siteId", column = "site_id"), @Result(property = "operationUnitId", column = "ou_id"),
			@Result(property = "operationUnitName", column = "ou_name"),
			@Result(property = "sitePurpose", column = "site_purpose"),
			@Result(property = "address", column = "site_address"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public Set<CustomerSite> findOperationUnitDeliverTo(@Param(value = "customer_id") Integer customerId, @Param(value = "ou_id") Integer operationUnitId);

}
