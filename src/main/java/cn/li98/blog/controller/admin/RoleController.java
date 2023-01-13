package cn.li98.blog.controller.admin;

import cn.li98.blog.common.Result;
import cn.li98.blog.annotation.OperationLogger;
import cn.li98.blog.model.entity.Role;
import cn.li98.blog.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: whtli
 * @date: 2022/12/16
 * @description: 角色控制层
 */
@RestController
@RequestMapping("/admin/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 获取角色列表
     *
     * @param roleName 角色名
     * @param pageNum  页码
     * @param pageSize 页容量
     * @return 角色列表的分页查询结果
     */
    @OperationLogger("获取角色列表")
    @GetMapping("/getRoleList")
    public Result findPage(@RequestParam(value = "roleName", defaultValue = "") String roleName,
                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", roleName);
        queryWrapper.orderByDesc("id");
        Page page = new Page(pageNum, pageSize);
        IPage pageData = roleService.page(page, queryWrapper);
        return Result.succ(pageData);
    }

    /**
     * 新增或者更新角色
     *
     * @param role 角色实体
     * @return 是否维护成功的提示
     */
    @OperationLogger("新增或者更新角色")
    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody Role role) {
        boolean flag = roleService.saveOrUpdate(role);
        if (flag) {
            return Result.succ("角色维护成功");
        }
        return Result.fail("角色维护失败");
    }

    /**
     * 根据id删除角色
     *
     * @param id 角色id
     * @return 是否删除成功的提示以及角色id
     */
    @OperationLogger("删除指定角色")
    @DeleteMapping("/deleteRoleById")
    public Result delete(@RequestParam Long id) {
        boolean flag = roleService.removeById(id);
        if (flag) {
            return Result.succ("删除成功", id);
        }
        return Result.fail("删除失败", id);
    }

    /**
     * 更新角色和菜单的对应关系
     *
     * @param data 参数，包含roleId和菜单id列表
     * @return
     */
    @OperationLogger("更新指定角色和菜单的对应关系")
    @PostMapping("updateRoleMenu")
    public Result updateRoleMenu(@RequestBody Map<String, Object> data) {
        // 获取参数中的roleId以及为这个role赋予权限（菜单id列表）
        Long roleId = Long.valueOf((Integer) data.get("roleId"));
        List<Object> ids = (List<Object>) data.get("menuIds");
        List<Long> menuIds = new ArrayList<>();
        for (Object item : ids) {
            menuIds.add((Long.valueOf((Integer) item)));
        }
        // 将角色及其新的菜单权限绑定在一起
        try {
            roleService.setRoleMenu(roleId, menuIds);
            return Result.succ("绑定成功", roleId);
        } catch (Exception e) {
            return Result.fail("绑定失败", roleId);
        }
    }

}
