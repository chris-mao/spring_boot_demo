/**
 * 
 */
package com.example.demo.auth.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.demo.auth.dao.AuthPermissionDao;
import com.example.demo.auth.entity.AuthPermission;
import com.example.demo.auth.entity.AuthRole;
import com.example.demo.auth.entity.AuthUser;
import com.example.demo.auth.service.AuthPermissionService;

/**
 * com.example.demo.auth.service.impl AuthPermissionServiceImpl
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Service
public class AuthPermissionServiceImpl implements AuthPermissionService {
	
	@Resource
	private AuthPermissionDao authPermissionDao;

	/* (non-Javadoc)
	 * @see com.example.demo.auth.service.AuthPermissionService#findAll()
	 */
	@Override
	public List<AuthPermission> findAll() {
		return authPermissionDao.findAll();
	}

	/* (non-Javadoc)
	 * @see com.example.demo.auth.service.AuthPermissionService#findByName(java.lang.String)
	 */
	@Override
	public AuthPermission findByName(String permissionName) {
		return authPermissionDao.findByName(permissionName);
	}

	/* (non-Javadoc)
	 * @see com.example.demo.auth.service.AuthPermissionService#findAllByUser(com.example.demo.auth.entity.AuthUser)
	 */
	@Override
	public Set<AuthPermission> findAllByUser(AuthUser user) {
		return null;//authPermissionDao.findAllByUserName(user.getUserName());
	}

	/* (non-Javadoc)
	 * @see com.example.demo.auth.service.AuthPermissionService#findAllByUser(java.lang.String)
	 */
	@Override
	public Set<AuthPermission> findAllByUser(String userName) {
		return null;//authPermissionDao.findAllByUserName(userName);
	}

	/* (non-Javadoc)
	 * @see com.example.demo.auth.service.AuthPermissionService#findAllByRole(com.example.demo.auth.entity.AuthRole)
	 */
	@Override
	public Set<AuthPermission> findAllByRole(AuthRole role) {
		return null; //authPermissionDao.findAllByRoleName(role.getRoleName());
	}

	/* (non-Javadoc)
	 * @see com.example.demo.auth.service.AuthPermissionService#findAllByRole(java.lang.String)
	 */
	@Override
	public Set<AuthPermission> findAllByRole(String roleName) {
		return null; //authPermissionDao.findAllByRoleName(roleName);
	}

	@Override
	public void grant(String permissionName, AuthRole role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void grant(String permissionName, AuthUser user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void revoke(String permissionName, AuthRole role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void revoke(String permissionName, AuthUser user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AuthPermission insert(AuthPermission permission) {
		this.authPermissionDao.insert(permission);
		return this.authPermissionDao.findByName(permission.getPermissionName());
	}

	@Override
	public int update(AuthPermission permission) {
		return this.authPermissionDao.udpate(permission);
	}

	@Override
	public void delete(Integer id) {
		this.authPermissionDao.delete(id);
	}

}
