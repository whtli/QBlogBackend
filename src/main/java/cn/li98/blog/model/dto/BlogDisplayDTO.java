package cn.li98.blog.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: whtli
 * @date: 2022/12/14
 * @description:
 */
@NoArgsConstructor
@Data
public class BlogDisplayDTO implements Serializable {
    private Long id;

    /**
     * 文章标题
     */
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
    private Long deleted;

    /**
     * 分类名
     */
    private String categoryName;

    private static final long serialVersionUID = 1L;

}
