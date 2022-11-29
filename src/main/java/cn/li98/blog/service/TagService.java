package cn.li98.blog.service;

import cn.li98.blog.model.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author: whtli
 * @date: 2022/11/28
 * @description: 博客标签业务层
 */
public interface TagService extends IService<Tag> {
    /**
     * 创建标签业务层
     *
     * @param tag 标签实体类，无id
     * @return 创建成功返回1
     */
    int createTag(Tag tag);

    /**
     * 编辑标签业务层
     *
     * @param tag 标签实体类，有id
     * @return 修改成功返回1
     */
    int updateTag(Tag tag);

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
}
