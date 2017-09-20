/**
 * 
 */
package com.example.demo.auth.dao;

import java.util.List;

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

import com.example.demo.auth.AuthUserState;
import com.example.demo.auth.dao.handler.AuthUserStateTypeHandler;
import com.example.demo.auth.entity.AuthUser;

/**
 * com.example.demo.auth.map AuthUserInterface
 * 
 * 系统用户数据访问类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface AuthUserDao {

	/**
	 * 查询所有用户信息
	 * 
	 * @return List
	 */
	@Select("SELECT user_id, user_name, nick_name, user_psd, salt, state, created_time, update_time FROM auth_user")
	@Results({ @Result(property = "userId", column = "user_id", id = true),
			@Result(property = "userName", column = "user_name"), @Result(property = "nickName", column = "nick_name"),
			@Result(property = "password", column = "user_psd"), @Result(property = "salt", column = "salt"),
			@Result(property = "state", column = "state", javaType = AuthUserState.class, typeHandler = AuthUserStateTypeHandler.class),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time"),
			@Result(property = "roles", column = "user_id", many = @Many(select = "com.example.demo.auth.dao.AuthRoleDao.findAllByUserId", fetchType = FetchType.LAZY) ) })
	public List<AuthUser> findAll();

	/**
	 * 根据用户编号查询用户信息
	 * 
	 * @param id
	 * @return AuthUser
	 */
	@Select("SELECT user_id, user_name, nick_name, user_psd, salt, state, created_time, update_time FROM auth_user WHERE user_id = #{id}")
	@Results({ @Result(property = "userId", column = "user_id", id = true),
			@Result(property = "userName", column = "user_name"), @Result(property = "nickName", column = "nick_name"),
			@Result(property = "password", column = "user_psd"), @Result(property = "salt", column = "salt"),
			@Result(property = "state", column = "state", javaType = AuthUserState.class, typeHandler = AuthUserStateTypeHandler.class),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time"),
			@Result(property = "roles", column = "user_id", many = @Many(select = "com.example.demo.auth.dao.AuthRoleDao.findAllByUserId", fetchType = FetchType.LAZY) ) })
	public AuthUser findById(@Param(value = "id")Integer id);

	/**
	 * 根据用户名称查询用户信息
	 * 
	 * @param userName
	 * @return AuthUser
	 */
	@Select("SELECT user_id, user_name, nick_name, user_psd, salt, state, created_time, update_time FROM auth_user WHERE user_name = #{name}")
	@Results({ @Result(property = "userId", column = "user_id", id = true),
			@Result(property = "userName", column = "user_name"), @Result(property = "nickName", column = "nick_name"),
			@Result(property = "password", column = "user_psd"), @Result(property = "salt", column = "salt"),
			@Result(property = "state", column = "state", javaType = AuthUserState.class, typeHandler = AuthUserStateTypeHandler.class),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time"),
			@Result(property = "roles", column = "user_id", many = @Many(select = "com.example.demo.auth.dao.AuthRoleDao.findAllByUserId", fetchType = FetchType.LAZY) ) })
	public AuthUser findByName(@Param(value = "name")String userName);

	/**
	 * 创建新用户
	 * 
	 * @param user
	 * @return 返回受影响的行数
	 */
	@Insert("INSERT INTO auth_user(user_name, nick_name, user_psd, salt, state, available, created_time) VALUES(#{userName}, #{nickName}, #{password}, #{salt}, #{state}, 1, NOW())")
	@Options(useGeneratedKeys = true, keyProperty="userId")
	public int insert(AuthUser user);

	/**
	 * 更新用户信息，不会修改密码和加密盐值
	 * 如果需要修改密码请使用 changePassword
	 * 
	 * @param user
	 * @return 返回受影响的行数
	 */
	@Update("UPDATE auth_user SET user_name = #{userName}, nick_name = #{nickName}, state = #{state} WHERE user_id = #{userId}")
	public int udpate(AuthUser user);

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return 返回受影响的行数
	 */
	@Delete("DELETE FROM auth_user WHERE user_id = #{id}")
	public int delete(@Param(value = "id")Integer id);
	
	/**
	 * 修改登录密码
	 * 
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 * @return boolean 更新成功返回true，否则返回false
	 */
	@Update("UPDATE auth_user SET user_psd = MD5(#{newPassword}) WHERE user_id = #{id} AND user_psd = MD5(#{oldPassword})")
	public int changePassword(@Param(value = "id")Integer id, @Param(value = "oldPassword")String oldPassword, @Param(value = "newPassword")String newPassword);
		
	/**
	 * 更新用户状态
	 * 
	 * @see AuthUserState
	 * 
	 * @param id
	 * @param state
	 * @return boolean 更新成功返回true，否则返回false
	 */
	@Update("UPDATE auth_user SET state = #{state} WHERE user_id = #{userId}")
	public int changeState(@Param(value = "userId")Integer id, @Param(value = "state")AuthUserState state);
	
	/**
	 * 添加新角色
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	@Insert("INSERT IGNORE auth_user_role(user_id, role_id, available, start_date) VALUE(#{userId}, #{roleId}, 1, NOW())")
	public int addRole(@Param(value = "userId")Integer userId, @Param(value = "roleId")Integer roleId);
	
	/**
	 * 移除已关联角色
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	@Delete("DELETE FROM auth_user_role WHERE user_id = #{userId} AND role_id = #{roleId}")
	public int removeRole(@Param(value = "userId")Integer userId, @Param(value = "roleId")Integer roleId);
}
