/**
 * 
 */
package com.jrsoft.organization.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrsoft.auth.utils.EasyTreeUtils;
import com.jrsoft.common.EasyDataGrid;
import com.jrsoft.organization.dao.DepartmentDAO;
import com.jrsoft.organization.entity.Department;
import com.jrsoft.organization.service.DepartmentService;

/**
 * com.jrsoft.employee.service.impl DepartmentServiceImpl
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

	/**
	 * 
	 */
	@Autowired
	private DepartmentDAO departmentDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jrsoft.app.service.AbstractDbService#findAll()
	 */
	@Override
	public List<Department> findAll() {
		return departmentDAO.findAll(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jrsoft.app.service.AbstractDbService#findAll(boolean)
	 */
	@Override
	public List<Department> findAll(boolean onlyAvailable) {
		return departmentDAO.findAll(onlyAvailable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jrsoft.app.service.AbstractDbService#findAll(int, int)
	 */
	@Override
	public PageInfo<Department> findAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<>(findAll());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jrsoft.app.service.AbstractDbService#findAll(int, int,
	 * java.lang.String)
	 */
	@Override
	public EasyDataGrid<Department> findAll(int pageIndex, int pageSize, String searchStr) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jrsoft.app.service.AbstractDbService#findOne(java.lang.Object)
	 */
	@Override
	public Department findOne(Department entity) {
		if (entity.getDepartmentId() != 0) {
			return departmentDAO.findById(entity.getDepartmentId());
		}
		if (null != entity.getDepartmentName()) {
			return departmentDAO.findByName(entity.getDepartmentName());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jrsoft.app.service.AbstractDbService#insert(java.lang.Object)
	 */
	@Override
	public boolean insert(Department entity) {
		return 1 == departmentDAO.insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jrsoft.app.service.AbstractDbService#update(java.lang.Object)
	 */
	@Override
	public boolean update(Department entity) {
		return 1 == departmentDAO.udpate(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jrsoft.app.service.AbstractDbService#delete(int)
	 */
	@Override
	public boolean delete(int id) {
		return 1 == departmentDAO.delete(id);
	}

	protected boolean hasChildren(int departmentId) {
		return departmentDAO.getChildrenCount(departmentId) > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jrsoft.employee.service.DepartmentService#findChildNodes(int,
	 * int, int, java.lang.String)
	 */
	@Override
	public EasyDataGrid<Department> findChildNodes(int parentId, int pageIndex, int pageSize, String searchStr) {
		PageInfo<Department> pageInfo;
		if (searchStr.isEmpty()) {
			PageHelper.startPage(pageIndex, pageSize);
			pageInfo = new PageInfo<Department>(departmentDAO.findChildNodes(parentId));
		} else {
			Department department = new Department();
			department.setDepartmentName("%" + searchStr + "%");
			PageHelper.startPage(pageIndex, pageSize);
			pageInfo = new PageInfo<Department>(departmentDAO.fuzzyQuery(department));
		}
		
		Department node;
		List<Department> nodes = pageInfo.getList();
		for (Iterator<Department> i = nodes.iterator(); i.hasNext();) {
			node = i.next();
			if (true == hasChildren(node.getDepartmentId())) { // 有子节点
				node.setState("closed");
			} else {
				node.setState("open");
			}
		}

		EasyDataGrid<Department> dg = new EasyDataGrid<Department>();
		dg.setTotal(pageInfo.getTotal());
		dg.setRows(pageInfo.getList());
		return dg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jrsoft.employee.service.DepartmentService#getDepartmentTree()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Department> getDepartmentTree() {
		return (List<Department>) EasyTreeUtils.buildTree(findAll());
	}

}
