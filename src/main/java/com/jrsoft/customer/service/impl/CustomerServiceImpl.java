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
import com.jrsoft.customer.dao.CustomerAccountDAO;
import com.jrsoft.customer.dao.CustomerSiteDAO;
import com.jrsoft.customer.entity.CustomerAccount;
import com.jrsoft.customer.entity.CustomerSite;
import com.jrsoft.customer.service.CustomerService;
import com.jrsoft.employee.dao.EmployeeDAO;
import com.jrsoft.employee.entity.Employee;
import com.jrsoft.price.dao.PriceListHeaderDAO;
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
	private CustomerAccountDAO customerAccountDAO;

	/**
	 * 
	 */
	@Resource
	private CustomerSiteDAO customerSiteDAO;

	/**
	 * 
	 */
	@Resource
	private EmployeeDAO employddDAO;

	/**
	 * 
	 */
	@Resource
	private PriceListHeaderDAO priceHeaderDAO;

	@Override
	public List<CustomerAccount> findAll() {
		return this.customerAccountDAO.findAll();
	}

	@Override
	public PageInfo<CustomerAccount> findAll(int pageNum) {
		PageHelper.startPage(pageNum, this.pageSize, "customer_name");
		return new PageInfo<CustomerAccount>(this.customerAccountDAO.findAll());
	}

	@Override
	public CustomerAccount findOne(CustomerAccount customer) {
		if (null != customer.getCustomerId()) {
			return this.customerAccountDAO.findById(customer.getCustomerId());
		}
		if (null != customer.getAccountNumber()) {
			return this.customerAccountDAO.findByAccountNumber(customer.getAccountNumber());
		}
		return null;
	}

	@Override
	public List<CustomerAccount> findAllByCredential(AuthUser credential) {
		if (null == credential.getUserName()) {
			return null;
		}
		return this.customerAccountDAO.findAllByCredential(credential.getUserName());
	}

	@Override
	public List<CustomerAccount> findAllQualifiedCustomers(PriceListHeader priceHeader) {
		if (null != priceHeader.getHeaderId()) {
			return customerAccountDAO.findAllQualifiedCustomers(priceHeader.getHeaderId());
		}
		if (null != priceHeader.getName()) {
			PriceListHeader thePriceHeader = this.priceHeaderDAO.findByName(priceHeader.getName());
			return customerAccountDAO.findAllQualifiedCustomers(thePriceHeader.getHeaderId());
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
		return this.customerSiteDAO.findAllBillTo(customerId);
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
		return this.customerSiteDAO.findOperationUnitBillTo(customerId, operationUnitId);
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
		return this.customerSiteDAO.findAllShipTo(customerId);
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
		return this.customerSiteDAO.findOperationUnitShipTo(customerId, operationUnitId);
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
		return this.customerSiteDAO.findAllDeliverTo(customerId);
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
		return this.customerSiteDAO.findOperationUnitDeliverTo(customerId, operationUnitId);
	}

	@Override
	public List<CustomerAccount> findAllByEmployee(Employee emp) {
		if (null != emp.getEmployeeId()) {
			return this.customerAccountDAO.findALlByEmployeeId(emp.getEmployeeId());
		}
		return null;
	}

}
