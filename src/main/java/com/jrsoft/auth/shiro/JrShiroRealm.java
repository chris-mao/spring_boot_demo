package com.jrsoft.auth.shiro;

import java.util.List;
import java.util.Set;

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

import com.jrsoft.auth.AuthUserStateEnum;
import com.jrsoft.auth.entity.AuthPermission;
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.auth.service.AuthPermissionService;
import com.jrsoft.auth.service.AuthRoleService;
import com.jrsoft.auth.service.AuthUserService;

/**
 * com.jrsoft.auth.shiro JrShiroRealm
 *
 * 实现系统用户身份认证，权限管理
 * 
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class JrShiroRealm extends AuthorizingRealm {

	/**
	 * 
	 */
	private final static Logger logger = LoggerFactory.getLogger(JrShiroRealm.class);

	/**
	 * 
	 */
	@Resource
	private AuthUserService authUserService;

	@Resource
	private AuthRoleService authRoleService;

	@Resource
	private AuthPermissionService authPermissionService;

	/**
	 * 获取用户权限
	 * 
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		AuthUser user = (AuthUser) principals.getPrimaryPrincipal();
		logger.info("==> 读取用户[" + user.getUserName() + "]权限...");

		// 获取用户录属的角色及相应权限
		logger.info("==> 读取用户[" + user.getUserName() + "]关联的角色...");
		Set<AuthRole> roles = authRoleService.findAllByUser(user);
		for (AuthRole role : roles) {
			authorizationInfo.addRole(role.getRoleName());
			logger.info("==> 读取角色[" + role.getRoleName() + "]关联的权限...");
			List<AuthPermission> permissions = authPermissionService.findRolePermissions(role);
			for (AuthPermission permission : permissions) {
				logger.info("==> 获取权限[" + permission.getPermissionName() + "]");
				authorizationInfo.addStringPermission(permission.getPermissionName());
			}
		}

		// 获取用户特有的权限（暂未实现）
		List<AuthPermission> permissions = this.authPermissionService.findIndividualPermissions(user);
		if (null != permissions) {
			for (AuthPermission permission : permissions) {
				authorizationInfo.addStringPermission(permission.getPermissionName());
			}
		}
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
		AuthUser user = new AuthUser(userName);
		user = authUserService.findOne(user);
		logger.info("==> 开始身份验证" + "[" + userName + "] " + "==> " + user);

		if (user == null) { // 没找到帐号
			throw new UnknownAccountException();
		} else if (user.getState() == AuthUserStateEnum.LOCKED) { // 帐号被锁
			throw new LockedAccountException();
		} else if (user.getState() == AuthUserStateEnum.INACTIVE) { // 帐号失效
			throw new DisabledAccountException();
		} else if (user.getState() == AuthUserStateEnum.EXPIRED) { // 帐号过期
			throw new ExpiredCredentialsException();
		}

		// AuthUserDecorator userDecorator = new AuthUserDecorator(user,
		// customerService);
		// SecurityUtils.getSubject().getSession().setAttribute("userDecorator",
		// userDecorator);

		SimpleAuthenticationInfo authInfo = new SimpleAuthenticationInfo(user, user.getPassword(),
				ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
		return authInfo;
	}

}
