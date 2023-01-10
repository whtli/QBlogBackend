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
  
+ [BlogController](src/main/java/cn/li98/blog/controller/admin/BlogController.java)
    ```java
  
    import cn.li98.blog.common.Result;
    import cn.li98.blog.service.BlogService;
    import cn.li98.blog.utils.QiniuUtils;
        import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;
    
    import java.util.*;
    
    /**
     * @author: whtli
     * @date: 2022/11/10
     * @description:
     */
    @Slf4j
    @RestController
    @RequestMapping("/admin/blog")
    public class BlogController {
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
   * @author: whtli
   * @date: 2022/11/15
   * @description: 七牛云工具类，AK等配置内容通过@Value()从application.yml中获取
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
+ [BlogController](src/main/java/cn/li98/blog/controller/admin/BlogController.java)
  ```java
  @RequestMapping("/admin/blog")
  public class BlogController {
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
  <insert id="createBlog" parameterType="cn.li98.blog.model.entity.Blog" useGeneratedKeys="true" keyProperty="id">
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

+ [BlogController](src/main/java/cn/li98/blog/controller/admin/BlogController.java)
  ```java
  @Slf4j
  @RestController
  @RequestMapping("/admin/blog")
  public class BlogController {
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
+ [BlogController](src/main/java/cn/li98/blog/controller/admin/BlogController.java)
  ```java
  @Slf4j
  @RestController
  @RequestMapping("/admin/blog")
  public class BlogController {
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

+ [StatisticController](src/main/java/cn/li98/blog/controller/admin/StatisticController.java)
  ```java
  @Slf4j
  @RestController
  @RequestMapping("/admin/statistic")
  public class StatisticController {
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

+ 新增了[Comment](src/main/java/cn/li98/blog/model/Comment.java)实体类

### 6.2 VO的设计注意事项：字段的使用
+ 因为要用于前端的echarts展示，所以字段名需要与echarts的规范设定，不能自行指定。
+ 比如分类名字段，不能定义为`categoryName`，必须定义为`name`；博客数量字段，不能定义为`blogCount`，必须定义为`value`，以方便前端的直接获取匹配。
+ 如果不安装echarts的字段设计来定义字段，则需要在VO传到前端之后，在前端复制一个字段正确的新列表，否则图表不能正常展示。


## 7. 批量删除博客
+ 在BlogController中添加接口，获取前端传来的列表，调用已有的删除单个博客的方法即可
  ```java
      /**
       * 删除博客，逻辑删除，对应字段deleted
       * 删除操作变为修改deleted字段的操作
       * 1为逻辑删除，0（数据库字段默认值）为未删除
       *
       * @param id
       * @return 被逻辑删除的博客id作为data
       */
      @DeleteMapping("/deleteBlogById")
      public Result deleteBlogById(@RequestParam Long id) {
          log.info("blog to delete : " + id);
          boolean delete = blogService.removeById(id);
          System.out.println("delete: " + delete);
          if (delete) {
              return Result.succ(20000, "博客删除成功", id);
          } else {
              return Result.fail("博客删除失败", id);
          }
      }
  
      /**
       * 批量删除博客，逻辑删除
       *
       * @param ids
       * @return 被逻辑删除的多个博客id列表
       */
      @DeleteMapping("/deleteBlogBatchByIds")
      public Result deleteBlogBatchByIds(@RequestParam String ids) {
          String[] list = ids.split(",");
          List<Long> idList = new ArrayList<>();
          for (String id : list) {
              idList.add(Long.valueOf(id));
          }
          int deletedBlogCount = 0;
          for (Long id : idList) {
              if (deleteBlogById(id).getCode() == 20000) {
                  deletedBlogCount++;
              } else {
                  Result.fail("ID为 " + id + " 的博客删除失败，后续删除停止", id);
              }
          }
          if (deletedBlogCount == idList.size()) {
              return Result.succ(20000, "批量删除成功", idList);
          }
          return Result.fail("批量删除失败");
      }
  ```


## 8. 把shiro-redis更换成jwt
+ 删除所有shiro-redis相关的配置
+ pom中添加依赖 
```xml
 <!-- JWT -->
 <dependency>
     <groupId>com.auth0</groupId>
     <artifactId>java-jwt</artifactId>
     <version>3.10.3</version>
 </dependency>
```
+ 新增 [TokenUtils](src/main/java/cn/li98/blog/utils/TokenUtils.java)
```java
@Component
public class TokenUtils {

    private static UserService staticUserService;

    @Resource
    private UserService userService;

    @PostConstruct
    public void setUserService() {
        staticUserService = userService;
    }

    /**
     * 生成token
     *
     * @return
     */
    public static String genToken(Long userId, String sign) {
        return JWT.create().withAudience(String.valueOf(userId)) // 将 user id 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 2小时后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
    }

    /**
     * 获取当前登录的用户信息
     *
     * @return user对象
     */
    public static User getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("Authorization");
            if (StrUtil.isNotBlank(token)) {
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserService.getById(Integer.valueOf(userId));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}

```
+ 新增 [JwtInterceptor](src/main/java/cn/li98/blog/config/interceptor/JwtInterceptor.java)
```java
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");
        // 如果不是映射到方法直接通过，不需要拦截
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 执行认证
        if (StrUtil.isBlank(token)) {
            throw new ServiceException(Constant.CODE_ACCESS_DENIED, "无token，请重新登录");
        }
        // 获取 token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new ServiceException(Constant.CODE_ACCESS_DENIED, "token验证失败，请重新登录");
        }
        // 根据token中的userid查询数据库
        User user = userService.getById(userId);
        if (user == null) {
            throw new ServiceException(Constant.CODE_ACCESS_DENIED, "用户不存在，请重新登录");
        }
        // 用户密码加签验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token); // 验证token
        } catch (JWTVerificationException e) {
            throw new ServiceException(Constant.CODE_ACCESS_DENIED, "token验证失败，请重新登录");
        }
        return true;
    }
}
```
+ 新增 [InterceptorConfig](src/main/java/cn/li98/blog/config/InterceptorConfig.java)
```java
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")  // 拦截所有请求，通过判断token是否合法来决定是否需要登录
                .excludePathPatterns("/admin/login", "/admin/register", "/**/export", "/**/import", "/admin/file/**",
                        "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/api", "/api-docs", "/api-docs/**")
                .excludePathPatterns("/**/*.html", "/**/*.js", "/**/*.css", "/**/*.woff", "/**/*.ttf");  // 放行静态文件

    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
}
```


## 9. 新增文件上传接口并实现功能
+ 数据库中创建`sys_file`表
```sql
-- ----------------------------
-- Table structure for `sys_file`
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件名称',
  `type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件类型',
  `size` bigint(20) DEFAULT NULL COMMENT '文件大小(kb)',
  `url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '下载链接',
  `md5` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件md5',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  `enable` tinyint(1) DEFAULT '1' COMMENT '是否启用链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

+ [FileController](src/main/java/cn/li98/blog/controller/admin/FileController.java)
```java
import java.io.IOException;
@RestController
@RequestMapping("/admin/file")
public class FileControllor {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Resource
    private FilesMapper fileMapper;

    /**
     * 文件上传接口
     *
     * @param file 前端传递过来的文件
     * @return url作为data
     * @throws IOException IO异常
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        // 定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUuid = uuid + StrUtil.DOT + type;

        File uploadFile = new File(fileUploadPath + fileUuid);
        // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        File parentFile = uploadFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        String url;
        // 获取文件的md5
        String md5 = SecureUtil.md5(file.getInputStream());
        // 从数据库查询是否存在相同的记录
        Files dbFiles = getFileByMd5(md5);
        // 文件已存在
        if (dbFiles != null) {
            url = dbFiles.getUrl();
        } else {
            // 上传文件到磁盘
            file.transferTo(uploadFile);
            // 数据库若不存在重复文件，则不删除刚才上传的文件
            url = "http://localhost:8080/admin/file/" + fileUuid;
        }

        // 存储到数据库
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size / 1024);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileMapper.insert(saveFile);

        return Result.succ("文件上传成功", url);
    }

    /**
     * 文件下载接口   http://localhost:8080/file/{fileUUID}
     *
     * @param fileUuid 文件唯一的标识码
     * @param response HttpServletResponse
     * @throws IOException IO异常
     */
    @GetMapping("/{fileUuid}")
    public void download(@PathVariable String fileUuid, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUuid);
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUuid, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    /**
     * 通过文件的md5查询文件
     *
     * @param md5 文件的md5
     * @return md5对应的文件
     */
    private Files getFileByMd5(String md5) {
        // 查询文件的md5是否存在
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<Files> filesList = fileMapper.selectList(queryWrapper);
        return filesList.size() == 0 ? null : filesList.get(0);
    }

    /**
     * 更新文件
     *
     * @param files 待更新的文件
     * @return Result
     */
    @PostMapping("/update")
    public Result update(@RequestBody Files files) {
        return Result.succ(fileMapper.updateById(files));
    }

    /**
     * 删除文件
     *
     * @param id 待删除文件的id
     * @return Result
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        Files files = fileMapper.selectById(id);
        files.setDeleted(true);
        fileMapper.updateById(files);
        return Result.succ(true);
    }

    /**
     * 批量删除文件
     * @param ids 待删除文件的id列表
     * @return Result
     */
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        // select * from sys_file where id in (id,id,id...)
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        List<Files> files = fileMapper.selectList(queryWrapper);
        for (Files file : files) {
            file.setDeleted(true);
            fileMapper.updateById(file);
        }
        return Result.succ(true);
    }
}
```

+ [FilesMapper](src/main/java/cn/li98/blog/dao/FilesMapper.java)
```java
public interface FilesMapper extends BaseMapper<Files> {
}
```

+ [Files](src/main/java/cn/li98/blog/model/Files.java)
```java
@Data
@TableName("sys_file")
public class Files {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String type;
    private Long size;
    private String url;
    private String md5;
    private Boolean deleted;
    private Boolean enable;
}
```


## 10. 新增博客导入接口并实现功能
+ [BlogControllor](src/main/java/cn/li98/blog/controller/admin/BlogController.java)
    ```java
    import java.io.IOException;
    import java.text.ParseException;
    @Slf4j
    @RestController
    @RequestMapping("/admin/blog")
    public class BlogControllor {
        @Autowired
        BlogService blogService;
    
        /**
         * 导入博客到数据库
         *
         * @param file Markdown博客文件
         * @return Result
         * @throws IOException    IO异常
         * @throws ParseException 时间转换异常
         */
        @PostMapping("/uploadBlog")
        public Result uploadBlog(@RequestParam MultipartFile file) throws IOException, ParseException {
            String originalFilename = file.getOriginalFilename();
            String type = FileUtil.extName(originalFilename);
            if (!type.equals("md")) {
                return Result.fail("博客文件类型错误，应为.md文件");
            }
            Blog blog = blogService.fileToBlog(file);
            int flag = blogService.createBlog(blog);
            if (flag == 1) {
                return Result.succ(originalFilename + " 导入成功", blog);
            }
            return Result.fail(originalFilename + " 导入失败");
        }
    }
    ```

