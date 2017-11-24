/**
 * 
 */
package com.jrsoft.configuration.shiro.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 会话监听器类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class JrSessionListener implements SessionListener {
	
	private final static Logger logger = LoggerFactory.getLogger(JrSessionListener.class);

	@Override
	public void onStart(Session session) {
		logger.info("会话创建： {}", session.getId());
	}

	@Override
	public void onStop(Session session) {
		logger.info("会话停止： {}", session.getId());
	}

	@Override
	public void onExpiration(Session session) {
		logger.info("会话过期： {}", session.getId());
	}

}
