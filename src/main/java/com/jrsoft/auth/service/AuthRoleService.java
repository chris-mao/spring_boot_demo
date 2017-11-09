/**
 * 
 */
package com.jrsoft.auth.service;

import java.util.List;
import java.util.Set;

import com.github.pagehelper.PageInfo;
import com.jrsoft.auth.entity.AuthPermission;
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.auth.entity.AuthUser;

/**
 * 系统角色服务接口
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface AuthRoleService {

	/**
	 * 系统预定义角色名称
	 */
	public static final String ADMINISTRAOR = "系统管理员";

	/**
	 * 系统预定义角色名称
	 */
	public static final String CUSTOMER = "销售客户";

	/**
	 * 系统预定义角色名称
	 */
	public static final String CUSTOMER_SERVICE_REPRESENTATIVE = "客服代表";

	/**
	 * 系统预定义角色名称
	 */
	public static final String SALES_REPRESENTATIVE = "销售代表";

	/**
	 * 查询所有角色信息，不分页
	 * 
	 * @return List
	 */
	public List<AuthRole> findAll();

	/**
	 * 查询所有数据，具有分页功能
	 * 
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            分页大小
	 * @return {@link PageInfo}
	 * @since 1.0
	 */
	public PageInfo<AuthRole> findAll(int pageNum, int pageSize);

	/**
	 * 根据传入的查询条件查询数据，肯有分页功能 如果参数searchStr为空，则查询所有角色数据，否则查询<code>roleName</code>
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
	public PageInfo<AuthRole> findAll(int pageIndex, int pageSize, String searchStr);

	/**
	 * 查询所有有效的角色信息
	 * 
	 * @since 1.1
	 * @return List
	 */
	public List<AuthRole> findAllAvailable();

	/**
	 * 按角色编号或是名称查询
	 * 
	 * @param role
	 * @return AuthRole
	 */
	public AuthRole findOne(AuthRole role);

	/**
	 * 根据用户查询其所拥有的有效角色清单
	 * 
	 * @param user
	 * @return Set
	 */
	public Set<AuthRole> findAllByUser(AuthUser user);

	/**
	 * 创建新角色
	 * 
	 * @param role
	 * @return 成功返回true,否则返回false
	 */
	public boolean insert(AuthRole role);

	/**
	 * 更新角色
	 * 
	 * @param role
	 * @return 成功返回true,否则返回false
	 */
	public boolean update(AuthRole role);

	/**
	 * 删除角色
	 * 
	 * @param id
	 * @return 成功返回true,否则返回false
	 */
	public boolean delete(int id);

	/**
	 * 添加新权限
	 * 
	 * @param role
	 * @param permission
	 * @return 成功返回true,否则返回false
	 */
	public boolean addPermission(AuthRole role, AuthPermission permission);

	/**
	 * 移除权限
	 * 
	 * @param role
	 * @param permission
	 * @return 成功返回true,否则返回false
	 */
	public boolean removePermission(AuthRole role, AuthPermission permission);

	/**
	 * 移除指定角色上所有权限
	 * 
	 * @param role
	 */
	public void removeAllPermissions(AuthRole role);
}
