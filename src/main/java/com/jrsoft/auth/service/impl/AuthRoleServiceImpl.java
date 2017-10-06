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
import com.jrsoft.auth.dao.AuthRoleDAO;
import com.jrsoft.auth.dao.AuthUserDAO;
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
	private AuthUserDAO authUserDAO;

	@Resource
	private AuthRoleDAO authRoleDAO;

	@Override
	public List<AuthRole> findAll() {
		return authRoleDAO.findAll();
	}

	@Override
	public PageInfo<AuthRole> findAll(int pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<AuthRole>(authRoleDAO.findAll());
	}

	@Override
	public AuthRole findOne(AuthRole role) {
		if (null != role.getRoleId()) {
			return authRoleDAO.findById(role.getRoleId());
		}
		if (null != role.getRoleName()) {
			return authRoleDAO.findByName(role.getRoleName());
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
			return authRoleDAO.findAllByUserId(user.getUserId());
		}
		if (null != user.getUserName()) {
			AuthUser u = this.authUserDAO.findByName(user.getUserName());
			return this.authRoleDAO.findAllByUserId(u.getUserId());
		}
		return null;
	}

	@Override
	public boolean insert(AuthRole role) {
		return 1 == authRoleDAO.insert(role);
	}

	@Override
	public boolean update(AuthRole role) {
		return 1 == authRoleDAO.udpate(role);

	}

	@Override
	public boolean delete(Integer id) {
		return 1 == authRoleDAO.delete(id);
	}

	@Override
	public boolean addPermission(AuthRole role, AuthPermission permission) {
		if (true == role.getPermissions().add(permission)) {
			return 1 == this.authRoleDAO.addPermission(role.getRoleId(), permission.getPermissionId());
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
				return 1 == this.authRoleDAO.removePermission(role.getRoleId(), permission.getPermissionId());
			}
		}
		return false;
	}

}
