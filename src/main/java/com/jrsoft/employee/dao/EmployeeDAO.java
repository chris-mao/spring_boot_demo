/**
 * 
 */
package com.jrsoft.employee.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jrsoft.employee.entity.Employee;

/**
 * com.jrsoft.employee.dao EmployeeDao
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface EmployeeDAO {

	@Select("SELECT employee_id, employee_name, phone, fax, email, oracle_account, available, created_time, update_time FROM employee")
	@Results({ @Result(property = "employeeId", column = "employee_id", id = true),
			@Result(property = "employeeName", column = "employee_name"), @Result(property = "phone", column = "phone"),
			@Result(property = "fax", column = "fax"), @Result(property = "email", column = "email"),
			@Result(property = "oracleAccount", column = "oracle_account"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<Employee> findAll();

	@Select("SELECT employee_id, employee_name, phone, fax, email, oracle_account, available, created_time, update_time FROM employee WHERE employee_id = #{id}")
	@Results({ @Result(property = "employeeId", column = "employee_id", id = true),
			@Result(property = "employeeName", column = "employee_name"), @Result(property = "phone", column = "phone"),
			@Result(property = "fax", column = "fax"), @Result(property = "email", column = "email"),
			@Result(property = "oracleAccount", column = "oracle_account"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public Employee findById(@Param(value = "id") Integer id);

	@Select("SELECT employee_id, employee_name, phone, fax, email, oracle_account, available, created_time, update_time FROM employee WHERE employee_name = #{emp_name}")
	@Results({ @Result(property = "employeeId", column = "employee_id", id = true),
			@Result(property = "employeeName", column = "employee_name"), @Result(property = "phone", column = "phone"),
			@Result(property = "fax", column = "fax"), @Result(property = "email", column = "email"),
			@Result(property = "oracleAccount", column = "oracle_account"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public Employee findByName(@Param(value = "emp_name") String employeeName);

	@Select("CALL sp_findEmployeesByCustomer(#{customer_number}, #{ou_id})")
	@Results({ @Result(property = "employeeId", column = "employee_id", id = true),
			@Result(property = "employeeName", column = "employee_name"), @Result(property = "phone", column = "phone"),
			@Result(property = "fax", column = "fax"), @Result(property = "email", column = "email"),
			@Result(property = "oracleAccount", column = "oracle_account"),
			@Result(property = "available", column = "available") })
	public List<Employee> findAllByCustomer(@Param(value = "customer_number") String customerNumber,
			@Param(value = "ou_id") Integer operationUnitId);

	/**
	 * 创建新用户
	 * 
	 * @param employee
	 * @return 返回受影响的行数
	 */
	@Insert("INSERT INTO employee(employee_name, phone, fax, email, oracle_account, created_time) VALUES(#{employeeName}, #{phone}, #{fax}, #{email}, #{oracleAccount}, NOW())")
	@Options(useGeneratedKeys = true, keyProperty = "employeeId")
	public int insert(Employee employee);

	/**
	 * 更新用户信息
	 * 
	 * @param employee
	 * @return 返回受影响的行数
	 */
	@Update("UPDATE employee SET employee_name = #{employeeName}, phone = #{phone}, fax = #{fax}, email = #{email}, oracle_account = #{oracleAccount}, available = #{available} WHERE employee_id = #{employeeId}")
	public int udpate(Employee employee);

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return 返回受影响的行数
	 */
	@Delete("DELETE FROM employee WHERE employee_id = #{id}")
	public int delete(@Param(value = "id") Integer id);
	
	/**
	 * 添加新客户
	 * 
	 * @param employeeId
	 * @param customerId
	 * @return
	 */
	@Insert("INSERT IGNORE employee_customer(employee_id, customer_id, created_time) VALUE(#{employeeId}, #{customerId}, NOW())")
	public int addCustomer(@Param(value = "employeeId") Integer employeeId, @Param(value = "customerId") Integer customerId);

	/**
	 * 移除已关联客户
	 * 
	 * @param employeeId
	 * @param customerId
	 * @return
	 */
	@Delete("DELETE FROM employee_customer WHERE employee_id = #{employeeId} AND customer_id = #{customerId}")
	public int removeCustomer(@Param(value = "employeeId") Integer employeeId, @Param(value = "customerId") Integer customerId);
	
	/**
	 * 移除指定员工的所有客户
	 * @param employeeId
	 */
	@Delete("DELETE FROM employee_customer WHERE employee_id = #{employeeId}")
	public void removeAllCustomers(@Param(value = "employeeId") Integer employeeId);

}
