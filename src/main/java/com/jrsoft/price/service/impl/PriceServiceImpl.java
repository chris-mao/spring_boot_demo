/**
 * 
 */
package com.jrsoft.price.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrsoft.customer.entity.CustomerSite;
import com.jrsoft.price.dao.PriceListHeaderDAO;
import com.jrsoft.price.dao.PriceListLineDAO;
import com.jrsoft.price.entity.PriceListHeader;
import com.jrsoft.price.entity.PriceListLine;
import com.jrsoft.price.service.PriceService;

/**
 * com.jrsoft.price.service.impl PriceServiceImpl
 *
 * 销售价格服务接口实现类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Service
public class PriceServiceImpl implements PriceService {

	@Value("${pageSize}")
	private int pageSize = 20;

	@Autowired
	private PriceListHeaderDAO priceListHeaderDAO;

	@Autowired
	private PriceListLineDAO priceListLineDAO;

	@Override
	public List<PriceListHeader> findAll() {
		return this.priceListHeaderDAO.findAll();
	}

	@Override
	public PageInfo<PriceListHeader> findAll(int pageNum) {
		PageHelper.startPage(pageNum, pageSize, "price_list_name");
		return new PageInfo<PriceListHeader>(this.priceListHeaderDAO.findAll());
	}

	@Override
	public List<PriceListHeader> findAllAvailablePriceListsByCustomerSite(int siteId) {
		return this.priceListHeaderDAO.findAllAvailablePriceListsByCustomerSite(siteId);
	}

	@Override
	public List<PriceListHeader> findAllAvailablePriceListsByCustomerSite(Set<CustomerSite> billTo) {
		if (null == billTo) {
			return null;
		}

		CustomerSite billToSite = null;
		List<PriceListHeader> result = null;
		Iterator<CustomerSite> iterator = billTo.iterator();
		while (iterator.hasNext()) {
			billToSite = iterator.next();
			// System.out.println("Bill To Site ==> " + billToSite.getSiteId());
			if (null == result) {
				result = findAllAvailablePriceListsByCustomerSite(billToSite.getSiteId());
			} else {
				result.addAll(findAllAvailablePriceListsByCustomerSite(billToSite.getSiteId()));
			}
		}
		return result;
	}

	@Override
	public List<PriceListHeader> findAllAvailablePriceListsByCustomerSite(int siteId, int itemId) {
		return this.priceListHeaderDAO.findAllAvailablePriceListsByCustomerSiteWithItem(siteId, itemId);
	}

	@Override
	public List<PriceListLine> findAllAvailablePriceLines(int headerId, int itemId) {
		return this.priceListLineDAO.findAllAvailablePriceLines(headerId, itemId);
	}

	@Override
	public List<PriceListLine> findAllPriceLines(PriceListHeader priceHeader) {
		if (0 != priceHeader.getHeaderId()) {
			return this.priceListLineDAO.findAllByHeaderId(priceHeader.getHeaderId());
		}
		if (null != priceHeader.getName()) {
			return this.priceListLineDAO.findAllByName(priceHeader.getName());
		}
		return null;
	}

	@Override
	public PriceListHeader findOne(PriceListHeader priceHeader) {
		if (0 != priceHeader.getHeaderId()) {
			return this.priceListHeaderDAO.findById(priceHeader.getHeaderId());
		}
		if (null != priceHeader.getName()) {
			return this.priceListHeaderDAO.findByName(priceHeader.getName());
		}
		return null;
	}
}
