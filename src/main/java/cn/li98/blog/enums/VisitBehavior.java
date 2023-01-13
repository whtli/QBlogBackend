package cn.li98.blog.enums;

/**
 * @author: whtli
 * @date: 2023/01/10
 * @description: 访问行为枚举类
 */
public enum VisitBehavior {
    /**
     * UNKNOWN
     */
    UNKNOWN("UNKNOWN", "UNKNOWN"),
    /**
     * 访问页面-首页
     */
    INDEX("访问页面", "首页"),
    /**
     * 访问页面-分类
     */
    CATEGORY("访问页面", "分类"),
    /**
     * 访问页面-标签
     */
    TAG("访问页面", "标签"),
    /**
     * 访问页面-归档
     */
    ARCHIVE("访问页面", "归档"),
    /**
     * 访问页面-关于
     */
    ABOUT("访问页面", "关于"),
    /**
     * 访问页面-友链
     */
    FRIEND("访问页面", "友链"),
    /**
     * 查看博客
     */
    BLOG("查看博客", ""),
    /**
     * 查看分类
     */
    CATEGORY_DETAIL("查看分类", ""),
    /**
     * 查看标签
     */
    TAG_DETAIL("查看标签", ""),
    /**
     * 搜索博客
     */
    SEARCH("搜索博客", ""),
    /**
     * 点击友链
     */
    CLICK_FRIEND("点击友链", "");

    /**
     * 访问行为
     */
    private String behavior;
    /**
     * 访问内容
     */
    private String content;

    VisitBehavior(String behavior, String content) {
        this.behavior = behavior;
        this.content = content;
    }

    public String getBehavior() {
        return behavior;
    }

    public String getContent() {
        return content;
    }
}
