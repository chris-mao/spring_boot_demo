/**
 * 
 */
package com.example.demo.customer.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.customer.entity.CustomerAccount;
import com.example.demo.customer.service.CusotmerService;
import com.github.pagehelper.PageInfo;

/**
 * com.example.demo.customer.service.impl CustomerServiceImpl
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Service
public class CustomerServiceImpl implements CusotmerService {

	@Override
	public List<CustomerAccount> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageInfo<CustomerAccount> findAll(int pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerAccount findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerAccount findByNumber(String customerNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerAccount> findAllByCredential(String credential) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerAccount> getQualifiedCustomers(int priceHeaderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerAccount> getQualifiedCustomers(String priceListName) {
		// TODO Auto-generated method stub
		return null;
	}

}
