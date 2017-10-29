/**
 * 
 */
package com.jrsoft.auth.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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
 * 系统权限服务接口实现类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Service
public class AuthPermissionServiceImpl implements AuthPermissionService {

	@Value("${pageSize}")
	private int pageSize = 20;

	@Autowired
	private AuthRoleDAO authRoleDAO;

	@Autowired
	private AuthPermissionDAO authPermissionDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jrsoft.auth.service.AuthPermissionService#findAll()
	 */
	@Override
	public List<AuthPermission> findAll() {
		return authPermissionDAO.findAll(false);
	}

	@Override
	public PageInfo<AuthPermission> findAll(int pageNum) {
		PageHelper.startPage(pageNum, this.pageSize);
		return new PageInfo<AuthPermission>(authPermissionDAO.findAll(false));
	}

	@Override
	public List<AuthPermission> findAllAvailable() {
		return authPermissionDAO.findAll(true);
	}

	@Override
	public AuthPermission findOne(AuthPermission permission) {
		if (0 != permission.getPermissionId()) {
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
		if (0 != role.getRoleId()) {
			return authPermissionDAO.findAllByRoleId(role.getRoleId());
		}
		if (null != role.getRoleName()) {
			AuthRole r = authRoleDAO.findByName(role.getRoleName());
			if (null == r) {
				return null;
			}
			return authPermissionDAO.findAllByRoleId(r.getRoleId());
		}
		return null;
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
	public boolean delete(int id) {
		return 1 == this.authPermissionDAO.delete(id);
	}

}
