package cn.li98.blog.service.impl;

import cn.li98.blog.common.Constant;
import cn.li98.blog.dao.DictMapper;
import cn.li98.blog.dao.MenuMapper;
import cn.li98.blog.dao.RoleMenuMapper;
import cn.li98.blog.model.entity.Dict;
import cn.li98.blog.model.entity.Menu;
import cn.li98.blog.model.entity.RoleMenu;
import cn.li98.blog.service.MenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: whtli
 * @date: 2022/12/16
 * @description: 菜单业务实现层
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Resource
    private MenuMapper menuMapper;

    @Resource
    private DictMapper dictMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    /**
     * 查询菜单
     *
     * @param menuName 菜单名（缺省）
     * @return 带层次关系的菜单列表
     */
    @Override
    public Map<String, Object> getMenuList(String menuName) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort_num");
        queryWrapper.like("name", menuName);

        // 查询所有数据
        List<Menu> list = menuMapper.selectList(queryWrapper);
        // 找出所有的一级菜单（pid为null）
        List<Menu> firstLevel = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        // 找出所有的二级菜单（pid不为null）
        List<Menu> secondLevel = list.stream().filter(menu -> menu.getPid() != null).collect(Collectors.toList());

        List<Menu> restSecondLevel = secondLevel;
        // 对于查询到的一级菜单，把其下包含的符合查询条件的二级菜单添加到其children中
        for (Menu menu : firstLevel) {
            // 筛选所有数据中pid=父级id的数据就是二级菜单，把二级菜单拼接到一级菜单下
            List<Menu> items = secondLevel.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList());
            menu.setChildren(items);
            restSecondLevel = restSecondLevel.stream().filter(item -> !items.contains(item)).collect(Collectors.toList());
        }
        // 对于剩余的符合查询条件的二级菜单，找到他们的上级菜单（即一级菜单），然后组合
        for (Menu menu : restSecondLevel) {
            // 找到二级菜单的上级菜单（即一级菜单）
            Menu parentItem = menuMapper.selectById(menu.getPid());
            parentItem.setChildren(new LinkedList<>());
            parentItem.getChildren().add(menu);
            firstLevel.add(parentItem);
        }

        // 获取所有的菜单id
        List<Long> allMenuIds = new ArrayList<>();
        for (Menu menu : list) {
            allMenuIds.add(menu.getId());
        }

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("menuList", firstLevel);
        data.put("allMenuIds", allMenuIds);
        data.put("total", list.size());
        return data;
    }

    /**
     * 获取图标信息
     *
     * @return 图标信息列表
     */
    @Override
    public List<Dict> getIconList() {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constant.DICT_TYPE_ICON);
        List<Dict> iconList = dictMapper.selectList(queryWrapper);
        return iconList;
    }

    /**
     * 从role_menu表中获取指定角色id拥有的菜单权限
     *
     * @param roleId 角色id
     * @return 当前角色id所拥有的所有菜单权限
     */
    @Override
    public List<Long> getMenusByRoleId(Long roleId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id", roleId);
        List<RoleMenu> roleMenu = roleMenuMapper.selectList(queryWrapper);
        List<Long> rightList = new LinkedList();
        for (RoleMenu item : roleMenu) {
            rightList.add(item.getMenuId());
        }
        return rightList;
    }
}
