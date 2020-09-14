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

 Date: 14/09/2020 11:36:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(64) NOT NULL,
  `date_add` datetime DEFAULT NULL,
  `date_update` datetime DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `pass_word` varchar(255) DEFAULT NULL,
  `roles` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (14, '2020-09-12 22:24:29', '2020-09-12 22:24:29', 'http://localhost:10000/u=3095823371,2737858048&fm=26&gp=0.jpg', '123', '[\"admin\",\"user\"]', 'admin');
INSERT INTO `user` VALUES (31, '2020-09-13 02:17:20', '2020-09-13 02:17:20', 'http://localhost:10000/u=3095823371,2737858048&fm=26&gp=0.jpg', '123', '[\"user\"]', 'user');
INSERT INTO `user` VALUES (336, '2020-09-13 22:25:21', '2020-09-13 22:25:21', 'http://localhost:10000/WX20200914-111707@2x.png', '123', '[\"user\"]', 'admin2');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
