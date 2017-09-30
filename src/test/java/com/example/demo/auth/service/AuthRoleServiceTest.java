package com.example.demo.auth.service;

import javax.annotation.Resource;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.auth.entity.AuthPermission;
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
	
	@Resource
	private AuthPermissionService authPermissionService;

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
		System.out.println(role);
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
	    Assert.assertEquals(true, this.authRoleService.insert(role));
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
		role.setAvailable(false);
		Assert.assertEquals(true, authRoleService.update(role));
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
	
	@Test
	public void testAddPermission() {
		AuthRole role = this.authRoleService.findById(1);//administrator role
		AuthPermission permission = this.authPermissionService.findById(36);
		
		//添加未分配的权限，应该返回true
		Assert.assertEquals(true, this.authRoleService.addPermission(role, permission));
		//添加已分配的权限，应该返回false
		Assert.assertEquals(false, this.authRoleService.addPermission(role, permission));
	}
	
	@Test
	public void testRemovePermission() {
		AuthRole role = this.authRoleService.findById(1);//administrator role
		AuthPermission permission = this.authPermissionService.findById(36);
		
		//移除已分配的权限，应该返回true
		Assert.assertEquals(true, this.authRoleService.removePermission(role, permission));
		//移除未分配的权限，应该返回false
		Assert.assertEquals(false, this.authRoleService.removePermission(role, permission));
	}

}
