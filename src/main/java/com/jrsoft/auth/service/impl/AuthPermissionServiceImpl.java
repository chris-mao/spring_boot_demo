/**
 * 
 */
package com.jrsoft.auth.service.impl;

import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrsoft.auth.dao.AuthPermissionDAO;
import com.jrsoft.auth.dao.AuthRoleDAO;
import com.jrsoft.auth.dao.AuthUserDAO;
import com.jrsoft.auth.entity.AuthPermission;
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.auth.service.AuthPermissionService;
import com.jrsoft.auth.utils.EasyTreeUtils;
import com.jrsoft.common.EasyDataGrid;

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
	
	/**
	 * @see com.jrsoft.auth.dao AuthUserDAO
	 */
	@Autowired
	private AuthUserDAO authUserDAO;

	/**
	 * @see com.jrsoft.auth.dao AuthRoleDao
	 */
	@Autowired
	private AuthRoleDAO authRoleDAO;

	/**
	 * @see com.jrsoft.auth.dao AuthPermissionDAO
	 */
	@Autowired
	private AuthPermissionDAO authPermissionDAO;

	@Override
	public List<AuthPermission> findAll() {
		return authPermissionDAO.findAll(false);
	}

	@Override
	public List<AuthPermission> findAll(boolean onlyAvailable) {
		return authPermissionDAO.findAll(onlyAvailable);
	}

	@Override
	public PageInfo<AuthPermission> findAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<AuthPermission>(authPermissionDAO.findAll(false));
	}

	protected boolean hasChildren(int permissionId) {
		return authPermissionDAO.getChildrenCount(permissionId) > 0;
	}

	@Override
	public EasyDataGrid<AuthPermission> findChildNodes(int parentId, int pageIndex, int pageSize, String searchStr) {
		PageInfo<AuthPermission> pageInfo;
		if (searchStr.isEmpty()) {
			PageHelper.startPage(pageIndex, pageSize);
			pageInfo = new PageInfo<AuthPermission>(authPermissionDAO.findChildNodes(parentId));
		} else {
			AuthPermission permission = new AuthPermission();
			permission.setPermissionName("%" + searchStr + "%");
			permission.setPermissionText("%" + searchStr + "%");
			PageHelper.startPage(pageIndex, pageSize);
			pageInfo = new PageInfo<AuthPermission>(authPermissionDAO.fuzzyQuery(permission));
		}

		AuthPermission node;
		List<AuthPermission> nodes = pageInfo.getList();
		for (Iterator<AuthPermission> i = nodes.iterator(); i.hasNext();) {
			node = i.next();
			if (true == hasChildren(node.getPermissionId())) { // 有子节点
				node.setState("closed");
			} else {
				node.setState("open");
			}
		}

		EasyDataGrid<AuthPermission> dg = new EasyDataGrid<AuthPermission>();
		dg.setTotal(pageInfo.getTotal());
		dg.setRows(pageInfo.getList());
		return dg;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<AuthPermission> getPermissionTree() {
		return (List<AuthPermission>) EasyTreeUtils.buildTree(authPermissionDAO.findAll(true));
	}

	@Override
	public List<AuthPermission> findRolePermissions(AuthRole role) {
		if (0 != role.getRoleId()) {
			return authPermissionDAO.findRolePermissionsByRoleId(role.getRoleId());
		}
		if (null != role.getRoleName()) {
			AuthRole r = authRoleDAO.findByName(role.getRoleName());
			if (null == r) {
				return null;
			}
			return authPermissionDAO.findRolePermissionsByRoleId(r.getRoleId());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuthPermission> getRolePermissionTree(AuthRole role) {
		return (List<AuthPermission>) EasyTreeUtils.buildTree(findRolePermissions(role));
	}
	
	@Override
	public List<AuthPermission> findIndividualPermissions(AuthUser user) {
		if (0 != user.getUserId()) {
			return authPermissionDAO.findIndividualPermissionsByUserId(user.getUserId());
		}
		if (null != user.getUserName()) {
			AuthUser u = authUserDAO.findByName(user.getUserName());
			if (null == u) {
				return null;
			}
			return authPermissionDAO.findIndividualPermissionsByUserId(u.getUserId());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuthPermission> getIndividualPermissionTree(AuthUser user) {
		return (List<AuthPermission>) EasyTreeUtils.buildTree(findIndividualPermissions(user));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuthPermission> getMenuTreeByUser(AuthUser user) {
		return (List<AuthPermission>) EasyTreeUtils.buildTree(authPermissionDAO.getUserMenu(user.getUserId()));
	}

	@Override
	public EasyDataGrid<AuthPermission> findAll(int pageIndex, int pageSize, String searchStr) {
		return null;
	}

}
