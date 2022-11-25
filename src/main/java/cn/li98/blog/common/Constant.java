package cn.li98.blog.common;

/**
 * @author: whtli
 * @date: 2022/11/19
 * @description:
 */
public interface Constant {
    /**
     * 成功
     */
    Integer CODE_SUCCESSFUL = 200;
    /**
     * 系统错误
     */
    Integer CODE_SYSTEM_ERROR = 500;
    /**
     * 权限不足
     */
    Integer CODE_ACCESS_DENIED = 401;
    /**
     * 参数错误
     */
    Integer CODE_PARAM_ERROR = 400;
    /**
     * 其他业务异常
     */
    Integer CODE_ELSE_ERROR = 600;
}
