package cn.li98.blog.controller.admin;


import cn.li98.blog.common.Result;
import cn.li98.blog.model.User;
import cn.li98.blog.model.dto.LoginDTO;
import cn.li98.blog.service.UserService;
import cn.li98.blog.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: whtli
 * @date: 2022/11/09
 * @description: UserController
 */
@Slf4j
@RestController
@RequestMapping("/admin")
public class UserController {
    @Autowired
    UserService userService;

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
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        return Result.succ(user);
    }

    @PostMapping("/logout")
    public Result logout() {
        return Result.succ(null);
    }
}
