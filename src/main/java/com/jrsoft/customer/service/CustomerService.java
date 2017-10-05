/**
 * 
 */
package com.jrsoft.customer.service;

import java.util.List;
import java.util.Set;

import com.github.pagehelper.PageInfo;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.customer.entity.CustomerAccount;
import com.jrsoft.customer.entity.CustomerSite;
import com.jrsoft.employee.entity.Employee;
import com.jrsoft.price.entity.PriceListHeader;

/**
 * com.jrsoft.customer.service CusotmerService
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface CustomerService {

	/**
	 * 查询所有客户数据，不分页
	 * 
	 * @return List
	 */
	public List<CustomerAccount> findAll();

	/**
	 * 查询所有客户数据，分页
	 * 
	 * @param pageNum
	 * @return PageInfo
	 */
	public PageInfo<CustomerAccount> findAll(int pageNum);
	
	/**
	 * 按客户编号或是名称查询
	 * 
	 * @param customer
	 * @return CustomerAccount
	 */
	public CustomerAccount findOne(CustomerAccount customer);

	/**
	 * 根据用户系统用户名获取客户信息
	 * 
	 * @param credential
	 * @return List
	 */
	public List<CustomerAccount> findAllByCredential(AuthUser credential);

	/**
	 * 根据价格表ID或是名称获取可以使用指定价格表的客户列表
	 * 
	 * @param priceHeader
	 * @return List
	 */
	public List<CustomerAccount> findAllQualifiedCustomers(PriceListHeader priceHeader);

	/**
	 * 根据员工ID或是名称获取指定员工对应负责的客户列表
	 * 
	 * @param emp
	 * @return List
	 */
	public List<CustomerAccount> findAllByEmployee(Employee emp);

	/**
	 * 
	 * @param customerId
	 * @return
	 */
	public Set<CustomerSite> findAllBillTo(Integer customerId);

	/**
	 * 
	 * @param customerId
	 * @param operationUnitId
	 * @return
	 */
	public CustomerSite findBillTo(Integer customerId, Integer operationUnitId);

	/**
	 * 
	 * @param customerId
	 * @return
	 */
	public Set<CustomerSite> findAllShipTo(Integer customerId);

	/**
	 * 
	 * @param customerId
	 * @param operationUnitId
	 * @return
	 */
	public Set<CustomerSite> findAllShipTo(Integer customerId, Integer operationUnitId);

	/**
	 * 
	 * @param customerId
	 * @return
	 */
	public Set<CustomerSite> findAllDeliverTo(Integer customerId);

	/**
	 * 
	 * @param customerId
	 * @param operationUnitId
	 * @return
	 */
	public Set<CustomerSite> findAllDeliverTo(Integer customerId, Integer operationUnitId);

}
