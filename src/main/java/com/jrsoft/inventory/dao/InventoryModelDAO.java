/**
 * 
 */
package com.jrsoft.inventory.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.jrsoft.inventory.entity.InventoryModel;

/**
 * com.jrsoft.inventory.dao InventoryItemDao
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface InventoryModelDAO {

	/**
	 * 
	 * @return
	 */
	@Select("SELECT DISTINCT model_id, model_name, model_desc, available, created_time, update_time FROM model ORDER BY model_name")
	@Results({ @Result(property = "modelId", column = "model_id"),
			@Result(property = "modelName", column = "model_name"),
			@Result(property = "modelDescription", column = "model_desc"),
			// @Result(property = "modelChineseDescription", column =
			// "chinese_desc"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<InventoryModel> findAll();

	/**
	 * 
	 * @param model
	 * @return
	 */
	@Select("SELECT DISTINCT model_id, model_name, model_desc, available FROM model WHERE model_id = #{model_id}")
	@Results({ @Result(property = "modelId", column = "model_id"),
			@Result(property = "modelName", column = "model_name"),
			@Result(property = "modelDescription", column = "model_desc"),
			// @Result(property = "modelChineseDescription", column =
			// "chinese_desc"),
			@Result(property = "available", column = "available") })
	public InventoryModel findById(@Param(value = "model_id") BigInteger modelId);

	@Select("SELECT DISTINCT model_id, model_name, model_desc, available FROM model WHERE model_name = #{model_name}")
	@Results({ @Result(property = "modelId", column = "model_id"),
			@Result(property = "modelName", column = "model_name"),
			@Result(property = "modelDescription", column = "model_desc"),
			// @Result(property = "modelChineseDescription", column =
			// "chinese_desc"),
			@Result(property = "available", column = "available") })
	public InventoryModel findByName(@Param(value = "model_name") String modelName);

	/**
	 * 
	 * @param source
	 * @return
	 */
	@Select("SELECT DISTINCT model_id, model_name, model_desc, available, created_time, update_time FROM model WHERE source = #{source}")
	@Results({ @Result(property = "modelId", column = "model_id"),
			@Result(property = "modelName", column = "model_name"),
			@Result(property = "modelDescription", column = "model_desc"),
			// @Result(property = "modelChineseDescription", column =
			// "chinese_desc"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<InventoryModel> findAllBySource(@Param(value = "source") String source);

}
