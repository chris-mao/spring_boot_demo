/**
 * 
 */
package com.example.demo.auth.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.demo.auth.dao.AuthRoleDao;
import com.example.demo.auth.entity.AuthRole;
import com.example.demo.auth.entity.AuthUser;
import com.example.demo.auth.service.AuthRoleService;

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

}
