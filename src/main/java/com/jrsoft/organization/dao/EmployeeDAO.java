/**
 * 
 */
package com.jrsoft.organization.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.jrsoft.organization.dao.sqlprovider.EmployeeDynaSqlProvider;
import com.jrsoft.organization.entity.Employee;

/**
 * 员工数据访问类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface EmployeeDAO {

	@SelectProvider(method = "findAllSql", type = EmployeeDynaSqlProvider.class)
	@Results({ @Result(property = "employeeId", column = "employee_id", id = true),
			@Result(property = "employeeName", column = "employee_name"), @Result(property = "email", column = "email"),
			@Result(property = "phone", column = "phone"), @Result(property = "fax", column = "fax"),
			@Result(property = "oracleAccount", column = "oracle_account"),
			@Result(property = "userId", column = "user_id"), @Result(property = "reportTo", column = "report_to"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<Employee> findAll(@Param(value = "available") boolean onlyAvailable);

	@Select("SELECT employee_id, employee_name, email, fax, phone, oracle_account, user_id, report_to, available, created_time, update_time FROM employee WHERE employee_id = #{id}")
	@Results({ @Result(property = "employeeId", column = "employee_id", id = true),
			@Result(property = "employeeName", column = "employee_name"), @Result(property = "email", column = "email"),
			@Result(property = "phone", column = "phone"), @Result(property = "fax", column = "fax"),
			@Result(property = "oracleAccount", column = "oracle_account"),
			@Result(property = "userId", column = "user_id"), @Result(property = "reportTo", column = "report_to"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public Employee findById(@Param(value = "id") int id);

	@Select("SELECT employee_id, employee_name, email, fax, phone, oracle_account, user_id, report_to, available, created_time, update_time FROM employee WHERE employee_name = #{name}")
	@Results({ @Result(property = "employeeId", column = "employee_id", id = true),
			@Result(property = "employeeName", column = "employee_name"), @Result(property = "email", column = "email"),
			@Result(property = "phone", column = "phone"), @Result(property = "fax", column = "fax"),
			@Result(property = "oracleAccount", column = "oracle_account"),
			@Result(property = "userId", column = "user_id"), @Result(property = "reportTo", column = "report_to"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public Employee findByName(@Param(value = "name") String employeeName);

	@Results({ @Result(property = "employeeId", column = "employee_id", id = true),
			@Result(property = "employeeName", column = "employee_name"), @Result(property = "email", column = "email"),
			@Result(property = "phone", column = "phone"), @Result(property = "fax", column = "fax"),
			@Result(property = "oracleAccount", column = "oracle_account"),
			@Result(property = "userId", column = "user_id"), @Result(property = "reportTo", column = "report_to"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<Employee> findAllByType();
	
	/**
	 * 根据用户编号查询其所拥有的有效角色清单
	 * 
	 * @param userName
	 * @return Set
	 */
	@Select("SELECT employee_id, employee_name, email, fax, phone, oracle_account, user_id, report_to, available, created_time, update_time FROM employee WHERE employee_name LIKE #{employeeName}")
	@Results({ @Result(property = "employeeId", column = "employee_id", id = true),
			@Result(property = "employeeName", column = "employee_name"), @Result(property = "email", column = "email"),
			@Result(property = "phone", column = "phone"), @Result(property = "fax", column = "fax"),
			@Result(property = "oracleAccount", column = "oracle_account"),
			@Result(property = "userId", column = "user_id"), @Result(property = "reportTo", column = "report_to"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<Employee> fuzzyQuery(Employee employee);
}
