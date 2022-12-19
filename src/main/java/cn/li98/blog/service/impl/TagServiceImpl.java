package cn.li98.blog.service.impl;

import cn.li98.blog.dao.TagMapper;
import cn.li98.blog.model.entity.Tag;
import cn.li98.blog.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: whtli
 * @date: 2022/11/28
 * @description: 博客标签业务实现层
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Autowired
    TagMapper tagMapper;

    /**
     * 创建标签业务实现层
     *
     * @param tag 标签实体类，无id
     * @return 创建成功返回1
     */
    @Override
    public int createTag(Tag tag) {
        return tagMapper.insert(tag);
    }

    /**
     * 编辑标签业务实现层
     *
     * @param tag 标签实体类，有id
     * @return 修改成功返回1
     */
    @Override
    public int updateTag(Tag tag) {
        return tagMapper.updateById(tag);
    }

    /**
     * 建立博客和标签的映射关系到blog_tag表
     *
     * @param blogId 博客id
     * @param tagId  标签id
     * @return 关联成功返回1
     */
    @Override
    public int saveBlogTag(Long blogId, Long tagId) {
        int res = tagMapper.saveBlogTag(blogId, tagId);
        if (res != 1) {
            throw new PersistenceException("维护博客标签关联表失败");
        }
        return res;
    }

    /**
     * 根据博客id查询其拥有的标签列表
     *
     * @param blogId 博客id
     * @return 标签列表
     */
    @Override
    public List<Tag> getTagsByBlogId(Long blogId) {
        return tagMapper.getTagsByBlogId(blogId);
    }

    /**
     * 根据标签id查询使用了这个标签的博客总数
     *
     * @param id 标签id
     * @return 博客总数
     */
    @Override
    public int getBlogCountByTagId(Long id) {
        return tagMapper.getBlogCountByTagId(id);
    }
}
