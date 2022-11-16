package cn.li98.blog.service.impl;

import cn.li98.blog.dao.BlogMapper;
import cn.li98.blog.model.Blog;
import cn.li98.blog.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @AUTHOR: whtli
 * @DATE: 2022/11/10
 * @DESCRIPTION:
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
    @Autowired
    BlogMapper blogMapper;

    /**
     * 为新增或修改的博客设置参数
     * 这些常规参数在新增和修改过程中具备相同的设置规则
     * 包括：类别id、阅读时长、阅读量
     *
     * @param blog
     * @return Blog
     */
    private Blog setItemsOfBlog(Blog blog) {
        if (blog.getCategoryId() == null) {
            blog.setCategoryId(1L);
        }
        // TODO: 分类、标签等功能判断新增等功能
        if (blog.getReadTime() == null || blog.getReadTime() <= 0) {
            // 粗略计算阅读时长
            blog.setReadTime((int) Math.round(blog.getWords() / 200.0));
        }
        if (blog.getViews() == null || blog.getViews() < 0) {
            blog.setViews(0);
        }
        return blog;
    }

    /**
     * 创建博客
     * 需要常规地设置类别id、阅读时长、阅读量参数
     * 需要单独地设置创建时间、更新时间（等于创建）、创建用户（默认为1唯一的管理员）
     *
     * @param blog
     * @return 创建成功返回1，失败返回0
     */
    @Override
    public int createBlog(Blog blog) {
        Blog newBlog = setItemsOfBlog(blog);
        Date date = new Date();
        newBlog.setCreateTime(date);
        newBlog.setUpdateTime(date);
        newBlog.setUserId(1L);
        return blogMapper.createBlog(newBlog);
    }

    /**
     * 更新博客
     * 需要常规地设置类别id、阅读时长、阅读量参数
     * 需要单独地设置更新时间（当前时间）
     *
     * @param blog
     * @return 更新成功返回1，失败返回0
     */
    @Override
    public int updateBlog(Blog blog) {
        Blog newBlog = setItemsOfBlog(blog);
        Date date = new Date();
        newBlog.setUpdateTime(date);
        return blogMapper.updateBlog(blog);
    }
}
