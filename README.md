# QBlog

## 基于SpringBoot和Vue的博客系统

<p align="center">
    <a href="https://github.com/spring-projects/spring-boot" target="_blank">	
      <img src="https://img.shields.io/badge/SpringBoot-2.7.5-orange">
    </a>
    <a href="https://github.com/vuejs/vue" target="_blank">	
      <img src="https://img.shields.io/badge/Vue-2.6.11-brightgreen">
    </a>
    <a href="https://docs.oracle.com/javase/8/docs/api/?xd_co_f=47c934d9-e663-4eba-819c-b726fc2d0847" target="_blank">	
      <img src="https://img.shields.io/badge/JDK-1.8-orange">
    </a>
    <a href="https://github.com/baomidou/mybatis-plus" target="_blank">	
      <img src="https://img.shields.io/badge/Mybatis--Plus-%3E3.5.2-orange">
    </a>
    <a href="https://github.com/ElemeFE/element" target="_blank">
        <img src="https://img.shields.io/badge/Element-%3E2.13.2-brightgreen" alt="element">
    </a>
    <a href="https://github.com/whtli/QBlogAdmin/blob/master/LICENSE" target="_blank">	
      <img src="https://img.shields.io/badge/license-MIT-blue">
    </a>
</p>

## 简介

+ 本项目：
    - 博客系统后端：https://github.com/whtli/QBlogBackend

+ 关联项目：
    - 后台管理系统：https://github.com/whtli/QBlogAdmin  账号`admin`密码`111111`
    - 前台访问界面：https://github.com/whtli/QBlogFront

+ 参考项目：
    - [NBlog](https://github.com/Naccl/NBlog)
    - [pure-design](https://github.com/xqnode/pure-design)

## 技术栈

### 后端

+ 核心框架：[Spring Boot](https://github.com/spring-projects/spring-boot)、MySQL
+ Token：[java-jwt](https://github.com/auth0/java-jwt)
+ ORM 框架：[MyBatis-Plus](https://github.com/baomidou/mybatis-plus)
+ NoSQL 缓存：[Redis](https://github.com/redis/redis)
+ 工具类库：[Hutool](https://github.com/dromara/hutool)
+ 离线 IP 地址库：[ip2region](https://github.com/lionsoul2014/ip2region)
+ UserAgent 解析：[yauaa](https://github.com/nielsbasjes/yauaa)
+ 图像云存储：七牛云

### 前端

+ 核心框架：[Vue2](https://github.com/vuejs/vue)、[Vue Router](https://github.com/vuejs/vue-router)、[Vuex](https://github.com/vuejs/vuex)
+ UI 框架：[Element UI](https://github.com/ElemeFE/element)
+ 后台 UI：基于[vue-admin-template](https://github.com/PanJiaChen/vue-admin-template) 模板
+ 前台 UI：参考[Semantic UI](https://semantic-ui.com/)
+ 博客排版：参考 [typo.css](https://github.com/sofish/typo.css)
+ 网络请求库：[axios](https://github.com/axios/axios)
+ MarkDown：[mavonEditor](https://github.com/hinesboy/mavonEditor)
+ 可视化图表库：[echarts](https://github.com/apache/echarts)
+ 其他：[v-viewer](https://github.com/fengyuanchen/viewerjs)

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

## 注意事项

+ 创建 MySQL 数据库，字符集为utf8mb4，并执行[qblog.sql](qblog.sql)初始化表
+ 确保 Maven 和 npm 能够成功导入现版本依赖
+ 项目运行之前需要修改[application.yml](src/main/resources/application.yml)的配置信息，如本地数据库名、七牛云的accessKey和accessSecretKey
+ 安装 Redis 并启动，然后启动后端服务
+ 后台管理系统的默认用户名密码为admin，123456
+ 分别在`QBlogAdmin`和`QBlogFront`目录下执行`npm install`安装依赖
+ 在`QBlogAdmin`目录下执行`npm run dev`启动后台页面
+ 在`QBlogFront`目录下执行`npm run serve`启动前台页面
+ 部署前需要根据实际情况修改前端项目的baseURL和后端项目的server.address和server.port

## 感谢上面提到的每个开源项目

