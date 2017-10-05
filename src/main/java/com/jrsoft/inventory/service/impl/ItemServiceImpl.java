/**
 * 
 */
package com.jrsoft.inventory.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrsoft.inventory.dao.ItemDao;
import com.jrsoft.inventory.entity.Item;
import com.jrsoft.inventory.service.ItemService;

/**
 * com.jrsoft.inventory.service.impl ItemServiceImpl
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Value("${pageSize}")
	private int pageSize;

	@Resource
	private ItemDao itemDao;

	@Override
	public List<Item> findAll() {
		return this.itemDao.findAll();
	}

	@Override
	public PageInfo<Item> findAll(int pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<Item>(this.itemDao.findAll());
	}

	@Override
	public Item findOne(Item item) {
		if (null != item.getItemId()) {
			return this.itemDao.findById(item.getItemId());
		}
		if (null != item.getItemName()) {
			return this.itemDao.findByName(item.getItemName());
		}
		return null;
	}

	@Override
	public List<Item> findAllBySource(String source) {
		return this.itemDao.findAllBySource(source);
	}

}
