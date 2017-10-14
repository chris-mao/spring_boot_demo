/**
 * 
 */
package com.jrsoft.employee.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.customer.dao.CustomerAccountDAO;
import com.jrsoft.customer.entity.CustomerAccount;
import com.jrsoft.employee.dao.EmployeeDAO;
import com.jrsoft.employee.entity.Employee;
import com.jrsoft.employee.service.EmployeeService;

/**
 * com.jrsoft.employee.service.impl EmployeeServiceImpl
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Value("${pageSize}")
	private int pageSize = 20;

	@Resource
	private EmployeeDAO employeeDAO;

	@Resource
	private CustomerAccountDAO customerDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jrsoft.employee.service.EmployeeService#findAll()
	 */
	@Override
	public List<Employee> findAll() {
		return this.employeeDAO.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jrsoft.employee.service.EmployeeService#findAll(int)
	 */
	@Override
	public PageInfo<Employee> findAll(int pageNum) {
		PageHelper.startPage(pageNum, this.pageSize, "employee_name");
		return new PageInfo<Employee>(this.employeeDAO.findAll());
	}

	@Override
	public Employee findOne(Employee emp) {
		if (null != emp.getEmployeeId()) {
			return this.employeeDAO.findById(emp.getEmployeeId());
		}
		if (null != emp.getEmployeeName()) {
			return this.employeeDAO.findByName(emp.getEmployeeName());
		}
		return null;
	}

	@Override
	public List<Employee> findAllByCustomer(CustomerAccount customer) {
		if (null != customer.getAccountNumber()) {
			return employeeDAO.findAllByCustomer(customer.getAccountNumber(), 0);
		}
		return null;
	}

	@Override
	public List<Employee> findAllByCredential(AuthUser credential) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jrsoft.employee.service.EmployeeService#insert(com.jrsoft.employee.
	 * entity.Employee)
	 */
	@Override
	public boolean insert(Employee employee) {
		return 1 == this.employeeDAO.insert(employee);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jrsoft.employee.service.EmployeeService#update(com.jrsoft.employee.
	 * entity.Employee)
	 */
	@Override
	public boolean update(Employee employee) {
		return 1 == this.employeeDAO.udpate(employee);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jrsoft.employee.service.EmployeeService#delete(java.lang.Integer)
	 */
	@Override
	public boolean delete(Integer id) {
		return 1 == this.employeeDAO.delete(id);
	}

	@Override
	public boolean addCustomer(Employee employee, CustomerAccount customer) {
		if (null == employee.getEmployeeId()) {
			return false;
		}
		if (null == customer.getCustomerId()) {
			return false;
		}
		return 1 == this.employeeDAO.addCustomer(employee.getEmployeeId(), customer.getCustomerId());
	}

	@Override
	public boolean removeCustomer(Employee employee, CustomerAccount customer) {
		if (null == employee.getEmployeeId()) {
			return false;
		}
		if (null == customer.getCustomerId()) {
			return false;
		}
		return 1 == this.employeeDAO.removeCustomer(employee.getEmployeeId(), customer.getCustomerId());
	}

	@Override
	public void removeAllCustomers(Employee employee) {
		if (null != employee.getEmployeeId()) {
			this.employeeDAO.removeAllCustomers(employee.getEmployeeId());
		}
	}

}
