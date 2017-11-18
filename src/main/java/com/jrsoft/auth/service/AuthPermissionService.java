/**
 * 
 */
package com.jrsoft.auth.service;

import java.util.List;
import com.github.pagehelper.PageInfo;
import com.jrsoft.auth.entity.AuthPermission;
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.common.EasyTreeGridNode;
import com.jrsoft.common.EasyDataGrid;
import com.jrsoft.common.EasyTreeNode;

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
	 * 查询所有权限信息，不具备分页功能 等同于findAll(false)
	 * 
	 * @param onlyAvailable
	 *            仅返回有效的权限
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
	 * 按角色编号或是角色名称查询其所拥有的权限
	 * 
	 * @since 1.0
	 * @param role
	 * @return Set
	 */
	public List<AuthPermission> findRolePermissions(AuthRole role);
	
	/**
	 * 
	 * @param role
	 * @return
	 */
	public List<EasyTreeNode> getRolePermissionTree(AuthRole role);

	/**
	 * 按用户编号或是用户名称查询其所拥有的个人权限
	 * 
	 * @since 1.1
	 * @param user
	 * @return
	 */
	public List<AuthPermission> findIndividualPermissions(AuthUser user);
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public List<EasyTreeNode> getIndividualPermissionTree(AuthUser user);

	/**
	 * 获取用户的菜单树
	 * <p>
	 * 查询其所拥有的角色权限及个人权限中查找所有<code>permission_kind</code>是<code>menu</code>的权限，并返回其树型数据结构
	 * </p>
	 * 
	 * @since 1.3
	 * @param user
	 * @return
	 */
	public List<EasyTreeNode> getMenuTreeByUser(AuthUser user);
}
