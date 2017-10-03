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
import com.jrsoft.customer.dao.CustomerAccountDao;
import com.jrsoft.customer.dao.CustomerSiteDao;
import com.jrsoft.customer.entity.CustomerAccount;
import com.jrsoft.customer.entity.CustomerSite;
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
	
	@Resource
	private CustomerSiteDao customerSiteDao;

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
	
	/* (non-Javadoc)
	 * @see com.jrsoft.customer.service.CustomerSiteService#findBillTo(java.lang.Integer)
	 */
	@Override
	public Set<CustomerSite> findAllBillTo(Integer customerId) {
		return this.customerSiteDao.findAllBillTo(customerId);
	}

	/* (non-Javadoc)
	 * @see com.jrsoft.customer.service.CustomerSiteService#findBillTo(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public CustomerSite findBillTo(Integer customerId, Integer operationUnitId) {
		return this.customerSiteDao.findOperationUnitBillTo(customerId, operationUnitId);
	}

	/* (non-Javadoc)
	 * @see com.jrsoft.customer.service.CustomerSiteService#findAllShipTo(java.lang.Integer)
	 */
	@Override
	public Set<CustomerSite> findAllShipTo(Integer customerId) {
		return this.customerSiteDao.findAllShipTo(customerId);
	}

	/* (non-Javadoc)
	 * @see com.jrsoft.customer.service.CustomerSiteService#findAllShipTo(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Set<CustomerSite> findAllShipTo(Integer customerId, Integer operationUnitId) {
		return this.customerSiteDao.findOperationUnitShipTo(customerId, operationUnitId);
	}

	/* (non-Javadoc)
	 * @see com.jrsoft.customer.service.CustomerSiteService#findAllDeliverTo(java.lang.Integer)
	 */
	@Override
	public Set<CustomerSite> findAllDeliverTo(Integer customerId) {
		return this.customerSiteDao.findAllDeliverTo(customerId);
	}

	/* (non-Javadoc)
	 * @see com.jrsoft.customer.service.CustomerSiteService#findAllDeliverTo(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Set<CustomerSite> findAllDeliverTo(Integer customerId, Integer operationUnitId) {
		return this.customerSiteDao.findOperationUnitDeliverTo(customerId, operationUnitId);
	}

}
