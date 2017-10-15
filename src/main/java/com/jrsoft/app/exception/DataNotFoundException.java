/**
 * 
 */
package com.jrsoft.app.exception;

/**
 * com.jrsoft.exception DataNotFoundException
 * 
 * 数据库中检索不到相匹配的数据时抛出此异常
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class DataNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataNotFoundException(String msg) {
		super(msg);
	}

}
