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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jrsoft.employee.entity.Employee;

/**
 * com.jrsoft.employee.dao EmployeeDao
 * 
 * 员工数据访问接口
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Component
@Repository
public interface EmployeeDAO {

	/**
	 * 查询所有员工信息
	 * 
	 * @return
	 */
	@Select("SELECT employee_id, employee_name, phone, fax, email, oracle_account, available, created_time, update_time FROM employee")
	@Results({ @Result(property = "employeeId", column = "employee_id", id = true),
			@Result(property = "employeeName", column = "employee_name"), @Result(property = "phone", column = "phone"),
			@Result(property = "fax", column = "fax"), @Result(property = "email", column = "email"),
			@Result(property = "oracleAccount", column = "oracle_account"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<Employee> findAll();

	/**
	 * 按编号查询员工信息
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT employee_id, employee_name, phone, fax, email, oracle_account, available, created_time, update_time FROM employee WHERE employee_id = #{id}")
	@Results({ @Result(property = "employeeId", column = "employee_id", id = true),
			@Result(property = "employeeName", column = "employee_name"), @Result(property = "phone", column = "phone"),
			@Result(property = "fax", column = "fax"), @Result(property = "email", column = "email"),
			@Result(property = "oracleAccount", column = "oracle_account"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public Employee findById(@Param(value = "id") int id);

	/**
	 * 按名字查询员工信息
	 * 
	 * @param employeeName
	 * @return
	 */
	@Select("SELECT employee_id, employee_name, phone, fax, email, oracle_account, available, created_time, update_time FROM employee WHERE employee_name = #{emp_name}")
	@Results({ @Result(property = "employeeId", column = "employee_id", id = true),
			@Result(property = "employeeName", column = "employee_name"), @Result(property = "phone", column = "phone"),
			@Result(property = "fax", column = "fax"), @Result(property = "email", column = "email"),
			@Result(property = "oracleAccount", column = "oracle_account"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public Employee findByName(@Param(value = "emp_name") String employeeName);

	/**
	 * 按客户查询与其接洽的员工清单
	 * 
	 * @param customerNumber
	 * @param operationUnitId
	 * @return
	 */
	@Select("CALL sp_findEmployeesByCustomer(#{customer_number}, #{ou_id})")
	@Results({ @Result(property = "employeeId", column = "employee_id", id = true),
			@Result(property = "employeeName", column = "employee_name"), @Result(property = "phone", column = "phone"),
			@Result(property = "fax", column = "fax"), @Result(property = "email", column = "email"),
			@Result(property = "oracleAccount", column = "oracle_account"),
			@Result(property = "available", column = "available") })
	public List<Employee> findAllByCustomer(@Param(value = "customer_number") String customerNumber,
			@Param(value = "ou_id") int operationUnitId);

	/**
	 * 按用户编号查询对应的员工信息
	 * 
	 * @param userId
	 * @return
	 */
	@Select("SELECT employee_id, employee_name, phone, fax, email, oracle_account, available, created_time, update_time FROM employee WHERE user_id = #{user_id}")
	@Results({ @Result(property = "employeeId", column = "employee_id", id = true),
			@Result(property = "employeeName", column = "employee_name"), @Result(property = "phone", column = "phone"),
			@Result(property = "fax", column = "fax"), @Result(property = "email", column = "email"),
			@Result(property = "oracleAccount", column = "oracle_account"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public Employee findOneByUserId(@Param(value = "user_id") int userId);

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
	public int delete(@Param(value = "id") int id);

	/**
	 * 添加新客户
	 * 
	 * @param employeeId
	 * @param customerId
	 * @return
	 */
	@Insert("INSERT IGNORE employee_customer(employee_id, customer_id, created_time) VALUE(#{employeeId}, #{customerId}, NOW())")
	public int addCustomer(@Param(value = "employeeId") int employeeId, @Param(value = "customerId") int customerId);

	/**
	 * 移除已关联客户
	 * 
	 * @param employeeId
	 * @param customerId
	 * @return
	 */
	@Delete("DELETE FROM employee_customer WHERE employee_id = #{employeeId} AND customer_id = #{customerId}")
	public int removeCustomer(@Param(value = "employeeId") int employeeId, @Param(value = "customerId") int customerId);

	/**
	 * 移除指定员工的所有客户
	 * 
	 * @param employeeId
	 */
	@Delete("DELETE FROM employee_customer WHERE employee_id = #{employeeId}")
	public void removeAllCustomers(@Param(value = "employeeId") int employeeId);

}
