package cn.li98.blog.service.impl;

import cn.li98.blog.dao.StatisticMapper;
import cn.li98.blog.model.vo.StatisticBlogCount;
import cn.li98.blog.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
     * @return
     */
    @Override
    public Map<String, List> getBlogCountList() {
        Map<String, List> map = new HashMap<>();
        List<StatisticBlogCount> blogCountList = statisticMapper.getBlogCountList();
        List<String> categoryName = new ArrayList<>();
        for (StatisticBlogCount item : blogCountList) {
            categoryName.add(item.getName());
        }
        List<Integer> blogCount = new LinkedList<>();
        for (StatisticBlogCount item : blogCountList) {
            blogCount.add(item.getValue());
        }
        map.put("blogCountList", blogCountList);
        map.put("categoryName", categoryName);
        return map;
    }
}
