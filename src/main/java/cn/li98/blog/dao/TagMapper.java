package cn.li98.blog.dao;

import cn.li98.blog.model.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author: whtli
 * @date: 2022/11/28
 * @description:
 */
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 建立博客和标签的映射关系到blog_tag表
     *
     * @param blogId 博客id
     * @param tagId  标签id
     * @return 映射成功返回1
     */
    int saveBlogTag(Long blogId, Long tagId);
}
