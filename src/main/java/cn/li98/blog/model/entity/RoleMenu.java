package cn.li98.blog.model.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: whtli
 * @date: 2022/12/18
 * @description: 角色菜单关联表实体类
 */
@Data
@AllArgsConstructor
@TableName("sys_role_menu")
public class RoleMenu implements Serializable {
    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 菜单id
     */
    private Long menuId;

    private static final long serialVersionUID = 1L;
}