/**
 * 
 */
package com.jrsoft.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * com.jrsoft.aspect ServiceLogAspect
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Aspect
@Component
public class ServiceLogAspect {

	private final static Logger logger = LoggerFactory.getLogger(ControllerLogAspect.class);

	@Pointcut("execution(public * com.jrsoft.*.service.*.*(..))")
	public void log() {
		//
	}

	@Before("log()")
	public void beforeLog(JoinPoint joinPoint) {
		logger.info("ServiceLogAspect.beforeLog() has been lunched");

		// class
		logger.info("class method = {}",
				joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

		// parameters
		for (Object obj : joinPoint.getArgs()) {
			logger.info("args = {}", obj);
		}
	}

	@After("log()")
	public void afterLog() {
		logger.info("ServiceLogAspect.afterLog() has been lunched");
	}

	@AfterReturning(pointcut = "log()", returning = "object")
	public void afterReturning(Object object) {
		logger.info("response = {}", object);
	}
}
