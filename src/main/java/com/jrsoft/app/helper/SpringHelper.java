/**
 * 
 */
package com.jrsoft.app.helper;

import org.springframework.context.ApplicationContext;

/**
 * com.jrsoft.helper SpringHelper
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class SpringHelper {

	/**
	 * 
	 */
	private static ApplicationContext applicationContext;

	/**
	 * 
	 * @param context
	 */
	public static void setApplicationContext(ApplicationContext context) {
		applicationContext = context;
	}

	/**
	 * 
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
