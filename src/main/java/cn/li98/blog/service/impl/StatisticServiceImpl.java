package cn.li98.blog.service.impl;

import cn.li98.blog.dao.StatisticMapper;
import cn.li98.blog.model.vo.StatisticBlogCount;
import cn.li98.blog.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @AUTHOR: whtli
 * @DATE: 2022/11/17
 * @DESCRIPTION: 用于获取统计数据的service实现层
 */
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
