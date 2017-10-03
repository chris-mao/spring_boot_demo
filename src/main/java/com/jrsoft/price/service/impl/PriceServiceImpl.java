/**
 * 
 */
package com.jrsoft.price.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrsoft.price.dao.PriceListHeaderDao;
import com.jrsoft.price.dao.PriceListLineDao;
import com.jrsoft.price.entity.PriceListHeader;
import com.jrsoft.price.entity.PriceListLine;
import com.jrsoft.price.service.PriceService;

/**
 * com.jrsoft.price.service.impl PriceServiceImpl
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Service
public class PriceServiceImpl implements PriceService {

	@Value("${pageSize}")
	private int pageSize;

	@Resource
	private PriceListHeaderDao priceListHeaderDao;

	@Resource
	private PriceListLineDao priceListLineDao;

	@Override
	public List<PriceListHeader> findAll() {
		return this.priceListHeaderDao.findAll();
	}

	@Override
	public PageInfo<PriceListHeader> findAll(int pageNum) {
		PageHelper.startPage(pageNum, pageSize, "price_list_name");
		return new PageInfo<PriceListHeader>(this.priceListHeaderDao.findAll());
	}

	@Override
	public PriceListHeader findById(Integer id) {
		return this.priceListHeaderDao.findById(id);
	}

	@Override
	public PriceListHeader findByName(String priceListName) {
		return this.priceListHeaderDao.findByName(priceListName);
	}

	@Override
	public List<PriceListHeader> findAllAvailablePriceListsByCustomerSite(int siteId) {
		return this.priceListHeaderDao.findAllAvailablePriceListsByCustomerSite(siteId);
	}

	@Override
	public List<PriceListHeader> findAllAvailablePriceListsByCustomerSite(int siteId, int itemId) {
		return this.priceListHeaderDao.findAllAvailablePriceListsByCustomerSiteWithItem(siteId, itemId);
	}

	@Override
	public List<PriceListLine> findAllAvailablePriceLines(int headerId, int itemId) {
		return this.priceListLineDao.findAllAvailablePriceLines(headerId, itemId);
	}

	@Override
	public List<PriceListLine> findAllPriceLinesByHeaderId(int headerId) {
		return this.priceListLineDao.findAllByHeaderId(headerId);
	}

	@Override
	public List<PriceListLine> findAllPriceLinesByName(String priceListName) {
		// PriceListHeader header =
		// this.priceListHeaderDao.findByName(priceListName);
		return this.priceListLineDao.findAllByName(priceListName);
	}
}
