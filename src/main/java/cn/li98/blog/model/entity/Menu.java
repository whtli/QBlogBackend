package cn.li98.blog.model.entity;

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
@TableName("sys_menu")
public class Menu implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 路径
     */
    private String path;

    /**
     * 前端组件名
     */
    private String component;

    /**
     * 是否隐藏
     */
    private boolean hidden;

    /**
     * 名称
     */
    private String name;

    /**
     * meta标题
     */
    private String title;

    /**
     * meta图标
     */
    private String icon;

    /**
     * 是否展示只有一个子菜单的一级菜单
     */
    private boolean alwaysShow;

    /**
     * 备注
     */
    private String remark;

    /**
     * 父级菜单id
     */
    private Long pid;

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