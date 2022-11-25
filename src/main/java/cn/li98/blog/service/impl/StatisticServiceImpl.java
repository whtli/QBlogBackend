package cn.li98.blog.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Month;
import cn.li98.blog.dao.StatisticMapper;
import cn.li98.blog.model.Blog;
import cn.li98.blog.model.vo.StatisticBlogCount;
import cn.li98.blog.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: whtli
 * @date: 2022/11/17
 * @description: 用于获取统计数据的service实现层
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
    public Map<String, Object> getBlogCountList() {
        Map<String, Object> map = new HashMap<>(4);
        // 查询分类博客数据
        List<StatisticBlogCount> blogCategoryList = statisticMapper.getBlogCategoryList();
        // 使用stream获取分类名列表，与for循环效果相同
        List<String> categoryName = blogCategoryList.stream().map(StatisticBlogCount::getName).collect(Collectors.toList());
        map.put("blogCategoryList", blogCategoryList);
        map.put("categoryName", categoryName);

        // 获取每月博客发布量数据
        List<Blog> blogMonthList = statisticMapper.selectList(null);
        Map<Integer, Integer> blogYearCount = new LinkedHashMap<>();
        int jan = 0;
        int feb = 0;
        int mar = 0;
        int apr = 0;
        int may = 0;
        int june = 0;
        int july = 0;
        int aug = 0;
        int sep = 0;
        int oct = 0;
        int nov = 0;
        int dec = 0;
        for (Blog blog : blogMonthList) {
            Date createTime = blog.getCreateTime();
            int year = DateUtil.year(createTime);
            blogYearCount.put(year, blogYearCount.getOrDefault(year, 0) + 1);
            if (year == DateUtil.year(new Date())) {
                Month month = DateUtil.monthEnum(createTime);
                switch (month) {
                    case JANUARY:
                        jan += 1;
                        break;
                    case FEBRUARY:
                        feb += 1;
                        break;
                    case MARCH:
                        mar += 1;
                        break;
                    case APRIL:
                        apr += 1;
                        break;
                    case MAY:
                        may += 1;
                        break;
                    case JUNE:
                        june += 1;
                        break;
                    case JULY:
                        july += 1;
                        break;
                    case AUGUST:
                        aug += 1;
                        break;
                    case SEPTEMBER:
                        sep += 1;
                        break;
                    case OCTOBER:
                        oct += 1;
                        break;
                    case NOVEMBER:
                        nov += 1;
                        break;
                    case DECEMBER:
                        dec += 1;
                        break;
                    default:
                        break;
                }
            }
        }
        map.put("blogYearCount", blogYearCount);
        map.put("blogMonthList", CollUtil.newArrayList(jan, feb, mar, apr, may, june, july, aug, sep, oct, nov, dec));
        return map;
    }
}
