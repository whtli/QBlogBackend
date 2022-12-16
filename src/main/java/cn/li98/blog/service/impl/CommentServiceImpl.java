package cn.li98.blog.service.impl;

import cn.li98.blog.dao.CommentMapper;
import cn.li98.blog.model.Comment;
import cn.li98.blog.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: whtli
 * @date: 2022/12/15
 * @description: 评论业务实现层
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    /**
     * 查询某一博客下的所有评论
     *
     * @param blogId 博客id
     * @return
     */
    @Override
    public List<Comment> getAllComments(Long blogId) {
        return commentMapper.getAllComments(blogId);
    }
}
