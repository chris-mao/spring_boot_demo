/**
 * 
 */
package com.jrsoft.auth.dao.sqlprovider;

import org.apache.ibatis.jdbc.SQL;

/**
 * com.jrsoft.auth.dao.sqlprovider AuthRoleDynaSqlProvider
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class AuthRoleDynaSqlProvider {

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

}
