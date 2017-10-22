/**
 * 
 */
package com.jrsoft.auth.dao.sqlprovider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

/**
 * com.jrsoft.auth.dao.sqlprovider AuthRoleDynaSqlProvider
 * 
 * 角色查询动态SQL提供者类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class AuthRoleDynaSqlProvider {

	/**
	 * 根据参数动态生成查询系统角色的SQL语句
	 * 
	 * @param onlyAvailable
	 * @return
	 */
	public String findAllSql(final boolean onlyAvailable) {
		return new SQL() {
			{
				SELECT("role_id, role_name, available, created_time, update_time");
				FROM("auth_role");
				if (true == onlyAvailable) {
					WHERE("available = true");
				}
				ORDER_BY("role_name");
			}
		}.toString();
	}

	/**
	 * 
	 * @param userId
	 * @param onlyAvailable
	 * @return
	 */
	public String findAllByUserIdSql(Map<String, Object> paramMap) {
		if ((Boolean) paramMap.get("onlyAvailable") == Boolean.TRUE) {
			return "CALL sp_findUserRoles(" + paramMap.get("userId") + ")";
		}
		return new SQL() {
			{
				SELECT("role_id, role_name, available, created_time, update_time");
				FROM("vw_auth_user_role");
				WHERE("user_id = " + paramMap.get("userId"));
			}
		}.toString();
	}

}
