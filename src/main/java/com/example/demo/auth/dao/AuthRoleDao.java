/**
 * 
 */
package com.example.demo.auth.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.FetchType;

import com.example.demo.auth.entity.AuthRole;

/**
 * com.example.demo.auth.dao AuthRoleDao
 * 
 * 系统角色数据访问类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface AuthRoleDao {

	/**
	 * 查询所有角色信息
	 * 
	 * @return List
	 */
	@Select("SELECT role_id, role_name, available, created_time, update_time FROM auth_role")
	@Results({ @Result(property = "roleId", column = "role_id", id = true),
			@Result(property = "roleName", column = "role_name"), @Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time"),
			@Result(property = "permissions", column = "role_name", many = @Many(select = "com.example.demo.auth.dao.AuthPermissionDao.findAllByRoleName", fetchType = FetchType.LAZY) ) })
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
			@Result(property = "permissions", column = "role_name", many = @Many(select = "com.example.demo.auth.dao.AuthPermissionDao.findAllByRoleName", fetchType = FetchType.LAZY) ) })
	public AuthRole findById(Integer id);

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
			@Result(property = "permissions", column = "role_name", many = @Many(select = "com.example.demo.auth.dao.AuthPermissionDao.findAllByRoleName", fetchType = FetchType.LAZY) ) })
	public AuthRole findByName(String roleName);

	/**
	 * 根据用户编号查询其所拥有的角色清单
	 * 
	 * @param userName
	 * @return Set
	 */
	@Select("SELECT role_id, role_name, available, created_time, update_time FROM vw_auth_user_role WHERE user_id = #{id}")
	@Results({ @Result(property = "roleId", column = "role_id", id = true),
			@Result(property = "roleName", column = "role_name"), @Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public Set<AuthRole> findAllByUserId(Integer userId);

	/**
	 * 根据用户名称查询其所拥有的角色清单
	 * 
	 * @param userName
	 * @return Set
	 */
	@Select("SELECT role_id, role_name, available, created_time, update_time FROM vw_auth_user_role WHERE user_name = #{name}")
	@Results({ @Result(property = "roleId", column = "role_id", id = true),
			@Result(property = "roleName", column = "role_name"), @Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public Set<AuthRole> findAllByUser(String userName);
	
	/**
	 * 
	 * @param role
	 * @return
	 */
	@Insert("INSERT INTO auth_role(role_name, available, created_time) VALUES(#{roleName}, #{available}, NOW())")
	@Options(useGeneratedKeys = true, keyProperty="roleId")
	public int insert(AuthRole role);
	
	/**
	 * 
	 * @param role
	 * @return
	 */
	@Update("UPDATE auth_role SET role_name = #{roleName}, available = #{available} WHERE role_id = #{roleId}")
	public int udpate(AuthRole role);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@Delete("DELETE FROM auth_role WHERE role_id = #{id}")
	public int delete(Integer id);

}
