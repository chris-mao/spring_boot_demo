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
import org.springframework.stereotype.Repository;

import com.jrsoft.auth.dao.sqlprovider.AuthPermissionDynaSqlProvider;
import com.jrsoft.auth.entity.AuthPermission;

/**
 * com.jrsoft.auth.dao AuthPermissionDao
 * 
 * 系统权限数据访问类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Repository
public interface AuthPermissionDAO {

	/**
	 * 查询所有权限
	 * 
	 * @param onlyAvailable
	 *            true仅查询所有可用权限，否则查询所有权限
	 * 
	 * @return List
	 */
	@SelectProvider(method = "findAllSql", type = AuthPermissionDynaSqlProvider.class)
	@Results({ @Result(property = "permissionId", column = "permission_id", id = true),
			@Result(property = "permissionName", column = "permission_name"),
			@Result(property = "permissionUrl", column = "permission_url"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<AuthPermission> findAll(@Param(value = "available") boolean onlyAvailable);

	/**
	 * 按权限编号查询
	 * 
	 * @param permissionId
	 * @return AuthPermission
	 */
	@Select("SELECT permission_id, permission_name, permission_url, available, created_time, update_time FROM auth_permission WHERE permission_id = #{id}")
	@Results({ @Result(property = "permissionId", column = "permission_id", id = true),
			@Result(property = "permissionName", column = "permission_name"),
			@Result(property = "permissionUrl", column = "permission_url"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public AuthPermission findById(@Param(value = "id") int id);

	/**
	 * 按权限名称查询
	 * 
	 * @param permissionName
	 * @return AuthPermission
	 */
	@Select("SELECT permission_id, permission_name, permission_url, available, created_time, update_time FROM auth_permission WHERE permission_name = #{name}")
	@Results({ @Result(property = "permissionId", column = "permission_id", id = true),
			@Result(property = "permissionName", column = "permission_name"),
			@Result(property = "permissionUrl", column = "permission_url"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public AuthPermission findByName(@Param(value = "name") String permissionName);

	/**
	 * 按角色ID查询其所拥有的权限
	 * 
	 * @param roleId
	 * @return
	 */
	@Select("CALL sp_findRolePermissions(#{roleId})")
	@Results({ @Result(property = "permissionId", column = "permission_id", id = true),
			@Result(property = "permissionName", column = "permission_name"),
			@Result(property = "permissionUrl", column = "permission_url"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public Set<AuthPermission> findAllByRoleId(@Param(value = "roleId") int roleId);

	/**
	 * 创建新权限
	 * 
	 * @param permission
	 * @return 受影响的行数
	 */
	@Insert("INSERT INTO auth_permission(permission_name, permission_url, available, created_time) VALUES(#{permissionName}, #{permissionUrl}, #{available}, NOW())")
	@Options(useGeneratedKeys = true, keyProperty = "permissionId")
	public int insert(AuthPermission permission);

	/**
	 * 更新权限
	 * 
	 * @param permission
	 * @return 受影响的行数
	 */
	@Update("UPDATE auth_permission SET permission_name = #{permissionName}, permission_url = #{permissionUrl}, available = #{available} WHERE permission_id = #{permissionId}")
	public int udpate(AuthPermission permission);

	/**
	 * 删除权限
	 * 
	 * @param id
	 * @return 受影响的行数
	 */
	@Delete("DELETE FROM auth_permission WHERE permission_id = #{id}")
	public int delete(@Param(value = "id") int id);
}
