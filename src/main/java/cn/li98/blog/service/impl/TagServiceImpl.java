package cn.li98.blog.service.impl;

import cn.li98.blog.dao.TagMapper;
import cn.li98.blog.model.Tag;
import cn.li98.blog.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: whtli
 * @date: 2022/11/28
 * @description: 博客标签业务实现层
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Autowired
    TagMapper tagMapper;
    @Override
    public int createTag(Tag tag) {
        return tagMapper.insert(tag);
    }

    @Override
    public void saveBlogTag(Long blogId, Long tagId) {
        if (tagMapper.saveBlogTag(blogId, tagId) != 1) {
            throw new PersistenceException("维护博客标签关联表失败");
        }
    }

    @Override
    public int updateTag(Tag tag) {
        return tagMapper.updateById(tag);
    }
}
