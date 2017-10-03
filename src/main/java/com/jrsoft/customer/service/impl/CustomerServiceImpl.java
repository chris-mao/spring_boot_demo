/**
 * 
 */
package com.jrsoft.customer.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrsoft.customer.dao.CustomerAccountDao;
import com.jrsoft.customer.entity.CustomerAccount;
import com.jrsoft.customer.service.CustomerService;

/**
 * com.jrsoft.customer.service.impl CustomerServiceImpl
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Value("${pageSize}")
	private int pageSize;
	
	@Resource
	private CustomerAccountDao customerAccountDao;

	@Override
	public List<CustomerAccount> findAll() {
		return this.customerAccountDao.findAll();
	}

	@Override
	public PageInfo<CustomerAccount> findAll(int pageNum) {
		PageHelper.startPage(pageNum, this.pageSize, "customer_name");
		return new PageInfo<CustomerAccount>(this.customerAccountDao.findAll());
	}

	@Override
	public CustomerAccount findById(Integer id) {
		return this.customerAccountDao.findById(id);
	}

	@Override
	public CustomerAccount findByAccountNumber(String accountNumber) {
		return this.customerAccountDao.findByAccountNumber(accountNumber);
	}

	@Override
	public List<CustomerAccount> findAllByCredential(String credential) {
		return this.customerAccountDao.findAllByCredential(credential);
	}

	@Override
	public List<CustomerAccount> findAllQualifiedCustomers(Integer priceHeaderId) {
		return this.customerAccountDao.findAllQualifiedCustomers(priceHeaderId);
	}

	@Override
	public List<CustomerAccount> findAllQualifiedCustomers(String priceListName) {
		// TODO Auto-generated method stub
		// TODO 将价格表名称转为ID再进行查询
		//priceListName = "EMR CDU SP CON PRICE LIST SZ";
		Integer priceHeaderId = 4733547;
		return this.findAllQualifiedCustomers(priceHeaderId);
	}

}
