/**
 * 
 */
package com.jrsoft.auth.dao;

import java.util.List;

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

import com.jrsoft.auth.AuthUserStateEnum;
import com.jrsoft.auth.dao.handler.AuthUserStateEnumTypeHandler;
import com.jrsoft.auth.dao.sqlprovider.AuthUserDynaSqlProvider;
import com.jrsoft.auth.entity.AuthUser;

/**
 * com.jrsoft.auth.map AuthUserInterface
 * 
 * 系统用户数据访问类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Repository
public interface AuthUserDAO {

	/**
	 * 查询所有用户信息
	 * 
	 * @param onlyAvailable
	 *            true仅查询所有可用用户，否则查询所有用户
	 * 
	 * @return List
	 */
	@SelectProvider(method = "findAllSql", type = AuthUserDynaSqlProvider.class)
	@Results({ @Result(property = "userId", column = "user_id", id = true),
			@Result(property = "userName", column = "user_name"), @Result(property = "nickName", column = "nick_name"),
			@Result(property = "email", column = "email"), @Result(property = "password", column = "user_psd"),
			@Result(property = "salt", column = "salt"),
			@Result(property = "state", column = "state", javaType = AuthUserStateEnum.class, typeHandler = AuthUserStateEnumTypeHandler.class),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<AuthUser> findAll(@Param(value = "available") boolean onlyAvailable);

	/**
	 * 根据用户编号查询用户信息
	 * 
	 * @param id
	 * @return AuthUser
	 */
	@Select("SELECT user_id, user_name, nick_name, email, user_psd, salt, state, available, created_time, update_time FROM auth_user WHERE user_id = #{id}")
	@Results({ @Result(property = "userId", column = "user_id", id = true),
			@Result(property = "userName", column = "user_name"), @Result(property = "nickName", column = "nick_name"),
			@Result(property = "email", column = "email"), @Result(property = "password", column = "user_psd"),
			@Result(property = "salt", column = "salt"),
			@Result(property = "state", column = "state", javaType = AuthUserStateEnum.class, typeHandler = AuthUserStateEnumTypeHandler.class),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public AuthUser findById(@Param(value = "id") int id);

	/**
	 * 根据用户名称查询用户信息
	 * 
	 * @param userName
	 * @return AuthUser
	 */
	@Select("SELECT user_id, user_name, nick_name, email, user_psd, salt, state, available, created_time, update_time FROM auth_user WHERE user_name = #{name}")
	@Results({ @Result(property = "userId", column = "user_id", id = true),
			@Result(property = "userName", column = "user_name"), @Result(property = "nickName", column = "nick_name"),
			@Result(property = "email", column = "email"), @Result(property = "password", column = "user_psd"),
			@Result(property = "salt", column = "salt"),
			@Result(property = "state", column = "state", javaType = AuthUserStateEnum.class, typeHandler = AuthUserStateEnumTypeHandler.class),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public AuthUser findByName(@Param(value = "name") String userName);

	@SelectProvider(method = "findSpecificSql", type = AuthUserDynaSqlProvider.class)
	@Results({ @Result(property = "userId", column = "user_id", id = true),
			@Result(property = "userName", column = "user_name"), @Result(property = "nickName", column = "nick_name"),
			@Result(property = "email", column = "email"), @Result(property = "password", column = "user_psd"),
			@Result(property = "salt", column = "salt"),
			@Result(property = "state", column = "state", javaType = AuthUserStateEnum.class, typeHandler = AuthUserStateEnumTypeHandler.class),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<AuthUser> findSpecific(@Param(value = "userName") String userName,
			@Param(value = "nickName") String nickName);

	/**
	 * 创建新用户
	 * 
	 * @param user
	 * @return 返回受影响的行数
	 */
	@Insert("INSERT INTO auth_user(user_name, nick_name, email, user_psd, salt, state, available, created_time) VALUES(#{userName}, #{nickName}, #{email}, MD5(#{password}), #{salt}, #{state}, 1, NOW())")
	@Options(useGeneratedKeys = true, keyProperty = "userId")
	public int insert(AuthUser user);

	/**
	 * 更新用户信息 不会修改密码和加密盐值 如果需要修改密码请使用 changePassword
	 * 
	 * @param user
	 * @return 返回受影响的行数
	 */
	@Update("UPDATE auth_user SET nick_name = #{nickName}, available = #{available}, state= #{state}, email = #{email} WHERE user_id = #{userId}")
	public int udpate(AuthUser user);

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return 返回受影响的行数
	 */
	@Delete("DELETE FROM auth_user WHERE user_id = #{id}")
	public int delete(@Param(value = "id") int id);

	/**
	 * 修改登录密码
	 * 
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 * @return boolean 更新成功返回true，否则返回false
	 */
	@Update("UPDATE auth_user SET user_psd = MD5(#{newPassword}) WHERE user_id = #{id} AND user_psd = MD5(#{oldPassword})")
	public int changePassword(@Param(value = "id") int id, @Param(value = "oldPassword") String oldPassword,
			@Param(value = "newPassword") String newPassword);

	/**
	 * 添加新角色
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	@Insert("INSERT IGNORE auth_user_role(user_id, role_id, available, start_date, created_time) VALUE(#{userId}, #{roleId}, 1, CURDATE(), NOW())")
	public int addRole(@Param(value = "userId") int userId, @Param(value = "roleId") int roleId);

	/**
	 * 移除已关联角色
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	@Delete("DELETE FROM auth_user_role WHERE user_id = #{userId} AND role_id = #{roleId}")
	public int removeRole(@Param(value = "userId") int userId, @Param(value = "roleId") int roleId);

	/**
	 * 移除指定用户的所有角色
	 * 
	 * @param userId
	 */
	@Delete("DELETE FROM auth_user_role WHERE user_id = #{userId}")
	public void removeAllRoles(@Param(value = "userId") int userId);
}
