/**
 * 
 */
package com.jrsoft.auth.service.impl;

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
 * 系统用户服务接口实现类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Service
public class AuthUserServiceImpl implements AuthUserService {

	@Value("${pageSize}")
	private int pageSize = 20;

	@Autowired
	private AuthUserDAO authUserDAO;

	@Override
	public List<AuthUser> findAll() {
		return authUserDAO.findAll(false);
	}

	@Override
	public PageInfo<AuthUser> findAll(int pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<AuthUser>(authUserDAO.findAll(false));
	}

	@Override
	public List<AuthUser> findAllAvailableUser() {
		return authUserDAO.findAll(true);
	}

	@Override
	public AuthUser findOne(AuthUser user) {
		if (0 != user.getUserId()) {
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
		user.setAvailable(user.getState() != AuthUserStateEnum.INACTIVE);
		return 1 == this.authUserDAO.udpate(user);
	}

	@Override
	public boolean delete(int id) {
		return 1 == this.authUserDAO.delete(id);
	}

	@Override
	public boolean changePassword(int id, String oldPassword, String newPassword) {
		return 1 == this.authUserDAO.changePassword(id, oldPassword, newPassword);
	}

	@Override
	public boolean addRole(AuthUser user, AuthRole role) {
		return 1 == this.authUserDAO.addRole(user.getUserId(), role.getRoleId());
	}

	@Override
	public boolean removeRole(AuthUser user, AuthRole role) {
		return 1 == this.authUserDAO.removeRole(user.getUserId(), role.getRoleId());
	}

	@Override
	public void removeAllRoles(AuthUser user) {
		if (0 != user.getUserId()) {
			this.authUserDAO.removeAllRoles(user.getUserId());
		}

	}

}
