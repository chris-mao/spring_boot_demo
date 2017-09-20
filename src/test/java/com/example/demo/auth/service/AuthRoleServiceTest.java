package com.example.demo.auth.service;

import javax.annotation.Resource;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.auth.entity.AuthRole;
import com.example.demo.auth.entity.AuthUser;

/**
 * 
 * com.example.demo.auth.service AuthRoleServiceTest
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthRoleServiceTest {

	@Resource
	private AuthRoleService authRoleService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testFindAll() {
		Assert.assertEquals(4, authRoleService.findAll().size());
	}

	@Test
	public void testFindById() {
		AuthRole role = authRoleService.findById(1);
		Assert.assertNotNull(role);
		Assert.assertEquals("administrator", role.getRoleName());
	}

	@Test
	public void testFindByName() {
		AuthRole role = authRoleService.findByName("csr");
		Assert.assertNotNull(role);
		Assert.assertEquals("csr", role.getRoleName());
	}

	@Test
	public void testFindAllByUserAuthUser() {
		AuthUser user = new AuthUser();
		user.setUserName("cmao");
		Assert.assertEquals(2, authRoleService.findAllByUser(user).size());
	}

	@Test
	public void testFindAllByUserString() {
		Assert.assertEquals(1, authRoleService.findAllByUser("admin").size());
	}

	@Test
	public void testInsert() {
		AuthRole role = null;
		final String roleName = "new role";
		role = authRoleService.findByName(roleName);
		if (null != role) {
			authRoleService.delete(role.getRoleId());
		}

		role = new AuthRole();
		role.setAvailable(true);
		role.setRoleName(roleName);
	    Assert.assertEquals(1, this.authRoleService.insert(role));
	    Assert.assertNotNull(role.getRoleId());
		Assert.assertNotNull(authRoleService.findByName(roleName));
	}

	@Test
	public void testUpdate() {
		final String roleName = "new role";
		final String newRoleName = "new role 123";
		AuthRole role = authRoleService.findByName(roleName);
		Assert.assertNotNull(role);
		
		role.setRoleName(newRoleName);
		Assert.assertEquals(1, authRoleService.update(role));
		Assert.assertNotNull(authRoleService.findByName(newRoleName));
	}
	
	@Test
	public void testDelete() {
		final String roleName = "new role 123";
		AuthRole role = authRoleService.findByName(roleName);
		Assert.assertNotNull(role);
		
		authRoleService.delete(role.getRoleId());
		Assert.assertNull(authRoleService.findByName(roleName));
	}

}
