package cn.li98.blog.controller.admin;

import cn.li98.blog.common.Result;
import cn.li98.blog.model.entity.Dict;
import cn.li98.blog.model.entity.Menu;
import cn.li98.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: whtli
 * @date: 2022/12/16
 * @description: 菜单接口
 */
@RestController
@RequestMapping("/admin/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 获取菜单列表（带层级关系）
     *
     * @param menuName 菜单名（缺省）
     * @return 带层次关系的菜单列表
     */
    @GetMapping("/getMenuList")
    public Result getMenuList(@RequestParam(defaultValue = "") String menuName) {
        return Result.succ(menuService.getMenuList(menuName));
    }

    /**
     * 获取图标信息
     *
     * @return 图标信息列表
     */
    @GetMapping("/getIconList")
    public Result getIconList() {
        List<Dict> iconList = menuService.getIconList();
        return Result.succ(iconList);
    }

    /**
     * 新增或更新菜单
     *
     * @param menu 菜单实体
     * @return 是否维护成功的提示
     */
    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody Menu menu) {
        boolean flag = menuService.saveOrUpdate(menu);
        if (flag) {
            return Result.succ("菜单维护成功");
        }
        return Result.fail("菜单维护失败");
    }

    /**
     * 根据id删除菜单
     *
     * @param id 菜单id
     * @return 是否删除成功的提示以及菜单id
     */
    @DeleteMapping("/deleteMenuById")
    public Result deleteMenuById(@RequestParam Long id) {
        boolean flag = menuService.removeById(id);
        if (flag) {
            return Result.succ("菜单删除成功", id);
        }
        return Result.fail("菜单删除失败", id);
    }

    /**
     * 获取指定角色id拥有的菜单权限
     *
     * @param roleId 角色id
     * @return 当前角色id所拥有的所有菜单权限
     */
    @GetMapping("/getMenusByRoleId")
    public Result getMenusByRoleId(@RequestParam Long roleId) {
        List<Long> rightList = menuService.getMenusByRoleId(roleId);
        return Result.succ(rightList);
    }

}
