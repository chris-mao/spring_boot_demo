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

/**
 * 
 * com.example.demo.auth.service AuthPermissionServiceTest
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthPermissionServiceTest {
	
	@Resource
	private AuthPermissionService authPermissionService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testFindAll() {
		Assert.assertEquals(21, authPermissionService.findAll().size());
	}

	@Test
	public void testFindById() {
		AuthPermission permission = authPermissionService.findById(1);
		System.out.println(permission);
		Assert.assertNotNull(permission);
	}

	@Test
	public void testFindByName() {
		AuthPermission permission = authPermissionService.findByName("Application\\Controller\\Index\\index");
		Assert.assertNotNull(permission);
	}
	
	@Test
	public void testInsert() {
		AuthPermission permission = null;
		final String permissionName = "new permission";
		permission = authPermissionService.findByName(permissionName);
		if (null != permission) {
			authPermissionService.delete(permission.getPermissionId());
		}

		permission = new AuthPermission();
		permission.setAvailable(true);
		permission.setPermissionName(permissionName);
		permission.setPermissionUrl("/aa/bb");
	    Assert.assertEquals(true, this.authPermissionService.insert(permission));
	    Assert.assertNotNull(permission.getPermissionId());
		Assert.assertNotNull(authPermissionService.findByName(permissionName));
	}
	
	@Test
	public void testUpdate() {
		final String permissionName = "new permission";
		final String newPermissionName = "new permission 123";
		AuthPermission permission = authPermissionService.findByName(permissionName);
		Assert.assertNotNull(permission);
		
		permission.setPermissionName(newPermissionName);
		permission.setAvailable(false);
		Assert.assertEquals(true, authPermissionService.update(permission));
		Assert.assertNotNull(authPermissionService.findByName(newPermissionName));
	}
	
	@Test
	public void testDelete() {
		final String permissionName = "new permission 123";
		AuthPermission permission = authPermissionService.findByName(permissionName);
		Assert.assertNotNull(permission);
		
		authPermissionService.delete(permission.getPermissionId());
		Assert.assertNull(authPermissionService.findByName(permissionName));
	}

	@Test
	public void testFindAllByRoleAuthRole() {
		AuthRole role = new AuthRole();
		role.setRoleName("csr");
		Assert.assertEquals(29, this.authPermissionService.findAllByRole(role.getRoleName()).size());
	}

	@Test
	public void testFindAllByRoleString() {
		final String roleName = "csr";
		Assert.assertEquals(29, this.authPermissionService.findAllByRole(roleName).size());
	}

}
