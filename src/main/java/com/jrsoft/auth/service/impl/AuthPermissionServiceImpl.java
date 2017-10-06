/**
 * 
 */
package com.jrsoft.auth.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrsoft.auth.dao.AuthPermissionDAO;
import com.jrsoft.auth.dao.AuthRoleDAO;
import com.jrsoft.auth.entity.AuthPermission;
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.auth.service.AuthPermissionService;

/**
 * com.jrsoft.auth.service.impl AuthPermissionServiceImpl
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Service
public class AuthPermissionServiceImpl implements AuthPermissionService {

	@Value("${pageSize}")
	private int pageSize;

	@Resource
	private AuthRoleDAO authRoleDAO;

	@Resource
	private AuthPermissionDAO authPermissionDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jrsoft.auth.service.AuthPermissionService#findAll()
	 */
	@Override
	public List<AuthPermission> findAll() {
		return authPermissionDAO.findAll();
	}

	@Override
	public PageInfo<AuthPermission> findAll(int pageNum) {
		PageHelper.startPage(pageNum, this.pageSize);
		return new PageInfo<AuthPermission>(authPermissionDAO.findAll());
	}

	@Override
	public AuthPermission findOne(AuthPermission permission) {
		if (null != permission.getPermissionId()) {
			return this.authPermissionDAO.findById(permission.getPermissionId());
		}
		if (null != permission.getPermissionName()) {
			return authPermissionDAO.findByName(permission.getPermissionName());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jrsoft.auth.service.AuthPermissionService#findAllByRole(com.
	 * jrsoft.auth.entity.AuthRole)
	 */
	@Override
	public Set<AuthPermission> findAllByRole(AuthRole role) {
		return authPermissionDAO.findAllByRoleId(role.getRoleId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jrsoft.auth.service.AuthPermissionService#findAllByRole(java.
	 * lang.String)
	 */
	@Override
	public Set<AuthPermission> findAllByRole(String roleName) {
		AuthRole role = this.authRoleDAO.findByName(roleName);
		if (null == role) {
			return null;
		}
		return authPermissionDAO.findAllByRoleId(role.getRoleId());
	}

	@Override
	public boolean insert(AuthPermission permission) {
		return 1 == this.authPermissionDAO.insert(permission);
	}

	@Override
	public boolean update(AuthPermission permission) {
		return 1 == this.authPermissionDAO.udpate(permission);
	}

	@Override
	public boolean delete(Integer id) {
		return 1 == this.authPermissionDAO.delete(id);
	}

}
