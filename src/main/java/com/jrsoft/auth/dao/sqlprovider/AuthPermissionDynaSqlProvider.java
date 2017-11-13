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

	/**
	 * 根据参数动态生成查询系统权限的SQL语句
	 * 
	 * @param onlyAvailable
	 * @return
	 */
	public String findAllSql(boolean onlyAvailable) {
		return new SQL() {
			{
				SELECT("permission_id, permission_name, permission_text, permission_kind, permission_url, weight, parent_id, available, created_time, update_time");
				FROM("auth_permission");
				if (onlyAvailable) {
					WHERE("available = true");
				}
				WHERE("permission_kind is not null");
				ORDER_BY("parent_id, weight");
			}
		}.toString();
	}

}
