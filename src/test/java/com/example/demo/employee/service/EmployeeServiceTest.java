package com.example.demo.employee.service;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.employee.entity.Employee;
import com.github.pagehelper.PageInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {
	
	@Resource
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
	public void testFindById() {
		Employee emp = employeeService.findById(1);
		Assert.assertNotNull(emp);
	}

	public void testFindAllByCustomerNumber() {
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
