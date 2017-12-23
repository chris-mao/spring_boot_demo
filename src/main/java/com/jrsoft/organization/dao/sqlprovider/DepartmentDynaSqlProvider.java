/**
 * 
 */
package com.jrsoft.organization.dao.sqlprovider;

import org.apache.ibatis.jdbc.SQL;

/**
 * com.jrsoft.employee.dao.sqlprovider DepartmentDynaSqlProvider
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class DepartmentDynaSqlProvider {
	
	/**
	 * 根据参数动态生成查询部门的SQL语句
	 * 
	 * @param onlyAvailable
	 * @return
	 */
	public String findAllSql(boolean onlyAvailable) {
		return new SQL(){{
			SELECT("department_id, department_name, parent_id, available, created_time, update_time");
			FROM("department");
			if (onlyAvailable) {
				WHERE("available = 1");
			}
			ORDER_BY("parent_id");
		}}.toString();
	}

}
