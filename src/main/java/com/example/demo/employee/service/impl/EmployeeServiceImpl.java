/**
 * 
 */
package com.example.demo.employee.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.employee.entity.Employee;
import com.example.demo.employee.service.EmployeeService;
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

	/* (non-Javadoc)
	 * @see com.example.demo.employee.service.EmployeeService#findAll()
	 */
	@Override
	public List<Employee> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.example.demo.employee.service.EmployeeService#findAll(int)
	 */
	@Override
	public PageInfo<Employee> findAll(int pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.example.demo.employee.service.EmployeeService#findById(java.lang.Integer)
	 */
	@Override
	public Employee findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.example.demo.employee.service.EmployeeService#findAllByCustomerNumber(java.lang.String)
	 */
	@Override
	public List<Employee> findAllByCustomerNumber(String customerNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.example.demo.employee.service.EmployeeService#findAllByCredential(java.lang.String)
	 */
	@Override
	public List<Employee> findAllByCredential(String credential) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.example.demo.employee.service.EmployeeService#insert(com.example.demo.employee.entity.Employee)
	 */
	@Override
	public boolean insert(Employee employee) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.example.demo.employee.service.EmployeeService#update(com.example.demo.employee.entity.Employee)
	 */
	@Override
	public boolean update(Employee employee) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.example.demo.employee.service.EmployeeService#delete(java.lang.Integer)
	 */
	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

}
