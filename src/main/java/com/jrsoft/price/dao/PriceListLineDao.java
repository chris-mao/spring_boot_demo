/**
 * 
 */
package com.jrsoft.price.dao;

import java.util.List;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import com.jrsoft.price.entity.PriceListLine;

/**
 * com.jrsoft.price.dao PriceListLineDao
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface PriceListLineDao {

	/**
	 * 根据价格表ID查询价格表行数据
	 * 
	 * @param headerId
	 * @return List
	 */
	@Select("SELECT item_id, uom, unit_price, start_date_active, end_date_active, qty_from, qty_to, created_time, update_time FROM price_list_line WHERE header_id = #{header_id}")
	@Results({
			@Result(property = "inventoryItem", column = "item_id", one = @One(select = "com.jrsoft.inventory.dao.ItemDao.findById", fetchType = FetchType.LAZY) ),
			@Result(property = "unitPrice", column = "unit_price"), @Result(property = "uom", column = "uom"),
			@Result(property = "startDate", column = "start_date_active"),
			@Result(property = "endDate", column = "end_date_active"),
			@Result(property = "minOrderQuantity", column = "qty_from"),
			@Result(property = "maxOrderQuantity", column = "qty_to"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<PriceListLine> findAllByHeaderId(@Param(value = "header_id") Integer headerId);

	/**
	 * 根据价格表名称查询价格表行数据
	 * 
	 * @param priceListName
	 * @return List
	 */
	@Select("SELECT item_id, uom, unit_price, start_date_active, end_date_active, qty_from, qty_to, created_time, update_time FROM price_list_line WHERE header_id = (SELECT header_id FROM price_list_header WHERE price_list_name = #{name})")
	@Results({
			@Result(property = "inventoryItem", column = "item_id", one = @One(select = "com.jrsoft.inventory.dao.ItemDao.findById", fetchType = FetchType.LAZY) ),
			@Result(property = "unitPrice", column = "unit_price"), @Result(property = "uom", column = "uom"),
			@Result(property = "startDate", column = "start_date_active"),
			@Result(property = "endDate", column = "end_date_active"),
			@Result(property = "minOrderQuantity", column = "qty_from"),
			@Result(property = "maxOrderQuantity", column = "qty_to"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<PriceListLine> findAllByName(@Param(value = "name") String priceListName);

	/**
	 * 在指定的价格表中获取指定产品的销售价格
	 * 
	 * @param headerId
	 * @param itemId
	 * @return List
	 */
	@Select("CALL sp_findAvailableSellingPrice(#{header_id}, #{item_id})")
	@Results({
			@Result(property = "inventoryItem", column = "model_id", one = @One(select = "com.jrsoft.inventory.dao.ItemDao.findById", fetchType = FetchType.LAZY) ),
			@Result(property = "unitPrice", column = "unit_price"), @Result(property = "uom", column = "uom"),
			@Result(property = "startDate", column = "start_date_active"),
			@Result(property = "endDate", column = "end_date_active"),
			@Result(property = "minOrderQuantity", column = "qty_from"),
			@Result(property = "maxOrderQuantity", column = "qty_to"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<PriceListLine> findAllAvailablePriceLines(@Param(value = "header_id") Integer headerId,
			@Param(value = "item_id") Integer itemId);

}
