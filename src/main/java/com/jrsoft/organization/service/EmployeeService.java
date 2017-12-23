package com.jrsoft.organization.service;

import java.util.List;

import com.jrsoft.app.service.AbstractDbService;
import com.jrsoft.organization.entity.Department;
import com.jrsoft.organization.entity.Employee;

/**
 * 员工服务接口
 * 
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface EmployeeService extends AbstractDbService<Employee> {
	
	public List<Employee> findAllByDepartment(Department dept);
}
