package com.jrsoft.auth.service;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jrsoft.auth.entity.AuthPermission;
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.auth.service.AuthPermissionService;
import com.jrsoft.auth.service.AuthRoleService;

/**
 * 
 * com.jrsoft.auth.service AuthRoleServiceTest
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
	public void testFindOne() {
		AuthRole r = new AuthRole();
		r.setRoleId(1);
		AuthRole role = authRoleService.findOne(r);
		Assert.assertNotNull(role);
		Assert.assertEquals("administrator", role.getRoleName());

		r.setRoleId(0);
		r.setRoleName("csr");
		role = authRoleService.findOne(r);
		Assert.assertNotNull(role);
		Assert.assertEquals("csr", role.getRoleName());
		System.out.println(role);
	}

	@Test
	public void testFindAllByUserAuthUser() {
		AuthUser user = new AuthUser();
		user.setUserName("cmao");
		Assert.assertEquals(1, authRoleService.findAllByUser(user).size());
	}

	@Test
	public void testFindAllByUserString() {
		AuthUser user = new AuthUser();
		user.setUserName("admin");
		Assert.assertEquals(1, authRoleService.findAllByUser(user).size());
	}

	@Test
	public void testInsert() {
		AuthRole r = new AuthRole();
		AuthRole role = null;
		final String roleName = "new role";
		r.setRoleName(roleName);
		role = authRoleService.findOne(r);
		if (null != role) {
			authRoleService.delete(role.getRoleId());
		}

		role = new AuthRole();
		role.setAvailable(true);
		role.setRoleName(roleName);
		Assert.assertEquals(true, this.authRoleService.insert(role));
		Assert.assertNotNull(role.getRoleId());
		Assert.assertNotNull(authRoleService.findOne(role));
	}

	@Test
	public void testUpdate() {
		AuthRole r = new AuthRole();
		final String roleName = "new role";
		final String newRoleName = "new role 123";
		r.setRoleName(roleName);
		AuthRole role = authRoleService.findOne(r);
		Assert.assertNotNull(role);

		role.setRoleName(newRoleName);
		role.setAvailable(false);
		Assert.assertEquals(true, authRoleService.update(role));
		Assert.assertNotNull(authRoleService.findOne(role));
	}

	@Test
	public void testDelete() {
		AuthRole r = new AuthRole();
		final String roleName = "new role 123";
		r.setRoleName(roleName);
		AuthRole role = authRoleService.findOne(r);
		Assert.assertNotNull(role);

		authRoleService.delete(role.getRoleId());
		Assert.assertNull(authRoleService.findOne(role));
	}

	@Test
	public void testAddPermission() {
		AuthRole r = new AuthRole();
		r.setRoleId(1);
		AuthRole role = this.authRoleService.findOne(r);// administrator role
		AuthPermission p = new AuthPermission();
		p.setPermissionId(36);
		AuthPermission permission = this.authPermissionService.findOne(p);

		// 添加未分配的权限，应该返回true
		Assert.assertEquals(true, this.authRoleService.addPermission(role, permission));
		// 添加已分配的权限，应该返回false
		Assert.assertEquals(false, this.authRoleService.addPermission(role, permission));
	}

	@Test
	public void testRemovePermission() {
		AuthRole r = new AuthRole();
		r.setRoleId(1);
		AuthRole role = this.authRoleService.findOne(r);// administrator role
		AuthPermission p = new AuthPermission();
		p.setPermissionId(36);
		AuthPermission permission = this.authPermissionService.findOne(p);

		// 移除已分配的权限，应该返回true
		Assert.assertEquals(true, this.authRoleService.removePermission(role, permission));
		// 移除未分配的权限，应该返回false
		Assert.assertEquals(false, this.authRoleService.removePermission(role, permission));
	}

}
