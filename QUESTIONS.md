## 1.集成mybatis并测试 (在3中更换为mybatis-plus)

+ pom.xml

    ```xml
            <dependency>
                <groupId>org.mybatis.spring.boot</groupId>
                <artifactId>mybatis-spring-boot-starter</artifactId>
                <version>2.2.2</version>
            </dependency>

            <dependency>
                <groupId>com.mysql</groupId>
                <artifactId>mysql-connector-j</artifactId>
                <scope>runtime</scope>
            </dependency>
    ```

+ application.yml

  ```yaml
  spring:
    datasource:
      url: jdbc:mysql://localhost:3306/database?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8
      username: xxxx
      password: xxxx
  ```

+ 测试运行报错Invalid bound statement (not found): cn.xxx.dao.TestMapper ……， 需要在yml文件里添加mybatis配置

    ```yaml
    mybatis:
      mapper-locations: classpath*:mapper/*.xml
      type-aliases-package: cn.xxx.dao
    ```


## 2. 使用mybatis-generator (目前来看并不好用，暂时放弃)

+ 添加配置文件

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <!DOCTYPE generatorConfiguration
          PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
          "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
  <generatorConfiguration>
      <properties resource="application.yml"></properties>
      <classPathEntry location="D:\Software\apache-maven-3.5.4\MavenLocalRepository\mysql\mysql-connector-java\8.0.20\mysql-connector-java-8.0.20.jar"/>
      <context id="test" targetRuntime="MyBatis3">
          <plugin type="org.mybatis.generator.plugins.EqualsHashCodePlugin"></plugin>
          <plugin type="org.mybatis.generator.plugins.SerializablePlugin"></plugin>
          <plugin type="org.mybatis.generator.plugins.ToStringPlugin"></plugin>
          <commentGenerator>
              <!-- 这个元素用来去除指定生成的注释中是否包含生成的日期 false:表示保护 -->
              <!-- 如果生成日期，会造成即使修改一个字段，整个实体类所有属性都会发生变化，不利于版本控制，所以设置为true -->
              <property name="suppressDate" value="true" />
              <!-- 是否去除自动生成的注释 true：是 ： false:否 -->
              <property name="suppressAllComments" value="true" />
          </commentGenerator>
          <!--数据库链接URL，用户名、密码 -->
          <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
                          connectionURL="jdbc:mysql://localhost:3306/nblog?useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=false&amp;serverTimezone=GMT%2B8"
                          userId="root"
                          password="root">
          </jdbcConnection>
          <javaTypeResolver>
              <!-- This property is used to specify whether MyBatis Generator should
                  force the use of java.math.BigDecimal for DECIMAL and NUMERIC fields, -->
              <property name="forceBigDecimals" value="false" />
          </javaTypeResolver>
          <!-- targetpakage是即将生成的目录，targetProject是对应的前缀目录。可根据自己需求生到对应目录。下次运行会直接默认覆盖原来位置的文件 -->
          <!-- 生成模型的包名和位置   映射实体类的位置 -->
          <javaModelGenerator targetPackage="cn.li98.blog.model" targetProject="src/main/java">
              <property name="enableSubPackages" value="false" />
              <property name="trimStrings" value="true" />
          </javaModelGenerator>
          <!-- 生成映射文件的包名和位置  mapper.xml -->
          <sqlMapGenerator targetPackage="mapper"
                           targetProject=".\src\main\resources">
              <property name="enableSubPackages" value="false" />
          </sqlMapGenerator>
          <!-- 生成DAO的包名和位置   mapper接口-->
          <javaClientGenerator type="XMLMAPPER" targetPackage="cn.li98.blog.dao" targetProject="src/main/java">
              <property name="enableSubPackages" value="false" />
          </javaClientGenerator>
  
          <!-- 要生成哪些表  orders是我的表名，Orders是生成的类名，比如我的映射类为Order，映射接口OrderMapper, 映射文件为OrderMapper.xml，
          可以添加多个表，里面的几个配置大概意思就是是否允许生成example文件和支持selectByExample。
          用过Mybatis的应该知道selectByExample，对于一些简单查询用这个还是比较方便的。 -->
          <table tableName="user" domainObjectName="User"
                 enableCountByExample="false" enableUpdateByExample="false"
                 enableDeleteByExample="false" enableSelectByExample="false"
                 selectByExampleQueryId="false"></table>
          <!-- 要生成哪些表  -->
          <table tableName="blog" domainObjectName="Blog"
                 enableCountByExample="false" enableUpdateByExample="false"
                 enableDeleteByExample="false" enableSelectByExample="false"
                 selectByExampleQueryId="false"></table>
      </context>
  </generatorConfiguration>
  
  ```

+ 修改pom文件，在plugins中添加新的plugin

  ```xml
  <plugin>
                  <groupId>org.mybatis.generator</groupId>
                  <artifactId>mybatis-generator-maven-plugin</artifactId>
                  <version>1.3.5</version>
                  <configuration>
                      <configurationFile>src/main/resources/mybatis-generator/GeneratorMapper.xml</configurationFile>
                      <verbose>true</verbose>
                      <overwrite>true</overwrite>
                  </configuration>
                  <executions>
                      <!-- <execution> <id>Generate MyBatis Artifacts</id> <goals> <goal>generate</goal> </goals> </execution> -->
                  </executions>
                  <dependencies>
                      <dependency>
                          <groupId>org.mybatis.generator</groupId>
                          <artifactId>mybatis-generator-core</artifactId>
                          <version>1.3.5</version>
                      </dependency>
                  </dependencies>
              </plugin>
  ```
+ 测试生成model、dao、映射文件


## 3.集成mybatis-plus并测试

+ pom.xml

    ```xml
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.5.2</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-freemarker</artifactId>
        </dependency>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>
        <!--mp代码生成器-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-generator</artifactId>
            <version>3.5.2</version>
        </dependency>
    ```

  + application.yml

    ```yaml
    spring:
      datasource:
        url: jdbc:mysql://localhost:3306/nblog?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8
        username: root
        password: root
        driver-class-name: com.mysql.cj.jdbc.Driver
  
    mybatis-plus:
      mapper-locations: classpath*:mapper/*.xml
      type-aliases-package: cn.li98.blog.dao
    ```
    
## 4.整合七牛云
+ [参考资料1](https://developer.qiniu.com/kodo/1239/java)
+ [参考资料2](https://blog.csdn.net/qq_42542348/article/details/126338528)
+ pom.xml
  ```xml
        <!--七牛云-->
        <dependency>
            <groupId>com.qiniu</groupId>
            <artifactId>qiniu-java-sdk</artifactId>
            <version>[7.7.0, 7.10.99]</version>
        </dependency>
  ```
+ application.yml
  ```yaml
  qiniu:
    # AK
    accessKey: 
    # SK
    accessSecretKey: 
    # 存储空间名
    bucketName: 
    # 外链域名/路径
    domainName: 
  ```
+ BlogControllor
  ```java
  package cn.li98.blog.controllor.admin;
  
  import cn.li98.blog.common.Result;
  import cn.li98.blog.model.Blog;
  import cn.li98.blog.service.BlogService;
  import cn.li98.blog.utils.QiniuUtils;
  import com.baomidou.mybatisplus.core.toolkit.StringUtils;
  import lombok.extern.slf4j.Slf4j;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.web.bind.annotation.*;
  import org.springframework.web.multipart.MultipartFile;
  
  import java.util.*;
  
  @Slf4j
  @RestController
  @RequestMapping("/admin/blog")
  public class BlogControllor {
      private static final String CREATE = "create";
      private static final String UPDATE = "update";
      @Autowired
      BlogService blogService;
  
      @Autowired
      QiniuUtils qiniuUtils;
  
      @PostMapping("/addImage")
      public Result addImage(@RequestParam(value = "image") MultipartFile multipartFile) {
          if (multipartFile.isEmpty()) {
              log.error("未选择图片");
              return Result.fail("请选择图片");
          }
          log.info("准备上传到七牛云");
          Map<String, String> uploadImagesUrl = qiniuUtils.uploadImage(multipartFile);
          log.info("成功返回图像地址 : " + uploadImagesUrl.get("imageUrl"));
          return Result.succ(20000, "上传成功", uploadImagesUrl);
      }
  
      @PostMapping("/deleteImg")
      public Result deleteImg(@RequestHeader("Img-Delete") String url) {
          if (url.isEmpty()) {
              return Result.fail("无此图片");
          }
          //删除云服务器文件
          boolean flag = qiniuUtils.delete(url);
          String filename = url.split("com/")[1];
          if (flag) {
              log.info("图片删除成功: " + filename);
              return Result.succ(20000, "图片删除成功", filename);
          }
          log.error("图片删除失败 : " + filename);
          return Result.fail("图片删除失败", filename);
      }
  }
  ```
+ [QiniuUtils.java](./src/main/java/cn/li98/blog/utils/QiniuUtils.java)
  ```java
  import com.qiniu.common.QiniuException;
  import com.qiniu.storage.BucketManager;
  import com.qiniu.storage.Configuration;
  import com.qiniu.storage.Region;
  import com.qiniu.storage.UploadManager;
  import com.qiniu.util.Auth;
  import lombok.extern.slf4j.Slf4j;
  import org.springframework.beans.factory.annotation.Value;
  import org.springframework.stereotype.Component;
  import org.springframework.web.multipart.MultipartFile;
  
  import java.io.IOException;
  import java.text.SimpleDateFormat;
  import java.util.*;
  
  /**
   * @AUTHOR: whtli
   * @DATE: 2022/11/15
   * @DESCRIPTION: 七牛云工具类，AK等配置内容通过@Value()从application.yml中获取
   */
  @Slf4j
  @Component
  public class QiniuUtils {
      /**
       * AK
       */
      @Value("${qiniu.accessKey}")
      private String accessKey;
      /**
       * SK
       */
      @Value("${qiniu.accessSecretKey}")
      private String accessSecretKey;
      /**
       * 存储空间名
       */
      @Value("${qiniu.bucketName}")
      private String bucketName;
      /**
       * 外链域名/路径
       */
      @Value("${qiniu.domainName}")
      private String domainName;
  
      /*
      // 处理多文件, 暂时不使用
      public Map<String, List<String>> uploadImages(MultipartFile[] multipartFiles){
          Map<String, List<String>> map = new HashMap<>();
          List<String> imageUrls = new ArrayList<>();
          for(MultipartFile file : multipartFiles){
              imageUrls.add(uploadImage(file));
          }
          map.put("imageUrl",imageUrls);
          return map;
      }*/
  
      /**
       * 上传图片到七牛云
       *
       * @param multipartFile
       * @return Map<String, String>
       */
      public Map<String, String> uploadImage(MultipartFile multipartFile) {
          log.info("开始上传到七牛云");
          try {
              // 1.获取文件上传的流
              byte[] fileBytes = multipartFile.getBytes();
              // 2.创建日期目录分隔
              SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
              String datePath = dateFormat.format(new Date());
  
              // 3.获取文件名
              String originalFilename = multipartFile.getOriginalFilename();
              String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
              String filename = datePath + "/" + UUID.randomUUID().toString().replace("-", "") + suffix;
  
              // 4.构造一个带指定 Region 对象的配置类
              // Region.huabei()根据自己的对象空间的地址选
              Configuration cfg = new Configuration(Region.huabei());
              // 后来换了稳定的新加坡的空间并且绑定了个人域名没有做https的配置这时候要配置这个选项
              // 域名不支持https访问会报错ssl验证error
              // cfh.useHttpsDomains=false 关闭实列即可 默认是开启的
              UploadManager uploadManager = new UploadManager(cfg);
  
              // 5.获取七牛云提供的 token
              Auth auth = Auth.create(accessKey, accessSecretKey);
              String upToken = auth.uploadToken(bucketName);
              uploadManager.put(fileBytes, filename, upToken);
  
              // 6.上传成功返回地址
              log.info("成功上传到七牛云");
              String imageUrl = domainName + filename;
              Map<String, String> map = new HashMap<>();
              map.put("imageUrl", imageUrl);
  
              return map;
          } catch (IOException e) {
              e.printStackTrace();
          }
          return null;
      }
  
      /**
       * 删除七牛云服务器中的图片
       *
       * @param url
       * @return true:删除成功; false:删除失败
       */
      public boolean delete(String url) {
          log.info("url : " + url);
          //创建凭证
          Auth auth = Auth.create(accessKey, accessSecretKey);
          BucketManager bucketManager = new BucketManager(auth, new Configuration(Region.huabei()));
          // 此时pos为全路径，需要将外链域名去掉，七牛云云端删除时只需要提供文件名即可
          // 截取最后一个指定字符串(此处为"/")之后的字符串。 此处fileName="9cb077ef572f49948f0dda60ed850a9d.jpg"
          String fileName = url.split(domainName)[1];
          log.info("file to delete : " + fileName);
          try {
              bucketManager.delete(bucketName, fileName);
              return true;
          } catch (QiniuException e) {
              // 如果遇到异常，说明删除失败
              log.error(String.valueOf(e.code()));
              log.error(e.response.toString());
          }
          return false;
      }
  }
  ```