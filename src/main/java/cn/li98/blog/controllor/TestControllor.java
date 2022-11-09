package com.demo.controllor;


import com.demo.common.Result;
import com.demo.service.TestService;
import org.bouncycastle.util.test.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : whtli
 */
@CrossOrigin
@RestController
//@RequestMapping("/test")
public class TestControllor {
    @Autowired
    TestService testService;

    @GetMapping("/add")
    public Result updateUserName(@RequestParam String name) {
//        Map<String, String> info = testService.getInfo();
//        System.out.println(info);
        System.out.println("success");
        Map<String, String> data = new HashMap<>(1);
        data.put("userBar", "Hello  " + name);
        return Result.ok("success!", data);
    }
}
