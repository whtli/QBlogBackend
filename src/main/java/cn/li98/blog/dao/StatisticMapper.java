package cn.li98.blog.dao;

import cn.li98.blog.model.entity.Blog;
import cn.li98.blog.model.vo.StatisticBlogCount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author: whtli
 * @date: 2022/11/17
 * @description: 统计数据持久层
 */
public interface StatisticMapper extends BaseMapper<Blog> {
    /**
     * 获取统计数据
     *
     * @return 以类别id为分组依据的统计数据列表
     */
    List<StatisticBlogCount> getBlogCategoryList();

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
