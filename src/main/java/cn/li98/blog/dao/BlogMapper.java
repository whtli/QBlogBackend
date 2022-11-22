package cn.li98.blog.dao;

import cn.li98.blog.model.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @AUTHOR: whtli
 * @DATE: 2022/11/13
 * @DESCRIPTION: BlogMapper
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
}