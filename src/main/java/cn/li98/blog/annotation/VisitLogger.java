package cn.li98.blog.annotation;

import cn.li98.blog.enums.VisitBehavior;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: whtli
 * @date: 2023/01/09
 * @description: 用于需要记录前端访问日志的方法
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VisitLogger {
    /**
     * 访问行为枚举
     */
    VisitBehavior value() default VisitBehavior.UNKNOWN;
}
