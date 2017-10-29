/**
 * 
 */
package com.jrsoft.price.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.jrsoft.price.entity.PriceListHeader;

/**
 * com.jrsoft.price.dao PriceListHeaderDao
 * 
 * 价格表头数据访问接口
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Repository
public interface PriceListHeaderDAO {

	/**
	 * 查询所有价格表头数据
	 * 
	 * @return List
	 */
	@Select("SELECT DISTINCT header_id, price_list_name, currency, start_date_active, end_date_active FROM price_list_header")
	@Results({ @Result(property = "headerId", column = "header_id", id = true),
			@Result(property = "name", column = "price_list_name"), @Result(property = "currency", column = "currency"),
			@Result(property = "startDate", column = "start_date_active"),
			@Result(property = "endDate", column = "end_date_active") })
	// @Result(property = "lines", column = "header_id", many = @Many(select =
	// "com.jrsoft.price.dao.PriceListLineDAO.findAllByHeaderId", fetchType =
	// FetchType.LAZY) ),
	public List<PriceListHeader> findAll();

	/**
	 * 根据价格表ID查询价格表头数据
	 * 
	 * @param id
	 * @return List
	 */
	@Select("SELECT DISTINCT header_id, price_list_name, currency, start_date_active, end_date_active FROM price_list_header WHERE header_id = #{header_id}")
	@Results({ @Result(property = "headerId", column = "header_id", id = true),
			@Result(property = "name", column = "price_list_name"), @Result(property = "currency", column = "currency"),
			@Result(property = "startDate", column = "start_date_active"),
			@Result(property = "endDate", column = "end_date_active") })
	// @Result(property = "lines", column = "header_id", many = @Many(select =
	// "com.jrsoft.price.dao.PriceListLineDAO.findAllByHeaderId", fetchType =
	// FetchType.LAZY) ) })
	public PriceListHeader findById(@Param(value = "header_id") int id);

	/**
	 * 根据价格表名称查询价格表数据
	 * 
	 * @param priceListName
	 * @return List
	 */
	@Select("SELECT DISTINCT header_id, price_list_name, currency, start_date_active, end_date_active FROM price_list_header WHERE price_list_name = #{price_name}")
	@Results({ @Result(property = "headerId", column = "header_id", id = true),
			@Result(property = "name", column = "price_list_name"), @Result(property = "currency", column = "currency"),
			@Result(property = "startDate", column = "start_date_active"),
			@Result(property = "endDate", column = "end_date_active") })
	// @Result(property = "lines", column = "header_id", many = @Many(select =
	// "com.jrsoft.price.dao.PriceListLineDAO.findAllByHeaderId", fetchType =
	// FetchType.LAZY) )})
	public PriceListHeader findByName(@Param(value = "price_name") String priceListName);

	/**
	 * 获取客户的有效价格表清单
	 * 
	 * @param siteId
	 * @return List
	 */
	@Select("CALL sp_findAvailablePriceList(#{site_id})")
	@Results({ @Result(property = "headerId", column = "header_id", id = true),
			@Result(property = "name", column = "price_list_name"), @Result(property = "currency", column = "currency"),
			@Result(property = "startDate", column = "start_date_active"),
			@Result(property = "endDate", column = "end_date_active") })
	// @Result(property = "lines", column = "header_id", many = @Many(select =
	// "com.jrsoft.price.dao.PriceListLineDAO.findAllByHeaderId", fetchType =
	// FetchType.LAZY) ),
	public List<PriceListHeader> findAllAvailablePriceListsByCustomerSite(@Param(value = "site_id") int siteId);

	/**
	 * 获取客户有效的且含有指定物料的价格表清单
	 * 
	 * @param siteId
	 * @param itemId
	 * @return List
	 */
	@Select("CALL sp_findAvailablePriceListWithItem(#{site_id}, #{item_id})")
	@Results({ @Result(property = "headerId", column = "header_id", id = true),
			@Result(property = "name", column = "price_list_name"), @Result(property = "currency", column = "currency"),
			@Result(property = "startDate", column = "start_date_active"),
			@Result(property = "endDate", column = "end_date_active") })
	// @Result(property = "lines", column = "header_id", many = @Many(select =
	// "com.jrsoft.price.dao.PriceListLineDAO.findAllByHeaderId", fetchType =
	// FetchType.LAZY) ),
	public List<PriceListHeader> findAllAvailablePriceListsByCustomerSiteWithItem(@Param(value = "site_id") int siteId,
			@Param(value = "item_id") int itemId);

}
