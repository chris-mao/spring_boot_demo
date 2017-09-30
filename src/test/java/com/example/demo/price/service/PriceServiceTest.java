package com.example.demo.price.service;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.price.entity.PriceListHeader;
import com.github.pagehelper.PageInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceServiceTest {
	
	@Resource
	private PriceService priceService;

	@Test
	public void testFindAll() {
		priceService.findAll();
	}

	@Test
	public void testFindAllInt() {
		PageInfo<PriceListHeader> headers = this.priceService.findAll(1);
		Assert.assertNotNull(headers);
	}

	@Test
	public void testFindById() {
		PriceListHeader header = priceService.findById(4730520);
		Assert.assertNotNull(header);
		System.out.println(header);
	}

	@Test
	public void testFindByName() {
		PriceListHeader header = this.priceService.findByName("EMR 675T PRICE LIST SZ");
		Assert.assertNotNull(header);
		System.out.println(header);
	}

	@Test
	public void testFindAllAvailablePriceListsByCustomerSiteInt() {
		int siteId = 253497;
		List<PriceListHeader> headers = this.priceService.findAllAvailablePriceListsByCustomerSite(siteId);
		Assert.assertNotNull(headers);
		Assert.assertEquals(1, headers.size());
		System.out.println(headers);
	}

	public void testFindAllAvailablePriceListsByCustomerSiteIntInt() {
		int siteId = 253497;
		int itemId = 1990538;
		List<PriceListHeader> headers = this.priceService.findAllAvailablePriceListsByCustomerSite(siteId, itemId);
		Assert.assertNotNull(headers);
		Assert.assertEquals(1, headers.size());
		System.out.println(headers);
		
		headers = this.priceService.findAllAvailablePriceListsByCustomerSite(siteId, 1);
		Assert.assertNotNull(headers);
		Assert.assertEquals(0, headers.size());
		System.out.println(headers);
	}

	public void testFindAllAvailablePriceLines() {
		fail("Not yet implemented"); // TODO
	}

}
