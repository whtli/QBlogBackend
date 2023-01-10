package cn.li98.blog.common.enums;

/**
 * @author: whtli
 * @date: 2023/01/10
 * @description: 访问行为枚举类
 */
public enum VisitBehavior {
    UNKNOWN("UNKNOWN", "UNKNOWN"),
    INDEX("访问页面", "首页"),
    CATEGORY("访问页面", "分类"),
    TAG("访问页面", "标签"),
    ARCHIVE("访问页面", "归档"),
    ABOUT("访问页面", "关于"),
    FRIEND("访问页面", "友链"),
    BLOG("查看博客", ""),
    CATEGORY_DETAIL("查看分类", ""),
    TAG_DETAIL("查看标签", ""),
    SEARCH("搜索博客", ""),
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
