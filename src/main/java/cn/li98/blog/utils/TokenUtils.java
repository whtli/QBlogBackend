package cn.li98.blog.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.li98.blog.model.entity.User;
import cn.li98.blog.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author: whtli
 * @date: 2022/11/24
 * @description: token工具类
 */
@Component
public class TokenUtils {


    private static UserService staticUserService;

    @Resource
    private UserService userService;

    @PostConstruct
    public void setUserService() {
        staticUserService = userService;
    }

    /**
     * 生成token
     *
     * @return
     */
    public static String genToken(Long userId, String sign) {
        // 将 user id 保存到 token 里面,作为载荷
        // 2小时后token过期
        // 以 password 作为 token 的密钥
        return JWT.create().withAudience(String.valueOf(userId))
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2))
                .sign(Algorithm.HMAC256(sign));
    }

    /**
     * 获取当前登录的用户信息
     *
     * @return user对象
     */
    public static User getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("Authorization");
            if (StrUtil.isNotBlank(token)) {
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserService.getById(Integer.valueOf(userId));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
