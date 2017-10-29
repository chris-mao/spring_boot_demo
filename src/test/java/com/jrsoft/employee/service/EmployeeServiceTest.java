package com.jrsoft.employee.service;

import static org.junit.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.pagehelper.PageInfo;
import com.jrsoft.employee.entity.Employee;
import com.jrsoft.employee.service.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {
	
	@Autowired
	private EmployeeService employeeService;

	@Test
	public void testFindAll() {
		List<Employee> employees = employeeService.findAll();
		Assert.assertNotNull(employees);
		Assert.assertEquals(9, employees.size());
	}

	@Test
	public void testFindAllInt() {
		PageInfo<Employee> page = employeeService.findAll(1);
		Assert.assertNotNull(page);
		Assert.assertEquals(true, page.isIsFirstPage());
		Assert.assertEquals(9, page.getList().size());
	}

	@Test
	public void testFindByEmployee() {
		Employee e = new Employee();
		e.setEmployeeId(1);
		Employee emp = employeeService.findOne(e);
		Assert.assertNotNull(emp);
	}

	public void testFindAllByCustomer() {
		fail("Not yet implemented"); // TODO
	}

	public void testFindAllByCredential() {
		fail("Not yet implemented"); // TODO
	}

	public void testInsert() {
		fail("Not yet implemented"); // TODO
	}

	public void testUpdate() {
		fail("Not yet implemented"); // TODO
	}

	public void testDelete() {
		fail("Not yet implemented"); // TODO
	}

}
