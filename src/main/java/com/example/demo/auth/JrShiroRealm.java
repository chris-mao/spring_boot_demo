package com.example.demo.auth;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.auth.entity.AuthPermission;
import com.example.demo.auth.entity.AuthRole;
import com.example.demo.auth.entity.AuthUser;
import com.example.demo.auth.service.AuthUserService;

public class JrShiroRealm extends AuthorizingRealm {
	
	private final static Logger logger = LoggerFactory.getLogger(JrShiroRealm.class);

	/**
	 * 
	 */
	@Resource
	private AuthUserService authUserService;

	// @Resource
	// private AuthPermissionService authPermissionService;

	/**
	 * 权限判断
	 * 
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		AuthUser user = (AuthUser) principals.getPrimaryPrincipal();
		logger.info("读取用户[" + user.getUserName() + "]权限 --> JrShiroRealm.doGetAuthorizationInfo()");

		// 获取用户录属的角色及相应权限
		logger.info("读取用户[" + user.getUserName() + "]关联的角色 --> JrShiroRealm.doGetAuthorizationInfo()");
		for (AuthRole role : user.getRoles()) {
			authorizationInfo.addRole(role.getRoleName());
			logger.info("读取角色[" + role.getRoleName() + "]关联的权限 --> JrShiroRealm.doGetAuthorizationInfo()");
			for (AuthPermission permission : role.getPermissions()) {
				authorizationInfo.addStringPermission(permission.getPermissionName());
			}
		}

		// 获取用户特有的权限（暂未实现）
		// Set<AuthPermission> permissions =
		// this.authPermissionService.findAllByUser(user);
		// if (null != permissions) {
		// for (AuthPermission permission : permissions) {
		// authorizationInfo.addStringPermission(permission.getPermissionName());
		// }
		// }
		return authorizationInfo;
	}

	/**
	 * 身份验证
	 *
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userName = (String) token.getPrincipal();
		AuthUser user = authUserService.findByName(userName);
		logger.info("身份验证" + "[" + userName + "] " + "-->JrShiroRealm.doGetAuthorizationInfo()");

		if (user == null) { // 没找到帐号
			throw new UnknownAccountException();
		} else if (user.getState() == AuthUserState.LOCKED) {   // 帐号被锁
			throw new LockedAccountException();
		} else if (user.getState() == AuthUserState.INACTIVE) { // 帐号失效
			throw new DisabledAccountException();
		} else if (user.getState() == AuthUserState.EXPIRED) {  // 帐号过期
			throw new ExpiredCredentialsException();
		}

		SimpleAuthenticationInfo authInfo = new SimpleAuthenticationInfo(user, user.getPassword(),
				ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
		return authInfo;
	}

}
