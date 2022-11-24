package cn.li98.blog.controllor;


import cn.li98.blog.common.Result;
import cn.li98.blog.model.User;
import cn.li98.blog.service.UserService;
import cn.li98.blog.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.SoundbankResource;
import java.util.HashMap;
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
        User currentUser = TokenUtils.getCurrentUser();
        System.out.println("获取当前用户信息 ====== " + currentUser.getUsername());

        Map<String, String> data = new HashMap<>(1);
        data.put("userBar", "Hello  " + name);
        return Result.succ("测试连接成功!", data);
    }
}
