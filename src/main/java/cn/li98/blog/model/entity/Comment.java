package cn.li98.blog.model.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: whtli
 * @date: 2022/12/15
 * @description: 评论实体类
 */
@NoArgsConstructor
@Data
@TableName("comment")
public class Comment implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论人id
     */
    private Long userId;

    /**
     * 评论时间
     */
    private String time;

    /**
     * 父id
     */
    private Long pid;

    /**
     * 最上级评论id
     */
    private Long originId;

    /**
     * 关联的博客id
     */
    private Long blogId;

    /**
     * 发布评论的用户名
     */
    @TableField(exist = false)
    private String username;

    /**
     * 发布评论的用户头像
     */
    @TableField(exist = false)
    private String avatar;

    /**
     * 当前评论的子评论
     */
    @TableField(exist = false)
    private List<Comment> children;

    /**
     * 父节点的用户昵称
     */
    @TableField(exist = false)
    private String pUsername;

    /**
     * 父节点的用户id
     */
    @TableField(exist = false)
    private Long pUserId;

    private static final long serialVersionUID = 1L;
}