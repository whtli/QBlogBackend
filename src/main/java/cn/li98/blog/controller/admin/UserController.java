package cn.li98.blog.controller.admin;


import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.li98.blog.common.Result;
import cn.li98.blog.model.entity.Menu;
import cn.li98.blog.model.entity.User;
import cn.li98.blog.model.dto.LoginDTO;
import cn.li98.blog.service.MenuService;
import cn.li98.blog.service.UserService;
import cn.li98.blog.utils.TokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: whtli
 * @date: 2022/11/09
 * @description: 用户相关功能的控制层
 */
@Slf4j
@RestController
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    private MenuService menuService;

    @GetMapping("/test")
    public Result updateUserName(@RequestParam String name) {
        User currentUser = TokenUtils.getCurrentUser();
        log.info("获取当前用户信息 ====== " + currentUser.getUsername());

        Map<String, String> data = new HashMap<>(1);
        data.put("userBar", "Hello  " + name);
        return Result.succ("测试连接成功!", data);
    }

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        log.info(loginDTO.toString());

        User user = userService.login(loginDTO);
        if (user == null) {
            return Result.fail("用户不存在或密码不正确");
        }


        String jwt = TokenUtils.genToken(user.getId(), user.getPassword());
        // response.setHeader("Authorization", jwt);
        // response.setHeader("Access-Control-Expose-Headers", "Authorization");
        return Result.succ(jwt);
    }

    @GetMapping("/getInfo")
    public Result getInfo(@RequestParam String token) {
        log.info("token ====== " + token);
        if (StrUtil.isBlank(token) || StrUtil.isEmpty(token)) {
            return Result.fail("没有可以用于获取用户信息的token，请重新登录");
        }
        User currentUser = TokenUtils.getCurrentUser();

        // 获取菜单权限
        List<Menu> menuList = menuService.getMenusByRoleFlag(currentUser.getRole());
        currentUser.setMenuList(menuList);

        log.info("获取当前用户信息 ====== " + currentUser);
        return Result.succ("获取当前用户信息成功!", currentUser);
    }

    @PostMapping("/logout")
    public Result logout() {
        return Result.succ(null);
    }

    /**
     * 获取用户列表
     *
     * @param username 用户名
     * @param pageNum  页码
     * @param pageSize 页容量
     * @return 用户列表的分页查询结果
     */
    @GetMapping("/getUserList")
    public Result findPage(@RequestParam(value = "username", defaultValue = "") String username,
                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);
        queryWrapper.orderByDesc("id");
        Page page = new Page(pageNum, pageSize);
        IPage pageData = userService.page(page, queryWrapper);
        return Result.succ(pageData);
    }

    /**
     * 新增或者更新
     *
     * @param user 用户实体类
     * @return 是否维护成功的提示
     */
    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody User user) {
        Date date = new Date();
        if (user.getId() == null) {
            user.setCreateTime(date);
        }
        user.setUpdateTime(date);
        // 密码加密
        String md5Password = SecureUtil.md5(user.getPassword());
        user.setPassword(md5Password);

        boolean flag = userService.saveOrUpdate(user);
        if (flag) {
            return Result.succ("用户信息维护成功");
        }
        return Result.fail("用户信息维护失败");
    }

    /**
     * 根据id删除用户
     *
     * @param id 用户id
     * @return 是否删除成功的提示以及用户id
     */
    @DeleteMapping("/deleteUserById")
    public Result delete(@RequestParam Long id) {
        boolean flag = userService.removeById(id);
        if (flag) {
            return Result.succ("删除成功", id);
        }
        return Result.fail("删除失败", id);
    }
}
