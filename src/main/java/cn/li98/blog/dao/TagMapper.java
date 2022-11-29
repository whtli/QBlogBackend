package cn.li98.blog.dao;

import cn.li98.blog.model.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author: whtli
 * @date: 2022/11/28
 * @description:
 */
public interface TagMapper extends BaseMapper<Tag> {
    int saveBlogTag(Long blogId, Long tagId);
}
