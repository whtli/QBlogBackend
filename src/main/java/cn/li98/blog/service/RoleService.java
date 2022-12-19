package cn.li98.blog.service;

import cn.li98.blog.model.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author: whtli
 * @date: 2022/12/16
 * @description: 角色业务层
 */
public interface RoleService extends IService<Role> {
    /**
     * 绑定角色和菜单的关系
     *
     * @param roleId  角色id
     * @param menuIds 菜单id数组
     * @return 角色id
     */
    int setRoleMenu(Long roleId, List<Long> menuIds);
}
