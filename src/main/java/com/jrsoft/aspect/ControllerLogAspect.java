/**
 * 
 */
package com.jrsoft.aspect;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * com.jrsoft.aspect LogAspect
 * 
 * 控制器切面类 所有被标注@Controller的控制器类中的方法被调用时，都会被此类拦截，并会在控制台中输出调用者的信息以及控制器方法的返回信息
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
	}

	@Before("log()")
	public void beforeLog(JoinPoint joinPoint) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		// url
		logger.info("用户在 {} 以 {} 方式访问 {}", request.getRemoteAddr(), request.getMethod(), request.getRequestURL());

		// class
		logger.info("控制器类 {} 中的 {} 方法被调用", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName());

		// parameters
		for (Object obj : joinPoint.getArgs()) {
			logger.info("调用参数 = {}", obj);
		}
	}

	@After("log()")
	public void afterLog(JoinPoint joinPoint) {
		logger.info("控制器类 {} 中的 {} 方法调用结束", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName());
	}

	@AfterReturning(pointcut = "log()", returning = "object")
	public void afterReturning(Object object) {
		logger.info("控制器返回值是： {}", object);
		logger.info("控制器调用正常结束");
	}

	@AfterThrowing(pointcut = "log()", throwing = "e")
	public void afterThrowing(JoinPoint joinPoint, Throwable e) {
		logger.warn("控制器调用出错！！！");
		logger.warn("在调用控制器类 {} 中的 {} 方法时出现异常", joinPoint.getSignature().getDeclaringTypeName(),
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
