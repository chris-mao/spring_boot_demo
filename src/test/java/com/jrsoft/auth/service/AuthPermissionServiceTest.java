package com.jrsoft.auth.service;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jrsoft.auth.entity.AuthPermission;
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.auth.service.AuthPermissionService;
import com.jrsoft.auth.service.AuthRoleService;

/**
 * 
 * com.jrsoft.auth.service AuthPermissionServiceTest
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
	private AuthRoleService authRoleService;

	@Resource
	private AuthPermissionService authPermissionService;

	@Test
	public void testFindAll() {
		Assert.assertEquals(71, authPermissionService.findAll().size());
	}

	@Test
	public void testFindOne() {
		AuthPermission p = new AuthPermission();
		p.setPermissionId(1);
		AuthPermission permission = authPermissionService.findOne(p);
		System.out.println(permission);
		Assert.assertNotNull(permission);
		p.setPermissionName("Application\\Controller\\Index\\index");
		permission = authPermissionService.findOne(p);
		Assert.assertNotNull(permission);
	}

	@Test
	@Transactional
	public void testInsert() {
		final String permissionName = "new permission";
		AuthPermission p = new AuthPermission();
		p.setPermissionName(permissionName);

		AuthPermission permission = authPermissionService.findOne(p);
		if (null != permission) {
			authPermissionService.delete(permission.getPermissionId());
		}

		permission = new AuthPermission();
		permission.setAvailable(true);
		permission.setPermissionName(permissionName);
		permission.setPermissionUrl("/aa/bb");
		Assert.assertEquals(true, this.authPermissionService.insert(permission));
		Assert.assertNotNull(permission.getPermissionId());
		Assert.assertNotNull(authPermissionService.findOne(p));
	}

	@Test
	@Transactional
	public void testUpdate() {
		final String permissionName = "new permission";
		final String newPermissionName = "new permission 123";
		AuthPermission p = new AuthPermission();
		p.setPermissionName(permissionName);

		AuthPermission permission = authPermissionService.findOne(p);
		Assert.assertNotNull(permission);

		permission.setPermissionName(newPermissionName);
		permission.setAvailable(false);
		Assert.assertEquals(true, authPermissionService.update(permission));
		p.setPermissionName(newPermissionName);
		Assert.assertNotNull(authPermissionService.findOne(p));
	}

	@Test
	@Transactional
	public void testDelete() {
		final String permissionName = "new permission 123";
		AuthPermission p = new AuthPermission();
		p.setPermissionName(permissionName);
		AuthPermission permission = authPermissionService.findOne(p);
		Assert.assertNotNull(permission);

		authPermissionService.delete(permission.getPermissionId());
		Assert.assertNull(authPermissionService.findOne(p));
	}

	@Test
	public void testFindAllByRoleAuthRole() {
		AuthRole r = new AuthRole();
		r.setRoleName("csr");

		Assert.assertEquals(29, this.authPermissionService.findRolePermissions(r).size());
	}

	@Test
	public void testFindAllByRoleString() {
		AuthRole r = new AuthRole();
		r.setRoleName("csr");
		Assert.assertEquals(29, this.authPermissionService.findRolePermissions(r).size());
	}
	
//	@Test
	public void testGetUserMenuTree() {
//		AuthUser user = new AuthUser("cmao");
//		List<EasyTreeNode> menu = authPermissionService.getUserMenuTree(user);
		
	}

}
