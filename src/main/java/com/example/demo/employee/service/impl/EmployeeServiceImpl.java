/**
 * 
 */
package com.example.demo.employee.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.employee.dao.EmployeeDao;
import com.example.demo.employee.entity.Employee;
import com.example.demo.employee.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * com.example.demo.employee.service.impl EmployeeServiceImpl
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Value("${pageSize}")
	private int pageSize;
	
	@Resource
	private EmployeeDao employeeDao;

	/* (non-Javadoc)
	 * @see com.example.demo.employee.service.EmployeeService#findAll()
	 */
	@Override
	public List<Employee> findAll() {
		return this.employeeDao.findAll();
	}

	/* (non-Javadoc)
	 * @see com.example.demo.employee.service.EmployeeService#findAll(int)
	 */
	@Override
	public PageInfo<Employee> findAll(int pageNum) {
		PageHelper.startPage(pageNum, this.pageSize, "employee_name");
		return new PageInfo<Employee>(this.employeeDao.findAll());
	}

	/* (non-Javadoc)
	 * @see com.example.demo.employee.service.EmployeeService#findById(java.lang.Integer)
	 */
	@Override
	public Employee findById(Integer id) {
		return this.employeeDao.findById(id);
	}

	/* (non-Javadoc)
	 * @see com.example.demo.employee.service.EmployeeService#findAllByCustomerNumber(java.lang.String)
	 */
	@Override
	public List<Employee> findAllByCustomerNumber(String customerNumber) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.example.demo.employee.service.EmployeeService#findAllByCredential(java.lang.String)
	 */
	@Override
	public List<Employee> findAllByCredential(String credential) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.example.demo.employee.service.EmployeeService#insert(com.example.demo.employee.entity.Employee)
	 */
	@Override
	public boolean insert(Employee employee) {
		return 1 == this.employeeDao.insert(employee);
	}

	/* (non-Javadoc)
	 * @see com.example.demo.employee.service.EmployeeService#update(com.example.demo.employee.entity.Employee)
	 */
	@Override
	public boolean update(Employee employee) {
		return 1 == this.employeeDao.udpate(employee);
	}

	/* (non-Javadoc)
	 * @see com.example.demo.employee.service.EmployeeService#delete(java.lang.Integer)
	 */
	@Override
	public boolean delete(Integer id) {
		return 1 == this.employeeDao.delete(id);
	}

}
