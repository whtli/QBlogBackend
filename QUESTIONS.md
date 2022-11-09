## 1.集成mybatis并测试

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
  
