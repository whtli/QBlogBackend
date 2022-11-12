package cn.li98.blog.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @AUTHOR: whtli
 * @DATE: 2022/11/09
 * @DESCRIPTION: shiro默认supports的是UsernamePasswordToken，而现在采用了jwt的方式，所以自定义一个JwtToken，来完成shiro的supports方法。
 */
public class JwtToken implements AuthenticationToken {
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
