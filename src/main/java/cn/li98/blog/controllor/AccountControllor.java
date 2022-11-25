package cn.li98.blog.controllor;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;

import cn.li98.blog.model.dto.LoginDTO;
import cn.li98.blog.common.Result;
import cn.li98.blog.model.User;
import cn.li98.blog.service.UserService;
import cn.li98.blog.utils.TokenUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: whtli
 * @date: 2022/11/10
 * @description:
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AccountControllor {
    @Autowired
    UserService userService;


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

    @GetMapping("/logout")
    public Result logout() {
        System.out.println("in logout");
        return Result.succ(null);
    }
}
