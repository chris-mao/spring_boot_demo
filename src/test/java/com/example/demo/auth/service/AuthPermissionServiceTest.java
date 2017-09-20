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
		Assert.assertEquals(50, authPermissionService.findAll().size());
	}

	@Test
	public void testFindByName() {
		AuthPermission permission = authPermissionService.findByName("Application\\Controller\\Index\\index");
		Assert.assertNotNull(permission);
	}

	public void testGrantStringAuthRole() {
		//
	}

	public void testGrantStringAuthUser() {
		//
	}

	public void testRevokeStringAuthRole() {
		//
	}

	public void testRevokeStringAuthUser() {
		//
	}

	public void testFindAllByUserAuthUser() {
		//
	}

	public void testFindAllByUserString() {
		//
	}

	public void testFindAllByRoleAuthRole() {
		//
	}

	public void testFindAllByRoleString() {
		//
	}

}
