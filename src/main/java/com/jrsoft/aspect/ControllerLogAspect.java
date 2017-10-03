/**
 * 
 */
package com.jrsoft.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * com.jrsoft.aspect LogAspect
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Aspect
@Component
public class ControllerLogAspect {
	
	private final static Logger logger = LoggerFactory.getLogger(ControllerLogAspect.class);

	@Pointcut("execution(public * com.jrsoft.*.controller.*.*(..))")
	public void log() {
		//
	}
	
	@Before("log()")
	public void beforeLog(JoinPoint joinPoint) {
		logger.info("ControllerLogAspect.beforeLog() has been lunched");
		
		ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		
		//url
		logger.info("url = {}", request.getRequestURL());
		
		//method
		logger.info("method = {}", request.getMethod());
		
		//access from ip
		logger.info("ip = {}", request.getRemoteAddr());
		
		//class
		logger.info("class method = {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
		
		//parameters
		for(Object obj: joinPoint.getArgs()) {
			logger.info("args = {}", obj);	
		}
	}
	
	@After("log()")
	public void afterLog() {
		logger.info("ControllerLogAspect.afterLog() has been lunched");
	}
	
	@AfterReturning(pointcut="log()", returning="object")
	public void afterReturning(Object object) {
		logger.info("response = {}", object);
	}
}
