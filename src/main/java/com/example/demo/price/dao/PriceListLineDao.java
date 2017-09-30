/**
 * 
 */
package com.example.demo.price.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.example.demo.price.entity.PriceListLine;

/**
 * com.example.demo.price.dao PriceListLineDao
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface PriceListLineDao {

	/**
	 * 获取有效的价格表行信息
	 * 
	 * @param headerId
	 * @return
	 */
	@Select("SELECT item_id, uom, unit_price, start_date_active, end_date_active, qty_from, qty_to, created_time, update_time FROM price_list_line WHERE header_id = #{header_id}")
	@Results({ @Result(property = "inventoryItemId", column = "item_id"),
//			@Result(property = "inventoryItemName", column = "model_name"),
//			@Result(property = "inventoryItemDescription", column = "model_desc"),
			@Result(property = "unitPrice", column = "unit_price"), @Result(property = "uom", column = "uom"),
			@Result(property = "startDate", column = "start_date_active"),
			@Result(property = "endDate", column = "end_date_active"),
			@Result(property = "minOrderQuantity", column = "qty_from"),
			@Result(property = "maxOrderQuantity", column = "qty_to"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<PriceListLine> findAllByHeaderId(@Param(value = "header_id") Integer headerId);

	/**
	 * 在指定的价格表中获取指定产品的销售价格
	 * 
	 * @param headerId
	 * @param itemId
	 * @return
	 */
	@Results({ @Result(property = "inventoryItemId", column = "model_id"),
			@Result(property = "inventoryItemName", column = "model_name"),
			@Result(property = "inventoryItemDescription", column = "model_desc"),
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
