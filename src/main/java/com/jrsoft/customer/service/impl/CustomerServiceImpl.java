/**
 * 
 */
package com.jrsoft.customer.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.common.EasyDataGrid;
import com.jrsoft.customer.entity.Customer;
import com.jrsoft.customer.service.CustomerService;
import com.jrsoft.organization.entity.Employee;

/**
 * 客户服务接口实现类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	@Override
	public List<Customer> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> findAll(boolean onlyAvailable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageInfo<Customer> findAll(int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EasyDataGrid<Customer> findAll(int pageIndex, int pageSize, String searchStr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer findOne(Customer role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Customer> findAllByUser(AuthUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insert(Customer role) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Customer role) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Customer> findAllByEmployee(Employee employee) {
		// TODO Auto-generated method stub
		return null;
	}

}
