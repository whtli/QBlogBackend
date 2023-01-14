package cn.li98.blog.service;

import java.util.Map;

/**
 * @author: whtli
 * @date: 2022/11/17
 * @description: 统计数据业务层
 */
public interface StatisticService {
    /**
     * 调用Mapper层获取统计数据
     *
     * @return 存放了博客分类统计数据列表和分类名列表的哈希表
     */
    Map<String, Object> getBlogStatistic();

    /**
     * 获取总PV
     *
     * @return 总PV值
     */
    int getTotalPageView();

    /**
     * 获取当日PV
     *
     * @return 日PV值
     */
    int getTodayPageView();

    /**
     * 获取评论总数
     *
     * @return 评论总数
     */
    int getTotalComment();

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
