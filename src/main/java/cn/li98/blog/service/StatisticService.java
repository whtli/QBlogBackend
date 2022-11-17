package cn.li98.blog.service;

import cn.li98.blog.model.vo.StatisticBlogCount;

import java.util.List;
import java.util.Map;

/**
 * @AUTHOR: whtli
 * @DATE: 2022/11/17
 * @DESCRIPTION: 用于获取统计数据的service
 */
public interface StatisticService {
    /**
     * 调用Mapper层获取统计数据
     * @return
     */
    Map<String, List> getBlogCountList();
}
