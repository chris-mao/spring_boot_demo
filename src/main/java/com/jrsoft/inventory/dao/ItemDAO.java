/**
 * 
 */
package com.jrsoft.inventory.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.jrsoft.inventory.entity.Item;

/**
 * com.jrsoft.inventory.dao InventoryItemDao
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface ItemDAO {

	/**
	 * 
	 * @return
	 */
	@Select("SELECT DISTINCT model_id, model_name, model_desc, chinese_desc, source, available, created_time, update_time FROM model ORDER BY source, model_name")
	@Results({ @Result(property = "itemId", column = "model_id", id = true),
			@Result(property = "itemName", column = "model_name"),
			@Result(property = "itemDescription", column = "model_desc"),
			@Result(property = "itemChineseDescription", column = "chinese_desc"),
			@Result(property = "source", column = "source"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<Item> findAll();

	/**
	 * 
	 * @param item
	 * @return
	 */
	@Select("SELECT DISTINCT model_id, model_name, model_desc, chinese_desc, available FROM model WHERE model_id = #{item_id}")
	@Results({ @Result(property = "itemId", column = "model_id", id = true),
			@Result(property = "itemName", column = "model_name"),
			@Result(property = "itemDescription", column = "model_desc"),
			@Result(property = "itemChineseDescription", column = "chinese_desc"),
			@Result(property = "available", column = "available") })
	public Item findById(@Param(value = "item_id") Integer itemId);

	@Select("SELECT DISTINCT model_id, model_name, model_desc, chinese_desc, available FROM model WHERE model_name = #{item_name}")
	@Results({ @Result(property = "itemId", column = "model_id", id = true),
			@Result(property = "itemName", column = "model_name"),
			@Result(property = "itemDescription", column = "model_desc"),
			@Result(property = "itemChineseDescription", column = "chinese_desc"),
			@Result(property = "available", column = "available") })
	public Item findByName(@Param(value = "item_name") String itemName);

	/**
	 * 
	 * @param source
	 * @return
	 */
	@Select("SELECT DISTINCT model_id, model_name, model_desc, chinese_desc, available, created_time, update_time FROM model WHERE source = #{source}")
	@Results({ @Result(property = "itemId", column = "model_id", id = true),
			@Result(property = "itemName", column = "model_name"),
			@Result(property = "itemDescription", column = "model_desc"),
			@Result(property = "itemChineseDescription", column = "chinese_desc"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<Item> findAllBySource(@Param(value = "source") String source);

}
