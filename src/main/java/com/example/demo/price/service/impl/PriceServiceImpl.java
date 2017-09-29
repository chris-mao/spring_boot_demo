/**
 * 
 */
package com.example.demo.price.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.price.entity.PriceListHeader;
import com.example.demo.price.entity.PriceListLine;
import com.example.demo.price.service.PriceService;
import com.github.pagehelper.PageInfo;

/**
 * com.example.demo.price.service.impl PriceServiceImpl
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Service
public class PriceServiceImpl implements PriceService {

	@Override
	public List<PriceListHeader> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageInfo<PriceListHeader> findAll(int pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PriceListHeader findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PriceListHeader findByName(String priceListName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PriceListHeader> getAvailablePriceListsByCustomerSite(int siteId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PriceListHeader> getAvailablePriceListByCustomerSite(int siteId, int itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PriceListLine> getAvailablePriceLines(int priceHeaderId, int itemId) {
		// TODO Auto-generated method stub
		return null;
	}
}
