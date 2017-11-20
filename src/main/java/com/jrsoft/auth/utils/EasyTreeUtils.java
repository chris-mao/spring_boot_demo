/**
 * 
 */
package com.jrsoft.auth.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jrsoft.auth.entity.AuthPermission;
import com.jrsoft.common.EasyTreeNode;

/**
 * 递归获取树节点集合
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class EasyTreeUtils {

	/**
	 * 对传入的数据列表进行数据格式转换，创建树型结构，使其符合Easy Tree控件要求
	 * 
	 * @since 1.0
	 * @param permissions
	 * @return
	 */
	public final static List<EasyTreeNode> buildTree(List<AuthPermission> permissions) {
		List<EasyTreeNode> newTrees = new ArrayList<EasyTreeNode>();
		for (AuthPermission permission : permissions) {
			if (permission.getParentId() == 0) { // 父节点
				// 递归获取父节点下的子节点
				EasyTreeNode node = new EasyTreeNode(permission.getPermissionId(), permission.getPermissionText());
				node.setChildren(getChildrenNode(node.getId(), permissions));
				newTrees.add(node);
			}
		}
		return newTrees;
	}

	/**
	 * 递归生成树子节点
	 * 
	 * @since 1.0
	 * @param parentId
	 * @param permissions
	 * @return
	 */
	private final static List<EasyTreeNode> getChildrenNode(int parentId, List<AuthPermission> permissions) {
		List<EasyTreeNode> newTrees = new ArrayList<EasyTreeNode>();
		for (AuthPermission permission : permissions) {
			if (permission.getParentId() == 0) { // 是顶层节点，跳过
				continue;
			}
			if (permission.getParentId() == parentId) {
				EasyTreeNode node = new EasyTreeNode(permission.getPermissionId(), permission.getPermissionText());
				// 递归获取子节点下的子节点
				node.setChildren(getChildrenNode(node.getId(), permissions));
				// 设置叶子节点的attributes属性的数据
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("url", permission.getPermissionUrl());
				node.setAttributes(map);
				newTrees.add(node);
			}
		}
		return newTrees;
	}

}
