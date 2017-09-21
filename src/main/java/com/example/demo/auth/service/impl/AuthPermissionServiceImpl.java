/**
 * 
 */
package com.example.demo.auth.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.auth.dao.AuthPermissionDao;
import com.example.demo.auth.entity.AuthPermission;
import com.example.demo.auth.entity.AuthRole;
import com.example.demo.auth.service.AuthPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

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
	
	@Value("${pageSize}")
	private int pageSize;

	@Resource
	private AuthPermissionDao authPermissionDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.demo.auth.service.AuthPermissionService#findAll()
	 */
	@Override
	public List<AuthPermission> findAll() {
		return authPermissionDao.findAll();
	}
	
	@Override
	public PageInfo<AuthPermission> findAll(int pageNum) {
		PageHelper.startPage(pageNum, this.pageSize);
		return new PageInfo<AuthPermission>(authPermissionDao.findAll());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.example.demo.auth.service.AuthPermissionService#findByName(java.lang.
	 * String)
	 */
	@Override
	public AuthPermission findByName(String permissionName) {
		return authPermissionDao.findByName(permissionName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.example.demo.auth.service.AuthPermissionService#findAllByRole(com.
	 * example.demo.auth.entity.AuthRole)
	 */
	@Override
	public Set<AuthPermission> findAllByRole(AuthRole role) {
		return authPermissionDao.findAllByRoleName(role.getRoleName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.example.demo.auth.service.AuthPermissionService#findAllByRole(java.
	 * lang.String)
	 */
	@Override
	public Set<AuthPermission> findAllByRole(String roleName) {
		return authPermissionDao.findAllByRoleName(roleName);
	}

	@Override
	public boolean insert(AuthPermission permission) {
		return 1 == this.authPermissionDao.insert(permission);
	}

	@Override
	public boolean update(AuthPermission permission) {
		return 1 == this.authPermissionDao.udpate(permission);
	}

	@Override
	public boolean delete(Integer id) {
		return 1 == this.authPermissionDao.delete(id);
	}

	@Override
	public AuthPermission findById(Integer id) {
		return this.authPermissionDao.findById(id);
	}

}
