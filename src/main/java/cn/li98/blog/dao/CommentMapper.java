package cn.li98.blog.dao;

import cn.li98.blog.model.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface CommentMapper extends BaseMapper<Comment> {
    int deleteByPrimaryKey(Long id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);
}