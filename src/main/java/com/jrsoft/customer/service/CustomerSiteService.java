/**
 * 
 */
package com.jrsoft.customer.service;

import java.util.Set;

import com.jrsoft.customer.entity.CustomerSite;

/**
 * com.jrsoft.customer.service CustomerSiteService
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface CustomerSiteService {
	
	/**
	 * 
	 * @param customerId
	 * @return
	 */
	public Set<CustomerSite> findAllBillTo(Integer customerId);
	
	/**
	 * 
	 * @param customerId
	 * @param operationUnitId
	 * @return
	 */
	public CustomerSite findBillTo(Integer customerId, Integer operationUnitId);
	
	/**
	 * 
	 * @param customerId
	 * @return
	 */
	public Set<CustomerSite> findAllShipTo(Integer customerId);
	
	/**
	 * 
	 * @param customerId
	 * @param operationUnitId
	 * @return
	 */
	public Set<CustomerSite> findAllShipTo(Integer customerId, Integer operationUnitId);
	
	/**
	 * 
	 * @param customerId
	 * @return
	 */
	public Set<CustomerSite> findAllDeliverTo(Integer customerId);
	
	/**
	 * 
	 * @param customerId
	 * @param operationUnitId
	 * @return
	 */
	public Set<CustomerSite> findAllDeliverTo(Integer customerId, Integer operationUnitId);

}
