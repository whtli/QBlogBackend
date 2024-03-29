package cn.li98.blog.service;

import cn.li98.blog.common.Result;
import cn.li98.blog.model.entity.Blog;
import cn.li98.blog.model.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author: whtli
 * @date: 2022/11/28
 * @description: 博客标签业务层
 */
public interface TagService extends IService<Tag> {
    /**
     * 建立博客和标签的映射关系到blog_tag表
     *
     * @param blogId 博客id
     * @param tagId  标签id
     * @return 关联成功返回1
     */
    int saveBlogTag(Long blogId, Long tagId);

    /**
     * 根据博客id查询其拥有的标签列表
     *
     * @param blogId 博客id
     * @return 标签列表
     */
    List<Tag> getTagsByBlogId(Long blogId);

    /**
     * 根据标签id查询使用了这个标签的博客
     *
     * @param tagId 标签id
     * @return 博客列表
     */
    List<Blog> getBlogsByTagId(Long tagId);

    /**
     * 在创建或更新博客之前检查当前博客所带的标签
     * 若所带标签已经存在则存入标签列表
     * 若所带标签不存在于数据库中则创建新标签然后存入标签列表
     *
     * @param tags 前端传来的选择的和手动输入的标签组成的列表
     * @return 当前博客对应的标签列表
     */
    List<Tag> checkTagsBeforeSubmitBlog(List<Object> tags);

    /**
     * 新增与修改标签的通用方法，通过判断id的有无来区分新增和修改
     *
     * @param tag 标签实体类
     * @return Result
     */
    Result submitTag(Tag tag);
}
