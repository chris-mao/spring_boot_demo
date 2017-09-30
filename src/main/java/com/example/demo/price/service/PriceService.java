/**
 * 
 */
package com.example.demo.price.service;

import java.util.List;

import com.example.demo.price.entity.PriceListHeader;
import com.example.demo.price.entity.PriceListLine;
import com.github.pagehelper.PageInfo;

/**
 * com.example.demo.price.service.impl PriceService
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface PriceService {

	/**
	 * 查询所有客户数据，不分页
	 * 
	 * @return List
	 */
	public List<PriceListHeader> findAll();

	/**
	 * 查询所有客户数据，分页
	 * 
	 * @param pageNum
	 * @return PageInfo
	 */
	public PageInfo<PriceListHeader> findAll(int pageNum);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public PriceListHeader findById(Integer id);

	/**
	 * 根据客户Account Number查询客户数据
	 * 
	 * @param priceListName
	 * @return CustomerAccount
	 */
	public PriceListHeader findByName(String priceListName);
	
	/**
	 * 获取客户的有效的价格表清单
	 * 
	 * @param siteId
	 * @return
	 */
	public List<PriceListHeader> findAllAvailablePriceListsByCustomerSite(int siteId);
	
	/**
	 * 获取客户有效的且含有指定物料的价格表清单
	 * 
	 * @param siteId
	 * @param itemId
	 * @return
	 */
	public List<PriceListHeader> findAllAvailablePriceListsByCustomerSite(int siteId, int itemId);
	
	/**
	 * 在指定价格表中获取指定产品的销售价格
	 * 
	 * @param headerId
	 * @param itemId
	 * @return
	 */
	public List<PriceListLine> findAllAvailablePriceLines(int headerId, int itemId);

}
