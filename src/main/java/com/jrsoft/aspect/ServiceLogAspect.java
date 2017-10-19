/**
 * 
 */
package com.jrsoft.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * com.jrsoft.aspect ServiceLogAspect
 * 
 * 服务切面类 所有被标注@Service的服务类中的方法被调用时，都会被此类拦截，并会在控制台中输出调用者的信息以及服务类中方法的返回信息
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Aspect
@Component
public class ServiceLogAspect {

	private final static Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

	@Pointcut("execution(public * com.jrsoft.*.service.*.*(..))")
	public void log() {
	}

	@Before("log()")
	public void beforeLog(JoinPoint joinPoint) {
		logger.info("=============");

		// class
		logger.info("服务类 {} 中的 {} 方法被调用", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName());

		// parameters
		for (Object obj : joinPoint.getArgs()) {
			 logger.info("调用参数 = {}", obj);
		}
	}

	@After("log()")
	public void afterLog() {
		// logger.info("====== Lunch End ======");
	}

	@AfterReturning(pointcut = "log()", returning = "object")
	public void afterReturning(Object object) {
		logger.info("返回值是： {}", object);
		logger.info("====== 服务类调用正常结束 ======");
	}
	
	@AfterThrowing(pointcut = "log()", throwing = "e")
	public void afterThrowing(JoinPoint joinPoint, Throwable e) {
		logger.warn("服务调用出错！！！");
		logger.warn("在调用服务类 {} 中的 {} 方法时出现异常", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName());

		// parameters
		for (Object obj : joinPoint.getArgs()) {
			 logger.warn("调用参数 = {}", obj);
		}
		logger.warn("异常名称：{}", e.getClass().getName());
		logger.warn("异常描述：{}", e.getMessage());
		
		// TODO 将调用信息写到数据库中
	}
}
