/**
 * 
 */
package com.example.demo.config.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.auth.shiro.JrShiroRealm;

/**
 * com.example.demo.config.shiro ShiroConfiguration
 * 
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
		logger.info("ShiroConfiguration.shiroFilter() has been loaded");
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
		filterChainDefinitionMap.put("/fonts/*", "anon");
		filterChainDefinitionMap.put("/img/*", "anon");
		filterChainDefinitionMap.put("/js/*", "anon");
		filterChainDefinitionMap.put("/**", "authc");
		filterBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		// 登录页面
		filterBean.setLoginUrl("/login");
		// 登录成功后要跳转的链接
		filterBean.setSuccessUrl("/index");
		// 未授权界面
		filterBean.setUnauthorizedUrl("/unauthorized");

		return filterBean;
	}

	/**
	 * shiro安全管理器，主要用于身份认证的管理，缓存管理，cookie管理
	 * 
	 * @return
	 */
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm
		securityManager.setRealm(jrShiroRealm());
		// 注入缓存管理器
		securityManager.setCacheManager(ehCacheManager());
		return securityManager;
	}

	/**
	 * 
	 * @return
	 */
	@Bean
	public JrShiroRealm jrShiroRealm() {
		JrShiroRealm jrShiroRealm = new JrShiroRealm();
		jrShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return jrShiroRealm;
	}

	/**
	 * 密码加密匹配器
	 * 
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("MD5");
		hashedCredentialsMatcher.setHashIterations(1);
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
		EhCacheManager cacheManager = new EhCacheManager();
		cacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
		return cacheManager;
	}
}
