/**
 * 
 */
package com.example.demo.customer.service;

import java.util.List;

import com.example.demo.customer.entity.CustomerAccount;
import com.github.pagehelper.PageInfo;

/**
 * com.example.demo.customer.service CusotmerService
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
	 * 
	 * @param id
	 * @return
	 */
	public CustomerAccount findById(Integer id);

	/**
	 * 根据客户Account Number查询客户数据
	 * 
	 * @param accountNumber
	 * @return CustomerAccount
	 */
	public CustomerAccount findByAccountNumber(String accountNumber);

	/**
	 * 根据用户系统用户名获取客户信息
	 * 
	 * @param credential
	 * @return List
	 */
	public List<CustomerAccount> findAllByCredential(String credential);

	// public List<CustomerAccount> findAllByEmployee();
	
	/**
	 * 获取可以使用指定价格表的客户列表
	 * 
	 * @param priceHeaderId
	 * @return
	 */
	public List<CustomerAccount> findAllQualifiedCustomers(Integer priceHeaderId);
	
	/**
	 * 获取可以使用指定价格表的客户列表
	 * 
	 * @param priceListName
	 * @return
	 */
	public List<CustomerAccount> findAllQualifiedCustomers(String priceListName);

}
