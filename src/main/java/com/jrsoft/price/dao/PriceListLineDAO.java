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
import org.springframework.stereotype.Repository;

import com.jrsoft.price.entity.PriceListLine;

/**
 * com.jrsoft.price.dao PriceListLineDao
 * 
 * 价格表行数据访问接口
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Repository
public interface PriceListLineDAO {

	/**
	 * 根据价格表ID查询价格表行数据
	 * 
	 * @param headerId
	 * @return List
	 */
	@Select("SELECT DISTINCT line_id, item_id, uom, unit_price, start_date_active, end_date_active, qty_from, qty_to FROM price_list_line WHERE header_id = #{header_id} ORDER BY start_date_active")
	@Results({
			@Result(property = "inventoryModel", column = "item_id", one = @One(select = "com.jrsoft.inventory.dao.InventoryModelDAO.findById", fetchType = FetchType.LAZY) ),
			@Result(property = "unitPrice", column = "unit_price"), @Result(property = "uom", column = "uom"),
			@Result(property = "startDate", column = "start_date_active"),
			@Result(property = "endDate", column = "end_date_active"),
			@Result(property = "minOrderQuantity", column = "qty_from"),
			@Result(property = "maxOrderQuantity", column = "qty_to") })
	public List<PriceListLine> findAllByHeaderId(@Param(value = "header_id") int headerId);

	/**
	 * 根据价格表名称查询价格表行数据
	 * 
	 * @param priceListName
	 * @return List
	 */
	@Select("SELECT DISTINCT line_id, item_id, uom, unit_price, start_date_active, end_date_active, qty_from, qty_to FROM vw_price_list_line WHERE header_id = (SELECT header_id FROM price_list_header WHERE price_list_name = #{name}) ORDER BY start_date_active")
	@Results({
			@Result(property = "inventoryModel", column = "item_id", one = @One(select = "com.jrsoft.inventory.dao.InventoryModelDAO.findById", fetchType = FetchType.LAZY) ),
			@Result(property = "unitPrice", column = "unit_price"), @Result(property = "uom", column = "uom"),
			@Result(property = "startDate", column = "start_date_active"),
			@Result(property = "endDate", column = "end_date_active"),
			@Result(property = "minOrderQuantity", column = "qty_from"),
			@Result(property = "maxOrderQuantity", column = "qty_to") })
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
			@Result(property = "inventoryModel", column = "item_id", one = @One(select = "com.jrsoft.inventory.dao.InventoryModelDAO.findById", fetchType = FetchType.LAZY) ),
			@Result(property = "unitPrice", column = "unit_price"), @Result(property = "uom", column = "uom"),
			@Result(property = "startDate", column = "start_date_active"),
			@Result(property = "endDate", column = "end_date_active"),
			@Result(property = "minOrderQuantity", column = "qty_from"),
			@Result(property = "maxOrderQuantity", column = "qty_to") })
	public List<PriceListLine> findAllAvailablePriceLines(@Param(value = "header_id") int headerId,
			@Param(value = "item_id") int itemId);

}
