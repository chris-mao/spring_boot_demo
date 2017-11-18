/**
 * 
 */
package com.jrsoft.auth.service;

import java.util.List;
import java.util.Set;

import com.github.pagehelper.PageInfo;
import com.jrsoft.auth.entity.AuthPermission;
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.common.EasyTreeGridNode;
import com.jrsoft.common.EasyDataGrid;

/**
 * 系统权限服务接口
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.3
 *
 */
public interface AuthPermissionService {

	/**
	 * 查询所有权限，不分页
	 * 
	 * @return List
	 */
	public List<AuthPermission> findAll();
	
	/**
	 * 查询所有权限信息，不具备分页功能
	 * 等同于findAll(false)
	 * 
	 * @param onlyAvailable 仅返回有效的权限
	 * 
	 * @since 1.1
	 * 
	 * @return List
	 */
	public List<AuthPermission> findAll(boolean onlyAvailable);

	/**
	 * 查询所有数据，具有分页功能
	 * 
	 * @since 1.0
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            分页大小
	 * @return {@link PageInfo}
	 */
	public PageInfo<AuthPermission> findAll(int pageNum, int pageSize);

	/**
	 * 根据传入的父节点编号查询其子节点数据，且有分页功能，如果参数searchStr不为空，则查询指定节点下所有符合查询条件(
	 * permission_name或是permission_text包含查询条件)的子节点
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
	public EasyDataGrid<EasyTreeGridNode> findChildNodes(int parentId, int pageIndex, int pageSize, String searchStr);

	/**
	 * 按权限编号或是名称查询
	 * 
	 * @since 1.0
	 * @param permission
	 * @return AuthPermission
	 */
	public AuthPermission findOne(AuthPermission permission);

	/**
	 * 创建新权限
	 * 
	 * @since 1.0
	 * @param permission
	 * @return 成功返回true,否则返回false
	 */
	public boolean insert(AuthPermission permission);

	/**
	 * 更新权限
	 * 
	 * @since 1.0
	 * @param permission
	 * @return 成功返回true,否则返回false
	 */
	public boolean update(AuthPermission permission);

	/**
	 * 删除权限
	 * 
	 * @since 1.0
	 * @param permission
	 * @return 成功返回true,否则返回false
	 */
	public boolean delete(int id);

	/**
	 * 按角色编号或是角色名称查询其所拥有的权限集合
	 * 
	 * @since 1.0
	 * @param role
	 * @return Set
	 */
	public Set<AuthPermission> findAllByRole(AuthRole role);

	/**
	 * 按角色编号或是角色名称获取指定节点下的权限集合
	 * 
	 * @since 1.3
	 * @param role
	 * @return Set
	 */
	public Set<EasyTreeGridNode> findChildNodesByRole(int parentId, AuthRole role);
}
