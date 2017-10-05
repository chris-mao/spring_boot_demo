/**
 * 
 */
package com.jrsoft.auth.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrsoft.auth.dao.AuthRoleDao;
import com.jrsoft.auth.dao.AuthUserDao;
import com.jrsoft.auth.entity.AuthPermission;
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.auth.service.AuthRoleService;

/**
 * com.jrsoft.auth.service.impl AuthRoleServiceImpl
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Service
public class AuthRoleServiceImpl implements AuthRoleService {

	@Value("${pageSize}")
	private int pageSize;

	@Resource
	private AuthUserDao authUserDao;

	@Resource
	private AuthRoleDao authRoleDao;

	@Override
	public List<AuthRole> findAll() {
		return authRoleDao.findAll();
	}

	@Override
	public PageInfo<AuthRole> findAll(int pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<AuthRole>(authRoleDao.findAll());
	}

	@Override
	public AuthRole findOne(AuthRole role) {
		if (null != role.getRoleId()) {
			return authRoleDao.findById(role.getRoleId());
		}
		if (null != role.getRoleName()) {
			return authRoleDao.findByName(role.getRoleName());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jrsoft.auth.service.AuthRoleService#findByUser(com.jrsoft
	 * .auth.entity.AuthUser)
	 */
	@Override
	public Set<AuthRole> findAllByUser(AuthUser user) {
		if (null != user.getUserId()) {
			return authRoleDao.findAllByUserId(user.getUserId());
		}
		if (null != user.getUserName()) {
			AuthUser u = this.authUserDao.findByName(user.getUserName());
			return this.authRoleDao.findAllByUserId(u.getUserId());
		}
		return null;
	}

	@Override
	public boolean insert(AuthRole role) {
		return 1 == authRoleDao.insert(role);
	}

	@Override
	public boolean update(AuthRole role) {
		return 1 == authRoleDao.udpate(role);

	}

	@Override
	public boolean delete(Integer id) {
		return 1 == authRoleDao.delete(id);
	}

	@Override
	public boolean addPermission(AuthRole role, AuthPermission permission) {
		if (true == role.getPermissions().add(permission)) {
			return 1 == this.authRoleDao.addPermission(role.getRoleId(), permission.getPermissionId());
		}
		return false;
	}

	@Override
	public boolean removePermission(AuthRole role, AuthPermission permission) {
		Iterator<AuthPermission> it = role.getPermissions().iterator();
		while (it.hasNext()) {
			AuthPermission p = it.next();
			if (p.equals(permission)) {
				it.remove();
				return 1 == this.authRoleDao.removePermission(role.getRoleId(), permission.getPermissionId());
			}
		}
		return false;
	}

}
