package cn.li98.blog.controllor;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import cn.li98.blog.config.shiro.AccountProfile;
import cn.li98.blog.config.shiro.AccountRealm;
import cn.li98.blog.model.dto.LoginDTO;
import cn.li98.blog.common.Result;
import cn.li98.blog.model.User;
import cn.li98.blog.service.UserService;
import cn.li98.blog.utils.JwtUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @AUTHOR: whtli
 * @DATE: 2022/11/10
 * @DESCRIPTION:
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AccountControllor {
    @Autowired
    UserService userService;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        /*String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return Result.fail("用户名和密码不能为空");
        }*/
        log.info(loginDTO.toString());

        User user = userService.login(loginDTO);
        if (user == null) {
            return Result.fail("用户不存在或密码不正确");
        }
        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(user, profile);

        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        /*return Result.succ(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map()
        );*/
        return Result.succ(profile);
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        System.out.println("in logout");
        return Result.succ(null);
    }
}
