package com.jrsoft.auth.service;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jrsoft.auth.AuthUserStateEnum;
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.auth.service.AuthRoleService;
import com.jrsoft.auth.service.AuthUserService;

/**
 * 
 * com.jrsoft.auth.service AuthUserServiceTest
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
	
	@Resource
	private AuthRoleService authRoleService;

	@Test
	public void testFindAll() {
		Assert.assertEquals(301, authUserService.findAll().size());
	}

	@Test
	public void testFindById() {
		AuthUser u = new AuthUser();
		u.setUserId(1);
		AuthUser user = this.authUserService.findOne(u);
		Assert.assertNotNull(user);
		Assert.assertEquals("admin", user.getUserName());
	}

	@Test
	public void testFindByName() {
		AuthUser u = new AuthUser();
		u.setUserName("admin");
		AuthUser user = this.authUserService.findOne(u);
		Assert.assertNotNull(user);
		Assert.assertEquals("admin", user.getUserName());
	}

	@Test
	public void testInsert() {
		AuthUser user = null;
		final String userName = "new user";
		AuthUser u = new AuthUser();
		u.setUserName(userName);
		user = authUserService.findOne(u);
		if (null != user) {
			authUserService.delete(user.getUserId());
		}

		user = new AuthUser();
		user.setUserName(userName);
		user.setNickName("haha");
		user.setPassword("password");
		user.setState(AuthUserStateEnum.LOCKED);
	    Assert.assertEquals(true, this.authUserService.insert(user));
	    Assert.assertNotNull(user.getUserId());
		Assert.assertNotNull(authUserService.findOne(user));
	}

	@Test
	public void testUpdate() {
		final String userName = "new user";
		final String newUserName = "new user 123";
		AuthUser u = new AuthUser();
		u.setUserName(userName);
		AuthUser user = authUserService.findOne(u);
		Assert.assertNotNull(user);
		
		user.setUserName(newUserName);
		user.setState(AuthUserStateEnum.EXPIRED);
		Assert.assertEquals(true, authUserService.update(user));
		Assert.assertNotNull(authUserService.findOne(user));
	}
	
	@Test
	public void testDelete() {
		final String userName = "new user 123";
		AuthUser u = new AuthUser();
		u.setUserName(userName);
		AuthUser user = authUserService.findOne(u);
		Assert.assertNotNull(user);
		
		authUserService.delete(user.getUserId());
		Assert.assertNull(authUserService.findOne(u));
	}

	@Test
	public void testChangePassword() {
		final String oldPassword = "password";
		final String newPassword = "Welcome123";
		Assert.assertEquals(true, authUserService.changePassword(1, oldPassword, newPassword));
		authUserService.changePassword(1, newPassword, oldPassword); //将密码改回去，方便下次测试使用
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void testAddRole() {
		AuthUser u = new AuthUser();
		u.setUserName("cmao");
		AuthUser user = this.authUserService.findOne(u);
		AuthRole r = new AuthRole();
		r.setRoleId(3);
		AuthRole role = this.authRoleService.findOne(r);//customer role
		
		//添加未关联的角色，应该返回true
		Assert.assertEquals(true, this.authUserService.grantRole(user, role));		
		//添加已关联的角色，应该返回false
		Assert.assertEquals(false, this.authUserService.grantRole(user, role));
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void testRemoveRole() {
		AuthUser u = new AuthUser();
		u.setUserName("cmao");
		AuthUser user = this.authUserService.findOne(u);
		AuthRole r = new AuthRole();
		r.setRoleId(3);
		AuthRole role = this.authRoleService.findOne(r);//customer role
		
		//移除已关联的角色，应该返回true
		Assert.assertEquals(true, this.authUserService.revokeRole(user, role));
		//移除未关联的角色，应该返回false
		Assert.assertEquals(false, this.authUserService.revokeRole(user, role));
	}

}
