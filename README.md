# QBlog

+ 基于SpringBoot和Vue的博客系统 

<p align="center">
	<img src="https://img.shields.io/badge/JDK-1.8+-orange">
	<img src="https://img.shields.io/badge/SpringBoot-2.7.5.RELEASE-brightgreen">
	<img src="https://img.shields.io/maven-central/v/com.baomidou/mybatis-plus.svg?style=flat-square">
	<img src="https://img.shields.io/badge/Vue-2.6.11-brightgreen">
	<img src="https://img.shields.io/badge/license-MIT-blue">
</p>

## 简介

+ 本项目：
    - 博客系统后端：https://github.com/whtli/QBlogBackend

+ 关联项目：
    - 后台管理系统：https://github.com/whtli/QBlogFront  账号`admin`密码`111111`
    - 前台访问界面：https://github.com/whtli/QBlogAdmin

## 技术栈

+ 核心框架：[Spring Boot](https://github.com/spring-projects/spring-boot)
+ Token：[java-jwt](https://github.com/auth0/java-jwt)
+ ORM 框架：[MyBatis-Plus](https://github.com/baomidou/mybatis-plus)
+ NoSQL 缓存：[Redis](https://github.com/redis/redis)
+ 工具类库：[Hutool](https://github.com/dromara/hutool)
+ 离线 IP 地址库：[ip2region](https://github.com/lionsoul2014/ip2region)
+ UserAgent 解析：[yauaa](https://github.com/nielsbasjes/yauaa)
+ 图像云存储：七牛云

## 实现功能
> 项目过程记录见[PROGRESS_RECORDING.md](PROGRESS_RECORDING.md)

### 后台相关功能
+ 博客、分类、标签等常用的增删改查
+ 整合 ECharts 的数据统计
+ 图像上传到七牛云
+ 博客导入导出
+ 文件上传
+ 自定义 AOP 记录后台操作日志
+ JWT 权限
+ 基于 RBAC 的权限模型
+ 动态路由
+ Redis 数据缓存
+ 单表增删改查（包括分页模糊查询）

### 前台相关功能
+ 博客列表
+ 博客归档
+ 分类查询
+ 标签查询
+ 多级评论
+ 自定义 AOP 记录前台访问日志

## 感谢上面提到的每个开源项目

