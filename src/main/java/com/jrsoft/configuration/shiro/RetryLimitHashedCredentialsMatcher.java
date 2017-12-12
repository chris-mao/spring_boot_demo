package com.jrsoft.configuration.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jrsoft.auth.service.AuthUserService;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义用户身份匹配器
 * 
 * 密码连续错误五次，帐号会被锁
 * 
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
	
	@Autowired
	private AuthUserService authUserService;
	
	/**
	 * 最大登录次数
	 */
	private final static int MaxRetryCount = 5;
	
	/**
	 * 
	 */
	private final static Logger logger = LoggerFactory.getLogger(RetryLimitHashedCredentialsMatcher.class);
	
	/**
	 * 
	 */
	private Cache<String, AtomicInteger> passwordRetryCache;

    /**
     * 构造函数
     * 
     * @param cacheManager
     */
	public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String userName = (String)token.getPrincipal();
        AtomicInteger retryCount = passwordRetryCache.get(userName);
        if(retryCount == null) {
            retryCount = new AtomicInteger(1);
            passwordRetryCache.put(userName, retryCount);
        }
        logger.info("==> 用户 {} 尝试第 {} 次登录: ", userName, retryCount.get());
        //增加登录尝试次数
        if(retryCount.incrementAndGet() > MaxRetryCount) {
        	logger.info("==> 用户 {} 的帐号被锁！！！！ ", userName, retryCount.get());
        	authUserService.lockUser(userName);
            throw new ExcessiveAttemptsException();
        }

        if(super.doCredentialsMatch(token, info)) { //身份认证成功，清除登录次数缓存
            passwordRetryCache.remove(userName);
            return true;
        }
        return false;
    }
}
