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
import com.jrsoft.auth.dao.AuthPermissionDAO;
import com.jrsoft.auth.dao.AuthRoleDAO;
import com.jrsoft.auth.entity.AuthPermission;
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.auth.service.AuthPermissionService;
import com.jrsoft.common.DataGrid;

/**
 * 系统权限服务接口实现类
 * 
 * @see AuthPermissionService
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Service
public class AuthPermissionServiceImpl implements AuthPermissionService {

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
	public PageInfo<AuthPermission> findAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<AuthPermission>(authPermissionDAO.findAll(false));
	}

	public DataGrid<AuthPermission> findAll(int pageIndex, int pageSize, String searchStr) {
		PageInfo<AuthPermission> pageInfo;
		if ("" == searchStr) {
			pageInfo = this.findAll(pageIndex, pageSize);
		} else {
			AuthPermission permission = new AuthPermission();
			permission.setPermissionName("%" + searchStr + "%");
			permission.setPermissionText("%" + searchStr + "%");
			PageHelper.startPage(pageIndex, pageSize);
			pageInfo = new PageInfo<AuthPermission>(authPermissionDAO.fuzzyQuery(permission));
		}

		DataGrid<AuthPermission> dg = new DataGrid<AuthPermission>();
		dg.setTotal(pageInfo.getTotal());
		dg.setRows(pageInfo.getList());
		return dg;
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
	public Set<AuthPermission> findPermissionTreeByRole(AuthRole role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<AuthPermission> findPermissionTreeByParent(int parentId) {
		return this.authPermissionDAO.findPermissionTreeByParent(parentId);
	}

}
