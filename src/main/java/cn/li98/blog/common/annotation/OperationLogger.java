package cn.li98.blog.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
