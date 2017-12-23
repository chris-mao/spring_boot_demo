/**
 * 
 */
package com.jrsoft.organization.service;

import java.util.List;

import com.jrsoft.app.service.AbstractDbService;
import com.jrsoft.common.EasyDataGrid;
import com.jrsoft.organization.entity.Department;

/**
 * com.jrsoft.employee.service DepartmentService
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface DepartmentService extends AbstractDbService<Department> {
	
	/**
	 * 根据传入的父节点编号查询其子节点数据，且有分页功能，如果参数searchStr不为空，则查询指定节点下所有符合查询条件(
	 * department_name包含查询条件)的子节点
	 * 
	 * @since 1.3
	 * @param parentId
	 *            父节点编号
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            分页大小
	 * @param searchStr
	 *            模糊查询内容
	 * @return
	 */
	public EasyDataGrid<Department> findChildNodes(int parentId, int pageIndex, int pageSize, String searchStr);
	
	/**
	 * 根据系统中所有有效的部门构建部门树
	 * 
	 * @since 1.1
	 * @return
	 */
	public List<Department> getDepartmentTree();

}