+ [BlogService](src/main/java/cn/li98/blog/service/BlogService.java)
    ```java
    public interface BlogService extends IService<Blog> {
    
        /**
         * 新发布博客/导入博客
         *
         * @param blog
         * @return 1：创建成功；0：创建失败
         */
        int createBlog(Blog blog);
    
        /**
         * 提取上传的md文件内容，赋值到Blog对象中
         *
         * @param file Markdown文件
         * @return Blog对象
         * @throws IOException    IO异常
         * @throws ParseException 时间转换异常
         */
        Blog fileToBlog(MultipartFile file) throws IOException, ParseException;
    }
    ```

+ [BlogServiceImpl](src/main/java/cn/li98/blog/service/impl/BlogServiceImpl.java)
    ```java
    import java.io.*;
    import java.text.ParseException;
    
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
         * 创建博客/导入博客
         * 需要常规地设置类别id、阅读时长、阅读量参数
         * 需要单独地设置创建时间、更新时间（等于创建）、创建用户（默认为1唯一的管理员）
         *
         * @param blog
         * @return 创建成功返回1，失败返回0
         */
        @Override
        public int createBlog(Blog blog) {
            Blog newBlog = setItemsOfBlog(blog);
            if (newBlog.getCreateTime() == null) {
                Date date = new Date();
                newBlog.setCreateTime(date);
                newBlog.setUpdateTime(date);
            }
            newBlog.setUserId(1L);
            return blogMapper.createBlog(newBlog);
        }
    
        /**
         * 提取上传的md文件内容，赋值到Blog对象中
         * @param file Markdown文件
         * @return Blog对象
         * @throws IOException IO异常
         * @throws ParseException 时间转换异常
         */
        @Override
        public Blog fileToBlog(MultipartFile file) throws IOException, ParseException {
            // 转成字符流
            InputStream is = file.getInputStream();
            InputStreamReader isReader = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isReader);
            StringBuilder content = new StringBuilder();
            String title = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            // 循环逐行读取
            while (br.ready()) {
                String line = br.readLine();
                if (line.contains("title: ")) {
                    title = line.substring(7);
                }
                if (line.contains("date: ")) {
                    date = sdf.parse(line.substring(6));
                }
                content.append(line);
                content.append('\n');
            }
            //关闭流
            br.close();
    
            // 新建博客类对象
            Blog blog = new Blog();
            blog.setTitle(title);
            blog.setFirstPicture("");
            blog.setContent(content.toString().split("date: ")[1].substring(24));
            blog.setDescription(file.getOriginalFilename());
            blog.setPublished(true);
            blog.setCommentEnabled(false);
            blog.setCreateTime(date);
            blog.setUpdateTime(date);
            blog.setViews(0);
            blog.setWords(WordCount.count(blog.getContent()));
            blog.setReadTime((int) Math.round(blog.getWords() / 200.0));
            blog.setCategoryId(1L);
            blog.setTop(false);
            blog.setPassword("");
            blog.setUserId(1L);
            blog.setDeleted(0L);
            return blog;
        }
    }
    ```

+ [WordCount](src/main/java/cn/li98/blog/common/WordCount.java)字数统计工具
    ```java
    public class WordCount {
        public static int count(String content) {
            if (content == null) {
                return 0;
            }
            String englishString = content.replaceAll("[\u4e00-\u9fa5]", "");
            String[] englishWords = englishString.split("[\\p{P}\\p{S}\\p{Z}\\s]+");
            int chineseWordCount = content.length() - englishString.length();
            int otherWordCount = englishWords.length;
            if (englishWords.length > 0 && englishWords[0].length() < 1) {
                otherWordCount--;
            }
            if (englishWords.length > 1 && englishWords[englishWords.length - 1].length() < 1) {
                otherWordCount--;
            }
            return chineseWordCount + otherWordCount;
        }
    }
    ```


## 11. 新增博客批量导入功能
+ 在[BlogControllor](src/main/java/cn/li98/blog/controller/admin/BlogController.java)中复用单个博客导入的接口和业务实现层，根据文件类型进行区分
```java
    @PostMapping("/submitBlog")
    public Result submitBlog(@Validated @RequestBody Blog blog) {
        // 验证字段
        if (StrUtil.isEmpty(blog.getTitle()) || StrUtil.isEmpty(blog.getDescription()) || StrUtil.isEmpty(blog.getContent())) {
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
            return Result.succ("博客发布成功");
        }
        return Result.fail("博客发布失败");
    }

    /**
     * 导入博客到数据库
     *
     * @param file Markdown博客文件或Excel文件
     * @return Result
     * @throws IOException    IO异常
     * @throws ParseException 时间转换异常
     */
    @PostMapping("/uploadBlog")
    public Result uploadBlog(@RequestParam MultipartFile file) throws IOException, ParseException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);

        if ("md".equals(type)) {
            // 是md文件，需要把规定格式的博客内容读取并拆分处理之后得到博客类对象才可以插入到数据库
            Blog blog = blogService.fileToBlog(file);
            int flag = blogService.createBlog(blog);
            if (flag == 1) {
                return Result.succ(originalFilename + " 导入成功", blog);
            }
        } else if ("xlsx".equals(type) || "xls".equals(type)) {
            // 逐行读取记录，每行是一个博客，列名对应数据库字段名
            InputStream inputStream = file.getInputStream();
            ExcelReader reader = ExcelUtil.getReader(inputStream);
            // 通过javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
            List<Blog> list = reader.readAll(Blog.class);
            int count = 0;
            for (int i = 0; i < list.size(); i ++) {
                try {
                    count += blogService.createBlog(list.get(i));
                } catch (Exception e) {
                    return  Result.fail("博客导入失败，停止继续导入，失败行数为：" + i, e.getMessage());
                }
            }
            if (count == list.size()) {
                return Result.succ(originalFilename + " 导入成功", list);
            } else {
                return Result.fail("成功导入的博客记录数与文件内记录数不匹配，未知原因");
            }
        } else {
            return Result.fail("博客文件类型错误，应为.md或.xlsx或.xls文件");
        }
        return Result.fail(originalFilename + " 导入失败");
    }
```

+ 调整业务实现层[BlogServiceImpl](./src/main/java/cn/li98/blog/service/impl/BlogServiceImpl.java)的代码
```java
    private Blog setItemsOfBlog(Blog blog) {
        if (blog.getCategoryId() == null) {
            blog.setCategoryId(1L);
        }
        // TODO: 分类、标签等功能判断新增等功能
        if (blog.getFirstPicture() == null) {
            blog.setFirstPicture("");
        }
        if (blog.getReadTime() == null || blog.getReadTime() <= 0) {
            // 计算字数，粗略计算阅读时长
            int words = WordCount.count(blog.getContent());
            int readTime = (int) Math.round(words / 200.0);
            blog.setWords(words);
            blog.setReadTime(readTime);
        }
        if (blog.getViews() == null || blog.getViews() < 0) {
            blog.setViews(0);
        }
        return blog;
    }
```


## 12. 新增博客分类、博客标签相关的功能
### 12.1 分类
+ 创建`博客分类`实体类
  ```java
  @Data
  @TableName("category")
  public class Category {
      @TableId(value = "id", type = IdType.AUTO)
      private Long id;
  
      @NotBlank(message = "分类名不能为空")
      String categoryName;
  }
  ```

+ 创建博客分类接口
  ```java
  @Slf4j
  @RestController
  @RequestMapping("/admin/category")
  public class CategoryControllor {
      @Autowired
      CategoryService categoryService;
  
      /**
       * 分页查询，获取分类列表
       *
       * @param pageNum  页码
       * @param pageSize 每页分类数量
       * @return 成功则Map作为data
       */
      @GetMapping("/getCategories")
      public Result getCategories(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
  
          QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
          // 新建一个分页规则，pageNum代表当前页码，pageSize代表每页数量
          Page page = new Page(pageNum, pageSize);
          // 借助Page实现分页查询，借助QueryWrapper实现多参数查询
          IPage pageData = categoryService.page(page, queryWrapper);
          if (pageData.getTotal() == 0 && pageData.getRecords().isEmpty()) {
              return Result.fail("查询失败，未查找到相应分类");
          }
          Map<String, Object> data = new HashMap<>(2);
          data.put("pageData", pageData);
          data.put("total", pageData.getTotal());
          return Result.succ("查询成功", data);
      }
  
      /**
       * 删除分类
       *
       * @param id 分类id（唯一）
       * @return 被删除的分类id作为data
       */
      @DeleteMapping("/deleteCategoryById")
      public Result deleteCategoryById(@RequestParam Long id) {
          log.info("category to delete : " + id);
          boolean delete = categoryService.removeById(id);
          if (delete) {
              return Result.succ("分类删除成功", id);
          } else {
              return Result.fail("分类删除失败", id);
          }
      }
  
      /**
       * 新增分类
       *
       * @param category 分类实体类
       * @return Result
       */
      @PostMapping("/addCategory")
      public Result addCategory(@Validated @RequestBody Category category) {
          return submitCategory(category);
      }
  
      /**
       * 修改分类
       *
       * @param category 分类实体类
       * @return Result
       */
      @PutMapping("/editCategory")
      public Result updateCategory(@Validated @RequestBody Category category) {
          return submitCategory(category);
      }
  
      /**
       * 新增与修改分类的通用方法，通过判断id的有无来区分新增还是修改
       *
       * @param category 分类实体类
       * @return Result
       */
      public Result submitCategory(Category category) {
          // 验证字段
          if (StrUtil.isEmpty(category.getCategoryName())) {
              return Result.fail("分类名不可为空");
          }
          int flag = 0;
          try {
              if (category.getId() == null) {
                  flag = categoryService.createCategory(category);
              } else {
                  flag = categoryService.updateCategory(category);
              }
          } catch (Exception e) {
              log.error(e.toString());
          }
          if (flag == 1) {
              return Result.succ("分类发布成功");
          }
          return Result.fail("分类发布失败");
      }
  }
  
  ```

+ 创建博客分类业务层
  ```java
  public interface CategoryService extends IService<Category> {
      int createCategory(Category category);
  
      int updateCategory(Category category);
  }
  
  ```

+ 创建博客分类业务实现层
  ```java
  @Service
  public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
      @Autowired
      CategoryMapper categoryMapper;
  
      @Override
      public int createCategory(Category category) {
          return categoryMapper.insert(category);
      }
  
      @Override
      public int updateCategory(Category category) {
          return categoryMapper.updateById(category);
      }
  }
  ```

+ 创建博客分类持久层
  ```java
  public interface CategoryMapper extends BaseMapper<Category> {
  }
  ```

### 12.2 标签
+ 创建`博客标签`实体类
  ```java
  @Data
  @TableName("tag")
  public class Tag {
      /**
       * 标签id
       */
      @TableId(value = "id", type = IdType.AUTO)
      private Long id;
      /**
       * 标签名称
       */
      private String tagName;
      /**
       * 标签颜色(与Semantic UI提供的颜色对应，可选)
       */
      private String color;
  }
  ```

