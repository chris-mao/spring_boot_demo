/**
 * 
 */
package com.jrsoft.auth;

/**
 * com.jrsoft.auth AuthUserState
 * 
 * 系统用户状态枚举值
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
