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

    /**
     * 通用的指向访客可见博客列表键值
     */
    String GUEST_BLOG_KEY = "GUEST_BLOG_KEY";

    /**
     * 前端可见的博客分页数
     */
    String PAGE_NUMBER_OF_PUBLISHED_BLOGS = "PAGE_NUMBER_OF_PUBLISHED_BLOGS";

    /**
     * 图标
     */
    String DICT_TYPE_ICON = "icon";

    /**
     * .xlsx文件
     */
    String FILE_TYPE_XLSX = "xlsx";
    /**
     * .xls文件
     */
    String FILE_TYPE_XLS = "xls";
    /**
     * .md文件
     */
    String FILE_TYPE_MD = "md";

    String IP_UNKNOWN = "unknown";

    String IP_V4_LOCALHOST = "127.0.0.1";
    String IP_V6_LOCALHOST = "0:0:0:0:0:0:0:1";

}
