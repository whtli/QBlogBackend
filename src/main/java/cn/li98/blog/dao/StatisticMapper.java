package cn.li98.blog.dao;

import cn.li98.blog.model.Blog;
import cn.li98.blog.model.vo.StatisticBlogCount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: whtli
 * @date: 2022/11/17
 * @description: 用于获取统计数据Mapper
 */
public interface StatisticMapper extends BaseMapper<Blog> {
    /**
     * 获取统计数据
     * @return 以类别id为分组依据的统计数据列表
     */
    List<StatisticBlogCount> getBlogCategoryList();

    int getTodayPageView();
}
