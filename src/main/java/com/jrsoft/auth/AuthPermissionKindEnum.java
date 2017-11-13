/**
 * 
 */
package com.jrsoft.auth;

/**
 * 权限类别枚举类
 * 
 * <p>
 * <ul>
 * <li><code>meun</code>显示在导航栏中</li>
 * <li><code>function</code>显示在每个独立的页面中</li>
 * </ul>
 * </p>
 *
 * 
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public enum AuthPermissionKindEnum {

	MENU(0, "menu"),

	FUNCTION(1, "function");

	private int code;

	private String text;

	AuthPermissionKindEnum(int code, String text) {
		this.code = code;
		this.text = text;
	}

	public int getCode() {
		return code;
	}

	public String getText() {
		return text;
	}

}