+ 创建博客标签接口
  ```java
  @Slf4j
  @RestController
  @RequestMapping("/admin/tag")
  public class TagControllor {
      @Autowired
      TagService tagService;
  
      /**
       * 分页查询，获取分类列表
       *
       * @param pageNum  页码
       * @param pageSize 每页分类数量
       * @return 成功则Map作为data
       */
      @GetMapping("/getTags")
      public Result getTags(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
  
          QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
          // 新建一个分页规则，pageNum代表当前页码，pageSize代表每页数量
          Page page = new Page(pageNum, pageSize);
          // 借助Page实现分页查询，借助QueryWrapper实现多参数查询
          IPage pageData = tagService.page(page, queryWrapper);
          if (pageData.getTotal() == 0 && pageData.getRecords().isEmpty()) {
              return Result.fail("查询失败，未查找到相应分类");
          }
          Map<String, Object> data = new HashMap<>(2);
          data.put("pageData", pageData);
          data.put("total", pageData.getTotal());
          return Result.succ("查询成功", data);
      }
  
      /**
       * 删除分类
       *
       * @param id 分类id（唯一）
       * @return 被删除的分类id作为data
       */
      @DeleteMapping("/deleteTagById")
      public Result deleteTagById(@RequestParam Long id) {
          log.info("tag to delete : " + id);
          boolean delete = tagService.removeById(id);
          if (delete) {
              return Result.succ("分类删除成功", id);
          } else {
              return Result.fail("分类删除失败", id);
          }
      }
  
      /**
       * 新增分类
       *
       * @param tag 分类实体类
       * @return Result
       */
      @PostMapping("/addTag")
      public Result addTag(@Validated @RequestBody Tag tag) {
          return submitTag(tag);
      }
  
      /**
       * 修改分类
       *
       * @param tag 分类实体类
       * @return Result
       */
      @PutMapping("/editTag")
      public Result updateTag(@Validated @RequestBody Tag tag) {
          return submitTag(tag);
      }
  
      /**
       * 新增与修改分类的通用方法，通过判断id的有无来区分新增还是修改
       *
       * @param tag 分类实体类
       * @return Result
       */
      public Result submitTag(Tag tag) {
          // 验证字段
          if (StrUtil.isEmpty(tag.getTagName())) {
              return Result.fail("分类名不可为空");
          }
          int flag = 0;
          try {
              if (tag.getId() == null) {
                  flag = tagService.createTag(tag);
              } else {
                  flag = tagService.updateTag(tag);
              }
          } catch (Exception e) {
              log.error(e.toString());
          }
          if (flag == 1) {
              return Result.succ("分类发布成功");
          }
          return Result.fail("分类发布失败");
      }
  }
  
  ```

+ 创建博客标签业务层
  ```java
  public interface TagService extends IService<Tag> {
      int createTag(Tag tag);
  
      void saveBlogTag(Long blogId, Long tagId);
  
      int updateTag(Tag tag);
  }
  
  ```

+ 创建博客标签业务实现层
  ```java
  @Service
  public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
      @Autowired
      TagMapper tagMapper;
      @Override
      public int createTag(Tag tag) {
          return tagMapper.insert(tag);
      }
  
      @Override
      public void saveBlogTag(Long blogId, Long tagId) {
          if (tagMapper.saveBlogTag(blogId, tagId) != 1) {
              throw new PersistenceException("维护博客标签关联表失败");
          }
      }
  
      @Override
      public int updateTag(Tag tag) {
          return tagMapper.updateById(tag);
      }
  }
  ```

+ 创建博客标签持久层
  ```java
  public interface TagMapper extends BaseMapper<Tag> {
      int saveBlogTag(Long blogId, Long tagId);
  }
  
  ```

+ 博客标签xml文件
  ```xml
  <mapper namespace="cn.li98.blog.dao.TagMapper">
    <!--维护 blog_tag 表(添加)-->
    <insert id="saveBlogTag">
      insert into blog_tag (blog_id, tag_id) values (#{blogId}, #{tagId})
    </insert>
  </mapper>
  ```


## 13. 整合写博客与选择（或直接创建）标签
+ 创建[BlogWriteDTO](src/main/java/cn/li98/blog/model/dto/BlogWriteDTO.java)用于接收前端的RequestBody（Blog和Tags）
  ```java
  @NoArgsConstructor
  @Data
  public class BlogWriteDTO {
      private Blog blog;
      private List<Object> tags;
  }
  ```

+ 在中添加获取所有分类和标签以供前端选择使用的接口
  ```java
      /**
       * 获取所有分类和标签以供前端选择使用
       *
       * @return 所有分类和所有标签
       */
      @GetMapping("/getCategoryAndTag")
      public Result getCategoryAndTag() {
          List<Category> categoryList = new LinkedList<>();
          List<Tag> tagList = new LinkedList<>();
          categoryList = categoryService.list();
          tagList = tagService.list();
  
          Map<String, Object> data = new HashMap<>(2);
          data.put("categoryList", categoryList);
          data.put("tagList", tagList);
  
          return Result.succ(data);
      }
  ```

+ 修改[BlogControllor](src/main/java/cn/li98/blog/controller/admin/BlogController.java)中创建博客的接口，关于标签的处理，如果是选择了已有的标签，则直接把选择的标签维护到新的标签列表tagList中；如果是输入的标签名，则判断是否已有之后进行不同的处理，如果已有则报错提示，如果是新标签名则创建，并维护到tagList中
  ```java
      /**
       * 创建、修改博客
       *
       * @param form 博客实体类
       * @return 成功则"发布成功"作为data
       */
      @PostMapping("/submitBlog")
      public Result submitBlog(@RequestBody BlogWriteDTO form) {
          Blog blog = form.getBlog();
          List<Object> tags = form.getTags();
          // tagList是遍历前端发送的所有标签并根据类型进行转换处理之后真正要使用的标签列表
          List<Tag> tagList = new ArrayList<>();
          for (Object t : tags) {
              if (t instanceof Integer) {
                  // 选择了已存在的标签
                  Tag tag = tagService.getById(((Integer) t).longValue());
                  tagList.add(tag);
              } else if (t instanceof String) {
                  // 直接输入的标签名，此时需要判断标签是否已存在
                  // 查询标签是否已存在
                  QueryWrapper wrapper = new QueryWrapper();
                  wrapper.eq("tag_name", (String) t);
                  if (tagService.getOne(wrapper) != null) {
                      return Result.fail("不可添加已存在的标签");
                  }
                  // 不存在则添加新标签
                  Tag tag = new Tag();
                  tag.setTagName((String) t);
                  tagService.createTag(tag);
                  tagList.add(tag);
              } else {
                  return Result.fail("标签不正确");
              }
          }
  
          // 验证字段
          if (StrUtil.isEmpty(blog.getTitle()) || StrUtil.isEmpty(blog.getDescription()) || StrUtil.isEmpty(blog.getContent())) {
              return Result.fail("参数有误");
          }
          int flag = 0;
          int tagCount = 0;
          try {
              if (blog.getId() == null) {
                  flag = blogService.createBlog(blog);
              } else {
                  flag = blogService.updateBlog(blog);
              }
              // 关联博客和标签(维护blog_tag表)，博客与tagList中的所有标签是一对多的关系
              for (Tag t : tagList) {
                  tagCount += tagService.saveBlogTag(blog.getId(), t.getId());
              }
          } catch (Exception e) {
              log.error(e.toString());
          }
          if (flag == 1 && tagCount == tagList.size()) {
              return Result.succ("博客发布成功");
          }
          return Result.fail("博客发布失败");
      }
  ```


## 14. 在阅读界面中联动展示博客所属的分类及其标签
+ 修改[BlogController](src/main/java/cn/li98/blog/controller/admin/BlogController.java)中的根据id查询博客接口
  ```java
      /**
       * 修改、阅读操作对应的根据指定id查询博客的接口
       * 可以根据指定的唯一id查询对应的博客、博客所属的分类、博客拥有的标签
       *
       * @param blogId 博客id（唯一）
       * @return Result
       */
      @GetMapping("/getBlogInfoById")
      public Result getBlogInfoById(@RequestParam Long blogId) {
          // 查询博客
          Blog blog = blogService.getById(blogId);
          Assert.notNull(blog, "该博客不存在");
          // 查询所属分类
          Category category = categoryService.getById(blog.getCategoryId());
          // 查询拥有的标签
          List <Tag> tagList = tagService.getTagsByBlogId(blogId);
          
          Map<String, Object> data = new LinkedHashMap<>();
          data.put("blog", blog);
          data.put("category", category);
          data.put("tagList", tagList);
          return Result.succ("查询成功", data);
      }
  ```

+ [TagService](src/main/java/cn/li98/blog/service/TagService.java)
  ```java
  public interface TagService extends IService<Tag> {
    /**
     * 根据博客id查询其拥有的标签列表
     *
     * @param blogId 博客id
     * @return 标签列表
     */
    List<Tag> getTagsByBlogId(Long blogId);
  }
  ```

+ [TagServiceImpl](src/main/java/cn/li98/blog/service/impl/TagServiceImpl.java)中实现
  ```java
  @Service
  public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
      @Autowired
      TagMapper tagMapper;
  
      /**
       * 根据博客id查询其拥有的标签列表
       *
       * @param blogId 博客id
       * @return 标签列表
       */
      @Override
      public List<Tag> getTagsByBlogId(Long blogId) {
          return tagMapper.getTagsByBlogId(blogId);
      }
  }
  ```

+ [TagMapper](src/main/java/cn/li98/blog/dao/TagMapper.java)
  ```java
  public interface TagMapper extends BaseMapper<Tag> {
      /**
       * 根据博客id查询其拥有的标签列表
       *
       * @param blogId 博客id
       * @return 标签列表
       */
      List<Tag> getTagsByBlogId(Long blogId);
  }
  ```

+ [TagMapper.xml](src/main/resources/mapper/TagMapper.xml)
  ```xml
      <!--根据博客id查询其拥有的标签列表-->
      <select id="getTagsByBlogId" resultType="cn.li98.blog.model.entity.Tag">
          SELECT id, tag_name, color from tag WHERE id in (SELECT tag_id FROM blog_tag WHERE blog_id=#{blogId})
      </select>
  ```


## 15. 自定义AOP记录操作日志
### 15.1 添加操作日志实体类与查询操作日志的接口
+ 新增实体类[OperationLog](src/main/java/cn/li98/blog/model/OperationLog.java)
    ```java
    @Data
    @NoArgsConstructor
    @TableName("operation_log")
    public class OperationLog implements Serializable {
        @TableId(value = "id", type = IdType.AUTO)
        private Long id;
    
        /**
         * 操作者用户名
         */
        private String username;
    
        /**
         * 请求接口
         */
        private String uri;
    
        /**
         * 请求方式
         */
        private String method;
    
        /**
         * 请求参数
         */
        private String param;
    
        /**
         * 操作描述
         */
        private String description;
    
        /**
         * ip
         */
        private String ip;
    
        /**
         * ip来源
         */
        private String ipSource;
    
        /**
         * 操作系统
         */
        private String os;
    
        /**
         * 浏览器
         */
        private String browser;
    
        /**
         * 请求耗时（毫秒）
         */
        private Integer times;
    
        /**
         * 操作时间
         */
        private Date createTime;
    
        /**
         * user-agent用户代理
         */
        private String userAgent;
    
        private static final long serialVersionUID = 1L;
    
        public OperationLog(String username, String description, String uri, String method, String userAgent, String ip, String ipSource, int times, String param, String os, String browser) {
            this.username = username;
            this.description = description;
            this.uri = uri;
            this.method = method;
            this.userAgent = userAgent;
            this.ip = ip;
            this.ipSource = ipSource;
            this.times = times;
            this.param = param;
            this.os = os;
            this.browser = browser;
            this.createTime = new Date();
        }
    }
    ```
