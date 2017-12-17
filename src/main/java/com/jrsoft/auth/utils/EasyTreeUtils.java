/**
 * 
 */
package com.jrsoft.auth.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private final static Logger logger = LoggerFactory.getLogger(EasyTreeUtils.class);

	/**
	 * 对传入的数据列表进行数据格式转换，创建树型结构，使其符合Easy Tree控件要求
	 * 
	 * @since 1.0
	 * @param permissions
	 * @return
	 */
	public final static List<? extends EasyTreeNode> buildTree(List<? extends EasyTreeNode> list) {
		logger.info("==>  开始构建树结构");
		List<EasyTreeNode> newTrees = new ArrayList<EasyTreeNode>();
		for (EasyTreeNode item : list) {
			if (item.getParentId() == 0) { // 父节点
				// 递归获取父节点下的子节点
				logger.info("buildTree 开始为节点 {} 添加子节点", item.getText());
				item.setChildren(getChildrenNode(item.getId(), list));
				logger.info("buildTree 为节点 {} 添加子节点结束", item.getText());
				newTrees.add(item);
			}
		}
		logger.info("==>  结束构建树结构  <==");
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
	private final static List<EasyTreeNode> getChildrenNode(int parentId, List<? extends EasyTreeNode> data) {
		List<EasyTreeNode> newTrees = new ArrayList<EasyTreeNode>();
		for (EasyTreeNode item : data) {
			if (item.getParentId() == 0) { // 是顶层节点，跳过
				continue;
			}
			if (item.getParentId() == parentId) {
				// 递归获取子节点下的子节点
				logger.info("setChildren 开始为节点 {} 添加子节点", item.getText());
				item.setChildren(getChildrenNode(item.getId(), data));
				logger.info("setChildren 为节点 {} 添加子节点结束", item.getText());
				// 设置叶子节点的attributes属性的数据
				// todo: 此处需要降低与AuthPermission类的耦合
				if (item.getClass().getName().equals(AuthPermission.class.getName())) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("url", ((AuthPermission) item).getPermissionUrl());
					item.setAttributes(map);
				}
				newTrees.add(item);
			}
		}
		return newTrees;
	}

}
