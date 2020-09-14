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

 Date: 14/09/2020 11:35:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `to_jump` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
BEGIN;
INSERT INTO `menu` VALUES (291, 'pi pi-fw pi-home', '首页', NULL, NULL, NULL, '/');
INSERT INTO `menu` VALUES (292, 'pi pi-fw pi-home', '后台系统', NULL, 'admin:monitor:list', '', NULL);
INSERT INTO `menu` VALUES (293, 'pi pi-fw pi-home', '性能监控', 292, 'admin:monitor:list', '', '/systemMarket/main');
INSERT INTO `menu` VALUES (294, 'pi pi-fw pi-home', '管理员专区', NULL, NULL, 'admin', NULL);
INSERT INTO `menu` VALUES (295, 'pi pi-fw pi-bookmark', '用户管理', 294, NULL, 'admin', '/user/main');
INSERT INTO `menu` VALUES (296, 'pi pi-fw pi-bookmark', '角色管理', 294, NULL, 'admin', '/role/main');
INSERT INTO `menu` VALUES (298, 'pi pi-fw pi-bookmark', '菜单管理', 294, NULL, 'admin', '/menu/main');
INSERT INTO `menu` VALUES (299, 'pi pi-fw pi-bookmark', '高级功能(开发者)', NULL, NULL, NULL, NULL);
INSERT INTO `menu` VALUES (300, 'pi pi-fw pi-bookmark', '进度条', 299, NULL, NULL, '/processBar/main');
INSERT INTO `menu` VALUES (301, 'pi pi-fw pi-bookmark', '消息提示', 299, NULL, NULL, '/tips/main');
INSERT INTO `menu` VALUES (302, 'pi pi-fw pi-bookmark', '操作日志', NULL, NULL, NULL, '/operatorLog/main');
INSERT INTO `menu` VALUES (303, 'pi pi-fw pi-bookmark', '文件上传', NULL, NULL, NULL, '/upload/main');
INSERT INTO `menu` VALUES (304, 'pi pi-fw pi-bookmark', '图表', NULL, NULL, NULL, '/charts/main');
INSERT INTO `menu` VALUES (305, 'pi pi-fw pi-bookmark', '富文本', NULL, NULL, NULL, '/editor/main');
INSERT INTO `menu` VALUES (306, 'pi pi-fw pi-bookmark', '其他', NULL, NULL, NULL, '/other/main');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
