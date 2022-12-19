package cn.li98.blog.dao;

import cn.li98.blog.model.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author: whtli
 * @date: 2022/12/01
 * @description: 评论相关的持久层
 */
public interface CommentMapper extends BaseMapper<Comment> {
    /**
     * 查询某一博客下的所有评论
     * @param blogId 博客id
     * @return
     */
    List<Comment> getAllComments(Long blogId);
}