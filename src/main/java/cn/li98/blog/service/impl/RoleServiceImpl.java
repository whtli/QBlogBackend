package cn.li98.blog.service.impl;

import cn.li98.blog.dao.RoleMapper;
import cn.li98.blog.dao.RoleMenuMapper;
import cn.li98.blog.model.Role;
import cn.li98.blog.model.RoleMenu;
import cn.li98.blog.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: whtli
 * @date: 2022/12/16
 * @description: 角色业务实现层
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    /**
     * 绑定角色和菜单的关系
     *
     * @param roleId  角色id
     * @param menuIds 菜单id数组
     * @return 角色id
     */
    @Override
    public int setRoleMenu(Long roleId, List<Long> menuIds) {
        // 先根据roleId查出已有的权限并删除
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        roleMenuMapper.delete(queryWrapper);

        // 再插入新的
        for (Long menuId : menuIds) {
            RoleMenu item = new RoleMenu(roleId, menuId);
            roleMenuMapper.insert(item);
        }
        return menuIds.size();
    }
}
