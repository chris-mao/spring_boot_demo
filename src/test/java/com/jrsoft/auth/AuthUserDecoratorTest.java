/**
 * 
 */
package com.jrsoft.auth;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import org.junit.Assert;

import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.auth.shiro.AuthUserDecorator;
import com.jrsoft.customer.service.CustomerService;


/**
 * com.jrsoft.auth AuthUserDecoratorTest
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthUserDecoratorTest {
	
	@Resource
	private CustomerService customerService;

	@Test
	public void test() {
		AuthUser user = new AuthUser();
		user.setUserName("1200078627");
		AuthUserDecorator ud = new AuthUserDecorator(user, customerService);
		Assert.assertNotNull(ud.getCustomerList());
		Assert.assertEquals(1, ud.getCustomerList().size());
		Assert.assertEquals(1, ud.getBillToSiteList().size());
		
	}

}
