/**
 * 
 */
package com.jrsoft.auth.dao.sqlprovider;

import org.apache.ibatis.jdbc.SQL;

/**
 * com.jrsoft.auth.dao.sqlprovider AuthPermissionDynaSqlProvider
 * 
 * 权限查询动态SQL提供者类
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
