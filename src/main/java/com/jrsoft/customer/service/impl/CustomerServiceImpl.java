/**
 * 
 */
package com.jrsoft.customer.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.customer.dao.CustomerAccountDao;
import com.jrsoft.customer.dao.CustomerSiteDao;
import com.jrsoft.customer.entity.CustomerAccount;
import com.jrsoft.customer.entity.CustomerSite;
import com.jrsoft.customer.service.CustomerService;
import com.jrsoft.employee.dao.EmployeeDao;
import com.jrsoft.employee.entity.Employee;
import com.jrsoft.price.dao.PriceListHeaderDao;
import com.jrsoft.price.entity.PriceListHeader;

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

	/**
	 * 
	 */
	@Resource
	private CustomerAccountDao customerAccountDao;

	/**
	 * 
	 */
	@Resource
	private CustomerSiteDao customerSiteDao;

	/**
	 * 
	 */
	@Resource
	private EmployeeDao employddDao;

	/**
	 * 
	 */
	@Resource
	private PriceListHeaderDao priceHeaderDao;

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
	public CustomerAccount findOne(CustomerAccount customer) {
		if (null != customer.getCustomerId()) {
			return this.customerAccountDao.findById(customer.getCustomerId());
		}
		if (null != customer.getAccountNumber()) {
			return this.customerAccountDao.findByAccountNumber(customer.getAccountNumber());
		}
		return null;
	}

	@Override
	public List<CustomerAccount> findAllByCredential(AuthUser credential) {
		return this.customerAccountDao.findAllByCredential(credential.getUserName());
	}

	@Override
	public List<CustomerAccount> findAllQualifiedCustomers(PriceListHeader priceHeader) {
		if (null != priceHeader.getHeaderId()) {
			return customerAccountDao.findAllQualifiedCustomers(priceHeader.getHeaderId());
		}
		if (null != priceHeader.getName()) {
			PriceListHeader thePriceHeader = this.priceHeaderDao.findByName(priceHeader.getName());
			return customerAccountDao.findAllQualifiedCustomers(thePriceHeader.getHeaderId());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jrsoft.customer.service.CustomerSiteService#findBillTo(java.lang.
	 * Integer)
	 */
	@Override
	public Set<CustomerSite> findAllBillTo(Integer customerId) {
		return this.customerSiteDao.findAllBillTo(customerId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jrsoft.customer.service.CustomerSiteService#findBillTo(java.lang.
	 * Integer, java.lang.Integer)
	 */
	@Override
	public CustomerSite findBillTo(Integer customerId, Integer operationUnitId) {
		return this.customerSiteDao.findOperationUnitBillTo(customerId, operationUnitId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jrsoft.customer.service.CustomerSiteService#findAllShipTo(java.lang.
	 * Integer)
	 */
	@Override
	public Set<CustomerSite> findAllShipTo(Integer customerId) {
		return this.customerSiteDao.findAllShipTo(customerId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jrsoft.customer.service.CustomerSiteService#findAllShipTo(java.lang.
	 * Integer, java.lang.Integer)
	 */
	@Override
	public Set<CustomerSite> findAllShipTo(Integer customerId, Integer operationUnitId) {
		return this.customerSiteDao.findOperationUnitShipTo(customerId, operationUnitId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jrsoft.customer.service.CustomerSiteService#findAllDeliverTo(java.
	 * lang.Integer)
	 */
	@Override
	public Set<CustomerSite> findAllDeliverTo(Integer customerId) {
		return this.customerSiteDao.findAllDeliverTo(customerId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jrsoft.customer.service.CustomerSiteService#findAllDeliverTo(java.
	 * lang.Integer, java.lang.Integer)
	 */
	@Override
	public Set<CustomerSite> findAllDeliverTo(Integer customerId, Integer operationUnitId) {
		return this.customerSiteDao.findOperationUnitDeliverTo(customerId, operationUnitId);
	}

	@Override
	public List<CustomerAccount> findAllByEmployee(Employee emp) {
		if (null != emp.getEmployeeId()) {
			return this.customerAccountDao.findALlByEmployeeId(emp.getEmployeeId());
		}
		return null;
	}

}
