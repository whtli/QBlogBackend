package cn.li98.blog.controllor;


import cn.li98.blog.common.Result;
import cn.li98.blog.model.User;
import cn.li98.blog.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : whtli
 */
//@CrossOrigin
@RestController
@RequestMapping("/admin")
public class TestControllor {
    @Autowired
    TestService testService;

    @GetMapping("/test")
    public Result updateUserName(@RequestParam String name) {
        System.out.println("---");
        List<User> info = testService.getInfo();
        System.out.println(info);
        System.out.println("---");

        System.out.println("success");
        Map<String, String> data = new HashMap<>(1);
        data.put("userBar", "Hello  " + name);
        return Result.ok("success!", data);
    }
}
