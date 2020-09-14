/*
 Navicat Premium Data Transfer

 Source Server         : rm-m5en86s08l07od20veo.mysql.rds.aliyuncs.com
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : rm-m5en86s08l07od20veo.mysql.rds.aliyuncs.com:3306
 Source Schema         : bird_admin

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 14/09/2020 11:35:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `date_add` datetime DEFAULT NULL,
  `date_update` datetime DEFAULT NULL,
  `descption` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `permissions` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8sewwnpamngi6b1dwaa88askk` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES (15, '2020-09-12 22:25:38', '2020-09-12 22:25:38', '系统管理员super23', 'admin', '[\"admin:monitor:editor\"]');
INSERT INTO `role` VALUES (30, '2020-09-12 22:36:14', '2020-09-12 22:36:14', '普通使用者', 'user', '[\"admin:monitor:list\"]');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
