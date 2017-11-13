/**
 * 
 */
package com.jrsoft.auth.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrsoft.auth.dao.AuthUserDelegateDAO;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.auth.entity.AuthUserDelegate;
import com.jrsoft.auth.service.AuthUserDelegateService;

/**
 * 身份代理服务接口实现类
 * 
 * @see AuthUserDelegateService
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Service
public class AuthUserDelegateServiceImpl implements AuthUserDelegateService {

	@Autowired
	private AuthUserDelegateDAO authUserDelegateDAO;

	@Override
	public void grantDelegate(AuthUser fromUser, AuthUser toUser) {
		this.grantDelegate(fromUser, toUser, Calendar.getInstance().getTime(), null);
	}

	@Override
	public void grantDelegate(AuthUser fromUser, AuthUser toUser, Date startDate, Date endDate) {
		if (!this.exists(fromUser, toUser)) {
			AuthUserDelegate delegate = new AuthUserDelegate(fromUser, toUser, startDate, endDate);
			this.authUserDelegateDAO.grantDelegate(delegate);
		}
	}

	@Override
	public void revokeDelegate(AuthUser fromUser, AuthUser toUser) {
		AuthUserDelegate delegate = new AuthUserDelegate(fromUser, toUser);
		this.authUserDelegateDAO.revokeDelegate(delegate);

	}

	@Override
	public boolean exists(AuthUser fromUser, AuthUser toUser) {
		return (null == this.authUserDelegateDAO.findOne(fromUser.getUserId(), toUser.getUserId())) ? false : true;
	}

	@Override
	public List<AuthUserDelegate> findAllByFromUser(AuthUser fromUser) {
		return this.authUserDelegateDAO.findAllByFromUser(fromUser.getUserId());
	}

	@Override
	public List<AuthUserDelegate> findAllByToUser(AuthUser toUser) {
		return this.authUserDelegateDAO.findAllByToUser(toUser.getUserId());
	}

}
