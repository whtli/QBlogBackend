package cn.li98.blog.service;

import cn.li98.blog.model.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author: whtli
 * @date: 2022/12/15
 * @description: 评论业务层
 */
public interface CommentService extends IService<Comment> {
    /**
     * 查询某一博客下的所有评论
     *
     * @param blogId 博客id
     * @return
     */
    List<Comment> getAllComments(Long blogId);
}
