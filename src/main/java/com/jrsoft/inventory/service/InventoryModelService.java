/**
 * 
 */
package com.jrsoft.inventory.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.jrsoft.inventory.entity.InventoryModel;

/**
 * com.jrsoft.inventory.service ItemService
 * 
 * 工厂型号服务接口
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface InventoryModelService {

	/**
	 * 
	 * @return
	 */
	public List<InventoryModel> findAll();

	/**
	 * 
	 * @param pageNum
	 * @return
	 */
	public PageInfo<InventoryModel> findAll(int pageNum);

	/**
	 * 
	 * @param item
	 * @return
	 */
	public InventoryModel findOne(InventoryModel item);

	/**
	 * 
	 * @param source
	 * @return
	 */
	public List<InventoryModel> findAllBySource(String source);

}
