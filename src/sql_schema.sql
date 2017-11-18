/*
 Navicat Premium Data Transfer

 Source Server         : Localhost
 Source Server Type    : MySQL
 Source Server Version : 50624
 Source Host           : 127.0.0.1
 Source Database       : etao_v4

 Target Server Type    : MySQL
 Target Server Version : 50624
 File Encoding         : utf-8

 Date: 10/20/2017 13:39:29 PM
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `auth_permission`
-- ----------------------------
DROP TABLE IF EXISTS `auth_permission`;
CREATE TABLE IF NOT EXISTS `auth_permission` (
  `permission_id` smallint(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `permission_kind` enum('Menu','Function') DEFAULT NULL COMMENT '权限类型，分为“菜单”与“功能”两类，“菜单”显示在导航栏中，“功能”显示在每个独立的页面中',
  `permission_name` varchar(64) NOT NULL COMMENT '权限名称，唯一',
  `permission_text` varchar(64) NOT NULL COMMENT '显示面界面上的文字',
  `permission_url` varchar(128) DEFAULT NULL,
  `weight` smallint(6) NOT NULL COMMENT '菜单显示顺序，按从小到大的顺序显示',
  `parent_id` smallint(6) NOT NULL DEFAULT '0' COMMENT '父菜单ID',
  `available` bit(1) NOT NULL DEFAULT b'1',
  `created_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`permission_id`),
  UNIQUE KEY `permission_name` (`permission_name`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='权限表';

ALTER TABLE `auth_permission` 
CHANGE COLUMN `permission_kind` `permission_kind` enum('menu','function') DEFAULT NULL COMMENT '权限类型，分为“菜单”与“功能”两类，“菜单”显示在导航栏中，“功能”显示在每个独立的页面中', 
CHANGE COLUMN `weight` `weight` smallint(6) UNSIGNED DEFAULT NULL COMMENT '菜单显示顺序，按从小到大的顺序显示', 
CHANGE COLUMN `parent_id` `parent_id` smallint(6) UNSIGNED DEFAULT 0 COMMENT '父菜单ID';

-- ----------------------------
--  Table structure for `auth_role`
-- ----------------------------
DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role` (
  `role_id` smallint(10) unsigned NOT NULL AUTO_INCREMENT,
  `role_name` varchar(64) NOT NULL,
  `navigation` varchar(32) DEFAULT NULL COMMENT '角色对应的导航菜单数组键名',
  `available` bit(1) NOT NULL DEFAULT b'1',
  `created_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_name` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='角色表';

ALTER TABLE `auth_role` 
ADD COLUMN `role_description` varchar(128) DEFAULT 角色描述 AFTER `role_name`, 
CHANGE COLUMN `navigation` `navigation` varchar(32) DEFAULT NULL COMMENT '角色对应的导航菜单数组键名' AFTER `role_description`, 
CHANGE COLUMN `is_valid` `is_valid` bit(1) NOT NULL AFTER `navigation`, 
CHANGE COLUMN `available` `available` bit(1) NOT NULL AFTER `is_valid`, 
CHANGE COLUMN `created_time` `created_time` datetime NOT NULL AFTER `available`, 
CHANGE COLUMN `update_time` `update_time` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP AFTER `created_time`;

-- ----------------------------
--  Table structure for `auth_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `auth_role_permission`;
CREATE TABLE `auth_role_permission` (
  `role_id` smallint(10) unsigned NOT NULL AUTO_INCREMENT,
  `permission_id` smallint(10) unsigned NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `available` bit(1) NOT NULL DEFAULT b'1',
  `created_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
--  Table structure for `auth_user`
-- ----------------------------
DROP TABLE IF EXISTS `auth_user`;
CREATE TABLE `auth_user` (
  `user_id` smallint(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(64) NOT NULL,
  `user_psd` varchar(64) NOT NULL,
  `nick_name` varchar(64) NOT NULL,
  `email` varchar(64) DEFAULT NULL,
  `salt` varchar(128) DEFAULT NULL,
  `state` enum('Active','Locked','Expired','Inactive') DEFAULT 'Inactive',
  `role_id` smallint(10) DEFAULT NULL COMMENT 'PHP程序中使用',
  `available` bit(1) NOT NULL DEFAULT b'1',
  `created_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`) USING BTREE,
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=922 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
--  Table structure for `auth_user_delegate`
-- ----------------------------
DROP TABLE IF EXISTS `auth_user_delegate`;
CREATE TABLE `auth_user_delegate` (
  `from_user_id` smallint(5) unsigned NOT NULL DEFAULT '0',
  `to_user_id` smallint(5) unsigned NOT NULL DEFAULT '0',
  `start_date` date NOT NULL,
  `end_date` date DEFAULT NULL,
  `available` bit(1) NOT NULL DEFAULT b'1',
  `created_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`from_user_id`,`to_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户代理表';

-- ----------------------------
--  Table structure for `auth_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `auth_user_role`;
CREATE TABLE `auth_user_role` (
  `user_id` smallint(11) unsigned NOT NULL,
  `role_id` smallint(11) unsigned NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `available` bit(1) NOT NULL DEFAULT b'1',
  `created_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户扩展角色表';

-- ----------------------------
--  View structure for `vw_auth_role_permission`
-- ----------------------------
DROP VIEW IF EXISTS `vw_auth_role_permission`;
CREATE ALGORITHM = UNDEFINED DEFINER = `root`@`localhost` SQL SECURITY DEFINER VIEW `etao_v4`.`vw_auth_role_permission` AS SELECT auth_role.role_id, 
	auth_role.role_name, 
	auth_permission.permission_id, 
	auth_permission.permission_kind, 
	auth_permission.permission_name, 
	auth_permission.permission_text, 
	auth_permission.parent_id,
	auth_permission.weight, 
	auth_permission.permission_url, 
	auth_permission.created_time, 
	auth_permission.update_time, 
	auth_permission.available, 
	auth_role_permission.start_date, 
	auth_role_permission.end_date
FROM auth_role INNER JOIN auth_role_permission ON auth_role.role_id = auth_role_permission.role_id
	 INNER JOIN auth_permission ON auth_role_permission.permission_id = auth_permission.permission_id
ORDER BY auth_role.role_id, parent_id, weight;

-- ----------------------------
--  View structure for `vw_auth_user`
-- ----------------------------
DROP VIEW IF EXISTS `vw_auth_user`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_auth_user` AS select `auth_user`.`user_id` AS `user_id`,`auth_user`.`user_name` AS `user_name`,`auth_user`.`user_psd` AS `user_psd`,`auth_user`.`nick_name` AS `nick_name`,`auth_user`.`role_id` AS `role_id`,`auth_user`.`available` AS `available`,`auth_user`.`created_time` AS `created_time`,`auth_user`.`update_time` AS `update_time`,`auth_role`.`role_name` AS `role_name`,`auth_role`.`navigation` AS `navigation` from (`auth_user` join `auth_role` on((`auth_user`.`role_id` = `auth_role`.`role_id`)));

-- ----------------------------
--  View structure for `vw_auth_user_role`
-- ----------------------------
DROP VIEW IF EXISTS `vw_auth_user_role`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_auth_user_role` AS select `au`.`user_id` AS `user_id`,`ar`.`role_id` AS `role_id`,`ar`.`role_name` AS `role_name`,curdate() AS `start_date`,NULL AS `end_date`,`ar`.`navigation` AS `navigation`,`ar`.`created_time` AS `created_time`,`ar`.`update_time` AS `update_time` from (`auth_user` `au` join `auth_role` `ar` on((`au`.`role_id` = `ar`.`role_id`))) union select `aur`.`user_id` AS `user_id`,`ar`.`role_id` AS `role_id`,`ar`.`role_name` AS `role_name`,`aur`.`start_date` AS `start_date`,`aur`.`end_date` AS `end_date`,`ar`.`navigation` AS `navigation`,`ar`.`created_time` AS `created_time`,`ar`.`update_time` AS `update_time` from (`auth_user_role` `aur` join `auth_role` `ar` on((`aur`.`role_id` = `ar`.`role_id`))) where (`aur`.`available` = 1);

-- ----------------------------
--  Procedure structure for `sp_findRolePermissions`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_findRolePermissions`;
delimiter ;;
CREATE DEFINER = `root`@`localhost` PROCEDURE `sp_findRolePermissions`(IN v_role_id smallint)
    READS SQL DATA
BEGIN
  /*
   * 查询角色权限
   *
   * 参数 int v_role_id
   */
  SELECT ap.permission_id, ap.permission_kind, ap.permission_name, ap.permission_text, parent_id, weight, ap.permission_url, arp.created_time, arp.update_time 
  FROM auth_permission ap
  INNER JOIN auth_role_permission arp USING(permission_id)
  INNER JOIN auth_role ar using(role_id)
  WHERE arp.available = 1 AND arp.role_id = v_role_id AND (CURDATE() BETWEEN IFNULL(arp.start_date,CURDATE()) AND IFNULL(arp.end_date,CURDATE()))
  ORDER BY ap.parent_id, weight;
