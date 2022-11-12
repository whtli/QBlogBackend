package cn.li98.blog.controllor;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpResponse;
import cn.li98.blog.common.LoginDTO;
import cn.li98.blog.common.Result;
import cn.li98.blog.model.User;
import cn.li98.blog.service.UserService;
import cn.li98.blog.utils.JwtUtils;
import cn.li98.blog.utils.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @AUTHOR: whtli
 * @DATE: 2022/11/10
 * @DESCRIPTION:
 */
@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AccountControllor {
    @Autowired
    UserService userService;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        System.out.println(loginDTO);
        User user = userService.getByName(loginDTO.getUsername());
        System.out.println(user);
        Assert.notNull(user, "用户不存在");

        if (!SecureUtil.md5(user.getPassword()).equals(SecureUtil.md5(loginDTO.getPassword()))) {
            return Result.fail("密码不正确");
        }
        System.out.println("--------");
        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        // 用户可以另一个接口
        return Result.succ(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map()
        );
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        System.out.println("in logout");
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }
}
