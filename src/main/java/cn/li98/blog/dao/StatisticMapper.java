package cn.li98.blog.dao;

import cn.li98.blog.model.vo.StatisticBlogCount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @AUTHOR: whtli
 * @DATE: 2022/11/17
 * @DESCRIPTION: 用于获取统计数据Mapper
 */
public interface StatisticMapper {
    /**
     * 获取统计数据
     * @return 以类别id为分组依据的统计数据列表
     */
    List<StatisticBlogCount> getBlogCountList();
}
