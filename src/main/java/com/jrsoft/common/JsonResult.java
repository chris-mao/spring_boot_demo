/**
 * 
 */
package com.jrsoft.common;

import java.io.Serializable;

/**
 * 以JSON格式封装服务器端对数据进行增、删、改操作返回值
 * 
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class JsonResult<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final int SUCCESS = 0;

	/**
	 * 
	 */
	public static final int ERROR = 100;

	/**
	 * 状态码
	 */
	private int state;

	/**
	 * 描述信息
	 */
	private String message;

	/**
	 * 实体对象
	 */
	private T data;

	/**
	 * 默认无参构造函数
	 * 
	 * @since 1.0
	 */
	public JsonResult() {
		this.state = SUCCESS;
		this.message = "no error";
	}
	
	public JsonResult(T data) {
		this.state = SUCCESS;
		this.message = "no error";
		this.data = data;
	}

	/**
	 * 有参构造函数
	 * 
	 * @since 1.0
	 * @param state
	 * @param message
	 */
	public JsonResult(int state, String message) {
		this.state = state;
		this.message = message;
	}

	/**
	 * 有参构造函数
	 * 
	 * @since 1.0
	 * 
	 * @param state
	 * @param message
	 * @param data
	 */
	public JsonResult(int state, String message, T data) {
		this.state = state;
		this.message = message;
		this.data = data;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "JsonResult [state=" + state + ", message=" + message + ", data=" + data + "]";
	}

}
