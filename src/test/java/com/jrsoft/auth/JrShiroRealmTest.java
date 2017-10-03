package com.jrsoft.auth;

import static org.junit.Assert.fail;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jrsoft.auth.entity.AuthUser;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JrShiroRealmTest {

	public void testDoGetAuthorizationInfoPrincipalCollection() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testdoGetAuthenticationInfo() {
		String userName = "admin";
		String password = "password";
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
		Assert.assertEquals(true, subject.isAuthenticated());
		AuthUser user = (AuthUser)subject.getPrincipal();
		Assert.assertEquals(user.getUserName(), "admin");
	}

//	@Test(expected=UnknownAccountException.class)
	public void testUnknowAccount() {
		String userName = "admi";
		String password = "password";
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
	}

//	@Test(expected=LockedAccountException.class)
	public void testLockedAccount() {
		String userName = "admi";
		String password = "password";
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
	}

//	@Test(expected=DisabledAccountException.class)
	public void testDisabledAccount() {
		String userName = "admi";
		String password = "password";
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
	}

//	@Test(expected=ExpiredCredentialsException.class)
	public void testExpiredAccount() {
		String userName = "admi";
		String password = "password";
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
	}

//	@Test(expected=IncorrectCredentialsException.class)
	public void testIncorrectCredentialAccount() {
		String userName = "admi";
		String password = "password";
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
	}

//	@Test(expected=AuthenticationException.class)
	public void testAuthFailure() {
		String userName = "admi";
		String password = "password";
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
	}

}
