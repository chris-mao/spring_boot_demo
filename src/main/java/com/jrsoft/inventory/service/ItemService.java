/**
 * 
 */
package com.jrsoft.inventory.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.jrsoft.inventory.entity.Item;

/**
 * com.jrsoft.inventory.service ItemService
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface ItemService {
	
	/**
	 * 
	 * @return
	 */
	public List<Item> findAll();
	
	/**
	 * 
	 * @param pageNum
	 * @return
	 */
	public PageInfo<Item> findAll(int pageNum);
	
	/**
	 * 
	 * @param item
	 * @return
	 */
	public Item findOne(Item item);
	
	/**
	 * 
	 * @param source
	 * @return
	 */
	public List<Item> findAllBySource(String source);

}
