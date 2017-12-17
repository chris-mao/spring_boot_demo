/**
 * 
 */
package com.jrsoft.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于前端Easy Tree显示数据使用
 * 
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public abstract class EasyTreeNode {
	
	/**
	 * 
	 */
	private boolean checked;

	/**
	 * 子节点
	 */
	private List<EasyTreeNode> children;

	/**
	 * 添加到节点的自定义属性
	 */
	private Map<String, Object> attributes = new HashMap<String, Object>();
	
	public EasyTreeNode() {
		
	}
	
	public abstract int getId();
	
	public abstract String getText();
	
	public abstract int getParentId();

	/**
	 * @return the attributes
	 */
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the children
	 */
	public List<EasyTreeNode> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<EasyTreeNode> children) {
		this.children = children;
	}
	
	/**
	 * <p>
	 * 针对EasyUI TreeGrid组件要求，必须有一个名为state的字段 其值集是 {'open', 'closed'}
	 * </p>
	 * 
	 * @since 1.0
	 */
	public String getState() {
		if ((null == this.children) || this.children.isEmpty()) {
			return "open";
		}
		return "closed";
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
