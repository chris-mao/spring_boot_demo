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
import org.springframework.stereotype.Repository;

import com.jrsoft.inventory.entity.InventoryModel;

/**
 * com.jrsoft.inventory.dao InventoryItemDao
 * 
 * 产品型号数据访问接口
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Repository
public interface InventoryModelDAO {

	/**
	 * 查询所有型号
	 * 
	 * @return
	 */
	@Select("SELECT DISTINCT model_id, model_name, model_desc, available FROM model ORDER BY model_name")
	@Results({ @Result(property = "modelId", column = "model_id"),
			@Result(property = "modelName", column = "model_name"),
			@Result(property = "modelDescription", column = "model_desc"),
			// @Result(property = "modelChineseDescription", column =
			// "chinese_desc"),
			@Result(property = "available", column = "available") })
	public List<InventoryModel> findAll();

	/**
	 * 按编号查询型号
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

	/**
	 * 按名称查询型号
	 * 
	 * @param modelName
	 * @return
	 */
	@Select("SELECT DISTINCT model_id, model_name, model_desc, available FROM model WHERE model_name = #{model_name}")
	@Results({ @Result(property = "modelId", column = "model_id"),
			@Result(property = "modelName", column = "model_name"),
			@Result(property = "modelDescription", column = "model_desc"),
			// @Result(property = "modelChineseDescription", column =
			// "chinese_desc"),
			@Result(property = "available", column = "available") })
	public InventoryModel findByName(@Param(value = "model_name") String modelName);

	/**
	 * 按库存组织查询型号
	 * 
	 * @param source
	 * @return
	 */
	@Select("SELECT DISTINCT model_id, model_name, model_desc, available FROM model WHERE source = #{source}")
	@Results({ @Result(property = "modelId", column = "model_id"),
			@Result(property = "modelName", column = "model_name"),
			@Result(property = "modelDescription", column = "model_desc"),
			// @Result(property = "modelChineseDescription", column =
			// "chinese_desc"),
			@Result(property = "available", column = "available") })
	public List<InventoryModel> findAllBySource(@Param(value = "source") String source);

}
