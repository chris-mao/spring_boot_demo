/**
 * 
 */
package com.jrsoft.organization.dao.sqlprovider;

import org.apache.ibatis.jdbc.SQL;

/**
 * com.jrsoft.employee.dao.sqlprovider EmployeeDynaSqlProvider
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class EmployeeDynaSqlProvider {
	
	/**
	 * 根据参数动态生成查询员工的SQL语句
	 * 
	 * @param onlyAvailable
	 * @return
	 */
	public String findAllSql(boolean onlyAvailable) {
		return new SQL(){{
			SELECT("employee_id, employee_name, email, fax, phone, oracle_account, user_id, report_to, available, created_time, update_time");
			FROM("employee");
			if (onlyAvailable) {
				WHERE("available = 1");
			}
			ORDER_BY("employee_name");
		}}.toString();
	}

}
