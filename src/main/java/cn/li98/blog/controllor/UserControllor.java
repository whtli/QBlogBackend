package cn.li98.blog.controllor;


import cn.li98.blog.common.Result;
import cn.li98.blog.model.User;
import cn.li98.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @AUTHOR: whtli
 * @DATE: 2022/11/09
 * @DESCRIPTION: UserControllor
 */
@RestController
@RequestMapping("/admin")
public class UserControllor {
    @Autowired
    UserService userService;

    @GetMapping("/test")
    public Result updateUserName(@RequestParam String name) {
        // DTO是实体类时，可以使用@Validated来校验
        System.out.println("---");
        User user = userService.getUserById(1L);
        System.out.println(user);
        System.out.println("---");

        System.out.println("success");
        Map<String, String> data = new HashMap<>(1);
        data.put("userBar", "Hello  " + name);
        return Result.succ(20000, "success!", data);
    }
}
