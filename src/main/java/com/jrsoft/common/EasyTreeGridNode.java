/**
 * 
 */
package com.jrsoft.common;

import com.jrsoft.auth.entity.AuthPermission;

/**
 * 针对Easy UI的 TreeGrid控件对AuthPermission进行了扩展
 * <p>
 * 坑1：必须有<code>state</code>字段，否则前端无法呈现树型结构
 * 坑2：这是一个大坑！！！返回的JSON格式如果是{"total":100,"rows":[]}需要在rows中的每一个对象都要有
 * <code>_parentId</code>字段，且根结点的</code>_parentId<code>字段必须是<code>null</code>
 * ，否则无法正确呈现子节点。
 * </p>
 * <p>
 * 示例数据<code>
 * {"total":4,"rows":[{"permissionId":1,"permissionName":"Application\\Controller\\Index\\index","permissionUrl":null,"permissionText":null,"weight":0,"parentId":0,"permissionKind":"FUNCTION","available":true,"createdTime":1494494711000,"updateTime":1510760038000,"state":"closed","_parentId":null},{"permissionId":92,"permissionName":"authPermission","permissionUrl":null,"permissionText":"System Permission","weight":1,"parentId":0,"permissionKind":"MENU","available":true,"createdTime":1510582708000,"updateTime":1510760045000,"state":"closed","_parentId":null},{"permissionId":91,"permissionName":"authRole","permissionUrl":null,"permissionText":"System Role","weight":2,"parentId":0,"permissionKind":"MENU","available":true,"createdTime":1510582684000,"updateTime":1510760045000,"state":"closed","_parentId":null},{"permissionId":90,"permissionName":"authUser","permissionUrl":null,"permissionText":"System User","weight":3,"parentId":0,"permissionKind":"MENU","available":true,"createdTime":1510582636000,"updateTime":1510760044000,"state":"closed","_parentId":null}]}
 * </code>
 * </p>
 * 
 * 
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class EasyTreeGridNode extends AuthPermission {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>
	 * 针对EasyUI TreeGrid组件要求，必须有一个名为state的字段 其值集是 {'open', 'closed'}
	 * </p>
	 * 
	 * @since 1.1
	 */
	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	/**
	 * 父节点为0时需要转为<code>null</code>
	 * 
	 * @return
	 */
	public Integer get_parentId() {
		if (this.getParentId() == 0) {
			return null;
		}
		return this.getParentId();
	}

	public void set_parentId(Integer _parentId) {
		this.setParentId(_parentId);
	}

}