+ 新增控制层[OperationLogController](src/main/java/cn/li98/blog/controller/admin/OperationLogController.java)
    ```java
    public class OperationLogController {
        @Autowired
        OperationLogService operationLogService;
    
        /**
         * 分页查询操作日志列表
         *
         * @param pageNum  页码
         * @param pageSize 每页个数
         * @return
         */
        @OperationLogger("查询操作日志")
        @GetMapping("/getOperationLogList")
        public Result operationLogs(@RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize) {
    
            QueryWrapper<OperationLog> queryWrapper = new QueryWrapper<>();
            // 根据创建时间查询逆序的列表结果，越新发布的博客越容易被看到
            queryWrapper.orderByDesc("create_time");
            // 新建一个分页规则，pageNum代表当前页码，pageSize代表每页数量
            Page page = new Page(pageNum, pageSize);
            // 借助Page实现分页查询，借助QueryWrapper实现多参数查询
            IPage pageData = operationLogService.page(page, queryWrapper);
            Map<String, Object> data = new HashMap<>(2);
            data.put("pageData", pageData);
            data.put("total", pageData.getTotal());
            return Result.succ("请求成功", data);
        }
    
        /**
         * 按id删除操作日志
         *
         * @param id 日志id
         * @return
         */
        @OperationLogger("按id删除操作日志")
        @DeleteMapping("/deleteOperationLogById")
        public Result delete(@RequestParam Long id) {
            operationLogService.removeById(id);
            return Result.succ("删除成功");
        }
    }
    ```
