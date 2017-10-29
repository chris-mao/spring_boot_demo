/**
 * 
 */
package com.jrsoft.auth.dao.sqlprovider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

/**
 * com.jrsoft.auth.dao.sqlprovider AuthUserDynaSqlProvider
 * 
 * 用户查询动态SQL提供者类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class AuthUserDynaSqlProvider {

	/**
	 * 根据参数动态生成查询系统用户的SQL语句
	 * 
	 * @param onlyAvailable
	 * @return
	 */
	public String findAllSql(boolean onlyAvailable) {
		return new SQL() {
			{
				SELECT("user_id, user_name, nick_name, email, user_psd, salt, state, available, created_time, update_time");
				FROM("auth_user");
				if (onlyAvailable) {
					WHERE("state = 'ACTIVE'");
				}
				ORDER_BY("user_name");
			}
		}.toString();
	}

	public String findSpecificSql(Map<String, Object> params) {
		String sql = new SQL() {
			{
				String userName = (String) params.get("userName");
				String nickName = (String) params.get("nickName");

				SELECT("user_id, user_name, nick_name, email, user_psd, salt, state, available, created_time, update_time");
				FROM("auth_user");
				WHERE(String.format("user_name LIKE '%%%s%%'", userName));
				OR();
				WHERE(String.format("nick_name LIKE '%%%s%%'", nickName));
				ORDER_BY("user_name");
			}
		}.toString();
		System.out.println("findSpecificSQL: " + sql);
		return sql;
	}

}
