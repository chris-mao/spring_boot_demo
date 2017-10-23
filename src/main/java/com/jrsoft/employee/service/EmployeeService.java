/**
 * 
 */
package com.jrsoft.employee.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.customer.entity.CustomerAccount;
import com.jrsoft.employee.entity.Employee;

/**
 * com.jrsoft.employee.service EmployeeService
 *
 * 员工服务接口
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface EmployeeService {

	/**
	 * 查询所有员工数据，不分页
	 * 
	 * @return List
	 */
	public List<Employee> findAll();

	/**
	 * 查询所有员工数据，分页
	 * 
	 * @param pageNum
	 * @return PageInfo
	 */
	public PageInfo<Employee> findAll(int pageNum);

	/**
	 * 按员工编号或是名称查询
	 * 
	 * @param emp
	 * @return Employee
	 */
	public Employee findOne(Employee emp);

	/**
	 * 根据客户Account Number查询对应的员工信息
	 * 
	 * @param customerNumber
	 * @return List
	 */
	public List<Employee> findAllByCustomer(CustomerAccount customer);

	/**
	 * 根据用户系统用户名获取员工信息
	 * 
	 * @param credential
	 * @return List
	 */
	public Employee findOneByCredential(AuthUser credential);

	/**
	 * 创建新员工
	 * 
	 * @param employee
	 * @return boolean 数据保存存成功返回true,否则返回false
	 */
	public boolean insert(Employee employee);

	/**
	 * 更新员工信息
	 * 
	 * @param employee
	 * @return boolean 更新成功返回true，否则返回false
	 */
	public boolean update(Employee employee);

	/**
	 * 删除员工
	 * 
	 * @param id
	 * @return boolean 删除成功返回true，否则返回false
	 */
	public boolean delete(int id);

	/**
	 * 添加新客户
	 * 
	 * @param employee
	 * @param customer
	 * @return boolean 成功返回true，否则返回false
	 */
	public boolean addCustomer(Employee employee, CustomerAccount customer);

	/**
	 * 移除客户
	 * 
	 * @param employee
	 * @param customer
	 * @return boolean 成功返回true，否则返回false
	 */
	public boolean removeCustomer(Employee employee, CustomerAccount customer);

	/**
	 * 移除指定员工的所有客户
	 * 
	 * @param employee
	 */
	public void removeAllCustomers(Employee employee);

}
