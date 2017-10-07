/**
 * 
 */
package com.jrsoft.auth.shiro;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jrsoft.auth.entity.AuthRole;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.customer.entity.CustomerAccount;
import com.jrsoft.customer.entity.CustomerSite;
import com.jrsoft.customer.service.CustomerService;

/**
 * com.jrsoft.auth.shiro AuthUserDecorator
 * 
 * 系统用户装饰类，封装了系统用户及其对应的OU、客户或是员工信息 用户身份认证成功后，会将此类写入到session中
 * 方便在程序其他地方读取当登录用户的相关信息
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class AuthUserDecorator {

	/**
	 * 日志对象
	 */
	private final static Logger logger = LoggerFactory.getLogger(AuthUserDecorator.class);

	/**
	 * 
	 */
	private CustomerService customerService = null;

	/**
	 * 
	 */
	private final AuthUser authUser;

	/**
	 * 
	 */
	private List<CustomerAccount> customerList = null;

	/**
	 * 
	 */
	private Set<CustomerSite> billToSiteList = null;

	/**
	 * 
	 * @param theUser
	 */
	public AuthUserDecorator(AuthUser theUser, CustomerService customerService) {
		if (theUser == null) {
			throw new IllegalArgumentException("认证用户对象(AuthUser)不能为空！");
		}
		logger.info("==> 开始对用户对象进行包装，并写入到Session中...");
		authUser = theUser;
		this.customerService = customerService;
		customerList = this.customerService.findAllByCredential(authUser);
	}

	/**
	 * 
	 * @return
	 */
	public Integer getUserId() {
		return authUser.getUserId();
	}

	/**
	 * 
	 * @return
	 */
	public String getUserName() {
		return authUser.getUserName();
	}

	/**
	 * 
	 * @return
	 */
	public String getNickName() {
		return authUser.getNickName();
	}

	/**
	 * 
	 * @return
	 */
	public String getEmail() {
		return authUser.getEmail();
	}

	/**
	 * 
	 * @return
	 */
	public Set<AuthRole> getRoles() {
		return authUser.getRoles();
	}

	/**
	 * 
	 * @return
	 */
	public List<CustomerAccount> getCustomerList() {
		return customerList;
	}

	/**
	 * 读取当前登录用户对应的BILL TO地址
	 * 
	 * @return
	 */
	public Set<CustomerSite> getBillToSiteList() {
		if (null == billToSiteList) {
			billToSiteList = new HashSet<CustomerSite>();
			CustomerAccount customer;
			logger.info("==> 开始获取登录用户对应的客户清单...");
			Iterator<CustomerAccount> iterator = customerList.iterator();
			while (iterator.hasNext()) {
				customer = iterator.next();
				logger.info("==> 开始提取客户 " + customer.getAccountNumber() + " 的BILL TO地址");
				billToSiteList.addAll(customer.getBillTo());
			}
			logger.info("读到 " + billToSiteList.size() + " 个BILL TO地址");
		}
		return billToSiteList;
	}

	/**
	 * 
	 * @return
	 */
	public Object getEmployee() {
		return null;
	}

}
