package com.jrsoft.auth.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.auth.entity.AuthUserDelegate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthUserDelegateServiceTest {

	@Autowired
	private AuthUserDelegateService authUserDelegateService;

	@Test
	public void testGrantDelegateAuthUserAuthUser() {
		AuthUser fromUser = new AuthUser(21); // Lansing
		AuthUser toUser = new AuthUser(2); // Chris
		this.authUserDelegateService.grantDelegate(fromUser, toUser);
		Assert.assertEquals(true, this.authUserDelegateService.exists(fromUser, toUser));
		System.out.println(this.authUserDelegateService.findAllByFromUser(fromUser));
	}

	@Test
	public void testGrantDelegateAuthUserAuthUserDateDate() {
		AuthUser fromUser = new AuthUser(20); // Sharon
		AuthUser toUser = new AuthUser(2); // Chris
		
		Date startDate = null;
		Date endDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			startDate = sdf.parse("2017-11-01");
			endDate = sdf.parse("2017-10-20");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		this.authUserDelegateService.grantDelegate(fromUser, toUser, startDate, endDate);
		List<AuthUserDelegate> delegates = this.authUserDelegateService.findAllByFromUser(fromUser);
		Assert.assertEquals(1, delegates.size());
		Assert.assertEquals(0, delegates.get(0).getStartDate().compareTo(startDate));
		Assert.assertEquals(0, delegates.get(0).getEndDate().compareTo(startDate)); //结束日期早于开始日期，正确结果应该是结束日期等于开始日期
		System.out.println(delegates.get(0).getStartDate());
		this.authUserDelegateService.revokeDelegate(fromUser, toUser);
	}

	@Test
	public void testRevokeDelegate() {
		AuthUser fromUser = new AuthUser(21); // Lansing
		AuthUser toUser = new AuthUser(2); // Chris
		this.authUserDelegateService.grantDelegate(fromUser, toUser);
		this.authUserDelegateService.revokeDelegate(fromUser, toUser);
		Assert.assertEquals(false, this.authUserDelegateService.exists(fromUser, toUser));
	}

	@Test
	public void testExists() {
		AuthUser fromUser = new AuthUser(21); // Lansing
		AuthUser toUser = new AuthUser(2); // Chris
		this.authUserDelegateService.grantDelegate(fromUser, toUser);
		Assert.assertEquals(true, this.authUserDelegateService.exists(fromUser, toUser));
		this.authUserDelegateService.revokeDelegate(fromUser, toUser);
		Assert.assertEquals(false, this.authUserDelegateService.exists(fromUser, toUser));
		
	}

	@Test
	public void testFindAllFromUsers() {
		AuthUser fromUser = new AuthUser(21); // Lansing
		AuthUser toUser = new AuthUser(2); // Chris
		this.authUserDelegateService.grantDelegate(fromUser, toUser);
		List<AuthUserDelegate> delegates = this.authUserDelegateService.findAllByFromUser(fromUser);
		Assert.assertEquals(1, delegates.size());
		this.authUserDelegateService.revokeDelegate(fromUser, toUser);
		delegates = this.authUserDelegateService.findAllByFromUser(fromUser);
		Assert.assertEquals(0, delegates.size());
	}

	@Test
	public void testFindAllToUsers() {
		AuthUser fromUser = new AuthUser(21); // Lansing
		AuthUser toUser = new AuthUser(20); // Sharon
		this.authUserDelegateService.grantDelegate(fromUser, toUser);
		List<AuthUserDelegate> delegates = this.authUserDelegateService.findAllByToUser(toUser);
		Assert.assertEquals(1, delegates.size());
		this.authUserDelegateService.revokeDelegate(fromUser, toUser);
		delegates = this.authUserDelegateService.findAllByToUser(toUser);
		Assert.assertEquals(0, delegates.size());
	}

}
