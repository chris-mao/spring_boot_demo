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
CREATE TABLE `auth_permission` (
  `permission_id` smallint(10) unsigned NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(64) NOT NULL,
  `permission_url` varchar(128) DEFAULT NULL,
  `is_valid` bit(1) NOT NULL DEFAULT b'1',
  `available` bit(1) NOT NULL DEFAULT b'1',
  `created_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`permission_id`),
  UNIQUE KEY `permission_name` (`permission_name`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
--  Table structure for `auth_role`
-- ----------------------------
DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role` (
  `role_id` smallint(10) unsigned NOT NULL AUTO_INCREMENT,
  `role_name` varchar(64) NOT NULL,
  `navigation` varchar(32) DEFAULT NULL COMMENT '角色对应的导航菜单数组键名',
  `is_valid` bit(1) NOT NULL DEFAULT b'1',
  `available` bit(1) NOT NULL DEFAULT b'1',
  `created_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_name` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
--  Table structure for `auth_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `auth_role_permission`;
CREATE TABLE `auth_role_permission` (
  `role_id` smallint(10) unsigned NOT NULL AUTO_INCREMENT,
  `permission_id` smallint(10) unsigned NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `is_valid` bit(1) NOT NULL DEFAULT b'1',
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
  `state` enum('Active','Locked','Expired','Inactive') DEFAULT 'Active',
  `role_id` smallint(10) DEFAULT NULL COMMENT 'PHP程序中使用',
  `is_valid` bit(1) NOT NULL DEFAULT b'1',
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
  `is_valid` bit(1) NOT NULL DEFAULT b'1',
  `available` bit(1) NOT NULL DEFAULT b'1',
  `created_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户扩展角色表';

-- ----------------------------
--  Table structure for `customer`
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `customer_id` int(10) NOT NULL,
  `customer_number` varchar(10) NOT NULL,
  `customer_name` varchar(200) NOT NULL,
  `full_name` varchar(200) NOT NULL,
  `chinese_name` varchar(200) DEFAULT NULL,
  `group_name` varchar(128) DEFAULT NULL,
  `bill_to_site_use_id` int(10) DEFAULT NULL,
  `ship_to_site_use_id` int(10) DEFAULT NULL,
  `bill_address` varchar(200) DEFAULT NULL COMMENT 'bill to',
  `customer_address` varchar(200) DEFAULT NULL COMMENT 'sold to',
  `country` varchar(32) DEFAULT NULL,
  `region` varchar(32) DEFAULT NULL COMMENT '国内客户为LOCAL，国外客户为EXPORT',
  `area` varchar(32) DEFAULT NULL,
  `contact_person` varchar(256) DEFAULT NULL,
  `telephone` varchar(256) DEFAULT NULL,
  `fax` varchar(256) DEFAULT NULL,
  `cell_phone` varchar(256) DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `deliver_type` varchar(64) DEFAULT NULL,
  `shipping_term_id` smallint(6) DEFAULT NULL,
  `payment_term_id` smallint(6) DEFAULT NULL,
  `customer_type_id` smallint(6) DEFAULT NULL,
  `rebate_allowed` tinyint(4) DEFAULT '1' COMMENT '是否允许使用返利',
  `sp_allowed` tinyint(4) DEFAULT '1' COMMENT '是否允许使用特价',
  `monitor_allowed` tinyint(4) DEFAULT '0' COMMENT '值为1表示该客户订单需要Sales Administrator审批后才生效',
  `credit_allowed` tinyint(4) DEFAULT '0',
  `plant_id` int(10) DEFAULT NULL,
  `user_id` int(10) DEFAULT NULL,
  `is_valid` tinyint(4) NOT NULL DEFAULT '1',
  `available` bit(1) NOT NULL DEFAULT b'1',
  `created_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `sync_time` datetime DEFAULT NULL,
  `sync_status` tinyint(4) DEFAULT NULL COMMENT '0:failure;1:success',
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `customer_number` (`customer_id`,`customer_number`,`customer_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户表';

-- ----------------------------
--  Table structure for `customer_communicator`
-- ----------------------------
DROP TABLE IF EXISTS `customer_communicator`;
CREATE TABLE `customer_communicator` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `customer_id` int(10) NOT NULL,
  `employee_id` int(10) NOT NULL,
  `created_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `customer_id` (`customer_id`,`employee_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2792 DEFAULT CHARSET=utf8 COMMENT='客户与客服对应关系';

-- ----------------------------
--  Table structure for `customer_sales`
-- ----------------------------
DROP TABLE IF EXISTS `customer_sales`;
CREATE TABLE `customer_sales` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `customer_id` int(10) unsigned NOT NULL,
  `site_id` int(10) unsigned DEFAULT NULL,
  `employee_id` int(10) unsigned NOT NULL,
  `created_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `customer_id` (`customer_id`,`employee_id`,`site_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2744 DEFAULT CHARSET=utf8 COMMENT='客户与销售对应关系';

-- ----------------------------
--  Table structure for `customer_sites`
-- ----------------------------
DROP TABLE IF EXISTS `customer_sites`;
CREATE TABLE `customer_sites` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `customer_id` int(10) NOT NULL,
  `ou_id` int(10) NOT NULL,
  `ou_name` varchar(200) NOT NULL,
  `site_purpose` varchar(64) NOT NULL,
  `site_id` int(10) NOT NULL,
  `site_address` varchar(200) NOT NULL,
  `is_valid` tinyint(4) NOT NULL DEFAULT '1',
  `available` bit(1) NOT NULL DEFAULT b'1',
  `created_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `customer_id` (`customer_id`,`site_purpose`,`site_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3329 DEFAULT CHARSET=utf8 COMMENT='客户地址信息表';

-- ----------------------------
--  Table structure for `customer_user`
-- ----------------------------
DROP TABLE IF EXISTS `customer_user`;
CREATE TABLE `customer_user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `customer_id` int(10) NOT NULL,
  `user_id` int(10) NOT NULL,
  `created_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `customer_id` (`customer_id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=289 DEFAULT CHARSET=utf8 COMMENT='客户登录帐号表';

-- ----------------------------
--  Table structure for `employee`
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `employee_id` int(10) NOT NULL AUTO_INCREMENT,
  `employee_name` varchar(64) NOT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `fax` varchar(32) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `oracle_account` varchar(32) DEFAULT NULL,
  `user_id` int(10) DEFAULT NULL,
  `report_to` int(10) DEFAULT '0',
  `is_valid` tinyint(4) NOT NULL DEFAULT '1',
  `available` bit(1) NOT NULL DEFAULT b'1',
  `created_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`employee_id`),
  UNIQUE KEY `employee_name` (`employee_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='员工表，包含客服／销售等人员';

-- ----------------------------
--  Table structure for `employee_customer`
-- ----------------------------
DROP TABLE IF EXISTS `employee_customer`;
CREATE TABLE `employee_customer` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `customer_id` int(10) unsigned NOT NULL,
  `employee_id` smallint(10) unsigned NOT NULL,
  `ou_id` smallint(5) unsigned NOT NULL DEFAULT '0',
  `created_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `customer_id` (`customer_id`,`employee_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2792 DEFAULT CHARSET=utf8 COMMENT='客户与客服对应关系';

-- ----------------------------
--  Table structure for `model`
-- ----------------------------
DROP TABLE IF EXISTS `model`;
CREATE TABLE `model` (
  `model_id` int(10) NOT NULL,
  `model_name` varchar(64) NOT NULL,
  `model_desc` varchar(128) DEFAULT NULL,
  `chinese_desc` varchar(4000) DEFAULT NULL,
  `source` varchar(128) NOT NULL DEFAULT '',
  `weight_unit` varchar(5) DEFAULT NULL,
  `net_weight` decimal(10,2) DEFAULT NULL,
  `gross_weight` decimal(10,2) DEFAULT NULL,
  `is_valid` tinyint(4) NOT NULL DEFAULT '1',
  `available` bit(1) NOT NULL DEFAULT b'1',
  `created_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `sync_time` datetime DEFAULT NULL,
  `sync_status` tinyint(4) DEFAULT NULL COMMENT '0:failure;1:success',
  PRIMARY KEY (`model_id`,`source`),
  UNIQUE KEY `model_id` (`model_id`,`source`) USING BTREE,
  KEY `model_name` (`model_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品型号';

-- ----------------------------
--  Table structure for `order_import_interface`
-- ----------------------------
DROP TABLE IF EXISTS `order_import_interface`;
CREATE TABLE `order_import_interface` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `customer_account` varchar(10) NOT NULL,
  `customer_po` varchar(32) NOT NULL,
  `ou_name` varchar(32) NOT NULL,
  `order_type` varchar(64) NOT NULL,
  `inventory_item` varchar(32) DEFAULT NULL,
  `customer_item` varchar(32) DEFAULT NULL,
  `qty` int(11) NOT NULL,
  `currency` varchar(10) NOT NULL,
  `price` float NOT NULL,
  `request_date` date NOT NULL,
  `deliver_to` varchar(200) DEFAULT NULL,
  `imported_by` varchar(255) DEFAULT NULL,
  `imported_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `plant`
-- ----------------------------
DROP TABLE IF EXISTS `plant`;
CREATE TABLE `plant` (
  `plant_id` int(10) NOT NULL AUTO_INCREMENT,
  `plant_code` varchar(10) NOT NULL,
  `plant_name` varchar(128) NOT NULL,
  `full_name` varchar(128) NOT NULL,
  `is_valid` tinyint(4) NOT NULL DEFAULT '1',
  `available` bit(1) NOT NULL DEFAULT b'1',
  `created_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`plant_id`),
  UNIQUE KEY `plant_code` (`plant_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1125 DEFAULT CHARSET=utf8 COMMENT='工厂表';

-- ----------------------------
--  Table structure for `plant_bank`
-- ----------------------------
DROP TABLE IF EXISTS `plant_bank`;
CREATE TABLE `plant_bank` (
  `bank_id` smallint(4) NOT NULL AUTO_INCREMENT,
  `plant_id` smallint(4) NOT NULL,
  `currency` varchar(10) NOT NULL,
  `bank_name` varchar(200) NOT NULL,
  `account_name` varchar(200) NOT NULL,
  `bank_account` varchar(64) NOT NULL,
  `tax_number` varchar(64) DEFAULT NULL,
  `swift_code` varchar(64) DEFAULT NULL,
  `web_site` varchar(200) DEFAULT NULL,
  `is_valid` tinyint(4) NOT NULL DEFAULT '1',
  `available` bit(1) NOT NULL DEFAULT b'1',
  `created_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`bank_id`),
  KEY `plant_id` (`plant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='工厂银行帐号表';

-- ----------------------------
--  Table structure for `plant_model`
-- ----------------------------
DROP TABLE IF EXISTS `plant_model`;
CREATE TABLE `plant_model` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `plant_id` int(6) NOT NULL,
  `model_id` int(10) NOT NULL,
  `family_id` int(10) DEFAULT NULL,
  `series_id` int(10) DEFAULT NULL,
  `is_valid` tinyint(4) NOT NULL DEFAULT '1',
  `available` bit(1) NOT NULL DEFAULT b'1',
  `created_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `sync_time` datetime DEFAULT NULL,
  `sync_status` tinyint(4) DEFAULT NULL COMMENT '0:failure;1:success',
  PRIMARY KEY (`id`),
  KEY `model_id` (`model_id`),
  KEY `family_id` (`family_id`),
  KEY `series_id` (`series_id`),
  KEY `plant_id` (`plant_id`) USING BTREE,
  KEY `combination_id` (`plant_id`,`model_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34860 DEFAULT CHARSET=utf8 COMMENT='工厂型号表';

-- ----------------------------
--  Table structure for `plant_org`
-- ----------------------------
DROP TABLE IF EXISTS `plant_org`;
CREATE TABLE `plant_org` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `plant_id` int(11) DEFAULT NULL,
  `source` varchar(111) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `plant_id` (`plant_id`,`source`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='工厂库存组织表';

-- ----------------------------
--  Table structure for `price_list_header`
-- ----------------------------
DROP TABLE IF EXISTS `price_list_header`;
CREATE TABLE `price_list_header` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `header_id` int(4) NOT NULL,
  `price_list_name` varchar(128) NOT NULL,
  `currency` varchar(10) NOT NULL,
  `qualifier_attribute` varchar(64) DEFAULT NULL,
  `qualifier_attr_value` varchar(240) DEFAULT NULL,
  `start_date_active` datetime DEFAULT NULL,
  `end_date_active` datetime DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `qualifier_attr_value` (`header_id`,`qualifier_attr_value`) USING BTREE,
  KEY `price_list_name` (`price_list_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3584 DEFAULT CHARSET=utf8 COMMENT='价格主表';

-- ----------------------------
--  Table structure for `price_list_line`
-- ----------------------------
DROP TABLE IF EXISTS `price_list_line`;
CREATE TABLE `price_list_line` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `line_id` int(4) NOT NULL,
  `header_id` int(4) NOT NULL,
  `item_id` int(4) NOT NULL,
  `uom` varchar(10) DEFAULT NULL,
  `unit_price` float DEFAULT NULL,
  `start_date_active` datetime DEFAULT NULL,
  `end_date_active` datetime DEFAULT NULL,
  `qty_from` int(11) DEFAULT '-222',
  `qty_to` bigint(11) DEFAULT '-222',
  `created_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `line_id` (`header_id`,`item_id`,`start_date_active`,`end_date_active`,`qty_from`,`qty_to`) USING BTREE,
  KEY `header_id` (`header_id`,`item_id`),
  KEY `start_date_active` (`start_date_active`,`end_date_active`)
) ENGINE=InnoDB AUTO_INCREMENT=725573 DEFAULT CHARSET=utf8 COMMENT='价格明细表';

-- ----------------------------
--  Table structure for `sales_order`
-- ----------------------------
DROP TABLE IF EXISTS `sales_order`;
CREATE TABLE `sales_order` (
  `order_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `customer_id` int(10) NOT NULL,
  `plant_id` int(10) NOT NULL,
  `site_id` int(10) NOT NULL,
  `model_id` int(10) NOT NULL,
  `rtd` date NOT NULL,
  `qty` int(10) NOT NULL DEFAULT '1',
  `currency` varchar(4) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `price_list_name` varchar(128) NOT NULL,
  `customer_po` varchar(50) NOT NULL,
  `bill_to` varchar(200) NOT NULL,
  `ship_to` varchar(200) NOT NULL,
  `deliver_to` varchar(200) NOT NULL,
  `mail_address` varchar(100) NOT NULL DEFAULT '',
  `remark` varchar(200) NOT NULL DEFAULT '',
  `oracle_so` varchar(10) NOT NULL DEFAULT '',
  `rebate_used` decimal(10,2) NOT NULL DEFAULT '0.00',
  `credit_used` decimal(10,2) NOT NULL DEFAULT '0.00',
  `BTB` tinyint(4) NOT NULL DEFAULT '0',
  `BTB_desc` varchar(200) NOT NULL DEFAULT '',
  `end_use` varchar(64) NOT NULL DEFAULT '',
  `is_valid` tinyint(4) NOT NULL DEFAULT '1',
  `created_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`),
  KEY `customer_id` (`customer_id`) USING BTREE,
  KEY `plant_id` (`plant_id`) USING BTREE,
  KEY `site_id` (`site_id`) USING BTREE,
  KEY `model_id` (`model_id`) USING BTREE,
  KEY `rtd` (`rtd`) USING BTREE,
  KEY `customer_po` (`customer_po`) USING BTREE,
  KEY `oracle_so` (`oracle_so`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='客户订单';

-- ----------------------------
--  Table structure for `shopping_cart`
-- ----------------------------
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `customer_id` int(10) NOT NULL,
  `site_id` int(10) NOT NULL,
  `model_id` int(10) NOT NULL,
  `rtd` date NOT NULL,
  `qty` int(10) NOT NULL,
  `currency` varchar(8) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `price_header_id` int(11) NOT NULL,
  `price_list_name` varchar(128) NOT NULL,
  `check_out` tinyint(4) NOT NULL DEFAULT '1',
  `is_valid` tinyint(4) NOT NULL DEFAULT '1',
  `available` bit(1) NOT NULL DEFAULT b'1',
  `created_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `customreId` (`customer_id`),
  KEY `model_id` (`model_id`),
  KEY `site_id` (`site_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COMMENT='购物车表';

-- ----------------------------
--  Table structure for `sys_access_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_access_log`;
CREATE TABLE `sys_access_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `user_name` varchar(32) NOT NULL,
  `remote_addr` varchar(32) NOT NULL,
  `remote_port` varchar(16) NOT NULL,
  `route` varchar(128) NOT NULL,
  `controller` varchar(64) NOT NULL,
  `action` varchar(64) NOT NULL,
  `parameters` varchar(128) DEFAULT NULL,
  `request_method` varchar(16) NOT NULL,
  `user_agent` varchar(512) NOT NULL,
  `access_time` datetime NOT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_name` (`user_name`),
  KEY `access_time` (`access_time`),
  KEY `controller` (`controller`,`action`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统访问日志表';

-- ----------------------------
--  Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` smallint(4) unsigned NOT NULL AUTO_INCREMENT,
  `module_id` smallint(4) NOT NULL,
  `menu_name` varchar(255) NOT NULL,
  `menu_text` varchar(255) NOT NULL,
  `route_name` varchar(128) DEFAULT NULL,
  `url_path` varchar(255) DEFAULT NULL,
  `parent_menu_id` smallint(4) NOT NULL DEFAULT '0',
  `access_control` bit(1) NOT NULL DEFAULT b'1',
  `created_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`menu_id`),
  UNIQUE KEY `module` (`module_id`,`menu_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='系统菜单表，暂没使用';

-- ----------------------------
--  Table structure for `sys_module`
-- ----------------------------
DROP TABLE IF EXISTS `sys_module`;
CREATE TABLE `sys_module` (
  `module_id` smallint(4) unsigned NOT NULL AUTO_INCREMENT,
  `module_name` varchar(32) NOT NULL,
  `module_desc` varchar(128) DEFAULT NULL,
  `module_author` varchar(32) NOT NULL,
  `module_version` varchar(8) NOT NULL DEFAULT '1.0',
  `effective_from` date NOT NULL,
  `effective_to` date DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`module_id`),
  UNIQUE KEY `module_name` (`module_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='系统模块表，暂没使用';

-- ----------------------------
--  View structure for `vw_auth_role_permission`
-- ----------------------------
DROP VIEW IF EXISTS `vw_auth_role_permission`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_auth_role_permission` AS select `auth_role`.`role_id` AS `role_id`,`auth_role`.`role_name` AS `role_name`,`auth_permission`.`permission_id` AS `permission_id`,`auth_permission`.`permission_name` AS `permission_name`,`auth_permission`.`permission_url` AS `permission_url`,`auth_permission`.`created_time` AS `created_time`,`auth_permission`.`update_time` AS `update_time`,`auth_permission`.`available` AS `available`,`auth_role_permission`.`start_date` AS `start_date`,`auth_role_permission`.`end_date` AS `end_date` from ((`auth_role` join `auth_role_permission` on((`auth_role`.`role_id` = `auth_role_permission`.`role_id`))) join `auth_permission` on((`auth_role_permission`.`permission_id` = `auth_permission`.`permission_id`)));

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
--  View structure for `vw_customer_sites`
-- ----------------------------
DROP VIEW IF EXISTS `vw_customer_sites`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_customer_sites` AS select `customer`.`customer_id` AS `customer_id`,`customer`.`customer_number` AS `customer_number`,`customer`.`customer_name` AS `customer_name`,`customer_sites`.`ou_id` AS `ou_id`,`customer_sites`.`ou_name` AS `ou_name`,`customer_sites`.`site_purpose` AS `site_purpose`,`customer_sites`.`site_id` AS `site_id`,`customer_sites`.`site_address` AS `site_address`,`customer_sites`.`available` AS `available`,`customer_sites`.`created_time` AS `created_time`,`customer_sites`.`update_time` AS `update_time`,`customer_sites`.`id` AS `id` from (`customer` join `customer_sites` on((`customer`.`customer_id` = `customer_sites`.`customer_id`)));

-- ----------------------------
--  View structure for `vw_distinct_model`
-- ----------------------------
DROP VIEW IF EXISTS `vw_distinct_model`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_distinct_model` AS select distinct `model`.`model_id` AS `model_id`,`model`.`model_name` AS `model_name`,`model`.`model_desc` AS `model_desc`,`model`.`chinese_desc` AS `chinese_desc` from `model`;

-- ----------------------------
--  View structure for `vw_price_list_header`
-- ----------------------------
DROP VIEW IF EXISTS `vw_price_list_header`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_price_list_header` AS select `vw_customer_sites`.`customer_id` AS `customer_id`,`vw_customer_sites`.`customer_number` AS `customer_number`,`vw_customer_sites`.`customer_name` AS `customer_name`,`vw_customer_sites`.`ou_id` AS `ou_id`,`vw_customer_sites`.`ou_name` AS `ou_name`,`price_list_header`.`header_id` AS `header_id`,`price_list_header`.`price_list_name` AS `price_list_name`,`price_list_header`.`currency` AS `currency`,`price_list_header`.`qualifier_attribute` AS `qualifier_attribute`,`price_list_header`.`qualifier_attr_value` AS `site_id`,`price_list_header`.`start_date_active` AS `start_date_active`,`price_list_header`.`end_date_active` AS `end_date_active`,`price_list_header`.`created_time` AS `created_time`,`price_list_header`.`update_time` AS `updated_time` from (`price_list_header` join `vw_customer_sites` on(((`price_list_header`.`qualifier_attr_value` = `vw_customer_sites`.`site_id`) and (`vw_customer_sites`.`site_purpose` = 'BILL_TO')))) order by `vw_customer_sites`.`customer_name`,`vw_customer_sites`.`ou_name`;

-- ----------------------------
--  View structure for `vw_price_list_line`
-- ----------------------------
DROP VIEW IF EXISTS `vw_price_list_line`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_price_list_line` AS select `pll`.`line_id` AS `line_id`,`pll`.`item_id` AS `item_id`,`m`.`model_name` AS `item_name`,`m`.`model_desc` AS `item_desc`,`pll`.`uom` AS `uom`,`pll`.`unit_price` AS `unit_price`,`pll`.`start_date_active` AS `start_date_active`,`pll`.`end_date_active` AS `end_date_active`,`pll`.`qty_from` AS `qty_from`,`pll`.`qty_to` AS `qty_to` from (`price_list_line` `pll` join `vw_distinct_model` `m` on((`pll`.`item_id` = `m`.`model_id`)));

-- ----------------------------
--  View structure for `vw_shopping_cart`
-- ----------------------------
DROP VIEW IF EXISTS `vw_shopping_cart`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_shopping_cart` AS select distinct `shopping_cart`.`id` AS `id`,`shopping_cart`.`customer_id` AS `customer_id`,`shopping_cart`.`site_id` AS `site_id`,`shopping_cart`.`model_id` AS `model_id`,`shopping_cart`.`rtd` AS `rtd`,`shopping_cart`.`qty` AS `qty`,`shopping_cart`.`currency` AS `currency`,`shopping_cart`.`price` AS `price`,`shopping_cart`.`price_header_id` AS `price_header_id`,`shopping_cart`.`price_list_name` AS `price_list_name`,`shopping_cart`.`available` AS `available`,`shopping_cart`.`created_time` AS `created_time`,`shopping_cart`.`update_time` AS `update_time`,`model`.`model_name` AS `model_name`,`model`.`model_desc` AS `model_desc`,`shopping_cart`.`check_out` AS `check_out` from (`shopping_cart` join `model` on((`shopping_cart`.`model_id` = `model`.`model_id`)));

-- ----------------------------
--  View structure for `vw_sys_menu`
-- ----------------------------
DROP VIEW IF EXISTS `vw_sys_menu`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_sys_menu` AS select `sys_menu`.`menu_id` AS `menu_id`,`sys_menu`.`module_id` AS `module_id`,`sys_menu`.`menu_name` AS `menu_name`,`sys_menu`.`menu_text` AS `menu_text`,`sys_menu`.`route_name` AS `route_name`,`sys_menu`.`url_path` AS `url_path`,`sys_menu`.`parent_menu_id` AS `parent_menu_id`,`sys_menu`.`access_control` AS `access_control`,`sys_menu`.`created_time` AS `created_time`,`sys_menu`.`update_time` AS `update_time`,`sys_module`.`module_name` AS `module_name` from (`sys_menu` join `sys_module` on((`sys_menu`.`module_id` = `sys_module`.`module_id`)));

-- ----------------------------
--  Procedure structure for `sp_createMenuTree`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_createMenuTree`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_createMenuTree`(IN menuId SMALLINT(4),IN nDepth INT)
BEGIN
DECLARE done INT DEFAULT 0;
DECLARE  module_name varchar(255);
DECLARE  menu_id smallint(4);
DECLARE  menu_name varchar(255);
DECLARE  route_name varchar(128);
DECLARE  url_path varchar(255);
DECLARE  parent_menu_id smallint(4);

DECLARE cur1 CURSOR FOR 
SELECT  module_name, menu_id, menu_name, route_name, url_path, parent_menu_id 
FROM sys_menu INNER JOIN sys_module USING(module_id)
WHERE parent_menu_id = menuId;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

INSERT INTO tmpLst(`module_name`, `menu_id`, `menu_name`, `route_name`, `url_path`, `parent_menu_id`, depth) 
VALUES (module_name, menu_id, menu_name, route_name, url_path, parent_menu_id, depth);

SET @@max_sp_recursion_depth = 10;
OPEN cur1;

FETCH cur1 INTO module_name, menu_id, menu_name, route_name, url_path, parent_menu_id;
WHILE done=0 DO
CALL sp_createMenuTree(menu_id, nDepth + 1);
FETCH cur1 INTO module_name, menu_id, menu_name, route_name, url_path, parent_menu_id;
END WHILE;

CLOSE cur1;
END
 ;;
delimiter ;

-- ----------------------------
--  Procedure structure for `sp_findAvailablePriceDetail`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_findAvailablePriceDetail`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_findAvailablePriceDetail`(IN `v_site_id` int)
    READS SQL DATA
BEGIN
  /*
   * 查询指定客户BILL TO上可用的价格表明细
   * 如果客户有多个有效的价格表，则会将所有价格表的明细一并返回
   * 参数 int v_site_id
   */
  SELECT DISTINCT plh.header_id, plh.price_list_name, plh.currency, model.model_name, 
    line_id, uom, unit_price, qty_from, qty_to, pll.start_date_active, pll.end_date_active
  FROM price_list_line pll
  LEFT JOIN price_list_header plh USING(header_id)
  INNER JOIN model ON pll.item_id = model.model_id
  WHERE (NOW() BETWEEN pll.start_date_active AND IFNULL(pll.end_date_active, NOW()))
  AND qualifier_attr_value = v_site_id
  ORDER BY price_list_name, model_name, qty_from, pll.start_date_active DESC;
END
 ;;
delimiter ;

-- ----------------------------
--  Procedure structure for `sp_findAvailablePriceDetail2`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_findAvailablePriceDetail2`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_findAvailablePriceDetail2`(IN `v_ou_Id` int,IN `v_customer_number` varchar(10))
    READS SQL DATA
BEGIN
  /*
   * 查询指定客户、指定OU下可用的价格表明细
   * 如果客户有多个有效的价格表，则会将所有价格表的明细一并返回
   * 参数 int         v_ou_id
   * 参数 varchar(10) v_customer_number
   *
   * 依赖函数 fn_findCustomerBillToSiteId
   */
  CALL sp_findAvailablePriceDetail(fn_findCustomerBillToSiteId(v_ou_id, v_customer_number));
END
 ;;
delimiter ;

-- ----------------------------
--  Procedure structure for `sp_findAvailablePriceList`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_findAvailablePriceList`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_findAvailablePriceList`(IN `v_site_id` int)
    READS SQL DATA
BEGIN
  /*
   * 查询指定客户的可用价格表清单
   *
   * 参数 int v_site_id
   */

  SELECT header_id, price_list_name, currency, start_date_active, end_date_active
  FROM price_list_header
  WHERE (NOW() BETWEEN start_date_active AND IFNULL(end_date_active, NOW()))
  AND qualifier_attr_value = v_site_id
  ORDER BY price_list_name;
END
 ;;
delimiter ;

-- ----------------------------
--  Procedure structure for `sp_findAvailablePriceList2`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_findAvailablePriceList2`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_findAvailablePriceList2`(IN `v_ou_id` int,IN `v_customer_number` varchar(10))
    READS SQL DATA
BEGIN
  /*
   * 查询指定客户在指定OU下的可用价格表清单
   *
   * 参数 int         v_ou_id
   * 参数 varchar(10) v_customer_number
   *
   * 依赖函数 fn_findCustomerBillToSiteId
   */

  CALL sp_findAvailablePriceList(fn_findCustomerBillToSiteId(v_ou_id, v_customer_number));
END
 ;;
delimiter ;

-- ----------------------------
--  Procedure structure for `sp_findAvailablePriceListWithItem`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_findAvailablePriceListWithItem`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_findAvailablePriceListWithItem`(IN v_site_id int, IN v_item_id int)
    READS SQL DATA
BEGIN
  /*
   * 查询指定客户、指定物料的可用价格表清单
   *
   * 参数 int v_site_id
   * 参数 int v_item_id
   */

  SELECT 
	header_id,
	price_list_name,
	currency,
	start_date_active,
	end_date_active
FROM
	price_list_header plh
WHERE
	header_id IN (
		SELECT DISTINCT
			header_id
		FROM
			price_list_line
		WHERE
			item_id = v_item_id
		AND (
			NOW() BETWEEN start_date_active
			AND IFNULL(end_date_active, NOW())
		)
	)
AND qualifier_attr_value = v_site_id;
END
 ;;
delimiter ;

-- ----------------------------
--  Procedure structure for `sp_findAvailablePriceListWithItem2`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_findAvailablePriceListWithItem2`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_findAvailablePriceListWithItem2`(IN v_ou_id int, IN v_customer_number varchar(10), IN v_item_id int)
    READS SQL DATA
BEGIN
  /*
   * 查询指定客户、指定物料的可用价格表清单
   *
   * 参数 int         v_ou_id
   * 参数 varchar(10) v_customer_number
   * 参数 int         v_item_id
   *
   * 依赖函数 fn_findCustomerBillToSiteId
   */
  CALL sp_findAvailablePriceListWithItem(fn_findCustomerBillToSiteId(v_ou_id, v_customer_number), v_item_id);
END
 ;;
delimiter ;

-- ----------------------------
--  Procedure structure for `sp_findAvailableSellingPrice`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_findAvailableSellingPrice`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_findAvailableSellingPrice`(IN `v_header_id` int,IN `v_item_id` int)
    READS SQL DATA
BEGIN
  /*
   * 在指定的价格表中查询指定物料的有效销售价格明细
   *
   * 如果只有单一销售价格，则返回当前有效的销售价格，按生效日期倒排序
   * 如果有Price Break，则返回当前有效的所有Price Break Line， 按起购数量和生效日期(倒排序)排序
   *
   * 参数 int v_header_id
   * 参数 int v_item_id
   */
  IF (SELECT CAST(AVG(qty_from) AS SIGNED) 
      FROM price_list_line pll
      LEFT JOIN price_list_header plh USING(header_id)
      WHERE plh.header_id = v_header_id AND v_item_id = v_item_id AND (NOW() BETWEEN pll.start_date_active AND IFNULL(pll.end_date_active, NOW()))) = -222 
  THEN #如果qty_from的平均值是-222，则表明该物料只有一个价格，按生效日期倒排序取第一条记录
    SELECT plh.price_list_name, plh.currency, pll.item_id as model_id, uom, unit_price, qty_from, qty_to
    FROM price_list_line pll
    LEFT JOIN price_list_header plh USING(header_id)
    WHERE plh.header_id = v_header_id AND item_id = v_item_id AND (NOW() BETWEEN pll.start_date_active AND IFNULL(pll.end_date_active, NOW()))
    ORDER BY qty_from, pll.start_date_active DESC LIMIT 1;
  ELSE #如果qty_from的平均不值是-222，则表明该物料有区间价格(Price Break)，按起始订购量正排序、生效日期倒排序
    SELECT plh.price_list_name, plh.currency, pll.item_id as model_id, uom, unit_price, qty_from, qty_to
    FROM price_list_line pll
    LEFT JOIN price_list_header plh USING(header_id)
    WHERE plh.header_id = v_header_id AND item_id = v_item_id AND (NOW() BETWEEN pll.start_date_active AND IFNULL(pll.end_date_active, NOW()))
    ORDER BY qty_from, pll.start_date_active DESC;
  END IF;
END
 ;;
delimiter ;

-- ----------------------------
--  Procedure structure for `sp_findCustomerByCredential`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_findCustomerByCredential`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_findCustomerByCredential`(IN v_user_name varchar(64))
    READS SQL DATA
BEGIN
  /*
   * 根据用户名查询对应的客户信息
   *
   * 参数 varchar(64) v_user_name
   */
  SELECT c.customer_id, c.customer_number, c.customer_name, c.full_name, c.country, c.available, c.created_time, c.update_time
  FROM customer c
  INNER JOIN customer_user cu USING(customer_id)
  WHERE cu.user_id = (SELECT user_id from auth_user WHERE user_name = v_user_name) AND c.available = 1
  LIMIT 1;
END
 ;;
delimiter ;

-- ----------------------------
--  Procedure structure for `sp_findCustomersByEmployee`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_findCustomersByEmployee`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_findCustomersByEmployee`(IN v_employee_id smallint, IN v_ou_id smallint)
    READS SQL DATA
BEGIN
  /*
   * 按员工编号查询对应的客户清单
   * 如果第二个参数为0，表示查询该客户在三个OU中对应的所有客服和销售人员
   * 否则表示查询指定OU下的客服和销售人员
   *
   * 参数 smallint v_employee_id
   * 参数 smallint v_ou_id，如果为0表示忽略OU
   *
   */

  IF v_ou_id = 0 THEN
    SELECT c.customer_id, c.customer_number, c.customer_name
    FROM customer c
    INNER JOIN employee_customer ec USING(customer_id)
    INNER JOIN employee e USING(employee_id)
    WHERE e.employee_id = v_employee_id AND c.available = 1
    ORDER BY c.customer_name;
  ELSE
    SELECT c.customer_id, c.customer_number, c.customer_name
    FROM customer c
    INNER JOIN employee_customer ec USING(customer_id)
    INNER JOIN employee e USING(employee_id)
    WHERE e.employee_id = v_employee_id AND ec.ou_id IN (0 /*代表跨OU的客服*/, v_ou_id) AND c.available = 1
    ORDER BY c.customer_name;
  END IF;
END
 ;;
delimiter ;

-- ----------------------------
--  Procedure structure for `sp_findEmployeeByCredential`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_findEmployeeByCredential`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_findEmployeeByCredential`(IN v_user_name varchar(64))
    READS SQL DATA
BEGIN
  /*
   * 根据用户名查询对应的员工信息
   *
   * 参数 varchar(64) v_user_name
   */
  SELECT e.employee_id, e.employee_name, e.phone, e.fax, e.email, e.created_time, e.update_time
  FROM employee e
  WHERE e.user_id = (SELECT user_id from auth_user WHERE user_name = v_user_name) AND e.available = 1
  LIMIT 1;
END
 ;;
delimiter ;

-- ----------------------------
--  Procedure structure for `sp_findEmployeesByCustomer`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_findEmployeesByCustomer`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_findEmployeesByCustomer`(IN v_customer_number varchar(10), IN `v_ou_id` smallint)
    READS SQL DATA
BEGIN
  /*
   * 按客户代码查询对应的员工信息(客服、销售)
   * 如果第二个参数为0，表示查询该客户在三个OU中对应的所有客服和销售人员
   * 否则表示查询指定OU下的客服和销售人员
   *
   * 参数 varchar(10) v_customer_number
   * 参数 smallint    v_ou_id，如果为0表示忽略OU
   */

  DECLARE v_customer_id int;

  SELECT IFNULL(customer_id, 0) INTO v_customer_id FROM customer WHERE customer_number = v_customer_number;

  IF v_ou_id = 0 THEN
    SELECT e.employee_id, e.employee_name, e.phone, e.fax, e.email, e.oracle_account 
    FROM employee e
    INNER JOIN employee_customer ec USING(employee_id)
    WHERE ec.customer_id = v_customer_id AND e.available = 1
    ORDER BY e.employee_name;
  ELSE
    SELECT e.employee_id, e.employee_name, e.phone, e.fax, e.email, e.oracle_account 
    FROM employee e
    INNER JOIN employee_customer ec USING(employee_id)
    WHERE ec.customer_id = v_customer_id AND ec.ou_id IN (0 /*代表跨OU的客服*/, v_ou_id) AND e.available = 1
    ORDER BY e.employee_name;
  END IF;
END
 ;;
delimiter ;

-- ----------------------------
--  Procedure structure for `sp_findItemsByWarehouse`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_findItemsByWarehouse`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_findItemsByWarehouse`(IN v_warehouse varchar(4))
BEGIN
  /*
   * 查询指定库存组织下的有效物料清单
   *
   * 参数 varchar(4) v_warehouse
   */
  SELECT model_id, model_name, model_desc, source, created_time, update_time
  FROM model where source = v_warehouse AND available = 1
  ORDER BY model_name;
END
 ;;
delimiter ;

-- ----------------------------
--  Procedure structure for `sp_findOrderableItems`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_findOrderableItems`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_findOrderableItems`(IN `v_site_id` int)
    READS SQL DATA
BEGIN
  /*
   * 查询指定客户的可订购物料列表
   * 参数 int v_site_id
   */
  SELECT DISTINCT model.model_id, model.model_name, model_desc
  FROM price_list_line pll
  LEFT JOIN price_list_header plh USING(header_id)
  INNER JOIN model ON pll.item_id = model.model_id
  WHERE (NOW() BETWEEN pll.start_date_active AND IFNULL(pll.end_date_active, NOW())) AND plh.qualifier_attr_value = v_site_id
  ORDER BY model_name;
END
 ;;
delimiter ;

-- ----------------------------
--  Procedure structure for `sp_findOrderableItems2`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_findOrderableItems2`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_findOrderableItems2`(IN `v_ou_id` int,IN `v_customer_number` varchar(10))
    READS SQL DATA
BEGIN
  /*
   * 查询指定客户的可订购物料列表
   * 参数 int         v_ou_id
   * 参数 varchar(10) v_customer_number
   */
  CALL sp_findOrderableItems(fn_findCustomerBillToSiteId(v_ou_id, v_customer_number));
END
 ;;
delimiter ;

-- ----------------------------
--  Procedure structure for `sp_findPlantBank`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_findPlantBank`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_findPlantBank`(IN v_ou_id int, IN v_currency varchar(3))
    READS SQL DATA
BEGIN
  /*
   * 查询工厂对应的银行信息
   *
   * 参数 int        v_ou_id
   * 参数 varchar(3) v_currency
   */

  SELECT bank_name, bank_account, account_name, tax_number, swift_code, web_site 
  FROM plant_bank 
  WHERE available = 1 AND plant_id = v_ou_id AND LOWER(currency) = v_currency;
END
 ;;
delimiter ;

-- ----------------------------
--  Procedure structure for `sp_findQualifiedCustomers`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_findQualifiedCustomers`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_findQualifiedCustomers`(IN `v_header_id` INT)
    READS SQL DATA
BEGIN
  /*
   * 查询可使用指定价格表的客户清单
   * 参数 int v_header_id
   */
  SELECT c.customer_id, c.customer_name, c.customer_number, c.country, c.available, c.created_time, c.update_time, cs.ou_id, cs.ou_name, cs.site_id
  FROM customer c
  INNER JOIN customer_sites cs USING(customer_id)
  WHERE site_purpose = 'BILL_TO'
  AND site_id IN (SELECT qualifier_attr_value FROM price_list_header WHERE header_id = v_header_id)
  ORDER BY customer_name;
END
 ;;
delimiter ;

-- ----------------------------
--  Procedure structure for `sp_findRolePermissions`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_findRolePermissions`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_findRolePermissions`(IN v_role_id smallint)
    READS SQL DATA
BEGIN
  /*
   * 查询角色权限
   *
   * 参数 int v_role_id
   */
  SELECT ap.permission_id, ap.permission_name, ap.permission_url, arp.created_time, arp.update_time 
  FROM auth_permission ap
  INNER JOIN auth_role_permission arp USING(permission_id)
  INNER JOIN auth_role ar using(role_id)
  WHERE arp.available = 1 AND arp.role_id = v_role_id AND (CURDATE() BETWEEN IFNULL(arp.start_date,CURDATE()) AND IFNULL(arp.end_date,CURDATE()))
  ORDER BY ap.permission_name;
END
 ;;
delimiter ;

-- ----------------------------
--  Procedure structure for `sp_findSellingPrice2`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_findSellingPrice2`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_findSellingPrice2`(IN `ouId` int,IN `customerNumber` varchar(10),IN `itemId` int,IN `qty` int)
    READS SQL DATA
    COMMENT 'æŸ¥è¯¢æŒ‡å®šå®¢æˆ·ã€æŒ‡å®šç‰©æ–™çš„é”€å”®å•ä»· å‚æ•°ouId, customerNumber, itemId, qty'
BEGIN
  IF qty = 0 THEN #没有指定采购数量，查询该物料的所有生效的销售价格
    IF (SELECT AVG(qty_from) 
        FROM price_list_line 
        LEFT JOIN price_list_header USING(header_id)
        WHERE item_id = itemId AND (NOW() BETWEEN price_list_line.start_date_active AND IFNULL(price_list_line.end_date_active, NOW()))
        AND qualifier_attr_value = (SELECT site_id FROM customer_sites WHERE ou_id = ouId AND site_purpose = 'BILL_TO' AND customer_id = (SELECT customer_id FROM customer WHERE customer_number=customerNumber))) = -222 
    THEN #如果qty_from的平均值是-222，则表明该物料只有一个价格，按生效日期倒排序取第一条记录
      SELECT price_list_header.currency, uom, unit_price
      FROM price_list_line 
      LEFT JOIN price_list_header USING(header_id)
      WHERE item_id = itemId AND (NOW() BETWEEN price_list_line.start_date_active AND IFNULL(price_list_line.end_date_active, NOW()))
      AND qualifier_attr_value = (SELECT site_id FROM customer_sites WHERE ou_id = ouId AND site_purpose = 'BILL_TO' AND customer_id = (SELECT customer_id FROM customer WHERE customer_number=customerNumber))
      ORDER BY qty_from, price_list_line.start_date_active DESC LIMIT 1;
    ELSE #如果qty_from的平均不值是-222，则表明该物料有区间价格(Price Break)，按起始订购量正排序、生效日期倒排序
      SELECT price_list_header.currency, uom, unit_price
      FROM price_list_line 
      LEFT JOIN price_list_header USING(header_id)
      WHERE item_id = itemId AND (NOW() BETWEEN price_list_line.start_date_active AND IFNULL(price_list_line.end_date_active, NOW()))
      AND qualifier_attr_value = (SELECT site_id FROM customer_sites WHERE ou_id = ouId AND site_purpose = 'BILL_TO' AND customer_id = (SELECT customer_id FROM customer WHERE customer_number=customerNumber))
      ORDER BY qty_from, price_list_line.start_date_active DESC;
    END IF;
  ELSE #指定采购数量，查询匹配的销售价格
    SELECT price_list_header.currency, uom, unit_price
    FROM price_list_line 
    LEFT JOIN price_list_header USING(header_id)
    WHERE item_id = itemId AND ((qty_from = -222) /* 正价格价格*/ OR (qty_from < qty) AND (qty_to >= qty) /*或是落在某个Price Break上*/)
    AND (NOW() BETWEEN price_list_line.start_date_active AND IFNULL(price_list_line.end_date_active, NOW()))
    AND qualifier_attr_value = (SELECT site_id FROM customer_sites WHERE ou_id = ouId AND site_purpose = 'BILL_TO' AND customer_id = (SELECT customer_id FROM customer WHERE customer_number=customerNumber))
    ORDER BY qty_from, price_list_line.start_date_active DESC LIMIT 1;
  END IF;
END
 ;;
delimiter ;

-- ----------------------------
--  Procedure structure for `sp_findUserPermissions`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_findUserPermissions`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_findUserPermissions`(IN v_user_id smallint)
    READS SQL DATA
BEGIN
  /*
   * 查询用户权限
   *
   * 参数 int v_role_id
   */

  #查询用户主角色权限
  SELECT  au.user_id, au.user_name, au.nick_name, au.role_id, ar.role_name, arp.permission_id, ap.permission_name 
  FROM auth_permission ap
  INNER JOIN auth_role_permission arp ON ap.permission_id = arp.permission_id
  INNER JOIN auth_role ar ON ar.role_id = arp.role_id
  INNER JOIN auth_user au ON au.role_id = ar.role_id
  WHERE arp.available = 1 AND au.user_id = v_user_id
  UNION
  #查询用户扩展角色权限
  SELECT  aur.user_id, au.user_name, au.nick_name, aur.role_id, ar.role_name, arp.permission_id, ap.permission_name 
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
  SELECT au.user_id, ar.role_id, ar.role_name, CURDATE() as start_date, NULL as end_date, ar.navigation, ar.created_time, ar.update_time
  FROM auth_user au
  INNER JOIN auth_role ar ON au.role_id = ar.role_id
  WHERE au.user_id = v_user_id
  UNION
  #查询用户护展角色
  SELECT aur.user_id, ar.role_id, ar.role_name, aur.start_date, aur.end_date, ar.navigation, ar.created_time, ar.update_time
  FROM auth_user_role aur
  INNER JOIN auth_role ar ON aur.role_id = ar.role_id
  WHERE aur.available = 1 AND aur.user_id = v_user_id AND (CURDATE() BETWEEN IFNULL(aur.start_date,CURDATE()) AND IFNULL(aur.end_date,CURDATE()));
END
 ;;
delimiter ;

-- ----------------------------
--  Procedure structure for `sp_putItemInCart`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_putItemInCart`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_putItemInCart`(IN v_site_id int, IN v_header_id int, IN v_item_id int, IN v_qty int, IN v_rtd varchar(10))
    MODIFIES SQL DATA
BEGIN
  /*
   * 将指定物料加入到购物车中
   * 系统以site_id, price_header_id, model_id和rtd作为唯一索引判断购物车中是否已存在相同的记录
   * 如果有，则进行数量的累加，并使用累加后的数量重新抓取销售价格
   * 如果没有，则将数据写入到购物车表中
   *
   * 参数 int         v_site_id
   * 参数 int         v_header_id
   * 参数 int         v_item_id
   * 参数 int         v_qty
   * 参数 varchar(10) v_rtd
   *
   * 依赖函数 fn_findSellingPriceInSpecificList
   *
   */

  DECLARE v_customer_id int;
  DECLARE v_price_list_name varchar(128);
  DECLARE v_currency varchar(10);

  SELECT customer_id  INTO v_customer_id FROM customer_sites WHERE site_id = v_site_id;

  SELECT price_list_name, currency INTO v_price_list_name, v_currency FROM price_list_header
  WHERE header_id = v_header_id AND qualifier_attr_value = v_site_id;

  #以site_id, price_header_id, model_id和rtd为作为唯一索引，检查产品是否已经存在于购物车中
  IF EXISTS(SELECT * FROM shopping_cart WHERE site_id = v_site_id AND price_header_id = v_header_id AND model_id = v_item_id AND rtd = v_rtd) THEN
    #update the record
    UPDATE shopping_cart
    SET qty = qty + v_qty, price = fn_findSellingPriceInSpecificList(v_header_id, v_item_id, qty + v_qty)
    WHERE site_id = v_site_id AND model_id = v_item_id AND rtd = v_rtd;
  ELSE
    #insert record
    INSERT INTO shopping_cart(customer_id, site_id, model_id, qty, rtd, currency, price, price_header_id, price_list_name, created_time)
    VALUE(v_customer_id, v_site_id, v_item_id, v_qty, v_rtd, v_currency, fn_findSellingPriceInSpecificList(v_header_id, v_item_id, v_qty), v_header_id, v_price_list_name, NOW());
  END IF;

END
 ;;
delimiter ;

-- ----------------------------
--  Function structure for `fn_findCustomerBillToSiteId`
-- ----------------------------
DROP FUNCTION IF EXISTS `fn_findCustomerBillToSiteId`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `fn_findCustomerBillToSiteId`(v_ou_id int, v_customer_number varchar(10)) RETURNS int(11)
    READS SQL DATA
BEGIN
  /*
   * 查询客户在指定OU下的BILL TO SITE ID
   *
   * 参数 int         v_ou_id
   * 参数 varchar(10) v_customer_number
   */

  DECLARE v_site_id int;

  SELECT site_id INTO v_site_id
  FROM customer
  LEFT JOIN customer_sites USING(customer_id)
  WHERE ou_id = v_ou_id AND customer_number = v_customer_number AND site_purpose = 'BILL_TO';

  RETURN IFNULL(v_site_id, 0);
END
 ;;
delimiter ;

-- ----------------------------
--  Function structure for `fn_findSellingPriceInSpecificList`
-- ----------------------------
DROP FUNCTION IF EXISTS `fn_findSellingPriceInSpecificList`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `fn_findSellingPriceInSpecificList`(v_header_id int, itemId int, v_qty int) RETURNS decimal(10,2)
    READS SQL DATA
BEGIN
  /*
   * 在指定的价格表中查询指定物料指定数量的销售单价
   *
   * 参数 int v_header_id
   * 参数 int v_item_id
   * 参数 int v_qty
   *
   * 返回类型 Decimal(10,2)
   */

  DECLARE v_unit_price decimal(10, 2);

  SELECT unit_price INTO v_unit_price
  FROM price_list_line pll
  WHERE pll.header_id = v_header_id AND item_id = itemId AND ((qty_from = -222) /* 正价格价格*/ OR (qty_from < v_qty) AND (qty_to >= v_qty) /*或是落在某个Price Break上*/)
  AND (NOW() BETWEEN pll.start_date_active AND IFNULL(pll.end_date_active, NOW()))
  ORDER BY pll.start_date_active DESC LIMIT 1;

  RETURN IFNULL(v_unit_price,0);
END
 ;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
