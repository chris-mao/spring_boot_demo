/**
 * 
 */
package com.jrsoft.configuration.shiro;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jrsoft.auth.shiro.JrShiroRealm;
import com.jrsoft.configuration.shiro.listener.JrSessionListener;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import net.sf.ehcache.CacheManager;

/**
 * Shiro配置类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Configuration
public class ShiroConfiguration {

	private final static Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

	/**
	 * ShiroFilterFactoryBean 处理拦截资源文件问题
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		logger.info("加载Shiro过滤器");
		ShiroFilterFactoryBean filterBean = new ShiroFilterFactoryBean();
		filterBean.setSecurityManager(securityManager);

		// 配置过滤器
		// authc:所有URL都必须通过认证才可以访问
		// anon: 所有URL都可以匿名访问
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/logout", "logout");
		// 允许匿名访问css/js/img资源
		filterChainDefinitionMap.put("/css/*", "anon");
		filterChainDefinitionMap.put("/img/*", "anon");
		filterChainDefinitionMap.put("/js/*", "anon");
		filterChainDefinitionMap.put("/bootstrap/*", "anon");
		filterChainDefinitionMap.put("/jquery-easyui-1.5.3/*", "anon");
		// 避免登录后下载favicon图标
		filterChainDefinitionMap.put("/favicon.ico", "anon");
		filterChainDefinitionMap.put("/**", "authc, perms");
		filterBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		// 登录页面
		filterBean.setLoginUrl("/login");
		// 登录成功后要跳转的链接
		filterBean.setSuccessUrl("/index");
		// 未授权界面
		filterBean.setUnauthorizedUrl("/403");

		return filterBean;
	}

	/**
	 * shiro安全管理器，主要用于身份认证的管理，缓存管理
	 * 
	 * @return
	 */
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 注入realm
		logger.info("向Shiro中注入realm");
		securityManager.setRealm(jrShiroRealm());
		// 注入缓存管理器
		logger.info("向Shiro中注入缓存管理器");
		securityManager.setCacheManager(ehCacheManager());
		// 注入会话管理器
		logger.info("向Shiro中注入会话管理器");
		securityManager.setSessionManager(sessionManager());
		return securityManager;
	}

	/**
	 * 
	 * @return
	 */
	@Bean
	public JrShiroRealm jrShiroRealm() {
		JrShiroRealm jrShiroRealm = new JrShiroRealm();
		logger.info("向Shiro中注入密码加密匹配器");
		 jrShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return jrShiroRealm;
	}

	/**
	 * 密码加密匹配器
	 * 
	 * @return
	 */
	@Bean
	public RetryLimitHashedCredentialsMatcher hashedCredentialsMatcher() {
		//HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		RetryLimitHashedCredentialsMatcher hashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher(ehCacheManager());
		hashedCredentialsMatcher.setHashAlgorithmName("MD5");
		hashedCredentialsMatcher.setHashIterations(1);
		hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
		return hashedCredentialsMatcher;
	}

	/**
	 * 开启shiro aop注解支持
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		logger.info("开启Shiro AOP注解支持");
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}

	/**
	 * Shiro缓存管理器
	 * 
	 * @return
	 */
	@Bean
	public EhCacheManager ehCacheManager() {
		CacheManager cacheManager = CacheManager.getCacheManager("es");
		if (null == cacheManager) {
			EhCacheManager ehCacheManager = new EhCacheManager();
			ehCacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
			return ehCacheManager;
		}
		EhCacheManager ehCacheManager = new EhCacheManager();
		ehCacheManager.setCacheManager(cacheManager);
		return ehCacheManager;
	}

	/**
	 * 会话管理器
	 * 
	 * @return
	 */
	@Bean
	public DefaultWebSessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		// 设置会话过期时间
		logger.info("设置会话过期时间");
		sessionManager.setGlobalSessionTimeout(12 * 60 * 60 * 1000); // 12 hours
		sessionManager.setSessionValidationInterval(sessionManager.getGlobalSessionTimeout());
		// 添加会话监听
		logger.info("添加会话监听");
		ArrayList<SessionListener> listeners = new ArrayList<SessionListener>();
		listeners.add(new JrSessionListener());
		sessionManager.setSessionListeners(listeners);
		sessionManager.setCacheManager(ehCacheManager());

		return sessionManager;
	}

	/**
	 * 为Thymeleaf增加Shiro方言，以便能够在模板文件中使用Shiro标签
	 * 
	 * @return
	 */
	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}
}
