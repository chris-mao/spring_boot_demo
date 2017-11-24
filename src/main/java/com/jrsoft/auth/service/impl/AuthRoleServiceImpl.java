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
import com.jrsoft.auth.dao.AuthRoleDAO;
import com.jrsoft.auth.dao.AuthUserDAO;
import com.jrsoft.auth.entity.AuthPermission;
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.auth.entity.AuthRolePermissionReleation;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.auth.service.AuthRoleService;
import com.jrsoft.common.EasyDataGrid;

/**
 * 系统角色服务接口实现类
 * 
 * @see AuthRoleService
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.2
 *
 */
@Service
public class AuthRoleServiceImpl implements AuthRoleService {

	@Autowired
	private AuthUserDAO authUserDAO;

	@Autowired
	private AuthRoleDAO authRoleDAO;

	@Override
	public List<AuthRole> findAll() {
		return authRoleDAO.findAll(false);
	}

	@Override
	public List<AuthRole> findAll(boolean onlyAvailable) {
		return authRoleDAO.findAll(onlyAvailable);
	}

	@Override
	public PageInfo<AuthRole> findAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<AuthRole>(authRoleDAO.findAll(false));
	}

	@Override
	public EasyDataGrid<AuthRole> findAll(int pageIndex, int pageSize, String searchStr) {
		PageInfo<AuthRole> pageInfo;

		if (searchStr.isEmpty()) {
			pageInfo = this.findAll(pageIndex, pageSize);
		} else {
			String roleName = "%" + searchStr + "%";
			PageHelper.startPage(pageIndex, pageSize);
			pageInfo = new PageInfo<AuthRole>(authRoleDAO.fuzzyQuery(roleName));
		}

		EasyDataGrid<AuthRole> dg = new EasyDataGrid<AuthRole>();
		dg.setTotal(pageInfo.getTotal());
		dg.setRows(pageInfo.getList());
		return dg;
	}

	@Override
	public AuthRole findOne(AuthRole role) {
		if (0 != role.getRoleId()) {
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
		if (0 != user.getUserId()) {
			return authRoleDAO.findAllByUserId(user.getUserId());
		}
		if (null != user.getUserName()) {
			AuthUser u = this.authUserDAO.findByName(user.getUserName());
			if (null == u) {
				return null;
			}
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
	public boolean delete(int id) {
		return 1 == authRoleDAO.delete(id);
	}

	@Override
	public boolean grantPermission(AuthRolePermissionReleation rolePermission) {
		//TODO 
		return false;
	}

	@Override
	public boolean updateGrantedPermission(AuthRolePermissionReleation rolePermission) {
		//TODO 
		return false;
	}

	@Override
	public boolean revokePermission(AuthRolePermissionReleation rolePermission) {
		//TODO 
		return false;
	}

	@Override
	public boolean addPermission(AuthRole role, AuthPermission permission) {
		return 1 == this.authRoleDAO.addPermission(role.getRoleId(), permission.getPermissionId());
	}

	@Override
	public boolean removePermission(AuthRole role, AuthPermission permission) {
		return 1 == this.authRoleDAO.removePermission(role.getRoleId(), permission.getPermissionId());
	}

	@Override
	public void removeAllPermissions(AuthRole role) {
		if (0 != role.getRoleId()) {
			this.authRoleDAO.removeAllPermissions(role.getRoleId());
		}
	}

	@Override
	public boolean addPermission(Integer roleId, Integer permissionId) {
		return 1 == this.authRoleDAO.addPermission(roleId, permissionId);
	}

	@Override
	public boolean removePermission(Integer roleId, Integer permissionId) {
		return 1 == this.authRoleDAO.removePermission(roleId, permissionId);
	}

	@Override
	public void removeAllPermissions(Integer roleId) {
		this.authRoleDAO.removeAllPermissions(roleId);
	}

}
