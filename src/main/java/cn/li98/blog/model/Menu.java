package cn.li98.blog.model;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: whtli
 * @date: 2022/12/16
 * @description: 菜单实体类
 */
@Data
@TableName("menu")
public class Menu implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 路径
     */
    private String path;

    /**
     * 图标
     */
    private String icon;

    /**
     * 描述
     */
    private String description;

    /**
     * 父id
     */
    private Long pid;

    /**
     * 前端组件名
     */
    private String component;

    /**
     * 排序
     */
    private String sortNum;

    /**
     * 子菜单
     */
    @TableField(exist = false)
    private List<Menu> children;

    private static final long serialVersionUID = 1L;
}