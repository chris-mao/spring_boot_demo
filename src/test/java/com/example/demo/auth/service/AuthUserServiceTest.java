package com.example.demo.auth.service;

import javax.annotation.Resource;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.auth.AuthUserState;
import com.example.demo.auth.entity.AuthUser;

import org.junit.Assert;

/**
 * 
 * com.example.demo.auth.service AuthUserServiceTest
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthUserServiceTest {
	
	@Resource
	private AuthUserService authUserService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testFindAll() {
		Assert.assertEquals(301, authUserService.findAll().size());
	}

	@Test
	public void testFindById() {
		AuthUser user = this.authUserService.findById(1);
		Assert.assertNotNull(user);
		Assert.assertEquals("admin", user.getUserName());
	}

	@Test
	public void testFindByName() {
		AuthUser user = this.authUserService.findByName("admin");
		Assert.assertNotNull(user);
		Assert.assertEquals("admin", user.getUserName());
	}

	@Test
	public void testInsert() {
		AuthUser user = null;
		final String userName = "new user";
		user = authUserService.findByName(userName);
		if (null != user) {
			authUserService.delete(user.getUserId());
		}

		user = new AuthUser();
		user.setUserName(userName);
		user.setNickName("haha");
		user.setPassword("password");
		user.setState(AuthUserState.LOCKED);
	    Assert.assertEquals(true, this.authUserService.insert(user));
	    Assert.assertNotNull(user.getUserId());
		Assert.assertNotNull(authUserService.findByName(userName));
	}

	@Test
	public void testUpdate() {
		final String userName = "new user";
		final String newUserName = "new user 123";
		AuthUser user = authUserService.findByName(userName);
		Assert.assertNotNull(user);
		
		user.setUserName(newUserName);
		user.setState(AuthUserState.EXPIRED);
		Assert.assertEquals(true, authUserService.update(user));
		Assert.assertNotNull(authUserService.findByName(newUserName));
	}
	
	@Test
	public void testDelete() {
		final String userName = "new user 123";
		AuthUser user = authUserService.findByName(userName);
		Assert.assertNotNull(user);
		
		authUserService.delete(user.getUserId());
		Assert.assertNull(authUserService.findByName(userName));
	}

	@Test
	public void testChangePassword() {
		final String oldPassword = "password";
		final String newPassword = "Welcome123";
		Assert.assertEquals(true, authUserService.changePassword(1, oldPassword, newPassword));
		authUserService.changePassword(1, newPassword, oldPassword); //将密码改回去，方便下次测试使用
	}
	
	@Test
	public void testChangeState() {
		Assert.assertEquals(true, authUserService.changeState(1, AuthUserState.INACTIVE));
		authUserService.changeState(1, AuthUserState.ACTIVE); //将状态改回去，方便下次测试使用
	}

}
