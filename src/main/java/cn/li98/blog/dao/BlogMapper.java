package cn.li98.blog.dao;

import cn.li98.blog.model.entity.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * @author: whtli
 * @date: 2022/11/13
 * @description: BlogMapper
 */
public interface BlogMapper extends BaseMapper<Blog> {

    /**
     * 新发布博客
     *
     * @param blog
     * @return 1:成功；0:失败
     */
    int createBlog(Blog blog);

    /**
     * 更新已有博客
     *
     * @param blog
     * @return 1:成功；0:失败
     */
    int updateBlog(Blog blog);

    /**
     * 博客可见性更改
     *
     * @param blogId 博客id
     * @return 更改成功返回1，失败返回0
     */
    int changeBlogStatusById(Long blogId);

    /**
     * 获取博客列表
     *
     * @param page   分页
     * @param params 标题、分类id、标签id列表等参数
     * @return 符合查询条件的博客列表
     */
    IPage<Blog> getBlogList(Page page, Map<String, Object> params);
}