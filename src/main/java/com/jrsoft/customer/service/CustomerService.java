/**
 * 
 */
package com.jrsoft.customer.service;

import java.util.Set;

import com.jrsoft.app.service.AbstractDbService;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.customer.entity.Customer;
import com.jrsoft.organization.entity.Employee;

/**
 * 客户服务接口
 * 
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface CustomerService extends AbstractDbService<Customer> {

	/**
	 * 根据用户查询其所对应的客户清单
	 * 
	 * @since 1.0
	 * @param user
	 * @return Set
	 */
	public Set<Customer> findAllByUser(AuthUser user);

	/**
	 * 根据员工查询分配到其名下的客户清单
	 * 
	 * @since 1.0
	 * @param employee
	 * @return Set
	 */
	public Set<Customer> findAllByEmployee(Employee employee);
}
