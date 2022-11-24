/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50734
Source Host           : localhost:3306
Source Database       : qblog

Target Server Type    : MYSQL
Target Server Version : 50734
File Encoding         : 65001

Date: 2022-11-24 10:21:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `about`
-- ----------------------------
DROP TABLE IF EXISTS `about`;
CREATE TABLE `about` (
  `id` bigint(20) NOT NULL,
  `name_en` varchar(255) DEFAULT NULL,
  `name_zh` varchar(255) DEFAULT NULL,
  `value` longtext,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of about
-- ----------------------------
INSERT INTO `about` VALUES ('1', 'title', '标题', '关于帅气的 Naccl');
INSERT INTO `about` VALUES ('2', 'musicId', '网易云歌曲ID', '423015580');
INSERT INTO `about` VALUES ('3', 'content', '正文Markdown', '');
INSERT INTO `about` VALUES ('4', 'commentEnabled', '评论开关', 'true');

-- ----------------------------
-- Table structure for `blog`
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL COMMENT '文章标题',
  `first_picture` varchar(255) NOT NULL COMMENT '文章首图，用于随机文章展示',
  `content` longtext NOT NULL COMMENT '文章正文',
  `description` longtext NOT NULL COMMENT '描述',
  `published` bit(1) NOT NULL COMMENT '公开或私密',
  `comment_enabled` bit(1) NOT NULL COMMENT '评论开关',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `views` int(11) NOT NULL COMMENT '浏览次数',
  `words` int(11) NOT NULL COMMENT '文章字数',
  `read_time` int(11) NOT NULL COMMENT '阅读时长(分钟)',
  `category_id` bigint(20) NOT NULL COMMENT '文章分类',
  `top` bit(1) NOT NULL COMMENT '是否置顶',
  `password` varchar(255) DEFAULT NULL COMMENT '密码保护',
  `user_id` bigint(20) DEFAULT NULL COMMENT '文章作者',
  `deleted` int(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `type_id` (`category_id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES ('1', '测试', '/img/avatar', 'content测试', 'description测试', '', '', '2022-11-11 19:56:26', '2022-11-11 19:56:32', '10', '100', '222', '1', '', null, '1', '0');
INSERT INTO `blog` VALUES ('6', '测试1', '', '测试描述1', '## 测试1\n测试正文1![图片1.png](http://rldb7nvd2.hb-bkt.clouddn.com/2022/11/16/953eba765af5432dacf8d880f5a24ebb.png)', '', '', '2022-11-16 09:31:58', '2022-11-16 09:31:58', '0', '7', '0', '3', '', '', '1', '0');
INSERT INTO `blog` VALUES ('7', '测试2', '', '测试222', '测试2', '', '', '2022-11-16 09:34:44', '2022-11-16 09:34:44', '0', '5', '0', '1', '', '', '1', '0');
INSERT INTO `blog` VALUES ('8', '111', '', '33333^3^', '222', '', '', '2022-11-16 10:10:55', '2022-11-16 10:10:55', '0', '6', '0', '2', '', '', '1', '0');
INSERT INTO `blog` VALUES ('9', '222', '', '444', '333', '', '', '2022-11-16 10:13:12', '2022-11-16 10:13:12', '0', '3', '0', '2', '', '', '1', '0');
INSERT INTO `blog` VALUES ('10', '333', '', '555', '444', '', '', '2022-11-16 10:13:29', '2022-11-16 10:13:29', '0', '3', '0', '2', '', '', '1', '0');
INSERT INTO `blog` VALUES ('11', '修改测试444', '', '666修改测试', '修改测试555', '', '', '2022-11-16 10:13:41', '2022-11-16 19:34:30', '0', '7', '0', '1', '', '', '1', '0');
INSERT INTO `blog` VALUES ('12', '555', '', '777', '666', '', '', '2022-11-16 10:13:58', '2022-11-16 10:13:58', '0', '3', '0', '2', '', '', '1', '0');
INSERT INTO `blog` VALUES ('13', '666测试', '', '888', '777', '', '', '2022-11-16 10:14:09', '2022-11-16 10:17:11', '0', '3', '0', '3', '', '', '1', '0');
INSERT INTO `blog` VALUES ('14', '777', '', '999', '888', '', '', '2022-11-16 10:14:22', '2022-11-16 10:14:22', '0', '3', '0', '2', '', '', '1', '0');
INSERT INTO `blog` VALUES ('15', '888', '', '111', '999', '', '', '2022-11-16 10:14:31', '2022-11-16 10:14:31', '0', '3', '0', '2', '', '', '1', '0');
INSERT INTO `blog` VALUES ('16', '999', '', '222', '111', '', '', '2022-11-16 10:14:41', '2022-11-16 10:14:41', '0', '3', '0', '2', '', '', '1', '0');
INSERT INTO `blog` VALUES ('17', '啊啊啊', '', '# 一级标题事实上', '事实上', '', '', '2022-11-16 19:33:52', '2022-11-16 19:33:52', '0', '7', '0', '1', '', '', '1', '0');
INSERT INTO `blog` VALUES ('18', '1118测试新增', '', '1118测试新增\n+ 1118测试新增撒大苏打', '描述1118测试新增水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水少时诵诗描述1118测试新增水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水少时诵诗书是少时诵诗书是少时诵诗书是上撒描述1118测试新增水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水少时诵诗书是少时诵诗书是少时诵诗书是上撒描述1118测试新增水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水少时诵诗书是少时诵诗书是少时诵诗书是上撒描述1118测试新增水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水少时诵诗书是少时诵诗书是少时诵诗书是上撒描述1118测试新增水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水少时诵诗书是少时诵诗书是少时诵诗书是上撒描述1118测试新增水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水少时诵诗书是少时诵诗书是少时诵诗书是上撒书是少时诵诗书是少时诵诗书是上撒', '', '', '2022-11-18 19:34:58', '2022-11-18 19:34:58', '0', '16', '0', '3', '', '', '1', '0');
INSERT INTO `blog` VALUES ('19', '图片测试', '', '# 上传图片测试\n![图片1.png](http://rldb7nvd2.hb-bkt.clouddn.com/2022/11/18/ab2c6b8d400c4f36a1791f0033eefd70.png)', '上传图片', '', '', '2022-11-18 21:34:36', '2022-11-18 21:34:36', '0', '7', '0', '1', '', '', '1', '0');
INSERT INTO `blog` VALUES ('20', '062.不同路径', '', '### 题目描述\n\n * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。\n * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。\n * 问总共有多少条不同的路径？\n\n```bash\nexample\ninput  : m = 3, n = 2\noutput : 3\nnote   : 从左上角开始，总共有 3 条路径可以到达右下角。\n         1. 向右 -> 向下 -> 向下\n         2. 向下 -> 向下 -> 向右\n         3. 向下 -> 向右 -> 向下\n\ninput  : m = 7, n = 3\noutput : 28\n```\n\n<!--more-->\n\n### 解题思路\n\n##### 思路1 动态规划\n+ 这是一个标准的动态规划问题，可以完成状态转移\n+ 转移方程：dp[i][j] = dp[i-1][j] + dp[i][j-1]\n+ 因为只能向右或向下移动，所以：\n    - 对于第一行和第一列的所有格子，都有且仅有一条路径可以直达其位置\n    - 对于非第一行或非第一列的格子，`到达其位置的路径数` = `到达其上方格子的路径数`+`到达其左方格子的路径数`\n+ 绘制网格图后，可以通过举例测试确定上述规律\n\n+ 时间复杂度:O(m x n)\n+ 空间复杂度:O(m x n)\n##### 思路2 组合数学\n从左上角到右下角的过程中，需要移动 m+n-2 次，其中有 m-1 次向下移动，n-1 次向右移动。\n因此路径的总数，就等于从 m+n-2 次移动中选择 m-1 次向下移动的方案数，即组合数：\n\nC = (m + n - 2)! / (m - 1)! * (n - 1)!\n因此直接计算出这个组合数即可。\n化简可得：C = (m + n - 2) * (m + n - 3) * ··· * n / (m - 1)!\n \n+ 时间复杂度:O(m)\n+ 空间复杂度:O(1)\n\n\n### 代码（Java）\n**思路1代码**\n```java\npublic class Solution {\n    public int uniquePaths(int m, int n) {\n        int[][] pathNum = new int[m][n];\n        /*\n        for (int j = 0; j < n; j++) {\n            pathNum[0][j] = 1;\n        }\n        for (int i = 1; i < m; i++) {\n            pathNum[i][0] = 1;\n        }\n        for (int i = 1; i < m; i++) {\n            for (int j = 1; j < n; j++) {\n                pathNum[i][j] = pathNum[i - 1][j] + pathNum[i][j - 1];\n            }\n        }\n        */\n\n        for (int i = 0; i < m; i++) {\n            for (int j = 0; j < n; j++) {\n                if (i == 0 || j == 0) {\n                    pathNum[i][j] = 1;\n                } else {\n                    pathNum[i][j] = pathNum[i - 1][j] + pathNum[i][j - 1];\n                }\n\n            }\n        }\n        /* 打印动态规划得到的二维数组\n        for (int i = 0; i < m; i++) {\n            for (int j = 0; j < n; j++) {\n                System.out.print(pathNum[i][j] + \"\\t\");\n            }\n            System.out.println();\n        }\n        */\n        return pathNum[m - 1][n - 1];\n    }\n}\n```\n**思路2代码**\n```java\npublic class Solution2 {\n    public int uniquePaths(int m, int n) {\n        long ans = 1;\n        for (int x = n, y = 1; y < m; x++, y++) {\n            // x和y同时前进 m - 2 次，刚好满足化简后的公式\n            ans = ans * x / y;\n        }\n        return (int) ans;\n    }\n}\n```', 'LeetCode腾讯精选练习50题-062.不同路径', '', '', '2022-11-18 21:56:51', '2022-11-18 21:56:51', '0', '2000', '10', '1', '', '', '1', '0');

-- ----------------------------
-- Table structure for `blog_tag`
-- ----------------------------
DROP TABLE IF EXISTS `blog_tag`;
CREATE TABLE `blog_tag` (
  `blog_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of blog_tag
-- ----------------------------

-- ----------------------------
-- Table structure for `category`
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', '测试类');
INSERT INTO `category` VALUES ('2', '测试类II');
INSERT INTO `category` VALUES ('3', '分类3');

-- ----------------------------
-- Table structure for `city_visitor`
-- ----------------------------
DROP TABLE IF EXISTS `city_visitor`;
CREATE TABLE `city_visitor` (
  `city` varchar(255) NOT NULL COMMENT '城市名称',
  `uv` int(11) NOT NULL COMMENT '独立访客数量',
  PRIMARY KEY (`city`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of city_visitor
-- ----------------------------

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(255) NOT NULL COMMENT '昵称',
  `email` varchar(255) NOT NULL COMMENT '邮箱',
  `content` varchar(255) NOT NULL COMMENT '评论内容',
  `avatar` varchar(255) NOT NULL COMMENT '头像(图片路径)',
  `create_time` datetime DEFAULT NULL COMMENT '评论时间',
  `ip` varchar(255) DEFAULT NULL COMMENT '评论者ip地址',
  `is_published` bit(1) NOT NULL COMMENT '公开或回收站',
  `is_admin_comment` bit(1) NOT NULL COMMENT '博主回复',
  `page` int(11) NOT NULL COMMENT '0普通文章，1关于我页面，2友链页面',
  `is_notice` bit(1) NOT NULL COMMENT '接收邮件提醒',
  `blog_id` bigint(20) DEFAULT NULL COMMENT '所属的文章',
  `parent_comment_id` bigint(20) NOT NULL COMMENT '父评论id，-1为根评论',
  `website` varchar(255) DEFAULT NULL COMMENT '个人网站',
  `qq` varchar(255) DEFAULT NULL COMMENT '如果评论昵称为QQ号，则将昵称和头像置为QQ昵称和QQ头像，并将此字段置为QQ号备份',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for `exception_log`
-- ----------------------------
DROP TABLE IF EXISTS `exception_log`;
CREATE TABLE `exception_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uri` varchar(255) NOT NULL COMMENT '请求接口',
  `method` varchar(255) NOT NULL COMMENT '请求方式',
  `param` varchar(2000) DEFAULT NULL COMMENT '请求参数',
  `description` varchar(255) DEFAULT NULL COMMENT '操作描述',
  `error` text COMMENT '异常信息',
  `ip` varchar(255) DEFAULT NULL COMMENT 'ip',
  `ip_source` varchar(255) DEFAULT NULL COMMENT 'ip来源',
  `os` varchar(255) DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(255) DEFAULT NULL COMMENT '浏览器',
  `create_time` datetime NOT NULL COMMENT '操作时间',
  `user_agent` varchar(2000) DEFAULT NULL COMMENT 'user-agent用户代理',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of exception_log
-- ----------------------------

-- ----------------------------
-- Table structure for `friend`
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(255) NOT NULL COMMENT '昵称',
  `description` varchar(255) NOT NULL COMMENT '描述',
  `website` varchar(255) NOT NULL COMMENT '站点',
  `avatar` varchar(255) NOT NULL COMMENT '头像',
  `is_published` bit(1) NOT NULL COMMENT '公开或隐藏',
  `views` int(11) NOT NULL COMMENT '点击次数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of friend
-- ----------------------------

-- ----------------------------
-- Table structure for `login_log`
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL COMMENT '用户名称',
  `ip` varchar(255) DEFAULT NULL COMMENT 'ip',
  `ip_source` varchar(255) DEFAULT NULL COMMENT 'ip来源',
  `os` varchar(255) DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(255) DEFAULT NULL COMMENT '浏览器',
  `status` bit(1) DEFAULT NULL COMMENT '登录状态',
  `description` varchar(255) DEFAULT NULL COMMENT '操作描述',
  `create_time` datetime NOT NULL COMMENT '登录时间',
  `user_agent` varchar(2000) DEFAULT NULL COMMENT 'user-agent用户代理',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of login_log
-- ----------------------------

-- ----------------------------
-- Table structure for `moment`
-- ----------------------------
DROP TABLE IF EXISTS `moment`;
CREATE TABLE `moment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` longtext NOT NULL COMMENT '动态内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `likes` int(11) DEFAULT NULL COMMENT '点赞数量',
  `is_published` bit(1) NOT NULL COMMENT '是否公开',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of moment
-- ----------------------------

-- ----------------------------
-- Table structure for `occurrence`
-- ----------------------------
DROP TABLE IF EXISTS `occurrence`;
CREATE TABLE `occurrence` (
  `id` int(3) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `symbol` varchar(10) NOT NULL COMMENT '化学符号',
  `name_en` varchar(50) NOT NULL COMMENT '英文元素名',
  `name_zh` varchar(50) NOT NULL COMMENT '中文元素名',
  `category` varchar(20) NOT NULL COMMENT 'Goldschmidt分类',
  `common_occurrence` longtext COMMENT '常见赋存模式',
  `example` longtext COMMENT '赋存模式例子',
  `reference` longtext COMMENT '参考文献',
  PRIMARY KEY (`id`,`symbol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='记录煤中元素的赋存模式';

-- ----------------------------
-- Records of occurrence
-- ----------------------------

-- ----------------------------
-- Table structure for `operation_log`
-- ----------------------------
DROP TABLE IF EXISTS `operation_log`;
CREATE TABLE `operation_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL COMMENT '操作者用户名',
  `uri` varchar(255) NOT NULL COMMENT '请求接口',
  `method` varchar(255) NOT NULL COMMENT '请求方式',
  `param` varchar(2000) DEFAULT NULL COMMENT '请求参数',
  `description` varchar(255) DEFAULT NULL COMMENT '操作描述',
  `ip` varchar(255) DEFAULT NULL COMMENT 'ip',
  `ip_source` varchar(255) DEFAULT NULL COMMENT 'ip来源',
  `os` varchar(255) DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(255) DEFAULT NULL COMMENT '浏览器',
  `times` int(11) NOT NULL COMMENT '请求耗时（毫秒）',
  `create_time` datetime NOT NULL COMMENT '操作时间',
  `user_agent` varchar(2000) DEFAULT NULL COMMENT 'user-agent用户代理',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for `schedule_job`
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(255) DEFAULT NULL COMMENT 'spring bean名称',
  `method_name` varchar(255) DEFAULT NULL COMMENT '方法名',
  `params` varchar(255) DEFAULT NULL COMMENT '参数',
  `cron` varchar(255) DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) DEFAULT NULL COMMENT '任务状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
INSERT INTO `schedule_job` VALUES ('1', 'redisSyncScheduleTask', 'syncBlogViewsToDatabase', '', '0 0 1 * * ?', '1', '每天凌晨一点，从Redis将博客浏览量同步到数据库', '2020-11-17 23:45:42');
INSERT INTO `schedule_job` VALUES ('2', 'visitorSyncScheduleTask', 'syncVisitInfoToDatabase', '', '0 0 0 * * ?', '1', '清空当天Redis访客标识，记录当天的PV和UV，更新当天所有访客的PV和最后访问时间，更新城市新增访客UV数', '2021-02-05 08:14:28');

-- ----------------------------
-- Table structure for `schedule_job_log`
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(255) DEFAULT NULL COMMENT 'spring bean名称',
  `method_name` varchar(255) DEFAULT NULL COMMENT '方法名',
  `params` varchar(255) DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务执行结果',
  `error` text COMMENT '异常信息',
  `times` int(11) NOT NULL COMMENT '耗时（单位：毫秒）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of schedule_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for `site_setting`
-- ----------------------------
DROP TABLE IF EXISTS `site_setting`;
CREATE TABLE `site_setting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name_en` varchar(255) DEFAULT NULL,
  `name_zh` varchar(255) DEFAULT NULL,
  `value` longtext,
  `type` int(11) DEFAULT NULL COMMENT '1基础设置，2页脚徽标，3资料卡，4友链信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of site_setting
-- ----------------------------
INSERT INTO `site_setting` VALUES ('1', 'blogName', '博客名称', 'Naccl\'s Blog', '1');
INSERT INTO `site_setting` VALUES ('2', 'webTitleSuffix', '网页标题后缀', ' - Naccl\'s Blog', '1');
INSERT INTO `site_setting` VALUES ('3', 'footerImgTitle', '页脚图片标题', '手机看本站', '1');
INSERT INTO `site_setting` VALUES ('4', 'footerImgUrl', '页脚图片路径', '/img/qr.png', '1');
INSERT INTO `site_setting` VALUES ('5', 'copyright', 'Copyright', '{\"title\":\"Copyright © 2019 - 2022\",\"siteName\":\"NACCL\'S BLOG\"}', '1');
INSERT INTO `site_setting` VALUES ('6', 'beian', 'ICP备案号', '', '1');
INSERT INTO `site_setting` VALUES ('7', 'reward', '赞赏码', '/img/reward.jpg', '1');
INSERT INTO `site_setting` VALUES ('8', 'commentAdminFlag', '博主评论标识', '咕咕', '1');
INSERT INTO `site_setting` VALUES ('9', 'avatar', '头像', '/img/avatar.jpg', '2');
INSERT INTO `site_setting` VALUES ('10', 'name', '昵称', 'Naccl', '2');
INSERT INTO `site_setting` VALUES ('11', 'rollText', '滚动个签', '\"云鹤当归天，天不迎我妙木仙；\",\"游龙当归海，海不迎我自来也。\"', '2');
INSERT INTO `site_setting` VALUES ('12', 'github', 'GitHub', 'https://github.com/Naccl', '2');
INSERT INTO `site_setting` VALUES ('13', 'telegram', 'Telegram', 'https://t.me/NacclOfficial', '2');
INSERT INTO `site_setting` VALUES ('14', 'qq', 'QQ', 'http://sighttp.qq.com/authd?IDKEY=', '2');
INSERT INTO `site_setting` VALUES ('15', 'bilibili', 'bilibili', 'https://space.bilibili.com/', '2');
INSERT INTO `site_setting` VALUES ('16', 'netease', '网易云音乐', 'https://music.163.com/#/user/home?id=', '2');
INSERT INTO `site_setting` VALUES ('17', 'email', 'email', 'mailto:you@example.com', '2');
INSERT INTO `site_setting` VALUES ('18', 'favorite', '自定义', '{\"title\":\"最喜欢的动漫 ?\",\"content\":\"异度侵入、春物语、NO GAME NO LIFE、实力至上主义的教室、辉夜大小姐、青春猪头少年不会梦到兔女郎学姐、路人女主、Re0、魔禁、超炮、俺妹、在下坂本、散华礼弥、OVERLORD、慎勇、人渣的本愿、白色相簿2、死亡笔记、DARLING in the FRANXX、鬼灭之刃\"}', '2');
INSERT INTO `site_setting` VALUES ('19', 'favorite', '自定义', '{\"title\":\"最喜欢我的女孩子们 ?\",\"content\":\"芙兰达、土间埋、食蜂操祈、佐天泪爷、樱岛麻衣、桐崎千棘、02、亚丝娜、高坂桐乃、五更琉璃、安乐冈花火、一色彩羽、英梨梨、珈百璃、时崎狂三、可儿那由多、和泉纱雾、早坂爱\"}', '2');
INSERT INTO `site_setting` VALUES ('20', 'favorite', '自定义', '{\"title\":\"最喜欢玩的游戏 ?\",\"content\":\"Stellaris、巫师、GTA、荒野大镖客、刺客信条、魔兽争霸、LOL、PUBG\"}', '2');
INSERT INTO `site_setting` VALUES ('21', 'badge', '徽标', '{\"title\":\"本博客已开源于 GitHub\",\"url\":\"https://github.com/Naccl/NBlog\",\"subject\":\"NBlog\",\"value\":\"Open Source\",\"color\":\"brightgreen\"}', '3');
INSERT INTO `site_setting` VALUES ('22', 'badge', '徽标', '{\"title\":\"由 Spring Boot 强力驱动\",\"url\":\"https://spring.io/projects/spring-boot/\",\"subject\":\"Powered\",\"value\":\"Spring Boot\",\"color\":\"blue\"}', '3');
INSERT INTO `site_setting` VALUES ('23', 'badge', '徽标', '{\"title\":\"Vue.js 客户端渲染\",\"url\":\"https://cn.vuejs.org/\",\"subject\":\"SPA\",\"value\":\"Vue.js\",\"color\":\"brightgreen\"}', '3');
INSERT INTO `site_setting` VALUES ('24', 'badge', '徽标', '{\"title\":\"UI 框架 Semantic-UI\",\"url\":\"https://semantic-ui.com/\",\"subject\":\"UI\",\"value\":\"Semantic-UI\",\"color\":\"semantic-ui\"}', '3');
INSERT INTO `site_setting` VALUES ('25', 'badge', '徽标', '{\"title\":\"阿里云提供服务器及域名相关服务\",\"url\":\"https://www.aliyun.com/\",\"subject\":\"VPS & DNS\",\"value\":\"Aliyun\",\"color\":\"blueviolet\"}', '3');
INSERT INTO `site_setting` VALUES ('26', 'badge', '徽标', '{\"title\":\"静态资源托管于 GitHub\",\"url\":\"https://github.com/\",\"subject\":\"OSS\",\"value\":\"GitHub\",\"color\":\"github\"}', '3');
INSERT INTO `site_setting` VALUES ('27', 'badge', '徽标', '{\"title\":\"jsDelivr 加速静态资源\",\"url\":\"https://www.jsdelivr.com/\",\"subject\":\"CDN\",\"value\":\"jsDelivr\",\"color\":\"orange\"}', '3');
INSERT INTO `site_setting` VALUES ('28', 'badge', '徽标', '{\"color\":\"lightgray\",\"subject\":\"CC\",\"title\":\"本站点采用 CC BY 4.0 国际许可协议进行许可\",\"url\":\"https://creativecommons.org/licenses/by/4.0/\",\"value\":\"BY 4.0\"}', '3');
INSERT INTO `site_setting` VALUES ('29', 'friendContent', '友链页面信息', '随机排序，不分先后。欢迎交换友链~(￣▽￣)~*\n\n* 昵称：Naccl\n* 一句话：游龙当归海，海不迎我自来也。\n* 网址：[https://naccl.top](https://naccl.top)\n* 头像URL：[https://naccl.top/img/avatar.jpg](https://naccl.top/img/avatar.jpg)\n\n仅凭个人喜好添加友链，请在收到我的回复邮件后再于贵站添加本站链接。原则上已添加的友链不会删除，如果你发现自己被移除了，恕不另行通知，只需和我一样做就好。\n\n', '4');
INSERT INTO `site_setting` VALUES ('30', 'friendCommentEnabled', '友链页面评论开关', '1', '4');

-- ----------------------------
-- Table structure for `tag`
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) NOT NULL,
  `color` varchar(255) DEFAULT NULL COMMENT '标签颜色(可选)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tag
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `nickname` varchar(255) NOT NULL COMMENT '昵称',
  `avatar` varchar(255) NOT NULL COMMENT '头像地址',
  `email` varchar(255) NOT NULL COMMENT '邮箱',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `role` varchar(255) NOT NULL COMMENT '角色访问权限',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'Admin', '$2a$10$4wnwMW8Z4Bn6wR4K1YlbquQunlHM/4rvudVBX8oyfx16xeVtI6i7C', 'Admin', '/img/avatar.jpg', 'admin@naccl.top', '2020-09-21 16:47:18', '2020-09-21 16:47:22', 'ROLE_admin');
INSERT INTO `user` VALUES ('2', 'admin', '111111', 'admin', '/img/avatar.jpg', 'admin@blog.cn', '2022-11-10 22:05:27', '2022-11-10 22:05:30', 'ROLE_admin');

-- ----------------------------
-- Table structure for `visitor`
-- ----------------------------
DROP TABLE IF EXISTS `visitor`;
CREATE TABLE `visitor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(36) NOT NULL COMMENT '访客标识码',
  `ip` varchar(255) DEFAULT NULL COMMENT 'ip',
  `ip_source` varchar(255) DEFAULT NULL COMMENT 'ip来源',
  `os` varchar(255) DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(255) DEFAULT NULL COMMENT '浏览器',
  `create_time` datetime NOT NULL COMMENT '首次访问时间',
  `last_time` datetime NOT NULL COMMENT '最后访问时间',
  `pv` int(11) DEFAULT NULL COMMENT '访问页数统计',
  `user_agent` varchar(2000) DEFAULT NULL COMMENT 'user-agent用户代理',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_uuid` (`uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of visitor
-- ----------------------------

-- ----------------------------
-- Table structure for `visit_log`
-- ----------------------------
DROP TABLE IF EXISTS `visit_log`;
CREATE TABLE `visit_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(36) DEFAULT NULL COMMENT '访客标识码',
  `uri` varchar(255) NOT NULL COMMENT '请求接口',
  `method` varchar(255) NOT NULL COMMENT '请求方式',
  `param` varchar(2000) NOT NULL COMMENT '请求参数',
  `behavior` varchar(255) DEFAULT NULL COMMENT '访问行为',
  `content` varchar(255) DEFAULT NULL COMMENT '访问内容',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `ip` varchar(255) DEFAULT NULL COMMENT 'ip',
  `ip_source` varchar(255) DEFAULT NULL COMMENT 'ip来源',
  `os` varchar(255) DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(255) DEFAULT NULL COMMENT '浏览器',
  `times` int(11) NOT NULL COMMENT '请求耗时（毫秒）',
  `create_time` datetime NOT NULL COMMENT '访问时间',
  `user_agent` varchar(2000) DEFAULT NULL COMMENT 'user-agent用户代理',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of visit_log
-- ----------------------------

-- ----------------------------
-- Table structure for `visit_record`
-- ----------------------------
DROP TABLE IF EXISTS `visit_record`;
CREATE TABLE `visit_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pv` int(11) NOT NULL COMMENT '访问量',
  `uv` int(11) NOT NULL COMMENT '独立用户',
  `date` varchar(255) NOT NULL COMMENT '日期"02-23"',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of visit_record
-- ----------------------------
