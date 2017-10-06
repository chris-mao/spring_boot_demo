/**
 * 
 */
package com.jrsoft.auth.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrsoft.auth.AuthUserStateEnum;
import com.jrsoft.auth.dao.AuthUserDAO;
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.auth.service.AuthUserService;

/**
 * com.jrsoft.auth.service.impl AuthUserServiceImpl
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

	@Autowired
	private AuthUserDAO authUserDAO;

	@Override
	public List<AuthUser> findAll() {
		return authUserDAO.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jrsoft.auth.service.AuthUserService#findAll()
	 */
	@Override
	public PageInfo<AuthUser> findAll(int pageNum) {
		PageHelper.startPage(pageNum, pageSize, "user_name");
		return new PageInfo<AuthUser>(authUserDAO.findAll());
	}

	@Override
	public AuthUser findOne(AuthUser user) {
		if (null != user.getUserId()) {
			return authUserDAO.findById(user.getUserId());
		}
		if (null != user.getUserName()) {
			return authUserDAO.findByName(user.getUserName());
		}
		return null;
	}

	@Override
	public boolean insert(AuthUser user) {
		return 1 == this.authUserDAO.insert(user);
	}

	@Override
	public boolean update(AuthUser user) {
		return 1 == this.authUserDAO.udpate(user);
	}

	@Override
	public boolean delete(Integer id) {
		return 1 == this.authUserDAO.delete(id);
	}

	@Override
	public boolean changePassword(Integer id, String oldPassword, String newPassword) {
		return 1 == this.authUserDAO.changePassword(id, oldPassword, newPassword);
	}

	@Override
	public boolean changeState(Integer id, AuthUserStateEnum state) {
		return 1 == this.authUserDAO.changeState(id, state);
	}

	@Override
	public boolean addRole(AuthUser user, AuthRole role) {
		if (true == user.getRoles().add(role)) {
			return 1 == this.authUserDAO.addRole(user.getUserId(), role.getRoleId());
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
				return 1 == this.authUserDAO.removeRole(user.getUserId(), role.getRoleId());
			}
		}
		return false;
	}

}
