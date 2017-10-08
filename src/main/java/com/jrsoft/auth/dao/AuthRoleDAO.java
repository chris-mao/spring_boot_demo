/**
 * 
 */
package com.jrsoft.auth.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.FetchType;

import com.jrsoft.auth.entity.AuthRole;

/**
 * com.jrsoft.auth.dao AuthRoleDao
 * 
 * 系统角色数据访问类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface AuthRoleDAO {

	/**
	 * 查询所有角色信息
	 * 
	 * @return List
	 */
	@Select("SELECT role_id, role_name, available, created_time, update_time FROM auth_role ORDER BY role_name")
	@Results({ @Result(property = "roleId", column = "role_id", id = true),
			@Result(property = "roleName", column = "role_name"), @Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time"),
			@Result(property = "permissions", column = "role_id", many = @Many(select = "com.jrsoft.auth.dao.AuthPermissionDAO.findAllByRoleId", fetchType = FetchType.LAZY) ) })
	public List<AuthRole> findAll();

	/**
	 * 按角色编号查询
	 * 
	 * @param id
	 * @return AuthRole
	 */
	@Select("SELECT role_id, role_name, available, created_time, update_time FROM auth_role WHERE role_id = #{id}")
	@Results({ @Result(property = "roleId", column = "role_id", id = true),
			@Result(property = "roleName", column = "role_name"), @Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time"),
			@Result(property = "permissions", column = "role_id", many = @Many(select = "com.jrsoft.auth.dao.AuthPermissionDAO.findAllByRoleId", fetchType = FetchType.LAZY) ) })
	public AuthRole findById(@Param(value = "id")Integer id);

	/**
	 * 按角色名称查询
	 * 
	 * @param roleName
	 * @return AuthRole
	 */
	@Select("SELECT role_id, role_name, available, created_time, update_time FROM auth_role WHERE role_name = #{name}")
	@Results({ @Result(property = "roleId", column = "role_id", id = true),
			@Result(property = "roleName", column = "role_name"), @Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time"),
			@Result(property = "permissions", column = "role_id", many = @Many(select = "com.jrsoft.auth.dao.AuthPermissionDAO.findAllByRoleId", fetchType = FetchType.LAZY) ) })
	public AuthRole findByName(@Param(value = "name")String roleName);

	/**
	 * 根据用户编号查询其所拥有的有效角色清单
	 * 
	 * @param userName
	 * @return Set
	 */
	@Select("CALL sp_findUserRoles(#{id})")
//	@Select("SELECT role_id, role_name, available, created_time, update_time FROM vw_auth_user_role WHERE user_id = #{id}")
	@Results({ @Result(property = "roleId", column = "role_id", id = true),
			@Result(property = "roleName", column = "role_name"), @Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time"),
			@Result(property = "permissions", column = "role_id", many = @Many(select = "com.jrsoft.auth.dao.AuthPermissionDAO.findAllByRoleId", fetchType = FetchType.LAZY) ) })
	public Set<AuthRole> findAllByUserId(@Param(value = "id")Integer userId);
	
	/**
	 * 创建新角色
	 * 
	 * @param role
	 * @return 返回受影响的行数
	 */
	@Insert("INSERT INTO auth_role(role_name, available, created_time) VALUES(#{roleName}, #{available}, NOW())")
	@Options(useGeneratedKeys = true, keyProperty="roleId")
	public int insert(AuthRole role);
	
	/**
	 * 更新角色
	 * 
	 * @param role
	 * @return 返回受影响的行数
	 */
	@Update("UPDATE auth_role SET role_name = #{roleName}, available = #{available} WHERE role_id = #{roleId}")
	public int udpate(AuthRole role);
	
	/**
	 * 删除角色
	 * 
	 * @param id
	 * @return 返回受影响的行数
	 */
	@Delete("DELETE FROM auth_role WHERE role_id = #{id}")
	public int delete(@Param(value = "id")Integer id);
	
	/**
	 * 添加新权限
	 * 
	 * @param roleId
	 * @param permissionId
	 * @return
	 */
	@Insert("INSERT IGNORE auth_role_permission(role_id, permission_id, available, start_date) VALUE(#{roleId}, #{permissionId}, 1, NOW())")
	public int addPermission(@Param(value = "roleId")Integer roleId, @Param(value = "permissionId")Integer permissionId);
	
	/**
	 * 移除已有权限
	 * 
	 * @param roleId
	 * @param permissionId
	 * @return
	 */
	@Delete("DELETE FROM auth_role_permission WHERE role_id = #{roleId} AND permission_id = #{permissionId}")
	public int removePermission(@Param(value = "roleId")Integer roleId, @Param(value = "permissionId")Integer permissionId);

}
