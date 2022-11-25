package cn.li98.blog.service;

import java.util.List;
import java.util.Map;

/**
 * @author: whtli
 * @date: 2022/11/17
 * @description: 用于获取统计数据的service
 */
public interface StatisticService {
    /**
     * 调用Mapper层获取统计数据
     * @return 存放了博客分类统计数据列表和分类名列表的哈希表
     */
    Map<String, Object> getBlogCountList();
}
