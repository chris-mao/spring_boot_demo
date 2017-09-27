/**
 * 
 */
package com.example.demo.auth.service.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.auth.AuthUserStateEnum;
import com.example.demo.auth.dao.AuthUserDao;
import com.example.demo.auth.entity.AuthRole;
import com.example.demo.auth.entity.AuthUser;
import com.example.demo.auth.service.AuthUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * com.example.demo.auth.service.impl AuthUserServiceImpl
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Service
public class AuthUserServiceImpl implements AuthUserService {
	
	@Value("${pageSize}")
	private int pageSize;

	@Resource
	private AuthUserDao authUserDao;

	@Override
	public List<AuthUser> findAll() {
		return authUserDao.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.demo.auth.service.AuthUserService#findAll()
	 */
	@Override
	public PageInfo<AuthUser> findAll(int pageNum) {
		PageHelper.startPage(pageNum, this.pageSize, "user_name");
		return new PageInfo<AuthUser>(authUserDao.findAll());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.demo.auth.service.AuthUserService#findById()
	 */
	@Override
	public AuthUser findById(Integer id) {
		return authUserDao.findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.demo.auth.service.AuthUserService#findByName()
	 */
	@Override
	public AuthUser findByName(String userName) {
		return authUserDao.findByName(userName);
	}

	@Override
	public boolean insert(AuthUser user) {
		return 1 == this.authUserDao.insert(user);
	}

	@Override
	public boolean update(AuthUser user) {
		return 1 == this.authUserDao.udpate(user);
	}

	@Override
	public boolean delete(Integer id) {
		return 1 == this.authUserDao.delete(id);
	}

	@Override
	public boolean changePassword(Integer id, String oldPassword, String newPassword) {
		return 1 == this.authUserDao.changePassword(id, oldPassword, newPassword);
	}

	@Override
	public boolean changeState(Integer id, AuthUserStateEnum state) {
		return 1 == this.authUserDao.changeState(id, state);
	}

	@Override
	public boolean addRole(AuthUser user, AuthRole role) {
		if (true == user.getRoles().add(role)) {
			return 1 == this.authUserDao.addRole(user.getUserId(), role.getRoleId());
		}
		return false;
	}

	@Override
	public boolean removeRole(AuthUser user, AuthRole role) {
		Iterator<AuthRole> it = user.getRoles().iterator();
		while (it.hasNext()) {
			AuthRole r = it.next();
			if (r.equals(role)) {
				it.remove();
				return 1 == this.authUserDao.removeRole(user.getUserId(), role.getRoleId());
			}
		}
		return false;
	}

}
