package cn.li98.blog.controller.front;

import cn.hutool.core.date.DateUtil;
import cn.li98.blog.common.Result;
import cn.li98.blog.model.entity.Comment;
import cn.li98.blog.model.entity.User;
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
 * @date: 2022/12/15
 * @description: 评论前台控制层
 */
@Slf4j
@RestController
@RequestMapping("/front")
public class CommentFrontController {
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
        // 查询所有的评论和回复
        List<Comment> allComments = commentService.getAllComments(blogId);
        return Result.succ(allComments);
    }

    /**
     * 新增或者更新评论
     *
     * @param comment 评论实体类（缺省）
     * @return 评论发布成功返回true，否则返回false
     */
    @PostMapping("/saveComment")
    public Result saveComment(@RequestBody Comment comment) {
        User user = TokenUtils.getCurrentUser();
        comment.setUserId(user.getId());
        comment.setTime(DateUtil.now());
        // 判断如果是回复，进行处理
        if (comment.getPid() != null) {
            // 查询当前回复的父级节点
            Long pid = comment.getPid();
            Comment pComment = commentService.getById(pid);
            if (pComment.getOriginId() != null) {
                // 如果当前回复的父级有祖宗节点，那么就为当前回复设置相同的祖宗节点
                comment.setOriginId(pComment.getOriginId());
            } else {
                // 否则就设置父级为当前回复的祖宗节点
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
