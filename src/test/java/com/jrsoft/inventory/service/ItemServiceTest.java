/**
 * 
 */
package com.jrsoft.inventory.service;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.pagehelper.PageInfo;
import com.jrsoft.employee.entity.Employee;
import com.jrsoft.inventory.entity.Item;
import com.jrsoft.inventory.service.impl.ItemServiceImpl;

/**
 * com.jrsoft.inventory.service ItemServiceTest
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemServiceTest extends ItemServiceImpl {
	
	@Resource
	private ItemService itemService;

//	@Test
	public void testFindAll() {
		Assert.assertNotNull(itemService.findAll());
	}
	
	@Test
	public void testFindAllInt() {
		PageInfo<Item> page = itemService.findAll(1);
		Assert.assertNotNull(page);
		Assert.assertEquals(true, page.isIsFirstPage());
		Assert.assertEquals(15, page.getList().size());
	}
	
	@Test
	public void testFindOne() {
		Item item = new Item();
		item.setItemName("3465300");
		Assert.assertNotNull(itemService.findOne(item));
	}
	
	@Test
	public void testFindAlBySource() {
		Assert.assertNotNull(itemService.findAllBySource("P38"));
	}

}
