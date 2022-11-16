package cn.li98.blog.service;

import cn.li98.blog.model.Blog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @AUTHOR: whtli
 * @DATE: 2022/11/10
 * @DESCRIPTION:
 */
public interface BlogService extends IService<Blog> {

    /**
     * 新发布博客
     *
     * @param blog
     */
    int createBlog(Blog blog);

    /**
     * 更新已有博客
     *
     * @param blog
     */
    int updateBlog(Blog blog);
}
