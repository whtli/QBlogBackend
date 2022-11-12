package cn.li98.blog.utils;

import cn.li98.blog.config.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

/**
 * @AUTHOR: whtli
 * @DATE: 2022/11/11
 * @DESCRIPTION:
 */
public class ShiroUtil {
    public static AccountProfile getProfile() {
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
