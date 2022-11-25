package cn.li98.blog.model.vo;

import lombok.Data;

/**
 * @author: whtli
 * @date: 2022/11/17
 * @description: 博客统计数据的VO，字段包括分类id、分类名、分类下的博客数量
 */
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
