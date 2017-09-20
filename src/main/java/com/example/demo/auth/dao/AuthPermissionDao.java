/**
 * 
 */
package com.example.demo.auth.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import com.example.demo.auth.entity.AuthPermission;

/**
 * com.example.demo.auth.dao AuthPermissionDao
 * 
 * 系统权限数据访问类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Service
public interface AuthPermissionDao {
	
	/**
	 * 查询所有权限
	 * 
	 * @return List
	 */
	@Select("SELECT permission_id, permission_name, permission_url, available, created_time, update_time FROM auth_permission")
	@Results({
		@Result(property="permissionId", column="permission_id", id=true),
		@Result(property="permissionName", column="permission_name"),
		@Result(property="permissionUrl", column="permission_url"),
		@Result(property="available", column="available"),
		@Result(property="createdTime", column="created_time"),
		@Result(property="updateTime", column="update_time")
	})
	public List<AuthPermission> findAll();
	
	/**
	 * 按权限编号查询
	 * 
	 * @param permissionId
	 * @return AuthPermission
	 */
	@Select("SELECT permission_id, permission_name, permission_url, available, created_time, update_time FROM auth_permission WHERE permission_id = #{id}")
	@Results({
		@Result(property="permissionId", column="permission_id", id=true),
		@Result(property="permissionName", column="permission_name"),
		@Result(property="permissionUrl", column="permission_url"),
		@Result(property="available", column="available"),
		@Result(property="createdTime", column="created_time"),
		@Result(property="updateTime", column="update_time")
	})
	public AuthPermission findById(Integer permissionId);
	
	/**
	 * 按权限名称查询
	 * 
	 * @param permissionName
	 * @return AuthPermission
	 */
	@Select("SELECT permission_id, permission_name, permission_url, available, created_time, update_time FROM auth_permission WHERE permission_name = #{name}")
	@Results({
		@Result(property="permissionId", column="permission_id", id=true),
		@Result(property="permissionName", column="permission_name"),
		@Result(property="permissionUrl", column="permission_url"),
		@Result(property="available", column="available"),
		@Result(property="createdTime", column="created_time"),
		@Result(property="updateTime", column="update_time")
	})
	public AuthPermission findByName(String permissionName);
	
	/**
	 * 按角色名称查询其所拥有的权限
	 * 
	 * @param permissionName
	 * @return Set
	 */
	@Select("SELECT permission_id, permission_name, permission_url, available, created_time, update_time FROM vw_auth_permission_permission WHERE permission_name = #{name}")
	@Results({
		@Result(property="permissionId", column="permission_id", id=true),
		@Result(property="permissionName", column="permission_name"),
		@Result(property="permissionUrl", column="permission_url"),
		@Result(property="available", column="available"),
		@Result(property="createdTime", column="created_time"),
		@Result(property="updateTime", column="update_time")
	})
	public Set<AuthPermission> findAllBypermissionName(String permissionName);
	
	@Insert("INSERT INTO auth_permission(permission_name, permission_url, available, created_time) VALUES(#{permissionName}, #{permissionUrl}, #{available}, NOW())")
	@Options(useGeneratedKeys = true, keyProperty="permissionId")
	public int insert(AuthPermission permission);
	
	/**
	 * 
	 * @param permission
	 * @return
	 */
	@Update("UPDATE auth_permission SET permission_name = #{permissionName}, permission_url = #{permissionUrl}, available = #{available} WHERE permission_id = #{permissionId}")
	public int udpate(AuthPermission permission);
	
	/**
	 * 
	 * @param id
	 */
	@Delete("DELETE FROM auth_permission WHERE permission_id = #{id}")
	public void delete(Integer id);

}