+ 新增业务层[OperationLogService](src/main/java/cn/li98/blog/service/OperationLogService.java)
+ 新增业务实现层[OperationLogServiceImpl](src/main/java/cn/li98/blog/service/impl/OperationLogServiceImpl.java)
+ 新增持久层[OperationLogMapper](src/main/java/cn/li98/blog/dao/OperationLogMapper.java)
+ 新增mapper[OperationLogMapper.xml](src/main/resources/mapper/OperationLogMapper.xml)
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    <mapper namespace="cn.li98.blog.dao.OperationLogMapper">
      <resultMap id="BaseResultMap" type="cn.li98.blog.model.entity.OperationLog">
        <id column="id" jdbcType="BIGINT" property="id" />
        <result column="username" jdbcType="VARCHAR" property="username" />
        <result column="uri" jdbcType="VARCHAR" property="uri" />
        <result column="method" jdbcType="VARCHAR" property="method" />
        <result column="param" jdbcType="VARCHAR" property="param" />
        <result column="description" jdbcType="VARCHAR" property="description" />
        <result column="ip" jdbcType="VARCHAR" property="ip" />
        <result column="ip_source" jdbcType="VARCHAR" property="ipSource" />
        <result column="os" jdbcType="VARCHAR" property="os" />
        <result column="browser" jdbcType="VARCHAR" property="browser" />
        <result column="times" jdbcType="INTEGER" property="times" />
        <result column="create_time" jdbcType="TIMESTAMP" property="createTime" />
        <result column="user_agent" jdbcType="VARCHAR" property="userAgent" />
      </resultMap>
      <sql id="Base_Column_List">
        id, username, uri, `method`, param, description, ip, ip_source, os, browser, times, 
        create_time, user_agent
      </sql>
    </mapper>
    ```

### 15.2 自定义AOP，在运行时实现记录操作日志
+ 新增依赖
    ```xml
            <!-- spring aop -->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-aop</artifactId>
            </dependency>
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>fastjson</artifactId>
                <version>2.0.9</version>
            </dependency>
            <!-- 解析客户端操作系统、浏览器 -->
            <dependency>
                <groupId>nl.basjes.parse.useragent</groupId>
                <artifactId>yauaa</artifactId>
                <version>5.20</version>
            </dependency>
            <!-- ip2region -->
            <dependency>
                <groupId>org.lionsoul</groupId>
                <artifactId>ip2region</artifactId>
                <version>1.7.2</version>
            </dependency>
            <dependency>
                <groupId>org.apache.commons</groupId>
                <artifactId>commons-lang3</artifactId>
                <version>3.12.0</version>
            </dependency>
    ```

+ 在resource中添加[ip2region](src/main/resources/ipdb/ip2region.db)数据

+ 新增自定义的Aspect，[OperationLogAspect](src/main/java/cn/li98/blog/common/aspect/OperationLogAspect.java)
    ```java
    import cn.li98.blog.common.annotation.OperationLogger;
    import cn.li98.blog.model.entity.OperationLog;
    import cn.li98.blog.service.OperationLogService;
    import cn.li98.blog.utils.*;
    import org.aspectj.lang.ProceedingJoinPoint;
    import org.aspectj.lang.annotation.Around;
    import org.aspectj.lang.annotation.Aspect;
    import org.aspectj.lang.annotation.Pointcut;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Component;
    import org.springframework.web.context.request.RequestContextHolder;
    import org.springframework.web.context.request.ServletRequestAttributes;
    
    import javax.servlet.http.HttpServletRequest;
    import java.util.Map;
    
    @Component
    @Aspect
    public class OperationLogAspect {
        @Autowired
        OperationLogService operationLogService;
    
        @Autowired
        UserAgentUtils userAgentUtils;
    
        ThreadLocal<Long> currentTime = new ThreadLocal<>();
    
        /**
         * 配置切入点
         */
        @Pointcut("@annotation(operationLogger)")
        public void logPointcut(OperationLogger operationLogger) {
        }
        
        /**
         * 配置环绕通知
         *
         * @param joinPoint       切入点
         * @param operationLogger 注解OperationLogger对象
         * @return joinPoint.proceed()
         * @throws Throwable
         */
        @Around("logPointcut(operationLogger)")
        public Object logAround(ProceedingJoinPoint joinPoint, OperationLogger operationLogger) throws Throwable {
            currentTime.set(System.currentTimeMillis());
            Object result = joinPoint.proceed();
            int times = (int) (System.currentTimeMillis() - currentTime.get());
            currentTime.remove();
            // 新建一个当前操作的日志对象并填充
            OperationLog operationLog = handleLog(joinPoint, operationLogger, times);
            // 将操作日志保持到数据库
            operationLogService.save(operationLog);
            return result;
        }
    
        /**
         * 获取HttpServletRequest请求对象，并设置OperationLog对象属性
         *
         * @param joinPoint       切入点
         * @param operationLogger 注解OperationLogger对象
         * @param times           操作所用时间
         * @return
         */
        private OperationLog handleLog(ProceedingJoinPoint joinPoint, OperationLogger operationLogger, int times) {
            // 从token中获取操作用户的名称
            String username = TokenUtils.getCurrentUser().getUsername();
            System.out.println("username: -----------  " + username);
            // 获取操作描述
            String description = operationLogger.value();
    
            // 获取请求内容中的属性
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            // 获取请求属性
            HttpServletRequest request = attributes.getRequest();
            // 获取请求接口
            String uri = request.getRequestURI();
            // 获取请求方式
            String method = request.getMethod();
            // 获取用户代理方式
            String userAgent = request.getHeader("User-Agent");
            // 借助IpAddressUtils工具类获取用户ip
            String ip = IpAddressUtils.getIpAddress(request);
            // 借助IpAddressUtils工具获取ip来源
            String ipSource = IpAddressUtils.getCityInfo(ip);
            // 借助AopUtils工具类获取请求参数
            Map<String, Object> requestParams = AopUtils.getRequestParams(joinPoint);
            String param = JacksonUtils.writeValueAsString(requestParams);
            // String param = StringUtils.substring(JacksonUtils.writeValueAsString(requestParams), 0, 2000);
            // 借助UserAgentUtils工具类获取操作系统和浏览器信息
            Map<String, String> userAgentMap = userAgentUtils.parseOsAndBrowser(userAgent);
            String os = userAgentMap.get("os");
            String browser = userAgentMap.get("browser");
            // 创建日志对象
            OperationLog log = new OperationLog(username, description, uri, method, userAgent, ip, ipSource, times, param, os, browser);
            return log;
        }
    }
    ```

+ 新增自定义注解[OperationLogger](src/main/java/cn/li98/blog/common/annotation/OperationLogger.java)
    ```java
    /**
     * @author: whtli
     * @date: 2022/12/08
     * @description: 用于需要记录操作日志的方法
     * JDK元注解
     *    @Retention：定义注解的保留策略
     *       @Retention(RetentionPolicy.SOURCE)             //注解仅存在于源码中，在class字节码文件中不包含
     *       @Retention(RetentionPolicy.CLASS)              //默认的保留策略，注解会在class字节码文件中存在，但运行时无法获得，
     *       @Retention(RetentionPolicy.RUNTIME)            //注解会在class字节码文件中存在，在运行时可以通过反射获取到
     *    @Target：指定被修饰的Annotation可以放置的位置(被修饰的目标)
     *       @Target(ElementType.TYPE)                      // 接口、类
     *       @Target(ElementType.FIELD)                     // 属性
     *       @Target(ElementType.METHOD)                    // 方法
     *       @Target(ElementType.PARAMETER)                 // 方法参数
     *       @Target(ElementType.CONSTRUCTOR)               // 构造函数
     *       @Target(ElementType.LOCAL_VARIABLE)            // 局部变量
     *       @Target(ElementType.ANNOTATION_TYPE)           // 注解
     *       @Target(ElementType.PACKAGE)                   // 包
     *    @Inherited：指定被修饰的Annotation将具有继承性 
     *    @Documented：指定被修饰的该Annotation可以被javadoc工具提取成文档
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface OperationLogger {
        /**
         * 操作描述
         */
        String value() default "";
    }
    ```

+ 新增[UserAgentUtils](src/main/java/cn/li98/blog/utils/UserAgentUtils.java)用户代理解析工具类，可以获取操作系统和浏览器类型
    ```java
    @Component
    public class UserAgentUtils {
        private UserAgentAnalyzer uaa;
    
        public UserAgentUtils() {
            this.uaa = UserAgentAnalyzer
                    .newBuilder()
                    .hideMatcherLoadStats()
                    .withField(UserAgent.OPERATING_SYSTEM_NAME_VERSION_MAJOR)
                    .withField(UserAgent.AGENT_NAME_VERSION)
                    .build();
        }
    
        /**
         * 从User-Agent解析客户端操作系统和浏览器版本
         *
         * @param userAgent 用户代理信息
         * @return 操作系统和浏览器类型组成的键值对map
         */
        public Map<String, String> parseOsAndBrowser(String userAgent) {
            UserAgent agent = uaa.parse(userAgent);
            String os = agent.getValue(UserAgent.OPERATING_SYSTEM_NAME_VERSION_MAJOR);
            String browser = agent.getValue(UserAgent.AGENT_NAME_VERSION);
            Map<String, String> map = new HashMap<>();
            map.put("os", os);
            map.put("browser", browser);
            return map;
        }
    }
    ```

+ 新增[IpAddressUtils](src/main/java/cn/li98/blog/utils/IpAddressUtils.java)ip记录工具类，可获取ip地址及其来源
    ```java
    @Slf4j
    @Component
    public class IpAddressUtils {
        /**
         * 在Nginx等代理之后获取用户真实IP地址
         *
         * @param request
         * @return
         */
        public static String getIpAddress(HttpServletRequest request) {
            String ip = request.getHeader("X-Real-IP");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("x-forwarded-for");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                    //根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        log.error("getIpAddress exception:", e);
                    }
                    ip = inet.getHostAddress();
                }
            }
            return StringUtils.substringBefore(ip, ",");
        }
    
        private static DbSearcher searcher;
        private static Method method;
    
        /**
         * 在服务启动时加载 ip2region.db 到内存中
         * 解决打包jar后找不到 ip2region.db 的问题
         *
         * @throws Exception 出现异常应该直接抛出终止程序启动，避免后续invoke时出现更多错误
         */
        @PostConstruct
        private void initIp2regionResource() throws Exception {
            InputStream inputStream = new ClassPathResource("/ipdb/ip2region.db").getInputStream();
            // 将 ip2region.db 转为 ByteArray
            byte[] dbBinStr = FileCopyUtils.copyToByteArray(inputStream);
            DbConfig dbConfig = new DbConfig();
            searcher = new DbSearcher(dbConfig, dbBinStr);
            // 二进制方式初始化 DBSearcher，需要使用基于内存的查找算法 memorySearch
            method = searcher.getClass().getMethod("memorySearch", String.class);
        }
    
        /**
         * 根据ip从 ip2region.db 中获取地理位置
         *
         * @param ip
         * @return
         */
        public static String getCityInfo(String ip) {
            if (ip == null || !Util.isIpAddress(ip)) {
                log.error("Error: Invalid ip address");
                return "";
            }
            try {
                DataBlock dataBlock = (DataBlock) method.invoke(searcher, ip);
                String ipInfo = dataBlock.getRegion();
                if (!StringUtils.isEmpty(ipInfo)) {
                    ipInfo = ipInfo.replace("|0", "");
                    ipInfo = ipInfo.replace("0|", "");
                    return ipInfo;
                }
            } catch (Exception e) {
                log.error("getCityInfo exception:", e);
            }
            return "";
        }
    }
    ```

+ 新增[AopUtils](src/main/java/cn/li98/blog/utils/AopUtils.java)工具类，可用于获取请求中的参数名与参数值
    ```java
    public class AopUtils {
        // 自定义需要忽略的参数
        private static Set<String> ignoreParams = new HashSet<String>() {
            {
                add("jwt");
            }
        };
    
        /**
         * 获取请求参数
         *
         * @param joinPoint
         * @return
         */
        public static Map<String, Object> getRequestParams(JoinPoint joinPoint) {
            Map<String, Object> map = new LinkedHashMap<>();
            // 参数名数组
            String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
            // 参数值数组
            Object[] args = joinPoint.getArgs();
            // 将符合条件的参数名与其对应的参数值，存到map中
            for (int i = 0; i < args.length; i++) {
                if (!isIgnoreParams(parameterNames[i]) && !isFilterObject(args[i])) {
                    map.put(parameterNames[i], args[i]);
                }
            }
            return map;
        }
    
        /**
         * 判断是否忽略参数
         *
         * @param params
         * @return
         */
        private static boolean isIgnoreParams(String params) {
            return ignoreParams.contains(params);
        }
    
        /**
         * consider if the data is file, httpRequest or response
         *
         * @param o the data
         * @return if match return true, else return false
         */
        private static boolean isFilterObject(final Object o) {
            return o instanceof HttpServletRequest || o instanceof HttpServletResponse || o instanceof MultipartFile;
        }
    }
    
    ```

+ 新增[JacksonUtils](src/main/java/cn/li98/blog/utils/JacksonUtils.java)，Jackson工具类
    ```java
    public class JacksonUtils {
        private static ObjectMapper objectMapper = new ObjectMapper();
    
        public static String writeValueAsString(Object value) {
            try {
                return objectMapper.writeValueAsString(value);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return "";
            }
        }
    
        public static <T> T readValue(String content, Class<T> valueType) {
            try {
                return objectMapper.readValue(content, valueType);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    
        public static <T> T readValue(String content, TypeReference<T> valueTypeRef) {
            try {
                return objectMapper.readValue(content, valueTypeRef);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    
        public static <T> T readValue(InputStream src, Class<T> valueType) {
            try {
                return objectMapper.readValue(src, valueType);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    
        public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
            return objectMapper.convertValue(fromValue, toValueType);
        }
    }
    ```

+ 在需要记录操作日志的方法前添加注解，如StatisticControllor中获取统计信息的方法
    ```java
        @OperationLogger("获取统计数据")
        @GetMapping("/getStatistic")
        public Result getStatistic() {
            Map<String, Object> map = statisticService.getBlogStatistic();
            int totalPageView = 0;
            int todayPageView = statisticService.getTodayPageView();
            int totalUniqueVisitor = 0;
            int todayUniqueVisitor = 0;
            int totalComment = statisticService.getTotalComment();
            map.put("totalPageView", totalPageView);
            map.put("todayPageView", todayPageView);
            map.put("totalUniqueVisitor", totalUniqueVisitor);
            map.put("todayUniqueVisitor", todayUniqueVisitor);
            map.put("totalComment", totalComment);
            return Result.succ(map);
        }
    ```


## 16. 集成redis缓存，以用户对博客的访问与管理员对博客的操作为例
+ 添加依赖
    ```xml
            <!-- redis -->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-data-redis</artifactId>
            </dependency>
    ```

+ 配置redis（如果没有自定义的密码、端口，则不需要在yml文件中配置，默认就好）

+ 在[Constant](src/main/java/cn/li98/blog/common/Constant.java)中添加通用的指向访客可见博客列表测试键值
    ```java
        /**
         * 通用的指向访客可见博客列表测试键值
         */
        String GUEST_BLOG_KEY = "GUEST_BLOG_KEY";
    ```

+ 在接口层添加用户可用的方法以及缓存操作，见[BlogGuestController](src/main/java/cn/li98/blog/controller/front/BlogGuestController.java)
    ```java
    package cn.li98.blog.controller.front;
    
    import cn.li98.blog.common.Constant;
    import cn.li98.blog.common.Result;
    import cn.li98.blog.common.annotation.OperationLogger;
    import cn.li98.blog.model.entity.Blog;
    import cn.li98.blog.service.BlogService;
    import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
    import com.baomidou.mybatisplus.core.toolkit.StringUtils;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.redis.core.RedisTemplate;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.bind.annotation.RestController;
    
    import java.util.List;
    
    /**
     * @author: whtli
     * @date: 2022/12/13
     * @description: 访客访问博客的接口
     */
    @Slf4j
    @RestController
    @RequestMapping("/admin/guest")
    public class BlogGuestController {
        @Autowired
        BlogService blogService;
    
        @Autowired
        private RedisTemplate redisTemplate;
    
        /**
         * 获取访客可见的博客列表
         *
         * @param title      博客标题
         * @param categoryId 博客分类
         * @param pageNum    页码
         * @return 成功则Map作为data
         */
        @OperationLogger("获取博客列表")
        @GetMapping("/getBlogList")
        public Result getBlogList(@RequestParam(value = "title", defaultValue = "") String title,
                                  @RequestParam(value = "categoryId", defaultValue = "") Long categoryId,
                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    
            List<Blog> blogList = null;
            // 1. 尝试从redis缓存中获取指定键值对应的数据
            List<Blog> list = redisTemplate.opsForList().range(Constant.GUEST_BLOG_KEY, 0, -1);
            // 2. 如果redis中无对应的数据
            if (list.isEmpty()) {
                // 3. 从数据库取出数据
                QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
                // 3.1 将查询参数以键值对的形式存放到QueryWrapper中
                if (!StringUtils.isEmpty(title) && !StringUtils.isBlank(title)) {
                    queryWrapper.like("title", title);
                }
                if (categoryId != null) {
                    queryWrapper.eq("category_id", categoryId);
                }
                // 3.2 博客的可见性需指定为“可见”，用户只能看到公开的博客
                queryWrapper.eq("published", true);
                // 3.3 根据创建时间查询逆序的列表结果，越新发布的博客越容易被看到
                queryWrapper.orderByDesc("create_time");
                // 3.4 查询符合条件的博客列表
                blogList = blogService.list(queryWrapper);
                if (blogList.size() == 0) {
                    return Result.fail("查询失败，未查找到相应博客");
                }
    
                // 4. 缓存到redis
                redisTemplate.opsForList().rightPush(Constant.GUEST_BLOG_KEY, blogList);
            } else {
                blogList = (List<Blog>) list.get(0);
            }
            return Result.succ("查询成功", blogList);
        }
    }
    ```

+ 在[BlogController](src/main/java/cn/li98/blog/controller/admin/BlogController.java)中添加管理员对博客做出增、删、改等操作之后清空redis中博客缓存的操作
    ```java
    // 以修改博客可见性接口为例
    
        /**
         * 博客可见性更改
         *
         * @param blogId 博客id
         * @return Result
         */
        @OperationLogger("更新博客可见性状态")
        @PostMapping("/changeBlogStatusById")
        public Result changeBlogStatusById(@RequestParam Long blogId) {
            int res = blogService.changeBlogStatusById(blogId);
            if (res == 1) {
                // 修改成功后清空redis中的博客缓存
                flushRedis(Constant.GUEST_BLOG_KEY);
                return Result.succ("博客可见性更改成功", res);
            }
            return Result.fail("博客可见性更改失败", res);
        }
    
        /**
         * 删除redis缓存中对应指定键值的内容
         *
         * @param key redis中博客缓存对应的键值
         */
        private void flushRedis(String key) {
            redisTemplate.delete(key);
        }
    ```


## 17. 新增多级评论功能
+ [Comment](src/main/java/cn/li98/blog/model/Comment.java)实体类
    ```java
    @NoArgsConstructor
    @Data
    @TableName("comment")
    public class Comment implements Serializable {
        @TableId(value = "id", type = IdType.AUTO)
        private Long id;
    
        /**
         * 评论内容
         */
        private String content;
    
        /**
         * 评论人id
         */
        private Long userId;
    
        /**
         * 评论时间
         */
        private String time;
    
        /**
         * 父id
         */
        private Long pid;
    
        /**
         * 最上级评论id
         */
        private Long originId;
    
        /**
         * 关联的博客id
         */
        private Long blogId;
    
        /**
         * 发布评论的用户名
         */
        @TableField(exist = false)
        private String username;
    
        /**
         * 发布评论的用户头像
         */
        @TableField(exist = false)
        private String avatar;
    
        /**
         * 当前评论的子评论
         */
        @TableField(exist = false)
        private List<Comment> children;
    
        /**
         * 父节点的用户昵称
         */
        @TableField(exist = false)
        private String pUsername;
    
        /**
         * 父节点的用户id
         */
        @TableField(exist = false)
        private Long pUserId;
    
        private static final long serialVersionUID = 1L;
    }
    ```

+ 新增[CommentFrontController](src/main/java/cn/li98/blog/controller/front/CommentFrontController.java)
    ```java
    @Slf4j
    @RestController
    @RequestMapping("/admin/front")
    public class CommentFrontController {
        @Autowired
        private CommentService commentService;
    
        /**
         * 查询某一博客下的所有评论
         * 实现多级评论嵌套
         *
         * @param blogId 博客id
         * @return 某一博客下的所有评论
         */
        @GetMapping("/loadComment")
        public Result loadComment(@RequestParam Long blogId) {
            // 查询所有的评论和回复数据
            List<Comment> articleComments = commentService.getAllComments(blogId);
            // 查询首层评论数据（不包括回复）
            List<Comment> originList = articleComments.stream().filter(comment -> comment.getOriginId() == null).collect(Collectors.toList());
    
            // 设置评论数据的子节点，也就是回复内容
            for (Comment origin : originList) {
                // 表示回复对象集合
                List<Comment> comments = articleComments.stream().filter(comment -> origin.getId().equals(comment.getOriginId())).collect(Collectors.toList());
                comments.forEach(comment -> {
                    // 找到当前评论的父级
                    Optional<Comment> pComment = articleComments.stream().filter(c1 -> c1.getId().equals(comment.getPid())).findFirst();
                    // 找到父级评论的用户id和用户名，并设置给当前的回复对象
                    pComment.ifPresent((v -> {
                        comment.setPUserId(v.getUserId());
                        comment.setPUsername(v.getUsername());
                    }));
                });
                origin.setChildren(comments);
            }
            return Result.succ(originList);
        }
    
        /**
         * 新增或者更新评论
         *
         * @param comment 评论实体类（缺省）
         * @return 评论发布成功返回true，否则返回false
         */
        @PostMapping("/saveComment")
        public Result saveComment(@RequestBody Comment comment) {
            comment.setUserId(TokenUtils.getCurrentUser().getId());
            comment.setTime(DateUtil.now());
            // 判断如果是回复，进行处理
            if (comment.getPid() != null) {
                Long pid = comment.getPid();
                Comment pComment = commentService.getById(pid);
                if (pComment.getOriginId() != null) {
                    // 如果当前回复的父级有祖宗，那么就设置相同的祖宗
                    comment.setOriginId(pComment.getOriginId());
                } else {
                    // 否则就设置父级为当前回复的祖宗
                    comment.setOriginId(comment.getPid());
                }
            }
            boolean flag = commentService.saveOrUpdate(comment);
            return Result.succ(flag);
        }
    
        /**
         * 根据id删除评论
         *
         * @param id 评论id
         * @return 被删除的评论id
         */
        @DeleteMapping("/deleteCommentById")
        public Result deleteCommentById(@RequestParam Long id) {
            commentService.removeById(id);
            return Result.succ(id);
        }
    }
    ```

+ 新增[CommentService](src/main/java/cn/li98/blog/service/CommentService.java)
    ```java
    public interface CommentService extends IService<Comment> {
        /**
         * 查询某一博客下的所有评论
         *
         * @param blogId 博客id
         * @return
         */
        List<Comment> getAllComments(Long blogId);
    }
    ```

+ 新增[CommentServiceImpl](src/main/java/cn/li98/blog/service/impl/CommentServiceImpl.java)
    ```java
    @Service
    public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
        @Autowired
        private CommentMapper commentMapper;
        /**
         * 查询某一博客下的所有评论
         *
         * @param blogId 博客id
         * @return
         */
        @Override
        public List<Comment> getAllComments(Long blogId) {
            return commentMapper.getAllComments(blogId);
        }
    }
    
    ```

+ 新增[CommentMapper](src/main/java/cn/li98/blog/dao/CommentMapper.java)
    ```java
    public interface CommentMapper extends BaseMapper<Comment> {
        /**
         * 查询某一博客下的所有评论
         * @param blogId 博客id
         * @return
         */
        List<Comment> getAllComments(Long blogId);
    }
    ```

+ 新增[CommentMapper](src/main/resources/mapper/CommentMapper.xml)
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    <mapper namespace="cn.li98.blog.dao.CommentMapper">
      <resultMap id="BaseResultMap" type="cn.li98.blog.model.entity.Comment">
        <id column="id" jdbcType="BIGINT" property="id" />
        <result column="content" jdbcType="VARCHAR" property="content" />
        <result column="user_id" jdbcType="BIGINT" property="userId" />
        <result column="time" jdbcType="TIMESTAMP" property="time" />
        <result column="pid" jdbcType="INTEGER" property="pid" />
        <result column="origin_id" jdbcType="INTEGER" property="originId" />
        <result column="blog_id" jdbcType="INTEGER" property="blogId" />
      </resultMap>
      <sql id="Base_Column_List">
        id, content, user_id, `time`, pid, origin_id, blog_id
      </sql>
      <select id="getAllComments" resultType="cn.li98.blog.model.entity.Comment">
        select c.*,u.username,u.avatar from comment c left join user u on c.user_id = u.id where c.blog_id = #{blogId} order by id desc
      </select>
    </mapper>
    ```


## 18. 新增系统管理相关功能
###  18.1 用户管理
+ 复用[UserController](src/main/java/cn/li98/blog/controller/admin/UserController.java)
+ 复用[User](src/main/java/cn/li98/blog/model/User.java)
+ 复用[UserService](src/main/java/cn/li98/blog/service/UserService.java)
+ 复用[UserServiceImpl](src/main/java/cn/li98/blog/service/impl/UserServiceImpl.java)
+ 复用[UserMapper](src/main/java/cn/li98/blog/dao/UserMapper.java)
+ 复用[UserMapper.xml](src/main/resources/mapper/UserMapper.xml)

### 18.2 角色管理
+ 新增[Role](src/main/java/cn/li98/blog/model/entity/Role.java)实体类

+ 新增[RoleController](src/main/java/cn/li98/blog/controller/admin/RoleController.java)
    - 更新角色和菜单的对应关系的接口
        ```java
            /**
             * 更新角色和菜单的对应关系
             *
             * @param data 参数，包含roleId和
             * @return
             */
            @PostMapping("updateRoleMenu")
            public Result updateRoleMenu(@RequestBody Map<String, Object> data) {
                // 获取参数中的roleId以及为这个role赋予权限（菜单id列表）
                Long roleId = Long.valueOf((Integer) data.get("roleId"));
                List<Object> Ids = (List<Object>) data.get("menuIds");
                List<Long> menuIds = new ArrayList<>();
                for (Object item : Ids) {
                    menuIds.add((Long.valueOf((Integer) item)));
                }
                // 将角色及其新的菜单权限绑定在一起
                try {
                    roleService.setRoleMenu(roleId, menuIds);
                    return Result.succ("绑定成功", roleId);
        
                } catch (Exception e) {
                    return Result.fail("绑定失败", roleId);
                }
            }
        ```

+ 新增[RoleService](src/main/java/cn/li98/blog/service/RoleService.java)
    ```java
    public interface RoleService extends IService<Role> {
        /**
         * 绑定角色和菜单的关系
         *
         * @param roleId  角色id
         * @param menuIds 菜单id数组
         * @return 角色id
         */
        int setRoleMenu(Long roleId, List<Long> menuIds);
    }
    ```

+ 新增[RoleServiceImpl](src/main/java/cn/li98/blog/service/impl/RoleServiceImpl.java)
    ```java
    @Service
    public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    
        @Resource
        private RoleMenuMapper roleMenuMapper;
    
        /**
         * 绑定角色和菜单的关系
         *
         * @param roleId  角色id
         * @param menuIds 菜单id数组
         * @return 角色id
         */
        @Override
        public int setRoleMenu(Long roleId, List<Long> menuIds) {
            // 先根据roleId查出已有的权限并删除
            QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id", roleId);
            roleMenuMapper.delete(queryWrapper);
    
            // 再插入新的
            for (Long menuId : menuIds) {
                RoleMenu item = new RoleMenu(roleId, menuId);
                roleMenuMapper.insert(item);
            }
            return menuIds.size();
        }
    }
    ```

+ 新增[RoleMapper.java](src/main/java/cn/li98/blog/dao/RoleMapper.java)

+ 新增[RoleMapper.xml](src/main/resources/mapper/RoleMapper.xml)

### 18.3 菜单管理
+ 新增[Menu](src/main/java/cn/li98/blog/model/entity/Menu.java)实体类

+ 新增[MenuController](src/main/java/cn/li98/blog/controller/admin/MenuController.java)
    - 获取菜单列表（带层级关系）接口
        ```java
            /**
             * 获取菜单列表（带层级关系）
             *
             * @param menuName 菜单名（缺省）
             * @return 带层次关系的菜单列表
             */
            @GetMapping("/getMenuList")
            public Result getMenuList(@RequestParam(defaultValue = "") String menuName) {
                return Result.succ(menuService.getMenuList(menuName));
            }
        ```
    - 获取图标信息接口
        ```java
            /**
             * 获取图标信息
             *
             * @return 图标信息列表
             */
            @GetMapping("/getIconList")
            public Result getIconList() {
                List<Dict> iconList = menuService.getIconList();
                return Result.succ(iconList);
            }
        ```
    - 获取指定角色id拥有的菜单权限接口
        ```java
            /**
             * 获取指定角色id拥有的菜单权限
             *
             * @param roleId 角色id
             * @return 当前角色id所拥有的所有菜单权限
             */
            @GetMapping("/getMenusByRoleId")
            public Result getMenusByRoleId(@RequestParam Long roleId) {
                List<Long> rightList = menuService.getMenusByRoleId(roleId);
                return Result.succ(rightList);
            }
        
        ```

+ 新增[MenuService](src/main/java/cn/li98/blog/service/MenuService.java)
    ```java
    public interface MenuService extends IService<Menu> {
        /**
         * 查询菜单
         *
         * @param menuName 菜单名（缺省）
         * @return 带层次关系的菜单列表
         */
        Map<String, Object> getMenuList(String menuName);
    
        /**
         * 获取图标信息
         *
         * @return 图标信息列表
         */
        List<Dict> getIconList();
    
        /**
         * 从role_menu表中获取指定角色id拥有的菜单权限
         *
         * @param roleId 角色id
         * @return 当前角色id所拥有的所有菜单权限
         */
        List<Long> getMenusByRoleId(Long roleId);
    }
    ```

+ 新增[MenuServiceImpl](src/main/java/cn/li98/blog/service/impl/MenuServiceImpl.java)
    ```java
    @Service
    public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
        @Resource
        private MenuMapper menuMapper;
    
        @Resource
        private DictMapper dictMapper;
    
        @Resource
        private RoleMenuMapper roleMenuMapper;
    
        /**
         * 查询菜单
         *
         * @param menuName 菜单名（缺省）
         * @return 带层次关系的菜单列表
         */
        @Override
        public Map<String, Object> getMenuList(String menuName) {
            QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByAsc("sort_num");
            queryWrapper.like("name", menuName);
    
            // 查询所有数据
            List<Menu> list = menuMapper.selectList(queryWrapper);
            // 找出所有的一级菜单（pid为null）
            List<Menu> firstLevel = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
            // 找出所有的二级菜单（pid不为null）
            List<Menu> secondLevel = list.stream().filter(menu -> menu.getPid() != null).collect(Collectors.toList());
    
            List<Menu> restSecondLevel = secondLevel;
            // 对于查询到的一级菜单，把其下包含的符合查询条件的二级菜单添加到其children中
            for (Menu menu : firstLevel) {
                // 筛选所有数据中pid=父级id的数据就是二级菜单，把二级菜单拼接到一级菜单下
                List<Menu> items = secondLevel.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList());
                menu.setChildren(items);
                restSecondLevel = restSecondLevel.stream().filter(item -> !items.contains(item)).collect(Collectors.toList());
            }
            // 对于剩余的符合查询条件的二级菜单，找到他们的上级菜单（即一级菜单），然后组合
            for (Menu menu : restSecondLevel) {
                // 找到二级菜单的上级菜单（即一级菜单）
                Menu parentItem = menuMapper.selectById(menu.getPid());
                parentItem.setChildren(new LinkedList<>());
                parentItem.getChildren().add(menu);
                firstLevel.add(parentItem);
            }
    
            // 获取所有的菜单id
            List<Long> allMenuIds = new ArrayList<>();
            for (Menu menu : list) {
                allMenuIds.add(menu.getId());
            }
    
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("menuList", firstLevel);
            data.put("allMenuIds", allMenuIds);
            data.put("total", list.size());
            return data;
        }
    
        /**
         * 获取图标信息
         *
         * @return 图标信息列表
         */
        @Override
        public List<Dict> getIconList() {
            QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("type", Constant.DICT_TYPE_ICON);
            List<Dict> iconList = dictMapper.selectList(queryWrapper);
            return iconList;
        }
    
        /**
         * 从role_menu表中获取指定角色id拥有的菜单权限
         *
         * @param roleId 角色id
         * @return 当前角色id所拥有的所有菜单权限
         */
        @Override
        public List<Long> getMenusByRoleId(Long roleId) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("role_id", roleId);
            List<RoleMenu> roleMenu = roleMenuMapper.selectList(queryWrapper);
            List<Long> rightList = new LinkedList();
            for (RoleMenu item : roleMenu) {
                rightList.add(item.getMenuId());
            }
            return rightList;
        }
    }
    ```

+ 新增[MenuMapper.java](src/main/java/cn/li98/blog/dao/MenuMapper.java)

+ 新增[MenuMapper.xml](src/main/resources/mapper/MenuMapper.xml)

+ 新增字典表，用于存储一些常用的内容，如图标等
    - 实体类[Dict](src/main/java/cn/li98/blog/model/Dict.java)
    - 持久层[DictMapper.java](src/main/java/cn/li98/blog/dao/DictMapper.java)
    - [DictMapper.xml](src/main/resources/mapper/DictMapper.xml)

+ [Constant](src/main/java/cn/li98/blog/common/Constant.java)中新增图标字典对应的常量
    ```java
        /**
         * 图标
         */
        String DICT_TYPE_ICON = "icon";
    ```

### 18.4 角色菜单关联表，角色与菜单是一对多的关系
+ [RoleMenu](src/main/java/cn/li98/blog/model/RoleMenu.java)实体类

+ [RoleMenuMapper.java](src/main/java/cn/li98/blog/dao/RoleMenuMapper.java)

+ [RoleMenuMapper.xml](src/main/resources/mapper/RoleMenuMapper.xml)


## 19. 新增动态路由的后端控制
+ 用户实体类，添加表属性之外的菜单列表
    ```java
        @TableField(exist = false)
        private List<Menu> menuList;
    ```

+ 用户管理控制层
    ```java
    @Slf4j
    @RestController
    @RequestMapping("/admin")
    public class UserController {
        @Autowired
        UserService userService;
    
        @Autowired
        private MenuService menuService;
    
        @GetMapping("/getInfo")
        public Result getInfo(@RequestParam String token) {
            log.info("token ====== " + token);
            if (StrUtil.isBlank(token) || StrUtil.isEmpty(token)) {
                return Result.fail("没有可以用于获取用户信息的token，请重新登录");
            }
            User currentUser = TokenUtils.getCurrentUser();
            // 获取菜单权限
            List<Menu> menuList = menuService.getMenusByRoleFlag(currentUser.getRole());
            currentUser.setMenuList(menuList);
            log.info("获取当前用户信息 ====== " + currentUser);
            return Result.succ("获取当前用户信息成功!", currentUser);
        }
    
        @PostMapping("/login")
        public Result login(@Validated @RequestBody LoginDTO loginDTO, HttpServletResponse response) {
            log.info(loginDTO.toString());
    
            User user = userService.login(loginDTO);
            if (user == null) {
                return Result.fail("用户不存在或密码不正确");
            }
    
            // 获取菜单权限
            List<Menu> menuList = menuService.getMenusByRoleFlag(user.getRole());
            user.setMenuList(menuList);
    
            String jwt = TokenUtils.genToken(user.getId(), user.getPassword());
            response.setHeader("Authorization", jwt);
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            return Result.succ(user);
        }
    }
    
    ```

+ 菜单管理业务层[MenuService](src/main/java/cn/li98/blog/service/MenuService.java)
    ```java
    public interface MenuService extends IService<Menu> {
    
        /**
         * 从role_menu表中获取指定角色标识拥有的菜单权限
         *
         * @param flag 角色标识
         * @return 当前角色标识所拥有的所有菜单权限
         */
        List<Menu> getMenusByRoleFlag(String flag);
    }
    ```

+ 菜单管理业务实现层[MenuServiceImpl](src/main/java/cn/li98/blog/service/impl/MenuServiceImpl.java)
    ```java
    @Service
    public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    
    
        /**
         * 从role_menu表中获取指定角色标识拥有的菜单权限
         *
         * @param flag 角色标识
         * @return 当前角色标识所拥有的所有菜单权限（菜单实体类）
         */
        @Override
        public List<Menu> getMenusByRoleFlag(String flag) {
            // 找到当前用户的角色对应的角色id，然后根据角色id从role_menu表中查找对应的菜单id
            QueryWrapper q1 = new QueryWrapper();
            q1.eq("flag", flag);
            Role role = roleMapper.selectOne(q1);
            QueryWrapper q2 = new QueryWrapper();
            q2.eq("role_id", role.getId());
            List<RoleMenu> roleMenu = roleMenuMapper.selectList(q2);
            // rightList是菜单id组成的权限列表
            List<Long> rightList = new LinkedList();
            for (RoleMenu item : roleMenu) {
                rightList.add(item.getMenuId());
            }
            // 根据菜单id组成的权限列表查询其对应的菜单实体类列表
            List<Menu> allMenuList = menuMapper.selectBatchIds(rightList);
            // 找到一级菜单
            List<Menu> firstLevel = allMenuList.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
            // 找到二级菜单
            List<Menu> secondLevel = allMenuList.stream().filter(menu -> menu.getPid() != null).collect(Collectors.toList());
            // 将二级菜单组装到一级菜单下
            for (Menu menu : firstLevel) {
                List<Menu> items = secondLevel.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList());
                menu.setChildren(items);
            }
            return firstLevel;
        }
    }
    ```


## 20. 调整redis的使用代码，分页查询根据查询参数实现自定义sql，弃用BlogDisplay类后改造使用Blog类
见以下文件
+ [BlogFrontController](src/main/java/cn/li98/blog/controller/front/BlogFrontController.java)
    ```java
        /**
         * 获取访客可见的博客列表
         *
         * @param pageNum 页码
         * @param pageSize 页内数量
         * @return 访客可见的博客列表
         */
        @OperationLogger("获取博客列表")
        @GetMapping("/getBlogList")
        public Result getBlogList(@RequestParam(value = "pageNum", defaultValue = "") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "") Integer pageSize) {
            Map<String, Object> data = new HashMap<>(2);
            // 1. 尝试从redis缓存中获取指定键值对应的数据
            List<Blog> list = redisTemplate.opsForList().range(Constant.GUEST_BLOG_KEY + "_" + pageNum, 0, -1);
            // 2. 如果redis中无对应的数据
            if (list.isEmpty()) {
                // 3. 从数据库取出数据
                // 3.1 将查询参数以键值对的形式存放到QueryWrapper
                QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
                // 3.2 博客的可见性需指定为“可见”，用户只能看到公开的博客
                queryWrapper.eq("published", true);
                // 3.3 根据创建时间查询逆序的列表结果，越新发布的博客越容易被看到
                queryWrapper.orderByDesc("create_time");
                // 3.4 查询符合条件的博客列表
                Page page = new Page(pageNum, pageSize);
                IPage pageData = blogService.page(page, queryWrapper);
                if (pageData.getTotal() == 0 && pageData.getRecords().isEmpty()) {
                    data.put("pageData", pageData);
                    data.put("total", pageData.getTotal());
                    return Result.succ("未查找到相应博客", data);
                }
                list = pageData.getRecords();
                // 3.5 处理博客列表
                List<Category> categoryList = categoryService.list();
                for (int i = 0; i < list.size(); i++) {
                    for (Category category : categoryList) {
                        if (list.get(i).getCategoryId().equals(category.getId())) {
                            list.get(i).setCategoryName(category.getCategoryName());
                        }
                    }
                }
                pageData.setRecords(list);
                // 4. 缓存到redis
                redisTemplate.opsForList().rightPush(Constant.GUEST_BLOG_KEY + "_" + pageNum, list);
                redisTemplate.opsForValue().set("PAGE_NUMBER_OF_PUBLISHED_BLOGS", pageData.getTotal());
                // 5. 返回给前端
                data.put("blogList", list);
                data.put("total", pageData.getTotal());
                return Result.succ("查询成功", data);
            } else if (!list.isEmpty()) {
                list = (List<Blog>) list.get(0);
                // 将查询结果填充到Map中
                data.put("blogList", list);
                data.put("total", redisTemplate.opsForValue().get("total"));
                return Result.succ("查询成功", data);
            }
            return Result.fail("查询失败");
        }
    ```

+ [BlogController](src/main/java/cn/li98/blog/controller/admin/BlogController.java)
    ```java
        /**
         * 获取博客列表
         * 可以实现无参数和多参数的分页查询
         * 将分页列表和总记录数作为键值对存储到Map中
         * 分页列表用于前端的当前页展示
         * 总记录数用于前端展示博客总数，这个数值是当前数据库中未被删除的博客总数，是所有分页中的博客个数的和
         *
         * @return 成功则Map作为data
         */
        @OperationLogger("获取博客列表")
        @PostMapping("/getBlogs")
        public Result getBlogs(@RequestBody Map<String, Object> params) {
    
            /*QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
            // 将查询参数以键值对的形式存放到QueryWrapper中
            if (!StringUtils.isEmpty(title) && !StringUtils.isBlank(title)) {
                queryWrapper.like("title", title);
            }
            if (categoryId != null) {
                queryWrapper.eq("category_id", categoryId);
            }
            // 根据创建时间查询逆序的列表结果，越新发布的博客越容易被看到
            queryWrapper.orderByDesc("create_time");
            // 新建一个分页规则，pageNum代表当前页码，pageSize代表每页数量
            Page page = new Page(pageNum, pageSize);
            // 借助Page实现分页查询，借助QueryWrapper实现多参数查询
            IPage pageData = blogService.page(page, queryWrapper);*/
    
            Map<String, Object> data = new HashMap<>(2);
            IPage<Blog> pageData = blogService.getBlogList(params);
            if (pageData.getTotal() == 0 && pageData.getRecords().isEmpty()) {
                data.put("pageData", pageData);
                data.put("total", pageData.getTotal());
                return Result.succ("未查找到相应博客", data);
            }
            
            List<Category> categoryList = categoryService.list();
            List<Blog> list = pageData.getRecords();
            for (int i = 0; i < list.size(); i++) {
                for (Category category : categoryList) {
                    if (list.get(i).getCategoryId().equals(category.getId())) {
                        list.get(i).setCategoryName(category.getCategoryName());
                    }
                }
            }
    
            pageData.setRecords(list);
            data.put("pageData", pageData);
            data.put("total", pageData.getTotal());
            return Result.succ("查询成功", data);
        }
    ```

+ [Blog](src/main/java/cn/li98/blog/model/entity/Blog.java)中新增不属于数据库字段的属性
    ```java
        /**
         * 分类名
         */
        @TableField(exist = false)
        private String categoryName;
    ```

+ [BlogService](src/main/java/cn/li98/blog/service/BlogService.java)
    ```java
        /**
         * 获取博客列表
         *
         * @param params 标题、分类id、标签id列表等参数
         * @return 符合查询条件的博客列表
         */
        IPage<Blog> getBlogList(Map<String, Object> params);
    ```

+ [BlogServiceImpl](src/main/java/cn/li98/blog/service/impl/BlogServiceImpl.java)
    ```java
        /**
         * 获取博客列表
         *
         * @param params 标题、分类id、标签id列表等参数
         * @return 符合查询条件的博客列表
         */
        @Override
        public IPage<Blog> getBlogList(@Param("params") Map<String, Object> params) {
            int pageNum = (int) params.get("pageNum");
            int pageSize = (int) params.get("pageSize");
            Page page = new Page(pageNum, pageSize);
            return blogMapper.getBlogList(page, params);
        }
    ```

+ [BaseMapper.java](src/main/java/cn/li98/blog/dao/BlogMapper.java)
    ```java
        /**
         * 获取博客列表
         *
         * @param page   分页
         * @param params 标题、分类id、标签id列表等参数
         * @return 符合查询条件的博客列表
         */
        IPage<Blog> getBlogList(Page page, Map<String, Object> params);
    ```

+ [BlogMapper.xml](src/main/resources/mapper/BlogMapper.xml)
    ```xml
        <!-- 根据查询条件获取博客列表-->
        <select id="getBlogList" resultType="cn.li98.blog.model.entity.Blog">
            select * from blog where deleted='0'
            <if test="params.title != null and params.title != ''">
                and title like CONCAT('%', #{params.title}, '%')
            </if>
            <if test="params.categoryId != null and params.categoryId != ''">
                and category_id = #{params.categoryId}
            </if>
            <if test="params.tagIds != null and params.tagIds.size > 0">
                and id in (SELECT blog_id FROM blog_tag WHERE tag_id in
                <foreach collection="params.tagIds" index="index" item="tag_id" open="(" separator="," close=")">
                    #{tag_id}
                </foreach>
                )
            </if>
        </select>
    ```


## 21. 完善用户登录与创建用户时的密码加密与判断

+ 取消[User](src/main/java/cn/li98/blog/model/entity/User.java)类中password字段的JsonIgnore注解
  ```java
  @Data
  @Accessors(chain = true)
  @TableName("user")
  public class User implements Serializable {
      @TableId(value = "id", type = IdType.AUTO)
      private Long id;
  
      @NotBlank(message = "昵称不能为空")
      private String username;
  
      // @JsonIgnore 取消此处注解
      private String password;
  
      private String nickname;
  
      private String avatar;
      @NotBlank(message = "邮箱不能为空")
      @Email(message = "邮箱格式不正确")
      private String email;
  
      private Date createTime;
  
      private Date updateTime;
  
      private String role;
  
      @TableField(exist = false)
      private List<Menu> menuList;
  
      private static final long serialVersionUID = 1L;
  
  }
  ```

+ 创建用户时，用户密码使用HuTool的工具类SecureUtil.md5进行加密后再存入数据库，见[UserController](src/main/java/cn/li98/blog/controller/admin/UserController.java)
  ```java
  @Slf4j
  @RestController
  @RequestMapping("/admin/user")
  public class UserController {
      @Autowired
      UserService userService;
  
      @Autowired
      private MenuService menuService;
  
      /**
       * 新增或者更新
       *
       * @param user 用户实体类
       * @return 是否维护成功的提示
       */
      @PostMapping("/saveOrUpdate")
      public Result saveOrUpdate(@RequestBody User user) {
          Date date = new Date();
          if (user.getId() == null) {
              user.setCreateTime(date);
          }
          user.setUpdateTime(date);
          // 密码加密
          String md5Password = SecureUtil.md5(user.getPassword());
          user.setPassword(md5Password);
  
          boolean flag = userService.saveOrUpdate(user);
          if (flag) {
              return Result.succ("用户信息维护成功");
          }
          return Result.fail("用户信息维护失败");
      }
  }
  ```

+ 登录时对输入的原密码进行加密，然后与数据库中的密码信息做对比，见[UserServiceImpl](src/main/java/cn/li98/blog/service/impl/UserServiceImpl.java)
  ```java
  @Service
  public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
      @Resource
      private UserMapper userMapper;
  
      @Override
      public User login(LoginDTO loginDTO) {
          QueryWrapper wrapper = new QueryWrapper();
          wrapper.eq("username", loginDTO.getUsername());
          // 密码通过md5加密后再判断
          String md5Password = SecureUtil.md5(loginDTO.getPassword());
          wrapper.eq("password", md5Password);
          User one = getOne(wrapper);
          if (one != null) {
              return one;
          }
          return null;
      }
  }
  ```


## 22. 完善统计功能，实现前端访问操作的日志进而实现PV和UV的统计
### 22.1 实现前端访问操作的自定义AOP记录访问日志，参考[15. 自定义AOP记录操作日志]
+ 新增[VisitLogger.java](src/main/java/cn/li98/blog/common/annotation/VisitLogger.java)
+ 新增[VisitLogAspect.java](src/main/java/cn/li98/blog/common/aspect/VisitLogAspect.java)
+ 新增[VisitLog.java](src/main/java/cn/li98/blog/model/entity/VisitLog.java)
+ 新增[VisitLogController.java](src/main/java/cn/li98/blog/controller/admin/VisitLogController.java)
+ 新增[VisitLogService.java](src/main/java/cn/li98/blog/service/VisitLogService.java)
+ 新增[VisitLogServiceImpl.java](src/main/java/cn/li98/blog/service/impl/VisitLogServiceImpl.java)
+ 新增[VisitLogMapper.java](src/main/java/cn/li98/blog/dao/VisitLogMapper.java)
+ 新增[VisitLogMapper.xml](src/main/resources/mapper/VisitLogMapper.xml)
+ 新增[VisitLogRemark.java](src/main/java/cn/li98/blog/model/dto/VisitLogRemark.java)
+ 新增[VisitBehavior.java](src/main/java/cn/li98/blog/common/enums/VisitBehavior.java)
+ 在前端访问相关的接口上添加VisitLogger注解，如[CategoryFrontController.java](src/main/java/cn/li98/blog/controller/front/CategoryFrontController.java)
+ 在数据库的菜单表中添加权限记录
```sql
INSERT INTO `menu` VALUES (19, '/visitLog', 'VisitLog', 0, 'VisitLog', '访问日志', 'el-icon-notebook-2', 1, 'VisitLog', 8, 19);
```
### 22.2 完成PV和UV的统计
+ 在[StatisticController](src/main/java/cn/li98/blog/controller/admin/StatisticController.java)中添加查询业务
  ```java
  @Slf4j
  @RestController
  @RequestMapping("/admin/data")
  public class StatisticController {
      @Autowired
      StatisticService statisticService;
  
      /**
       * 获取统计数据
       *
       * @return 存放了博客分类统计数据列表和分类名列表的哈希表
       */
      @OperationLogger("获取统计数据")
      @GetMapping("/getStatistic")
      public Result getStatistic() {
          Map<String, Object> map = statisticService.getBlogStatistic();
          int totalPageView = statisticService.getTotalPageView();
          int todayPageView = statisticService.getTodayPageView();
          int totalUniqueVisitor = statisticService.getTotalUniqueVisitor();
          int todayUniqueVisitor = statisticService.getTodayUniqueVisitor();
          int totalComment = statisticService.getTotalComment();
          map.put("totalPageView", totalPageView);
          map.put("todayPageView", todayPageView);
          map.put("totalUniqueVisitor", totalUniqueVisitor);
          map.put("todayUniqueVisitor", todayUniqueVisitor);
          map.put("totalComment", totalComment);
          return Result.succ(map);
      }
  }
  ```

+ 在[StatisticService.java](src/main/java/cn/li98/blog/service/StatisticService.java)中添加接口
  ```java
  public interface StatisticService {
      /**
       * 获取总PV
       *
       * @return 总PV值
       */
      int getTotalPageView();
  
      /**
       * 获取总UV
       *
       * @return 总UV值
       */
      int getTotalUniqueVisitor();
  
      /**
       * 获取日UV
       *
       * @return 日UV值
       */
      int getTodayUniqueVisitor();
  }
  ```

+ 在业务实现层[StatisticServiceImpl.java](src/main/java/cn/li98/blog/service/impl/StatisticServiceImpl.java)中实现功能
  ```java
  @Service
  public class StatisticServiceImpl implements StatisticService {
  
      @Resource
      private StatisticMapper statisticMapper;
  
      /**
       * 获取总PV
       *
       * @return 总PV值
       */
      @Override
      public int getTotalPageView() {
          int totalPageView = statisticMapper.getTotalPageView();
          return totalPageView;
      }
      
      /**
       * 获取总UV
       *
       * @return 总UV值
       */
      @Override
      public int getTotalUniqueVisitor() {
          int totalUniqueVisitor = statisticMapper.getTotalUniqueVisitor();
          return totalUniqueVisitor;
      }
  
      /**
       * 获取日UV
       *
       * @return 日UV值
       */
      @Override
      public int getTodayUniqueVisitor() {
          int todayUniqueVisitor = statisticMapper.getTodayUniqueVisitor();
          return todayUniqueVisitor;
      }
  }
  ```

+ 在持久层中添加[StatisticMapper.java](src/main/java/cn/li98/blog/dao/StatisticMapper.java)方法
  ```java
  public interface StatisticMapper extends BaseMapper<Blog> {
      /**
       * 获取总PV
       *
       * @return 总PV值
       */
      int getTotalPageView();
  
      /**
       * 获取总UV
       *
       * @return 总UV值
       */
      int getTotalUniqueVisitor();
  
      /**
       * 获取日UV
       *
       * @return 日UV值
       */
      int getTodayUniqueVisitor();
  }
  ```

+ 在[StatisticMapper.xml](src/main/resources/mapper/StatisticMapper.xml)中添加sql语句
  ```xml
      <select id="getTotalPageView" resultType="java.lang.Integer">
          SELECT count(*) FROM visit_log
      </select>
      <select id="getTotalUniqueVisitor" resultType="java.lang.Integer">
          SELECT count(DISTINCT ip, to_days(create_time)) FROM visit_log
      </select>
      <select id="getTodayUniqueVisitor" resultType="java.lang.Integer">
          SELECT count(DISTINCT ip) FROM visit_log where to_days(create_time) = to_days(now())
      </select>
  ```