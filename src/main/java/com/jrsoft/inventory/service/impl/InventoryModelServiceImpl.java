/**
 * 
 */
package com.jrsoft.inventory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrsoft.inventory.dao.InventoryModelDAO;
import com.jrsoft.inventory.entity.InventoryModel;
import com.jrsoft.inventory.service.InventoryModelService;

/**
 * com.jrsoft.inventory.service.impl InventoryModelServiceImpl
 *
 * 工厂型号服务接口实现类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Service
public class InventoryModelServiceImpl implements InventoryModelService {

	@Value("${pageSize}")
	private int pageSize = 20;

	@Autowired
	private InventoryModelDAO inventoryModelDAO;

	@Override
	public List<InventoryModel> findAll() {
		return this.inventoryModelDAO.findAll();
	}

	@Override
	public PageInfo<InventoryModel> findAll(int pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<InventoryModel>(this.inventoryModelDAO.findAll());
	}

	@Override
	public InventoryModel findOne(InventoryModel item) {
		if (null != item.getModelId()) {
			return this.inventoryModelDAO.findById(item.getModelId());
		}
		if (null != item.getModelName()) {
			return this.inventoryModelDAO.findByName(item.getModelName());
		}
		return null;
	}

	@Override
	public List<InventoryModel> findAllBySource(String source) {
		return this.inventoryModelDAO.findAllBySource(source);
	}

}
