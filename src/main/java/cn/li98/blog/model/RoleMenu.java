package cn.li98.blog.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * sys_role_menu
 * @author 
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