/**
 * 
 */
package com.example.demo.customer.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.customer.entity.CustomerAccount;
import com.github.pagehelper.PageInfo;

/**
 * com.example.demo.customer.service CustomerServiceTest
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {
	
	@Resource
	private CustomerService customerService;
	
	private static final Integer CUSTOMER_ID = 1835577;
	
	private static final String ACCOUNT_NUMBER = "1200079098";
	
	private static final Integer PRICE_HEADER_ID = 4733547;

	/**
	 * Test method for {@link com.example.demo.customer.service.impl.CustomerServiceImpl#findAll()}.
	 */
	@Test
	public void testFindAll() {
		List<CustomerAccount> customers = customerService.findAll();
		Assert.assertNotNull(customers);
		Assert.assertEquals(524, customers.size());
	}

	/**
	 * Test method for {@link com.example.demo.customer.service.impl.CustomerServiceImpl#findAll(int)}.
	 */
	@Test
	public void testFindAllInt() {
		PageInfo<CustomerAccount> page = customerService.findAll(1);
		Assert.assertNotNull(page);
		Assert.assertEquals(true, page.isIsFirstPage());
		Assert.assertEquals(15, page.getList().size());
	}

	/**
	 * Test method for {@link com.example.demo.customer.service.impl.CustomerServiceImpl#findById(java.lang.Integer)}.
	 */
	@Test
	public void testFindById() {
		CustomerAccount customer = this.customerService.findById(CUSTOMER_ID);
		Assert.assertNotNull(customer);
		System.out.println(customer);
	}
	
	/**
	 * Test method for {@link com.example.demo.customer.service.impl.CustomerServiceImpl#findByAccountNumber(java.lang.String)}.
	 */
	@Test
	public void testFindByAccountNumber() {
		CustomerAccount customer = this.customerService.findByAccountNumber(ACCOUNT_NUMBER);
		Assert.assertNotNull(customer);
		System.out.println(customer);
	}
	
	@Test
	public void testFindByCredential() {
		List<CustomerAccount> customers = this.customerService.findAllByCredential(ACCOUNT_NUMBER);
		Assert.assertNotNull(customers);
		Assert.assertEquals(1, customers.size());
	}
	
	@Test
	public void testFindAllQualifiedCustomersInt() {
		List<CustomerAccount> customers = this.customerService.findAllQualifiedCustomers(PRICE_HEADER_ID);
		Assert.assertNotNull(customers);
		Assert.assertEquals(27, customers.size());
	}

}
