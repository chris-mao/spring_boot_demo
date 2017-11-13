/**
 * 
 */
package com.jrsoft.auth;

/**
 * 用户状态枚举类
 * 
 * <p>
 * <ul>
 * <li><code>ACTIVE</code>激活状态</li>
 * <li><code>LOCKED</code>被锁定状态</li>
 * <li><code>EXPIRED</code>过期状态</li>
 * <li><code>INACTIVE</code>未激活状态</li>
 * </ul>
 * </p>
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public enum AuthUserStateEnum {

	ACTIVE(0, "Active"),

	LOCKED(1, "Locked"),

	EXPIRED(2, "Expired"),

	INACTIVE(3, "Inactive");

	private int code;

	private String text;

	AuthUserStateEnum(int code, String text) {
		this.code = code;
		this.text = text;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return the message
	 */
	public String getText() {
		return text;
	}
}
