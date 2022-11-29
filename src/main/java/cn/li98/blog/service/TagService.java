package cn.li98.blog.service;

import cn.li98.blog.model.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

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
     */
    void saveBlogTag(Long blogId, Long tagId);

}
