## 1.集成mybatis并测试 (在3中更换为mybatis-plus)

+ [pom.xml](pom.xml)
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

+ [application.yml](./src/main/resources/application.yml)
  ```yaml
  spring:
    datasource:
      url: jdbc:mysql://localhost:3306/database?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8
      username: xxxx
      password: xxxx
  ```

+ 测试运行报错Invalid bound statement (not found): cn.xxx.dao.TestMapper ……， 需要在[application.yml](./src/main/resources/application.yml)文件里添加mybatis配置
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

+ 修改[pom.xml](pom.xml)文件，在plugins中添加新的plugin

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

+ [pom.xml](pom.xml)
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

+ [application.yml](./src/main/resources/application.yml)
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

+ [pom.xml](pom.xml)
  ```xml
        <!--七牛云-->
        <dependency>
            <groupId>com.qiniu</groupId>
            <artifactId>qiniu-java-sdk</artifactId>
            <version>[7.7.0, 7.10.99]</version>
        </dependency>
  ```

+ [application.yml](./src/main/resources/application.yml)
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
  
+ [BlogControllor](./src/main/java/cn/li98/blog/controllor/admin/BlogControllor.java)
    ```java
    import cn.hutool.core.lang.Assert;
    import cn.li98.blog.common.Result;
    import cn.li98.blog.model.Blog;
    import cn.li98.blog.service.BlogService;
    import cn.li98.blog.utils.QiniuUtils;
    import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
    import com.baomidou.mybatisplus.core.metadata.IPage;
    import com.baomidou.mybatisplus.core.toolkit.StringUtils;
    import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;
    
    import java.util.*;
    
    /**
     * @AUTHOR: whtli
     * @DATE: 2022/11/10
     * @DESCRIPTION:
     */
    @Slf4j
    @RestController
    @RequestMapping("/admin/blog")
    public class BlogControllor {
        @Autowired
        BlogService blogService;
    
        @Autowired
        QiniuUtils qiniuUtils;
    
        /**
         * 向七牛云服务器中上传一张图片
         *
         * @param multipartFile
         * @return 上传成功后返回的图片地址作为data
         */
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
    
        /**
         * 删除七牛云中的指定图片
         *
         * @param url
         * @return 图片名（带路径）作为data
         */
        @PostMapping("/deleteImg")
        public Result deleteImg(@RequestHeader("Img-Delete") String url) {
            if (url.isEmpty()) {
                return Result.fail("无此图片");
            }
            //删除云服务器图片
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


## 5. 实现增删改查功能
### 新增、修改
+ 因为功能类似，只有个别的字段需要做区分设置，所以将增、改功能整合到同一个controllor中，然后根据id的有无来区分类型，并在service、impl等后续过程中进行不同的操作
+ [BlogControllor](./src/main/java/cn/li98/blog/controllor/admin/BlogControllor.java)
  ```java
  @RequestMapping("/admin/blog")
  public class BlogControllor {
      @Autowired
      BlogService blogService;
  
      /**
       * 创建、修改博客
       *
       * @param blog
       * @return 成功则"发布成功"作为data
       */
      @PostMapping("/submitBlog")
      public Result submitBlog(@RequestBody Blog blog) {
          // 验证字段
          if (StringUtils.isEmpty(blog.getTitle()) || StringUtils.isEmpty(blog.getDescription()) || StringUtils.isEmpty(blog.getContent()) || blog.getWords() == null || blog.getWords() < 0) {
              return Result.fail("参数有误");
          }
          int flag = 0;
          try {
              if (blog.getId() == null) {
                  flag = blogService.createBlog(blog);
              } else {
                  flag = blogService.updateBlog(blog);
              }
          } catch (Exception e) {
              log.error(e.toString());
          }
          if (flag == 1) {
              return Result.succ("发布成功");
          }
          return Result.fail("失败");
      }
      
      /**
       * 修改操作对应的根据指定id查询博客的接口
       * 可以根据指定的唯一id查询对应的博客
       *
       * @param id
       * @return 成功则Blog作为data
       */
      @GetMapping("/getBlogById")
      public Result getBlogById(@RequestParam Long id) {
          Blog blog = blogService.getById(id);
          Assert.notNull(blog, "该博客不存在");
          return Result.succ(20000, "查询成功", blog);
      }
  }
  ```

+ [BlogService](./src/main/java/cn/li98/blog/service/BlogService.java)
  ```java
  public interface BlogService extends IService<Blog> {
  
      /**
       * 新发布博客
       *
       * @param blog
       */
      int createBlog(Blog blog);
  
      /**
       * 更新已有博客
       *
       * @param blog
       */
      int updateBlog(Blog blog);
  }
  ```

+ [BlogServiceImpl](./src/main/java/cn/li98/blog/service/impl/BlogServiceImpl.java)
  ```java
  @Service
  public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
      @Autowired
      BlogMapper blogMapper;
  
      /**
       * 为新增或修改的博客设置参数
       * 这些常规参数在新增和修改过程中具备相同的设置规则
       * 包括：类别id、阅读时长、阅读量
       *
       * @param blog
       * @return Blog
       */
      private Blog setItemsOfBlog(Blog blog) {
          if (blog.getCategoryId() == null) {
              blog.setCategoryId(1L);
          }
          // TODO: 分类、标签等功能判断新增等功能
          if (blog.getReadTime() == null || blog.getReadTime() <= 0) {
              // 粗略计算阅读时长
              blog.setReadTime((int) Math.round(blog.getWords() / 200.0));
          }
          if (blog.getViews() == null || blog.getViews() < 0) {
              blog.setViews(0);
          }
          return blog;
      }
  
      /**
       * 创建博客
       * 需要常规地设置类别id、阅读时长、阅读量参数
       * 需要单独地设置创建时间、更新时间（等于创建）、创建用户（默认为1唯一的管理员）
       *
       * @param blog
       * @return 创建成功返回1，失败返回0
       */
      @Override
      public int createBlog(Blog blog) {
          Blog newBlog = setItemsOfBlog(blog);
          Date date = new Date();
          newBlog.setCreateTime(date);
          newBlog.setUpdateTime(date);
          newBlog.setUserId(1L);
          return blogMapper.createBlog(newBlog);
      }
  
      /**
       * 更新博客
       * 需要常规地设置类别id、阅读时长、阅读量参数
       * 需要单独地设置更新时间（当前时间）
       *
       * @param blog
       * @return 更新成功返回1，失败返回0
       */
      @Override
      public int updateBlog(Blog blog) {
          Blog newBlog = setItemsOfBlog(blog);
          Date date = new Date();
          newBlog.setUpdateTime(date);
          return blogMapper.updateBlog(blog);
      }
  }
  ```

+ [BlogMapper.xml](./src/main/resources/mapper/BlogMapper.xml)
  ```xml
  <!--  新增博客-->
  <insert id="createBlog" parameterType="cn.li98.blog.model.Blog" useGeneratedKeys="true" keyProperty="id">
    insert into blog (title, first_picture, description, content, is_published, is_comment_enabled,
    is_top, create_time, update_time, views, words, read_time, category_id, user_id, password)
    values (#{title}, #{firstPicture}, #{description}, #{content},#{isPublished}, #{isCommentEnabled},
    #{isTop}, #{createTime}, #{updateTime}, #{views}, #{words}, #{readTime}, #{categoryId}, #{userId}, #{password})
  </insert>
  <!--  更新博客-->
  <update id="updateBlog">
  update blog set title=#{title}, first_picture=#{firstPicture}, content=#{content}, description=#{description},
  is_published=#{isPublished}, is_comment_enabled=#{isCommentEnabled},
  is_top=#{isTop}, update_time=#{updateTime}, views=#{views},
  words=#{words}, read_time=#{readTime}, category_id=#{categoryId}, password=#{password}
  where id=#{id}
  </update>
  ```
### 删（逻辑删除）
+ 在blog表中新增逻辑删除字段`is_deleted` 
+ 在[Blog](./src/main/java/cn/li98/blog/model/Blog.java)实体类中对应的新增逻辑删除字段 `isDeleted`，并为其添加@TableLogic注解
    ```java
        /**
         * 逻辑删除
         */
        @TableLogic
        private Long isDeleted;
    ```

+ [BlogControllor](./src/main/java/cn/li98/blog/controllor/admin/BlogControllor.java)
  ```java
  @Slf4j
  @RestController
  @RequestMapping("/admin/blog")
  public class BlogControllor {
      @Autowired
      BlogService blogService;
  
      /**
       * 删除博客，逻辑删除，对应字段is_deleted
       * 删除操作变为修改is_deleted字段的操作
       * 1为逻辑删除，0（数据库字段默认值）为未删除
       *
       * @param id
       * @return 被逻辑删除的博客id作为data
       */
      @DeleteMapping("/deleteBlogById")
      public Result deleteBlogById(@RequestParam Long id) {
          System.out.println("deleteBlogById: " + id);
          boolean delete = blogService.removeById(id);
          System.out.println("delete: " + delete);
          if (delete) {
              return Result.succ(20000, "博客删除成功", id);
          } else {
              return Result.fail("博客删除失败", id);
          }
      }
  }
  ```

### 查询（多参数查询+分页查询）
+ [BlogControllor](./src/main/java/cn/li98/blog/controllor/admin/BlogControllor.java)
  ```java
  @Slf4j
  @RestController
  @RequestMapping("/admin/blog")
  public class BlogControllor {
      @Autowired
      BlogService blogService;
      
      /**
       * 获取博客列表
       * 可以实现无参数查询和多参数查询
       * 可以实现分页查询
       * 将分页列表和总记录数作为键值对存储到Map中
       * 分页列表用于前端的当前页展示
       * 总记录数用于前端展示博客总数，这个数值是当前数据库中未被删除的博客总数，是所有分页中的博客个数的和
       *
       * @param title
       * @param categoryId
       * @param pageNum
       * @param pageSize
       * @return 成功则Map作为data
       */
      @GetMapping("/getBlogs")
      public Result getBlogs(@RequestParam(value = "title", defaultValue = "") String title,
                             @RequestParam(value = "categoryId", defaultValue = "") Long categoryId,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
  
          QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
          // 将查询参数以键值对的形式存放到QueryWrapper中
          if (!StringUtils.isEmpty(title) && !StringUtils.isBlank(title)) {
              queryWrapper.like("title", title);
          }
          if (categoryId != null) {
              queryWrapper.eq("category_id", categoryId);
          }
          // 新建一个分页规则，pageNum代表当前页码，pageSize代表每页数量
          Page page = new Page(pageNum, pageSize);
          // 借助Page实现分页查询，借助QueryWrapper实现多参数查询
          IPage pageData = blogService.page(page, queryWrapper);
          if (pageData.getTotal() == 0 && pageData.getRecords().isEmpty()) {
              return Result.fail("查询失败，未查找到相应博客");
          }
          Map<String, Object> data = new HashMap<String, Object>();
          data.put("pageData", pageData);
          data.put("total", pageData.getTotal());
          return Result.succ(20000, "查询成功", data);
      }
  }
  ```


## 6. 添加获取统计数据的功能
### 6.1 添加对应于前端获取统计数据的后端功能 
+ [StatisticBlogCount](./src/main/java/cn/li98/blog/model/vo/StatisticBlogCount.java)，注意事项见6.2
```java
@Data
public class StatisticBlogCount {
    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 分类名
     */
    private String name;

    /**
     * 分类下博客数量
     */
    private Integer value;
}
```

+ [StatisticControllor](./src/main/java/cn/li98/blog/controllor/admin/StatisticControllor.java)
```java
@Slf4j
@RestController
@RequestMapping("/admin/statistic")
public class StatisticControllor {
    @Autowired
    StatisticService statisticService;

    /**
     * 获取统计数据
     *
     * @return 存放了博客分类统计数据列表和分类名列表的哈希表
     */
    @GetMapping("/getStatistic")
    public Result getStatistic() {
        Map<String, List> map = statisticService.getBlogCountList();
        // System.out.println(map);
        return Result.succ(map);
    }
}
```
+ [StatisticService](./src/main/java/cn/li98/blog/service/StatisticService.java)
```java
public interface StatisticService {
    /**
     * 调用Mapper层获取统计数据
     * @return 存放了博客分类统计数据列表和分类名列表的哈希表
     */
    Map<String, List> getBlogCountList();
}
```

+ [StatisticServiceImpl](./src/main/java/cn/li98/blog/service/impl/StatisticServiceImpl.java)
```java
@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    StatisticMapper statisticMapper;

    /**
     * 调用Mapper层获取统计数据
     *
     * @return 存放了博客分类统计数据列表和分类名列表的哈希表
     */
    @Override
    public Map<String, List> getBlogCountList() {
        Map<String, List> map = new HashMap<>();
        List<StatisticBlogCount> blogCountList = statisticMapper.getBlogCountList();
        /* 使用for循环获取分类名列表
        List<String> categoryName = new ArrayList<>();
        for (StatisticBlogCount item : blogCountList) {
            categoryName.add(item.getName());
        }*/
        // 使用stream获取分类名列表，与for循环效果相同
        List<String> categoryName = blogCountList.stream().map(StatisticBlogCount::getName).collect(Collectors.toList());
        map.put("blogCountList", blogCountList);
        map.put("categoryName", categoryName);
        return map;
    }
}
```

+ dao层的[StatisticMapper](./src/main/java/cn/li98/blog/dao/StatisticMapper.java)
```java
@Mapper
public interface StatisticMapper {
    /**
     * 获取统计数据
     * @return 以类别id为分组依据的统计数据列表
     */
    List<StatisticBlogCount> getBlogCountList();
}
```

+ [StatisticMapper.xml](./src/main/resources/mapper/StatisticMapper.xml)
```xml
<mapper namespace="cn.li98.blog.dao.StatisticMapper">
  <!--  查询分类博客数据-->
  <select id="getBlogCountList" resultType="cn.li98.blog.model.vo.StatisticBlogCount">
    SELECT category_id, category_name as name, COUNT(category_id) AS value
    from blog b LEFT JOIN category c ON b.category_id = c.id
    WHERE b.is_deleted = 0
    group by category_id
  </select>
</mapper>
```

### 6.2 VO的设计注意事项：字段的使用
+ 因为要用于前端的echarts展示，所以字段名需要与echarts的规范设定，不能自行指定。
+ 比如分类名字段，不能定义为`categoryName`，必须定义为`name`；博客数量字段，不能定义为`blogCount`，必须定义为`value`，以方便前端的直接获取匹配。
+ 如果不安装echarts的字段设计来定义字段，则需要在VO传到前端之后，在前端复制一个字段正确的新列表，否则图表不能正常展示。
