/**
 * 
 */
package com.jrsoft.auth.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import com.jrsoft.auth.dao.sqlprovider.AuthRoleDynaSqlProvider;
import com.jrsoft.auth.entity.AuthRole;

/**
 * 系统角色数据访问类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.1
 *
 */
public interface AuthRoleDAO {

	/**
	 * 查询所有角色信息
	 * 
	 * @param onlyAvailable
	 *            <code>true</code>仅查询所有可用角色，否则查询所有角色
	 * 
	 * @return List
	 */
	@SelectProvider(method = "findAllSql", type = AuthRoleDynaSqlProvider.class)
	@Results({ @Result(property = "roleId", column = "role_id", id = true),
			@Result(property = "roleName", column = "role_name"),
			@Result(property = "roleDescription", column = "role_description"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	// @Result(property = "permissions", column = "role_id", many = @Many(select
	// = "com.jrsoft.auth.dao.AuthPermissionDAO.findAllByRoleId", fetchType =
	// FetchType.LAZY) ) })
	public List<AuthRole> findAll(@Param(value = "available") boolean onlyAvailable);

	/**
	 * 按角色编号查询
	 * 
	 * @param id
	 * @return AuthRole
	 */
	@Select("SELECT role_id, role_name, role_description, available, created_time, update_time FROM auth_role WHERE role_id = #{id}")
	@Results({ @Result(property = "roleId", column = "role_id", id = true),
			@Result(property = "roleName", column = "role_name"),
			@Result(property = "roleDescription", column = "role_description"), @Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	// @Result(property = "permissions", column = "role_id", many = @Many(select
	// = "com.jrsoft.auth.dao.AuthPermissionDAO.findAllByRoleId", fetchType =
	// FetchType.LAZY) ) })
	public AuthRole findById(@Param(value = "id") int id);

	/**
	 * 按角色名称查询
	 * 
	 * @param roleName
	 * @return AuthRole
	 */
	@Select("SELECT role_id, role_name, role_description, available, created_time, update_time FROM auth_role WHERE role_name = #{name}")
	@Results({ @Result(property = "roleId", column = "role_id", id = true),
			@Result(property = "roleName", column = "role_name"),
			@Result(property = "roleDescription", column = "role_description"), @Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	// @Result(property = "permissions", column = "role_id", many = @Many(select
	// = "com.jrsoft.auth.dao.AuthPermissionDAO.findAllByRoleId", fetchType =
	// FetchType.LAZY) ) })
	public AuthRole findByName(@Param(value = "name") String roleName);
	
	/**
	 * 按角色名模糊查询
	 * 
	 * @since 1.1
	 * @param roleName
	 * @return
	 */
	@Select("SELECT role_id, role_name, role_description, available, created_time, update_time FROM auth_role WHERE role_name LIKE #{name}")
	@Results({ @Result(property = "roleId", column = "role_id", id = true),
			@Result(property = "roleName", column = "role_name"),
			@Result(property = "roleDescription", column = "role_description"), @Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<AuthRole> fuzzyQuery(@Param(value = "name") String roleName);

	/**
	 * 根据用户编号查询其所拥有的有效角色清单
	 * 
	 * @param userName
	 * @return Set
	 */
	// @SelectProvider(method = "findAllByUserIdSql", type =
	// AuthRoleDynaSqlProvider.class)
	@Select("CALL sp_findUserRoles(#{id})")
	@Results({ @Result(property = "roleId", column = "role_id", id = true),
			@Result(property = "roleName", column = "role_name"),
			@Result(property = "roleDescription", column = "role_description"), @Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	// @Result(property = "permissions", column = "role_id", many = @Many(select
	// = "com.jrsoft.auth.dao.AuthPermissionDAO.findAllByRoleId", fetchType =
	// FetchType.LAZY) ) })
	public Set<AuthRole> findAllByUserId(@Param(value = "id") int userId);

	/**
	 * 创建新角色
	 * 
	 * @param role
	 * @return 返回受影响的行数
	 */
	@Insert("INSERT INTO auth_role(role_name, role_description, available, created_time) VALUES(#{roleName}, #{roleDescription}, #{available}, NOW())")
	@Options(useGeneratedKeys = true, keyProperty = "roleId")
	public int insert(AuthRole role);

	/**
	 * 更新角色
	 * 
	 * @param role
	 * @return 返回受影响的行数
	 */
	@Update("UPDATE auth_role SET role_name = #{roleName}, role_description = #{roleDescription}, available = #{available} WHERE role_id = #{roleId}")
	public int udpate(AuthRole role);

	/**
	 * 删除角色
	 * 
	 * @param id
	 * @return 返回受影响的行数
	 */
	@Delete("DELETE FROM auth_role WHERE role_id = #{id}")
	public int delete(@Param(value = "id") int id);

	/**
	 * 添加新权限
	 * 
	 * @param roleId
	 * @param permissionId
	 * @return
	 */
	@Insert("INSERT IGNORE auth_role_permission(role_id, permission_id, available, start_date, created_time) VALUE(#{roleId}, #{permissionId}, 1, CURDATE(), NOW())")
	public int addPermission(@Param(value = "roleId") int roleId,
			@Param(value = "permissionId") int permissionId);

	/**
	 * 移除已有权限
	 * 
	 * @param roleId
	 * @param permissionId
	 * @return
	 */
	@Delete("DELETE FROM auth_role_permission WHERE role_id = #{roleId} AND permission_id = #{permissionId}")
	public int removePermission(@Param(value = "roleId") int roleId,
			@Param(value = "permissionId") int permissionId);
	
	/**
	 * 移除指定角色上所有权限
	 * 
	 * @param roleId
	 */
	@Delete("DELETE FROM auth_role_permission WHERE role_id = #{roleId}")
	public void removeAllPermissions(@Param(value = "roleId") int roleId);

}
