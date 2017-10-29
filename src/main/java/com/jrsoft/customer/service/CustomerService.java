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
 * 客户接口
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
	 * 根据发票地址查询客户数据
	 * 
	 * @param customerId
	 * @return Set
	 */
	public Set<CustomerSite> findAllBillTo(int customerId);

	/**
	 * 查询客户在特定OU下的发票地址
	 * 
	 * @param customerId
	 * @param operationUnitId
	 * @return CustomerSite
	 */
	public CustomerSite findBillTo(int customerId, int operationUnitId);

	/**
	 * 查询客户所有的收货地址
	 * 
	 * @param customerId
	 * @return Set
	 */
	public Set<CustomerSite> findAllShipTo(int customerId);

	/**
	 * 查询客户在特定OU下的所有收货地址
	 * 
	 * @param customerId
	 * @param operationUnitId
	 * @return Set
	 */
	public Set<CustomerSite> findAllShipTo(int customerId, int operationUnitId);

	/**
	 * 查询客户所有Deliver地址
	 * 
	 * @param customerId
	 * @return Set
	 */
	public Set<CustomerSite> findAllDeliverTo(int customerId);

	/**
	 * 查询客户在特定OU下的所有Deliver地址
	 * 
	 * @param customerId
	 * @param operationUnitId
	 * @return Set
	 */
	public Set<CustomerSite> findAllDeliverTo(int customerId, int operationUnitId);

	/**
	 * 判断客户编号对应的客户是否已分配到指定员工名下 如果已分配则返回该客户对象实例，否则返回null
	 * 
	 * @param emp
	 * @param customerId
	 * @return CustomerAccount
	 */
	public CustomerAccount isMine(Employee emp, int customerId);

	/**
	 * 判断客户代码对应的客户是否已分配到指定员工名下 如果已分配则返回该客户对象实例，否则返回null
	 * 
	 * @param emp
	 * @param accountNumber
	 * @return CustomerAccount
	 */
	public CustomerAccount isMine(Employee emp, String accountNumber);

	/**
	 * 判断客户编号对应的客户是否已分配到指定系统用户名下 如果已分配则返回该客户对象实例，否则返回null
	 * 
	 * @param user
	 * @param customerId
	 * @return CustomerAccount
	 */
	public CustomerAccount isMine(AuthUser user, int customerId);

}
