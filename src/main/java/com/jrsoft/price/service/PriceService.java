/**
 * 
 */
package com.jrsoft.price.service;

import java.util.List;
import java.util.Set;

import com.github.pagehelper.PageInfo;
import com.jrsoft.customer.entity.CustomerSite;
import com.jrsoft.price.entity.PriceListHeader;
import com.jrsoft.price.entity.PriceListLine;

/**
 * com.jrsoft.price.service.impl PriceService
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface PriceService {

	/**
	 * 查询所有价格表头数据，不分页
	 * 
	 * @return List
	 */
	public List<PriceListHeader> findAll();

	/**
	 * 查询所有价格表头数据，分页
	 * 
	 * @param pageNum
	 * @return PageInfo
	 */
	public PageInfo<PriceListHeader> findAll(int pageNum);

	/**
	 * 根据价格表ID查询价格表头数据
	 * 
	 * @param id 价格表Id
	 * @return PriceListHeader
	 */
	public PriceListHeader findById(Integer id);

	/**
	 * 根据价格表名称查询价格表数据
	 * 
	 * @param priceListName 价格表名称
	 * @return PriceListHeader
	 */
	public PriceListHeader findByName(String priceListName);
	
	/**
	 * 获取客户的有效的价格表清单
	 * 
	 * @param siteId 客户SHIP TO地址编号
	 * @return List
	 */
	public List<PriceListHeader> findAllAvailablePriceListsByCustomerSite(int siteId);
	
	public List<PriceListHeader> findAllAvailablePriceListsByCustomerSite(Set<CustomerSite> billTo);
	
	/**
	 * 获取客户有效的且含有指定物料的价格表清单
	 * 
	 * @param siteId 客户SHIP TO地址编号
	 * @param itemId 物料编号
	 * @return List
	 */
	public List<PriceListHeader> findAllAvailablePriceListsByCustomerSite(int siteId, int itemId);
	
	/**
	 * 获取价格表中的价格数据
	 * 
	 * @param headerId 价格表Id
	 * @return List
	 */
	public List<PriceListLine> findAllPriceLinesByHeaderId(int headerId);
	
	/**
	 * 
	 * @param priceListName 价格表名称
	 * @return List
	 */
	public List<PriceListLine> findAllPriceLinesByName(String priceListName);
	
	/**
	 * 在指定价格表中获取指定产品的销售价格
	 * 
	 * @param headerId 价格表Id
	 * @param itemId 物料编号
	 * @return List
	 */
	public List<PriceListLine> findAllAvailablePriceLines(int headerId, int itemId);

}
