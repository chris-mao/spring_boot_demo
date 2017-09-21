/**
 * 
 */
package com.example.demo.auth.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.auth.dao.AuthRoleDao;
import com.example.demo.auth.entity.AuthPermission;
import com.example.demo.auth.entity.AuthRole;
import com.example.demo.auth.entity.AuthUser;
import com.example.demo.auth.service.AuthRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * com.example.demo.auth.service.impl AuthRoleServiceImpl
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
	private AuthRoleDao authRoleDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.demo.auth.service.AuthRoleService#findAll()
	 */
	@Override
	public List<AuthRole> findAll() {
		return authRoleDao.findAll();
	}

	@Override
	public PageInfo<AuthRole> findAll(int pageNum) {
		PageHelper.startPage(pageNum, this.pageSize);
		return new PageInfo<AuthRole>(authRoleDao.findAll());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.demo.auth.service.AuthRoleService#findByName(java.lang.
	 * String)
	 */
	@Override
	public AuthRole findByName(String roleName) {
		return authRoleDao.findByName(roleName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.example.demo.auth.service.AuthRoleService#findByUser(com.example.demo
	 * .auth.entity.AuthUser)
	 */
	@Override
	public Set<AuthRole> findAllByUser(AuthUser user) {
		return authRoleDao.findAllByUser(user.getUserName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.demo.auth.service.AuthRoleService#findByUser(java.lang.
	 * String)
	 */
	@Override
	public Set<AuthRole> findAllByUser(String userName) {
		return authRoleDao.findAllByUser(userName);
	}

	@Override
	public AuthRole findById(Integer id) {
		return authRoleDao.findById(id);
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
