/**
 * 
 */
package com.jrsoft.auth.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrsoft.auth.AuthUserStateEnum;
import com.jrsoft.auth.dao.AuthUserDAO;
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.auth.entity.AuthUserRoleReleation;
import com.jrsoft.auth.service.AuthUserService;
import com.jrsoft.common.EasyDataGrid;

/**
 * 系统用户服务接口实现类
 * 
 * @see AuthUserService
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Service
public class AuthUserServiceImpl implements AuthUserService {

	/**
	 * com.jrsoft.auth.dao.AuthUserDAO
	 */
	@Autowired
	private AuthUserDAO authUserDAO;

	@Override
	public List<AuthUser> findAll() {
		return authUserDAO.findAll(false);
	}

	@Override
	public List<AuthUser> findAll(boolean onlyAvailable) {
		return authUserDAO.findAll(onlyAvailable);
	}

	@Override
	public PageInfo<AuthUser> findAll(int pageIndex, int pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		return new PageInfo<AuthUser>(authUserDAO.findAll(false));
	}

	@Override
	public EasyDataGrid<AuthUser> findAll(int pageIndex, int pageSize, String searchStr) {
		PageInfo<AuthUser> pageInfo;
		if (searchStr.isEmpty()) {
			pageInfo = this.findAll(pageIndex, pageSize);
		} else {
			AuthUser user = new AuthUser();
			user.setUserName("%" + searchStr + "%");
			user.setNickName("%" + searchStr + "%");
			PageHelper.startPage(pageIndex, pageSize);
			pageInfo = new PageInfo<AuthUser>(authUserDAO.fuzzyQuery(user));
		}

		EasyDataGrid<AuthUser> dg = new EasyDataGrid<AuthUser>();
		dg.setTotal(pageInfo.getTotal());
		dg.setRows(pageInfo.getList());
		return dg;
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
	public Set<AuthUserRoleReleation> findUserRoles(int id) {
		return authUserDAO.findUserRoles(id);
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean grantRole(AuthUser user, AuthRole role) {
		return 1 == this.authUserDAO.addRole(user.getUserId(), role.getRoleId());
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean revokeRole(AuthUser user, AuthRole role) {
		return 1 == this.authUserDAO.removeRole(user.getUserId(), role.getRoleId());
	}

	@Override
	@SuppressWarnings("deprecation")
	public void revokeAllRoles(AuthUser user) {
		if (0 != user.getUserId()) {
			this.authUserDAO.removeAllRoles(user.getUserId());
		}
	}

	@Override
	public boolean grantRole(AuthUserRoleReleation releation) {
		return this.authUserDAO.addRoleRelation(releation) == 1;
	}

	@Override
	public boolean updateGrantedRole(AuthUserRoleReleation releation) {
		return authUserDAO.updateRoleRelation(releation) == 1;
	}

	@Override
	public boolean revokeRole(AuthUserRoleReleation releation) {
		return authUserDAO.deleteRoleRelation(releation) == 1;
	}

	private boolean lockUser(AuthUser user) {
		user.setState(AuthUserStateEnum.LOCKED);
		return update(user);
	}

	@Override
	public void lockUser(int userId) {
		AuthUser user = authUserDAO.findById(userId);
		if (null != user) {
			lockUser(user);
		}
	}

	@Override
	public void lockUser(String userName) {
		AuthUser user = authUserDAO.findByName(userName);
		if (null != user) {
			lockUser(user);
		}
	}

}
