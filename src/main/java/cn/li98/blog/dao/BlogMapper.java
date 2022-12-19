package cn.li98.blog.dao;

import cn.li98.blog.model.entity.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
}