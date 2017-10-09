/**
 * 
 */
package com.jrsoft.auth.dao.sqlprovider;

import org.apache.ibatis.jdbc.SQL;

/**
 * com.jrsoft.auth.dao.sqlprovider AuthPermissionDynaSqlProvider
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class AuthPermissionDynaSqlProvider {

	public String findAllSql(boolean onlyAvailable) {
		return new SQL() {
			{
				SELECT("permission_id, permission_name, permission_url, available, created_time, update_time");
				FROM("auth_permission");
				if (onlyAvailable) {
					WHERE("available = true");
				}
				ORDER_BY("permission_name");
			}
		}.toString();
	}

}
