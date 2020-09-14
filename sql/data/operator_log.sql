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

 Date: 14/09/2020 11:35:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for operator_log
-- ----------------------------
DROP TABLE IF EXISTS `operator_log`;
CREATE TABLE `operator_log` (
  `id` bigint(20) NOT NULL,
  `change_after` varchar(255) DEFAULT NULL,
  `change_before` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `operator_user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of operator_log
-- ----------------------------
BEGIN;
INSERT INTO `operator_log` VALUES (314, '{\"dateAdd\":\"2020-09-14T00:20:13.460\",\"dateUpdate\":\"2020-09-14T00:20:13.460\",\"passWord\":\"1323\",\"roles\":\"1231323\",\"userName\":\"tysong66\"}', '', 'save user', 'admin');
INSERT INTO `operator_log` VALUES (315, '{\"dateAdd\":\"2020-09-13T11:24:29\",\"dateUpdate\":\"2020-09-13T11:24:29\",\"icon\":\"http://localhost:10000/u=3095823371,2737858048&fm=26&gp=0.jpg\",\"id\":14,\"passWord\":\"123\",\"roles\":\"[\\\"admin\\\",\\\"user\\\"]\",\"userName\":\"tysong\"}', '', 'delete user', 'admin');
INSERT INTO `operator_log` VALUES (317, '{\"dateAdd\":\"2020-09-14T00:21:31.537\",\"dateUpdate\":\"2020-09-14T00:21:31.537\",\"icon\":\"h\",\"passWord\":\"132\",\"roles\":\"231312\",\"userName\":\"tysong2123\"}', '', 'save user', 'admin');
INSERT INTO `operator_log` VALUES (318, '{\"dateAdd\":\"2020-09-13T11:24:29\",\"dateUpdate\":\"2020-09-13T11:24:29\",\"icon\":\"http://localhost:10000/u=3095823371,2737858048&fm=26&gp=0.jpg\",\"id\":14,\"passWord\":\"123\",\"roles\":\"[\\\"admin\\\",\\\"user\\\"]\",\"userName\":\"tysong\"}', '', 'delete user', 'admin');
INSERT INTO `operator_log` VALUES (320, '{\"dateAdd\":\"2020-09-14T00:22:24.239\",\"dateUpdate\":\"2020-09-14T00:22:24.239\",\"icon\":\"http://localhost:10000/u=3095823371,2737858048&fm=26&gp=0.jpg\",\"passWord\":\"231321\",\"roles\":\"2qe3123\",\"userName\":\"tysong213\"}', '', 'save user', 'admin');
INSERT INTO `operator_log` VALUES (321, '{\"dateAdd\":\"2020-09-13T11:24:29\",\"dateUpdate\":\"2020-09-13T11:24:29\",\"icon\":\"http://localhost:10000/u=3095823371,2737858048&fm=26&gp=0.jpg\",\"id\":14,\"passWord\":\"123\",\"roles\":\"[\\\"admin\\\",\\\"user\\\"]\",\"userName\":\"tysong\"}', '', 'delete user', 'admin');
INSERT INTO `operator_log` VALUES (322, '{\"dateAdd\":\"2020-09-14T00:23:06.031\",\"dateUpdate\":\"2020-09-14T00:23:06.031\",\"descption\":\"大老板\",\"name\":\"boss\",\"permissions\":\"[\\\"*\\\"]\"}', '', 'save role', 'admin');
INSERT INTO `operator_log` VALUES (324, '{\"dateAdd\":\"2020-09-14T00:23:06\",\"dateUpdate\":\"2020-09-14T00:23:06\",\"descption\":\"大老板\",\"id\":323,\"name\":\"boss\",\"permissions\":\"[\\\"*\\\"]\"}', '', 'delete role', 'admin');
INSERT INTO `operator_log` VALUES (326, '{\"label\":\"测试菜单\"}', '', 'save MenuEntity', 'admin');
INSERT INTO `operator_log` VALUES (327, '{\"id\":325,\"label\":\"测试菜单\"}', '', 'delete MenuEntity', 'admin');
INSERT INTO `operator_log` VALUES (329, '{\"label\":\"测试菜单\"}', '', 'save MenuEntity', 'admin');
INSERT INTO `operator_log` VALUES (330, '{\"id\":328,\"label\":\"测试菜单\"}', '', 'delete MenuEntity', 'admin');
INSERT INTO `operator_log` VALUES (332, '{\"dateAdd\":\"2020-09-14T00:55:14.292\",\"dateUpdate\":\"2020-09-14T00:55:14.292\",\"icon\":\"http://localhost:10000/u=3095823371,2737858048&fm=26&gp=0.jpg\",\"passWord\":\"213\",\"roles\":\"312\",\"userName\":\"tysong\"}', '', 'save user', 'admin');
INSERT INTO `operator_log` VALUES (333, '{\"dateAdd\":\"2020-09-13T11:24:29\",\"dateUpdate\":\"2020-09-13T11:24:29\",\"icon\":\"http://localhost:10000/u=3095823371,2737858048&fm=26&gp=0.jpg\",\"id\":14,\"passWord\":\"123\",\"roles\":\"[\\\"admin\\\"]\",\"userName\":\"tysong\"}', '', 'save user', 'admin');
INSERT INTO `operator_log` VALUES (334, '{\"dateAdd\":\"2020-09-13T11:24:29\",\"dateUpdate\":\"2020-09-13T11:24:29\",\"icon\":\"http://localhost:10000/u=3095823371,2737858048&fm=26&gp=0.jpg\",\"id\":14,\"passWord\":\"123\",\"roles\":\"[\\\"admin\\\",\\\"user\\\"]\",\"userName\":\"tysong\"}', '', 'save user', 'admin');
INSERT INTO `operator_log` VALUES (335, '{\"dateAdd\":\"2020-09-14T11:25:21.080\",\"dateUpdate\":\"2020-09-14T11:25:21.080\",\"icon\":\"http://localhost:10000/WX20200914-111707@2x.png\",\"passWord\":\"123\",\"roles\":\"213\",\"userName\":\"admin2\"}', '', 'save user', 'admin');
INSERT INTO `operator_log` VALUES (337, '{\"dateAdd\":\"2020-09-14T11:25:21\",\"dateUpdate\":\"2020-09-14T11:25:21\",\"icon\":\"http://localhost:10000/WX20200914-111707@2x.png\",\"id\":336,\"passWord\":\"123\",\"roles\":\"[\\\"user\\\"]\",\"userName\":\"admin2\"}', '', 'save user', 'admin');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
