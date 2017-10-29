/**
 * 
 */
package com.jrsoft.customer.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.pagehelper.PageInfo;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.customer.entity.CustomerAccount;
import com.jrsoft.customer.entity.CustomerSite;
import com.jrsoft.customer.service.CustomerService;
import com.jrsoft.price.entity.PriceListHeader;

/**
 * com.jrsoft.customer.service CustomerServiceTest
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {
	
	@Autowired
	private CustomerService customerService;
	
	private static final Integer CUSTOMER_ID = 1835577;
	
	private static final String ACCOUNT_NUMBER = "1200079098";
	
	private static final Integer PRICE_HEADER_ID = 4733547;

	/**
	 * Test method for {@link com.jrsoft.customer.service.impl.CustomerServiceImpl#findAll()}.
	 */
	@Test
	public void testFindAll() {
		List<CustomerAccount> customers = customerService.findAll();
		Assert.assertNotNull(customers);
		Assert.assertEquals(524, customers.size());
	}

	/**
	 * Test method for {@link com.jrsoft.customer.service.impl.CustomerServiceImpl#findAll(int)}.
	 */
	@Test
	public void testFindAllInt() {
		PageInfo<CustomerAccount> page = customerService.findAll(1);
		Assert.assertNotNull(page);
		Assert.assertEquals(true, page.isIsFirstPage());
		Assert.assertEquals(15, page.getList().size());
	}

	/**
	 * Test method for {@link com.jrsoft.customer.service.impl.CustomerServiceImpl#findById(java.lang.Integer)}.
	 */
	@Test
	public void testFindById() {
		CustomerAccount ca = new CustomerAccount();
		ca.setCustomerId(CUSTOMER_ID);
		CustomerAccount customer = this.customerService.findOne(ca);
		Assert.assertNotNull(customer);
		System.out.println(customer);
	}
	
	/**
	 * Test method for {@link com.jrsoft.customer.service.impl.CustomerServiceImpl#findByAccountNumber(java.lang.String)}.
	 */
	@Test
	public void testFindByAccountNumber() {
		CustomerAccount ca = new CustomerAccount();
		ca.setAccountNumber(ACCOUNT_NUMBER);
		CustomerAccount customer = this.customerService.findOne(ca);
		Assert.assertNotNull(customer);
		System.out.println(customer);
	}
	
	@Test
	public void testFindByCredential() {
		AuthUser user = new AuthUser();
		user.setUserName(ACCOUNT_NUMBER);
		List<CustomerAccount> customers = this.customerService.findAllByCredential(user);
		Assert.assertNotNull(customers);
		Assert.assertEquals(1, customers.size());
	}
	
	@Test
	public void testFindAllQualifiedCustomersInt() {
		PriceListHeader priceHeader = new PriceListHeader();
		priceHeader.setHeaderId(PRICE_HEADER_ID);
		List<CustomerAccount> customers = this.customerService.findAllQualifiedCustomers(priceHeader);
		Assert.assertNotNull(customers);
		Assert.assertEquals(27, customers.size());
	}
	
	@Test
	public void testFindAllBillTo() {
		Set<CustomerSite> sites = this.customerService.findAllBillTo(CustomerServiceTest.CUSTOMER_ID);
		Assert.assertNotNull(sites);
		Assert.assertEquals(3, sites.size());
	}

	@Test
	public void testFindBillTo() {
		CustomerSite site = this.customerService.findBillTo(CustomerServiceTest.CUSTOMER_ID, 1124);
		Assert.assertNotNull(site);
	}

	@Test
	public void testFindAllShipToInteger() {
		Set<CustomerSite> sites = this.customerService.findAllShipTo(CustomerServiceTest.CUSTOMER_ID);
		Assert.assertNotNull(sites);
		Assert.assertEquals(3, sites.size());
	}

	@Test
	public void testFindAllShipToIntegerInteger() {
		Set<CustomerSite> sites = this.customerService.findAllShipTo(CustomerServiceTest.CUSTOMER_ID, 1124);
		Assert.assertNotNull(sites);
		Assert.assertEquals(1, sites.size());
		Assert.assertEquals(Boolean.FALSE, sites.isEmpty());

		sites = this.customerService.findAllShipTo(CustomerServiceTest.CUSTOMER_ID, 1123);
		Assert.assertNotNull(sites);
		Assert.assertEquals(1, sites.size());
		Assert.assertEquals(Boolean.FALSE, sites.isEmpty());
	}

	@Test
	public void testFindAllDeliverToInteger() {
		Set<CustomerSite> sites = this.customerService.findAllDeliverTo(CustomerServiceTest.CUSTOMER_ID);
		Assert.assertNotNull(sites);
		Assert.assertEquals(5, sites.size());
	}

	@Test
	public void testFindAllDeliverToIntegerInteger() {
		Set<CustomerSite> sites = this.customerService.findAllDeliverTo(CustomerServiceTest.CUSTOMER_ID, 1124);
		Assert.assertNotNull(sites);
		Assert.assertEquals(5, sites.size());

		sites = this.customerService.findAllDeliverTo(CustomerServiceTest.CUSTOMER_ID, 1123);
		Assert.assertNotNull(sites);
		Assert.assertEquals(0, sites.size());
		Assert.assertEquals(Boolean.TRUE, sites.isEmpty());
	}

}
