package cn.li98.blog.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: whtli
 * @date: 2022/11/09
 * @description: 博客实体类
 */
@Data
@TableName("blog")
public class Blog implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文章标题
     */
    @NotBlank(message = "文章标题不能为空")
    private String title;

    /**
     * 文章首图，用于随机文章展示
     */
    private String firstPicture;

    /**
     * 文章正文
     */
    private String content;

    /**
     * 描述
     */
    private String description;

    /**
     * 公开或私密
     */
    private Boolean published;

    /**
     * 评论开关
     */
    private Boolean commentEnabled;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 浏览次数
     */
    private Integer views;

    /**
     * 文章字数
     */
    private Integer words;

    /**
     * 阅读时长(分钟)
     */
    private Integer readTime;

    /**
     * 文章分类
     */
    private Long categoryId;

    /**
     * 是否置顶
     */
    private Boolean top;

    /**
     * 密码保护
     */
    private String password;

    /**
     * 文章作者
     */
    private Long userId;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Long deleted;

    /**
     * 分类名
     */
    @TableField(exist = false)
    private String categoryName;

    /**
     * 博客标签列表
     */
    @TableField(exist = false)
    private List<Tag> tagList;

    private static final long serialVersionUID = 1L;
}