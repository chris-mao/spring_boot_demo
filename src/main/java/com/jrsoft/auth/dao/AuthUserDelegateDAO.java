/**
 * 
 */
package com.jrsoft.auth.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import com.jrsoft.auth.entity.AuthUserDelegate;

/**
 * 身份代理数据访问类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface AuthUserDelegateDAO {

	/**
	 * 查询受托人／代理人（我把身份委托给了谁）
	 * 
	 * @param fromUserId
	 * @return List
	 */
	@Select("CALL sp_findAllDelegates(#{fromUserId})")
	@Results({
			@Result(property = "fromUser", column = "from_user_id", one = @One(select = "com.jrsoft.auth.dao.AuthUserDAO.findById", fetchType = FetchType.EAGER) ),
			@Result(property = "toUser", column = "to_user_id", one = @One(select = "com.jrsoft.auth.dao.AuthUserDAO.findById", fetchType = FetchType.EAGER) ),
			@Result(property = "startDate", column = "start_date"), @Result(property = "endDate", column = "end_date"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<AuthUserDelegate> findAllByFromUser(@Param(value = "fromUserId") Integer fromUserId);

	/**
	 * 查询委托人（谁把身份委托给了我）
	 * 
	 * @param toUserId
	 * @return
	 */
	@Select("CALL sp_findAllClients(${toUserId})")
	@Results({
			@Result(property = "fromUser", column = "from_user_id", one = @One(select = "com.jrsoft.auth.dao.AuthUserDAO.findById", fetchType = FetchType.EAGER) ),
			@Result(property = "toUser", column = "to_user_id", one = @One(select = "com.jrsoft.auth.dao.AuthUserDAO.findById", fetchType = FetchType.EAGER) ),
			@Result(property = "startDate", column = "start_date"), @Result(property = "endDate", column = "end_date"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<AuthUserDelegate> findAllByToUser(@Param(value = "toUserId") Integer toUserId);

	/**
	 * 查询是否存在委托关系
	 * 
	 * @param fromUserId
	 * @param toUserId
	 * @return AuthUserDelegate
	 */
	@Select("SELECT from_user_id, to_user_id, start_date, end_date, created_time, update_time, available FROM auth_user_delegate WHERE from_user_id = #{fromUserId} AND to_user_id = #{toUserId} AND (CURDATE() BETWEEN IFNULL(start_date,CURDATE()) AND IFNULL(end_date,CURDATE()))")
	@Results({
			@Result(property = "fromUser", column = "from_user_id", one = @One(select = "com.jrsoft.auth.dao.AuthUserDAO.findById", fetchType = FetchType.LAZY) ),
			@Result(property = "toUser", column = "to_user_id", one = @One(select = "com.jrsoft.auth.dao.AuthUserDAO.findById", fetchType = FetchType.LAZY) ),
			@Result(property = "startDate", column = "start_date"), @Result(property = "endDate", column = "end_date"),
			@Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public AuthUserDelegate findOne(@Param(value = "fromUserId") Integer fromUserId,
			@Param(value = "toUserId") Integer toUserId);

	/**
	 * 创建新委托关系
	 * 
	 * @param authUserDelegate
	 * @return
	 */
	@Insert("INSERT INTO auth_user_delegate(from_user_id, to_user_id, start_date, end_date, available, created_time) VALUES(#{fromUser.userId}, #{toUser.userId}, #{startDate}, #{endDate}, 1, NOW())")
	public int grantDelegate(AuthUserDelegate authUserDelegate);

	/**
	 * 删除委托关系
	 * 
	 * @param fromUserId
	 * @param toUserId
	 * @return
	 */
	@Delete("DELETE FROM auth_user_delegate WHERE from_user_id = #{fromUser.userId} AND to_user_id = #{toUser.userId}")
	public int revokeDelegate(AuthUserDelegate authUserDelegate);

}
