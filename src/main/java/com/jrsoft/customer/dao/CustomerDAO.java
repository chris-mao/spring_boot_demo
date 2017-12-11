/**
 * 
 */
package com.jrsoft.customer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;

import com.jrsoft.auth.AuthUserStateEnum;
import com.jrsoft.auth.dao.handler.AuthUserStateEnumTypeHandler;
import com.jrsoft.auth.dao.sqlprovider.AuthUserDynaSqlProvider;
import com.jrsoft.customer.entity.Customer;

/**
 * com.jrsoft.customer.dao CustomerDAO
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface CustomerDAO {
	
	@SelectProvider(method = "findAllSql", type = AuthUserDynaSqlProvider.class)
	@Results({ @Result(property = "userId", column = "user_id", id = true),
			@Result(property = "userName", column = "user_name"), @Result(property = "nickName", column = "nick_name"),
			@Result(property = "email", column = "email"), @Result(property = "password", column = "user_psd"),
			@Result(property = "salt", column = "salt"),
			@Result(property = "state", column = "state", javaType = AuthUserStateEnum.class, typeHandler = AuthUserStateEnumTypeHandler.class),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	// @Result(property = "roles", column = "user_id", many = @Many(select =
	// "com.jrsoft.auth.dao.AuthRoleDAO.findAllByUserId", fetchType =
	// FetchType.LAZY) ) })
	public List<Customer> findAll(@Param(value = "available") boolean onlyAvailable);
	
	public Customer findById(@Param(value = "id") int id);
	
	public Customer findByNumber(@Param(value = "customerNumber") String customerNumber);
	
	public Customer findByName(@Param(value = "name") String customerName);
	
	public List<Customer> findAllByEmployee();
}
