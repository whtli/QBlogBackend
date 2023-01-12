package cn.li98.blog.service.impl;

import cn.li98.blog.dao.CommentMapper;
import cn.li98.blog.model.entity.Comment;
import cn.li98.blog.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: whtli
 * @date: 2022/12/15
 * @description: 评论业务实现层
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Resource
    private CommentMapper commentMapper;
    /**
     * 查询某一博客下的所有评论
     *
     * @param blogId 博客id
     * @return
     */
    @Override
    public List<Comment> getAllComments(Long blogId) {
        // 查询所有的评论和回复数据
        List<Comment> articleComments = commentMapper.getAllComments(blogId);
        // 查询首层评论数据（不包括回复）
        List<Comment> originList = articleComments.stream().filter(comment -> comment.getOriginId() == null).collect(Collectors.toList());
        // 设置评论数据的子节点，也就是回复内容
        for (Comment origin : originList) {
            // 表示回复对象集合
            List<Comment> comments = articleComments.stream().filter(comment -> origin.getId().equals(comment.getOriginId())).collect(Collectors.toList());
            comments.forEach(comment -> {
                // 找到当前评论的父级
                Optional<Comment> pComment = articleComments.stream().filter(c1 -> c1.getId().equals(comment.getPid())).findFirst();
                // 找到父级评论的用户id和用户名，并设置给当前的回复对象
                pComment.ifPresent((v -> {
                    comment.setPUserId(v.getUserId());
                    comment.setPUsername(v.getUsername());
                }));
            });
            origin.setChildren(comments);
        }
        return originList;
    }
}
