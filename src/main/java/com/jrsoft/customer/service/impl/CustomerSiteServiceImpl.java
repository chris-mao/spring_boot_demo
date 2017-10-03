/**
 * 
 */
package com.jrsoft.customer.service.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrsoft.customer.dao.CustomerSiteDao;
import com.jrsoft.customer.entity.CustomerSite;
import com.jrsoft.customer.service.CustomerSiteService;

/**
 * com.jrsoft.customer.service.impl CustomerSiteServiceImpl
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Service
public class CustomerSiteServiceImpl implements CustomerSiteService {
	
	@Resource
	private CustomerSiteDao customerSiteDao;

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
