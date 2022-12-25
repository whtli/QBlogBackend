package cn.li98.blog.controller.admin;

import cn.hutool.core.date.DateUtil;
import cn.li98.blog.common.Result;
import cn.li98.blog.model.entity.Comment;
import cn.li98.blog.service.CommentService;
import cn.li98.blog.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: whtli
 * @date: 2022/12/25
 * @description: 评论接口
 */
@Slf4j
@RestController
@RequestMapping("/admin/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 查询某一博客下的所有评论
     * 实现多级评论嵌套
     *
     * @param blogId 博客id
     * @return 某一博客下的所有评论
     */
    @GetMapping("/loadComment")
    public Result loadComment(@RequestParam Long blogId) {
        // 查询所有的评论和回复数据
        List<Comment> articleComments = commentService.getAllComments(blogId);
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
        return Result.succ(originList);
    }

    /**
     * 新增或者更新评论
     *
     * @param comment 评论实体类（缺省）
     * @return 评论发布成功返回true，否则返回false
     */
    @PostMapping("/saveComment")
    public Result saveComment(@RequestBody Comment comment) {
        comment.setUserId(TokenUtils.getCurrentUser().getId());
        comment.setTime(DateUtil.now());
        // 判断如果是回复，进行处理
        if (comment.getPid() != null) {
            Long pid = comment.getPid();
            Comment pComment = commentService.getById(pid);
            if (pComment.getOriginId() != null) {
                // 如果当前回复的父级有祖宗，那么就设置相同的祖宗
                comment.setOriginId(pComment.getOriginId());
            } else {
                // 否则就设置父级为当前回复的祖宗
                comment.setOriginId(comment.getPid());
            }
        }
        boolean flag = commentService.saveOrUpdate(comment);
        return Result.succ(flag);
    }

    /**
     * 根据id删除评论
     *
     * @param id 评论id
     * @return 被删除的评论id
     */
    @DeleteMapping("/deleteCommentById")
    public Result deleteCommentById(@RequestParam Long id) {
        commentService.removeById(id);
        return Result.succ(id);
    }
}
