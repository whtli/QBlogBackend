package cn.li98.blog.service;

import cn.li98.blog.model.Dict;
import cn.li98.blog.model.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author: whtli
 * @date: 2022/12/16
 * @description: 菜单业务层
 */
public interface MenuService extends IService<Menu> {
    /**
     * 查询菜单
     *
     * @param menuName 菜单名（缺省）
     * @return 带层次关系的菜单列表
     */
    Map<String, Object> getMenuList(String menuName);

    /**
     * 获取图标信息
     *
     * @return 图标信息列表
     */
    List<Dict> getIconList();

    /**
     * 从role_menu表中获取指定角色id拥有的菜单权限
     *
     * @param roleId 角色id
     * @return 当前角色id所拥有的所有菜单权限
     */
    List<Long> getMenusByRoleId(Long roleId);
}
