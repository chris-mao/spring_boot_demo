package com.example.demo.price.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.price.entity.PriceListHeader;
import com.example.demo.price.entity.PriceListLine;
import com.github.pagehelper.PageInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceServiceTest {
	
	@Resource
	private PriceService priceService;
	
	private static final int SITE_ID = 253497;
	
	private static final int HEADER_ID = 4730520;
	
	private static final String PRICE_LIST_NAME = "EMR 675T PRICE LIST SZ";
	
	private static final int ITEM_ID = 1990538;

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
		PriceListHeader header = priceService.findById(HEADER_ID);
		Assert.assertNotNull(header);
		System.out.println(header);
	}

	@Test
	public void testFindByName() {
		PriceListHeader header = this.priceService.findByName(PRICE_LIST_NAME);
		Assert.assertNotNull(header);
		Assert.assertEquals(header.getPriceListName(), PRICE_LIST_NAME);
	}

	@Test
	public void testFindAllAvailablePriceListsByCustomerSiteInt() {
		List<PriceListHeader> headers = this.priceService.findAllAvailablePriceListsByCustomerSite(SITE_ID);
		Assert.assertNotNull(headers);
		Assert.assertEquals(1, headers.size());
	}

	public void testFindAllAvailablePriceListsByCustomerSiteIntInt() {
		List<PriceListHeader> headers = this.priceService.findAllAvailablePriceListsByCustomerSite(SITE_ID, ITEM_ID);
		Assert.assertNotNull(headers);
		Assert.assertEquals(1, headers.size());
		
		headers = this.priceService.findAllAvailablePriceListsByCustomerSite(SITE_ID, 1);
		Assert.assertNotNull(headers);
		Assert.assertEquals(0, headers.size());
	}

	@Test
	public void testFindAllAvailablePriceLines() {
		List<PriceListLine> lines = this.priceService.findAllAvailablePriceLines(HEADER_ID, ITEM_ID);
		Assert.assertNotNull(lines);
		Assert.assertEquals(1, lines.size());		
	}
	
	@Test
	public void testFindAllPriceLinesByHeaderId() {
		List<PriceListLine> lines = this.priceService.findAllPriceLinesByHeaderId(HEADER_ID);
		Assert.assertNotNull(lines);
		Assert.assertEquals(12, lines.size());
	}
	
	@Test
	public void testFindAllPriceLinesByName() {
		List<PriceListLine> lines = this.priceService.findAllPriceLinesByName(PRICE_LIST_NAME);
		Assert.assertNotNull(lines);
		Assert.assertEquals(12, lines.size());
	}

}
