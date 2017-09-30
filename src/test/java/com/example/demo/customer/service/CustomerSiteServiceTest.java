package com.example.demo.customer.service;

import java.util.Set;

import javax.annotation.Resource;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.customer.entity.CustomerSite;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerSiteServiceTest {

	@Resource
	private CustomerSiteService customerSiteService;

	private static final Integer CUSTOMER_ID = 1835577;

	// private static final String ACCOUNT_NUMBER = "1200079098";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testFindAllBillTo() {
		Set<CustomerSite> sites = this.customerSiteService.findAllBillTo(CustomerSiteServiceTest.CUSTOMER_ID);
		Assert.assertNotNull(sites);
		Assert.assertEquals(3, sites.size());
	}

	@Test
	public void testFindBillTo() {
		CustomerSite site = this.customerSiteService.findBillTo(CustomerSiteServiceTest.CUSTOMER_ID, 1124);
		Assert.assertNotNull(site);
	}

	@Test
	public void testFindAllShipToInteger() {
		Set<CustomerSite> sites = this.customerSiteService.findAllShipTo(CustomerSiteServiceTest.CUSTOMER_ID);
		Assert.assertNotNull(sites);
		Assert.assertEquals(3, sites.size());
	}

	@Test
	public void testFindAllShipToIntegerInteger() {
		Set<CustomerSite> sites = this.customerSiteService.findAllShipTo(CustomerSiteServiceTest.CUSTOMER_ID, 1124);
		Assert.assertNotNull(sites);
		Assert.assertEquals(1, sites.size());
		Assert.assertEquals(Boolean.FALSE, sites.isEmpty());

		sites = this.customerSiteService.findAllShipTo(CustomerSiteServiceTest.CUSTOMER_ID, 1123);
		Assert.assertNotNull(sites);
		Assert.assertEquals(1, sites.size());
		Assert.assertEquals(Boolean.FALSE, sites.isEmpty());
	}

	@Test
	public void testFindAllDeliverToInteger() {
		Set<CustomerSite> sites = this.customerSiteService.findAllDeliverTo(CustomerSiteServiceTest.CUSTOMER_ID);
		Assert.assertNotNull(sites);
		Assert.assertEquals(5, sites.size());
	}

	@Test
	public void testFindAllDeliverToIntegerInteger() {
		Set<CustomerSite> sites = this.customerSiteService.findAllDeliverTo(CustomerSiteServiceTest.CUSTOMER_ID, 1124);
		Assert.assertNotNull(sites);
		Assert.assertEquals(5, sites.size());

		sites = this.customerSiteService.findAllDeliverTo(CustomerSiteServiceTest.CUSTOMER_ID, 1123);
		Assert.assertNotNull(sites);
		Assert.assertEquals(0, sites.size());
		Assert.assertEquals(Boolean.TRUE, sites.isEmpty());
	}

}
