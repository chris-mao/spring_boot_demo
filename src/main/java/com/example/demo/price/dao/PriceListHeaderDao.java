/**
 * 
 */
package com.example.demo.price.dao;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import com.example.demo.price.entity.PriceListHeader;

/**
 * com.example.demo.price.dao PriceListHeaderDao
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface PriceListHeaderDao {

	/**
	 * 
	 * @return
	 */
	@Select("SELECT header_id, price_list_name, currency, start_date_active, end_date_active, created_time, update_time FROM price_list_header")
	@Results({ @Result(property = "headerId", column = "header_id", id = true),
			@Result(property = "name", column = "price_list_name"), @Result(property = "currency", column = "currency"),
			@Result(property = "startDate", column = "start_date_active"),
			@Result(property = "endDate", column = "end_date_active"),
			@Result(property = "lines", column = "header_id", many = @Many(select = "com.example.demo.price.dao.PriceListLineDao.findAllByHeaderId", fetchType = FetchType.LAZY) ),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<PriceListHeader> findAll();

	/**
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT header_id, price_list_name, currency, start_date_active, end_date_active, created_time, update_time FROM price_list_header WHERE header_id = #{header_id}")
	@Results({ @Result(property = "headerId", column = "header_id", id = true),
			@Result(property = "name", column = "price_list_name"), @Result(property = "currency", column = "currency"),
			@Result(property = "startDate", column = "start_date_active"),
			@Result(property = "edDate", column = "end_date_active"),
			@Result(property = "lines", column = "header_id", many = @Many(select = "com.example.demo.price.dao.PriceListLineDao.findAllByHeaderId", fetchType = FetchType.LAZY) ),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public PriceListHeader findById(@Param(value = "header_id") Integer id);

	/**
	 * 
	 * @param priceListName
	 * @return
	 */
	@Select("SELECT header_id, price_list_name, currency, start_date_active, end_date_active, created_time, update_time FROM price_list_header WHERE price_list_name = #{price_name}")
	@Results({ @Result(property = "headerId", column = "header_id", id = true),
			@Result(property = "name", column = "price_list_name"), @Result(property = "currency", column = "currency"),
			@Result(property = "startDate", column = "start_date_active"),
			@Result(property = "endDate", column = "end_date_active"),
			@Result(property = "lines", column = "header_id", many = @Many(select = "com.example.demo.price.dao.PriceListLineDao.findAllByHeaderId", fetchType = FetchType.LAZY) ),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public PriceListHeader findByName(@Param(value = "price_name") String priceListName);

	/**
	 * 获取客户的有效价格表清单
	 * 
	 * @param siteId
	 * @return
	 */
	@Select("CALL sp_findAvailablePriceList(#{site_id})")
	@Results({ @Result(property = "headerId", column = "header_id", id = true),
			@Result(property = "name", column = "price_list_name"), @Result(property = "currency", column = "currency"),
			@Result(property = "startDate", column = "start_date_active"),
			@Result(property = "endDate", column = "end_date_active"),
			@Result(property = "lines", column = "header_id", many = @Many(select = "com.example.demo.price.dao.PriceListLineDao.findAllByHeaderId", fetchType = FetchType.LAZY) ),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<PriceListHeader> findAllAvailablePriceListsByCustomerSite(@Param(value = "site_id") Integer siteId);

	/**
	 * 获取客户有效的且含有指定物料的价格表清单
	 * 
	 * @param siteId
	 * @param itemId
	 * @return
	 */
	@Select("CALL sp_findAvailablePriceListWithItem(#{site_id}, #{item_id})")
	@Results({ @Result(property = "headerId", column = "header_id", id = true),
			@Result(property = "name", column = "price_list_name"), @Result(property = "currency", column = "currency"),
			@Result(property = "startDate", column = "start_date_active"),
			@Result(property = "endDate", column = "end_date_active"),
			@Result(property = "lines", column = "header_id", many = @Many(select = "com.example.demo.price.dao.PriceListLineDao.findAllByHeaderId", fetchType = FetchType.LAZY) ),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<PriceListHeader> findAllAvailablePriceListsByCustomerSiteWithItem(@Param(value = "site_id") Integer siteId,
			@Param(value = "item_id") Integer itemId);

}