END
 ;;
delimiter ;

-- ----------------------------
--  Procedure structure for `sp_findUserPermissions`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_findUserPermissions`;
delimiter ;;
CREATE DEFINER = `root`@`localhost` PROCEDURE `sp_findUserPermissions`(IN v_user_id smallint)
    READS SQL DATA
BEGIN
  /*
   * 查询用户权限
   *
   * 参数 int v_role_id
   */

  #查询用户主角色权限
  SELECT  au.user_id, au.user_name, au.nick_name, au.role_id, ar.role_name, arp.permission_id, ap.permission_kind, ap.permission_name, ap.permission_text,
    ap.parent_id, ap.weight
  FROM auth_permission ap
  INNER JOIN auth_role_permission arp ON ap.permission_id = arp.permission_id
  INNER JOIN auth_role ar ON ar.role_id = arp.role_id
  INNER JOIN auth_user au ON au.role_id = ar.role_id
  WHERE arp.available = 1 AND au.user_id = v_user_id
  UNION
  #查询用户扩展角色权限
  SELECT  aur.user_id, au.user_name, au.nick_name, aur.role_id, ar.role_name, arp.permission_id, ap.permission_kind, ap.permission_name, ap.permission_text,
    ap.parent_id, ap.weight
  FROM auth_permission ap
  INNER JOIN auth_role_permission arp ON ap.permission_id = arp.permission_id
  INNER JOIN auth_role ar ON ar.role_id = arp.role_id
  INNER JOIN auth_user_role aur ON aur.role_id = ar.role_id
  INNER JOIN auth_user au ON au.user_id = aur.user_id
  WHERE arp.available = 1 AND au.user_id = v_user_id AND (CURDATE() BETWEEN IFNULL(aur.start_date,CURDATE()) AND IFNULL(aur.end_date,CURDATE())) 
AND (CURDATE() BETWEEN IFNULL(arp.start_date,CURDATE()) AND IFNULL(arp.end_date,CURDATE()));
END
 ;;
delimiter ;

-- ----------------------------
--  Procedure structure for `sp_findUserRoles`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_findUserRoles`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_findUserRoles`(IN v_user_id smallint)
    READS SQL DATA
BEGIN
  /*
   * 查询用户角色，及有效的扩展角色
   *
   * 参数 int v_role_id
   */

  #查询用户角色
  SELECT au.user_id, ar.role_id, ar.role_name, ar.role_description, CURDATE() as start_date, NULL as end_date, ar.navigation, ar.created_time, ar.update_time
  FROM auth_user au
  INNER JOIN auth_role ar ON au.role_id = ar.role_id
  WHERE au.user_id = v_user_id
  UNION
  #查询用户护展角色
  SELECT aur.user_id, ar.role_id, ar.role_name, ar.role_description, aur.start_date, aur.end_date, ar.navigation, ar.created_time, ar.update_time
  FROM auth_user_role aur
  INNER JOIN auth_role ar ON aur.role_id = ar.role_id
  WHERE aur.available = 1 AND aur.user_id = v_user_id AND (CURDATE() BETWEEN IFNULL(aur.start_date,CURDATE()) AND IFNULL(aur.end_date,CURDATE()));
END
 ;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
