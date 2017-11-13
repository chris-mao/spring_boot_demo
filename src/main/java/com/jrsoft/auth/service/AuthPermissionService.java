/**
 * 
 */
package com.jrsoft.auth.service;

import java.util.List;
import java.util.Set;

import com.github.pagehelper.PageInfo;
import com.jrsoft.auth.entity.AuthPermission;
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.common.DataGrid;

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
	 * 根据传入的查询条件查询数据，具有分页功能 如果参数searchStr为空，则查询所有角色数据，否则查询<code>roleName</code>
	 * 中含有其内容的角色数据
	 * 
	 * @since 1.2
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            分页大小
	 * @param searchStr
	 *            模糊查询内容
	 * @return
	 */
	public DataGrid<AuthPermission> findAll(int pageIndex, int pageSize, String searchStr);

	/**
	 * 查询所有有效的权限信息
	 * 
	 * @since 1.1
	 * 
	 * @return List
	 */
	public List<AuthPermission> findAllAvailable();

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
	 * 按角色编号或是角色名称查询其所拥有的权限
	 * 
	 * @since 1.0
	 * @param role
	 * @return Set
	 */
	public Set<AuthPermission> findAllByRole(AuthRole role);

	/**
	 * 按角色编号或是角色名称获取其所拥有的权限（树型结构）
	 * 
	 * @since 1.3
	 * @param role
	 * @return Set
	 */
	public Set<AuthPermission> findPermissionTreeByRole(AuthRole role);

	/**
	 * 按父结点编号获取其子权限（树型结构）
	 * 
	 * @since 1.3
	 * @param parentId
	 * @return Set
	 */
	public Set<AuthPermission> findPermissionTreeByParent(int parentId);
}
