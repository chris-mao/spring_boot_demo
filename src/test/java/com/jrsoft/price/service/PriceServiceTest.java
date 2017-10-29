package com.jrsoft.price.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.pagehelper.PageInfo;
import com.jrsoft.price.entity.PriceListHeader;
import com.jrsoft.price.entity.PriceListLine;
import com.jrsoft.price.service.PriceService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceServiceTest {
	
	@Autowired
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
		PriceListHeader plh = new PriceListHeader();
		plh.setHeaderId(HEADER_ID);
		PriceListHeader header = priceService.findOne(plh);
		Assert.assertNotNull(header);
		System.out.println(header);
	}

	@Test
	public void testFindByName() {
		PriceListHeader plh = new PriceListHeader();
		plh.setName(PRICE_LIST_NAME);
		PriceListHeader header = this.priceService.findOne(plh);
		Assert.assertNotNull(header);
		Assert.assertEquals(header.getName(), PRICE_LIST_NAME);
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
		PriceListHeader plh = new PriceListHeader();
		plh.setHeaderId(HEADER_ID);
		List<PriceListLine> lines = this.priceService.findAllPriceLines(plh);
		Assert.assertNotNull(lines);
		Assert.assertEquals(12, lines.size());
	}
	
	@Test
	public void testFindAllPriceLinesByName() {
		PriceListHeader plh = new PriceListHeader();
		plh.setName(PRICE_LIST_NAME);
		List<PriceListLine> lines = this.priceService.findAllPriceLines(plh);
		Assert.assertNotNull(lines);
		Assert.assertEquals(12, lines.size());
	}

}
