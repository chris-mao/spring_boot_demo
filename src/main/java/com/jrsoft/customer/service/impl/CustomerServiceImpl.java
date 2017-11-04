/**
 * 
 */
package com.jrsoft.customer.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

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
import com.jrsoft.employee.entity.Employee;
import com.jrsoft.price.dao.PriceListHeaderDAO;
import com.jrsoft.price.entity.PriceListHeader;

/**
 * com.jrsoft.customer.service.impl CustomerServiceImpl
 * 
 * 客户接口实现类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	@Value("${pageSize}")
	private int pageSize = 20;

	/**
	 * 
	 */
	@Autowired
	private CustomerAccountDAO customerAccountDAO;

	/**
	 * 
	 */
	@Autowired
	private CustomerSiteDAO customerSiteDAO;

	/**
	 * 
	 */
	@Autowired
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
		if (0 != customer.getCustomerId()) {
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
		if (0 != priceHeader.getHeaderId()) {
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
	 * @see com.jrsoft.customer.service.CustomerSiteService#findBillTo(int)
	 */
	@Override
	public Set<CustomerSite> findAllBillTo(int customerId) {
		return this.customerSiteDAO.findAllBillTo(customerId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jrsoft.customer.service.CustomerSiteService#findBillTo(int, int)
	 */
	@Override
	public CustomerSite findBillTo(int customerId, int operationUnitId) {
		return this.customerSiteDAO.findOperationUnitBillTo(customerId, operationUnitId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jrsoft.customer.service.CustomerSiteService#findAllShipTo(int)
	 */
	@Override
	public Set<CustomerSite> findAllShipTo(int customerId) {
		return this.customerSiteDAO.findAllShipTo(customerId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jrsoft.customer.service.CustomerSiteService#findAllShipTo(int,
	 * int)
	 */
	@Override
	public Set<CustomerSite> findAllShipTo(int customerId, int operationUnitId) {
		return this.customerSiteDAO.findOperationUnitShipTo(customerId, operationUnitId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jrsoft.customer.service.CustomerSiteService#findAllDeliverTo(int)
	 */
	@Override
	public Set<CustomerSite> findAllDeliverTo(int customerId) {
		return this.customerSiteDAO.findAllDeliverTo(customerId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jrsoft.customer.service.CustomerSiteService#findAllDeliverTo(int,
	 * int)
	 */
	@Override
	public Set<CustomerSite> findAllDeliverTo(int customerId, int operationUnitId) {
		return this.customerSiteDAO.findOperationUnitDeliverTo(customerId, operationUnitId);
	}

	@Override
	public List<CustomerAccount> findAllByEmployee(Employee emp) {
		if (0 == emp.getEmployeeId()) {
			return null;
		}
		return this.customerAccountDAO.findAllByEmployeeId(emp.getEmployeeId());
	}

	@Override
	public CustomerAccount isMine(Employee emp, int customerId) {
		List<CustomerAccount> customers = this.findAllByEmployee(emp);
		if (null == customers) {
			return null;
		}

		CustomerAccount ca;
		Iterator<CustomerAccount> iterator = customers.iterator();
		while (iterator.hasNext()) {
			ca = iterator.next();
			if (customerId == ca.getCustomerId()) {
				return ca;
			}
		}
		return null;
	}

	@Override
	public CustomerAccount isMine(Employee emp, String accountNumber) {
		List<CustomerAccount> customers = this.findAllByEmployee(emp);
		if (null == customers) {
			return null;
		}

		CustomerAccount ca;
		Iterator<CustomerAccount> iterator = customers.iterator();
		while (iterator.hasNext()) {
			ca = iterator.next();
			if (accountNumber == ca.getAccountNumber()) {
				return ca;
			}
		}
		return null;
	}

	@Override
	public CustomerAccount isMine(AuthUser user, int customerId) {
		List<CustomerAccount> customers = this.findAllByCredential(user);
		if (null == customers) {
			return null;
		}

		CustomerAccount ca;
		Iterator<CustomerAccount> iterator = customers.iterator();
		while (iterator.hasNext()) {
			ca = iterator.next();
			if (customerId == ca.getCustomerId()) {
				return ca;
			}
		}
		return null;
	}

}
