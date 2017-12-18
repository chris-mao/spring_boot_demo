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
import com.jrsoft.auth.AuthPermissionKindEnum;
import com.jrsoft.auth.dao.handler.AuthPermissionKindEnumTypeHandler;
import com.jrsoft.auth.dao.sqlprovider.AuthPermissionDynaSqlProvider;
import com.jrsoft.auth.entity.AuthPermission;

/**
 * 系统权限数据访问类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.1
 *
 */
public interface AuthPermissionDAO {

	/**
	 * 查询所有权限
	 * 
	 * @param onlyAvailable
	 *            <code>true</code>仅查询所有可用权限，否则查询所有权限
	 * 
	 * @return List
	 */
	@SelectProvider(method = "findAllSql", type = AuthPermissionDynaSqlProvider.class)
	@Results({ @Result(property = "permissionId", column = "permission_id", id = true),
			@Result(property = "permissionName", column = "permission_name"),
			@Result(property = "permissionUrl", column = "permission_url"),
			@Result(property = "permissionText", column = "permission_text"),
			@Result(property = "permissionKind", column = "permission_kind", javaType = AuthPermissionKindEnum.class, typeHandler = AuthPermissionKindEnumTypeHandler.class),
			@Result(property = "weight", column = "weight"), @Result(property = "parentId", column = "parent_id"),
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
	@Select("SELECT permission_id, permission_name, permission_url, permission_kind, permission_text, weight, parent_id, available, created_time, update_time FROM auth_permission WHERE permission_id = #{id}")
	@Results({ @Result(property = "permissionId", column = "permission_id", id = true),
			@Result(property = "permissionName", column = "permission_name"),
			@Result(property = "permissionUrl", column = "permission_url"),
			@Result(property = "permissionText", column = "permission_text"),
			@Result(property = "permissionKind", column = "permission_kind", javaType = AuthPermissionKindEnum.class, typeHandler = AuthPermissionKindEnumTypeHandler.class),
			@Result(property = "weight", column = "weight"), @Result(property = "parentId", column = "parent_id"),
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
	@Select("SELECT permission_id, permission_name, permission_url, permission_kind, permission_text, weight, parent_id, available, created_time, update_time FROM auth_permission WHERE permission_name = #{name}")
	@Results({ @Result(property = "permissionId", column = "permission_id", id = true),
			@Result(property = "permissionName", column = "permission_name"),
			@Result(property = "permissionUrl", column = "permission_url"),
			@Result(property = "permissionText", column = "permission_text"),
			@Result(property = "permissionKind", column = "permission_kind", javaType = AuthPermissionKindEnum.class, typeHandler = AuthPermissionKindEnumTypeHandler.class),
			@Result(property = "weight", column = "weight"), @Result(property = "parentId", column = "parent_id"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public AuthPermission findByName(@Param(value = "name") String permissionName);

	/**
	 * 按权限名或是显示文本模糊查询
	 * 
	 * @since 1.1
	 * @param permission
	 * @return
	 */
	@Select("SELECT permission_id, permission_name, permission_url, permission_kind, permission_text, weight, parent_id, available, created_time, update_time FROM auth_permission WHERE permission_name LIKE #{permissionName} OR permission_text like #{permissionText} ORDER BY permission_kind, weight")
	@Results({ @Result(property = "permissionId", column = "permission_id", id = true),
			@Result(property = "permissionName", column = "permission_name"),
			@Result(property = "permissionUrl", column = "permission_url"),
			@Result(property = "permissionText", column = "permission_text"),
			@Result(property = "permissionKind", column = "permission_kind", javaType = AuthPermissionKindEnum.class, typeHandler = AuthPermissionKindEnumTypeHandler.class),
			@Result(property = "weight", column = "weight"), @Result(property = "parentId", column = "parent_id"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<AuthPermission> fuzzyQuery(AuthPermission permission);

	/**
	 * 按父节点编号查询子权限
	 * 
	 * @since 1.1
	 * @param parentId
	 * @return
	 */
	@Select("SELECT permission_id, permission_name, permission_url, permission_kind, permission_text, weight, parent_id, available, created_time, update_time FROM auth_permission WHERE parent_id = #{parentId} ORDER BY weight")
	@Results({ @Result(property = "permissionId", column = "permission_id", id = true),
			@Result(property = "permissionName", column = "permission_name"),
			@Result(property = "permissionUrl", column = "permission_url"),
			@Result(property = "permissionText", column = "permission_text"),
			@Result(property = "permissionKind", column = "permission_kind", javaType = AuthPermissionKindEnum.class, typeHandler = AuthPermissionKindEnumTypeHandler.class),
			@Result(property = "weight", column = "weight"), @Result(property = "parentId", column = "parent_id"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<AuthPermission> findChildNodes(@Param(value = "parentId") int parentId);

	/**
	 * @since 1.1
	 * @param parentId
	 * @return
	 */
	@Select("SELECT COUNT(permission_id) FROM auth_permission WHERE parent_id = #{parentId}")
	public int getChildrenCount(@Param(value = "parentId") int parentId);

	/**
	 * 按角色编号查询其所拥有的权限
	 * 
	 * @since 1.0
	 * @param roleId
	 * @return
	 */
	@Select("CALL sp_findRolePermissions(#{roleId})")
	@Results({ @Result(property = "permissionId", column = "permission_id", id = true),
			@Result(property = "permissionName", column = "permission_name"),
			@Result(property = "permissionUrl", column = "permission_url"),
			@Result(property = "permissionText", column = "permission_text"),
			@Result(property = "permissionKind", column = "permission_kind", javaType = AuthPermissionKindEnum.class, typeHandler = AuthPermissionKindEnumTypeHandler.class),
			@Result(property = "weight", column = "weight"), @Result(property = "parentId", column = "parent_id"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<AuthPermission> findRolePermissionsByRoleId(@Param(value = "roleId") int roleId);
	
	
	/**
	 * 按用户编号查询其所拥有的个人权限
	 * 
	 * @since 1.1
	 * @param userId
	 * @return
	 */
	@Select("SELECT permission_id, permission_name, permission_url, permission_kind, permission_text, weight, parent_id, available, created_time, update_time FROM vw_auth_user_permission WHERE user_id = #{userId}")
	@Results({ @Result(property = "permissionId", column = "permission_id", id = true),
		@Result(property = "permissionName", column = "permission_name"),
		@Result(property = "permissionUrl", column = "permission_url"),
		@Result(property = "permissionText", column = "permission_text"),
		@Result(property = "permissionKind", column = "permission_kind", javaType = AuthPermissionKindEnum.class, typeHandler = AuthPermissionKindEnumTypeHandler.class),
		@Result(property = "weight", column = "weight"), @Result(property = "parentId", column = "parent_id"),
		@Result(property = "available", column = "available"),
		@Result(property = "createdTime", column = "created_time"),
		@Result(property = "updateTime", column = "update_time") })
public List<AuthPermission> findIndividualPermissionsByUserId(@Param(value = "userId") int userId);
	
	/**
	 * 按用户编号查询其所拥有的角色权限及个人权限
	 * 
	 * @since 1.1
	 * @param userId
	 * @return
	 */
	@Select("CALL sp_findUserPermissions(#{userId})")
	@Results({ @Result(property = "permissionId", column = "permission_id", id = true),
			@Result(property = "permissionName", column = "permission_name"),
			@Result(property = "permissionUrl", column = "permission_url"),
			@Result(property = "permissionText", column = "permission_text"),
			@Result(property = "permissionKind", column = "permission_kind", javaType = AuthPermissionKindEnum.class, typeHandler = AuthPermissionKindEnumTypeHandler.class),
			@Result(property = "weight", column = "weight"), @Result(property = "parentId", column = "parent_id"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public Set<AuthPermission> findAllByUserId1(@Param(value = "userId") int userId);

	/**
	 * 按用户编号查询其所拥有的角色权限及个人权限中查找所有<code>permission_kind</code>是<code>menu</code>的权限
	 * 
	 * @since 1.1
	 * @param userId
	 * @return
	 */
	@Select("CALL sp_getUserMenu(#{userId})")
	@Results({ @Result(property = "permissionId", column = "permission_id", id = true),
			@Result(property = "permissionName", column = "permission_name"),
			@Result(property = "permissionUrl", column = "permission_url"),
			@Result(property = "permissionText", column = "permission_text"),
			@Result(property = "permissionKind", column = "permission_kind", javaType = AuthPermissionKindEnum.class, typeHandler = AuthPermissionKindEnumTypeHandler.class),
			@Result(property = "weight", column = "weight"), @Result(property = "parentId", column = "parent_id"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<AuthPermission> getUserMenu(@Param(value = "userId") int userId);

	/**
	 * 创建新权限
	 * 
	 * @param permission
	 * @return 受影响的行数
	 */
	@Insert("INSERT INTO auth_permission(permission_name, permission_url, permission_text, permission_kind, weight, parent_id, available, created_time) VALUES(#{permissionName}, #{permissionUrl}, #{permissionText}, #{permissionKind}, #{weight}, #{parentId}, #{available}, NOW())")
	@Options(useGeneratedKeys = true, keyProperty = "permissionId")
	public int insert(AuthPermission permission);

	/**
	 * 更新权限
	 * 
	 * @param permission
	 * @return 受影响的行数
	 */
	@Update("UPDATE auth_permission SET permission_name = #{permissionName}, permission_url = #{permissionUrl}, permission_text = #{permissionText}, permission_kind = #{permissionKind}, weight = #{weight}, parent_id = #{parentId}, available = #{available} WHERE permission_id = #{permissionId}")
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
