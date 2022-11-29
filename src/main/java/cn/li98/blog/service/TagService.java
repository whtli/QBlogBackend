package cn.li98.blog.service;

import cn.li98.blog.model.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author: whtli
 * @date: 2022/11/28
 * @description: 博客标签业务层
 */
public interface TagService extends IService<Tag> {
    int createTag(Tag tag);

    void saveBlogTag(Long blogId, Long tagId);

    int updateTag(Tag tag);
}
