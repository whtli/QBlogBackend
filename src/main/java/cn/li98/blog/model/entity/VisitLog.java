package cn.li98.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: whtli
 * @date: 2023/01/09
 * @description: 访问日志实体类
 */
@Data
@NoArgsConstructor
@TableName("visit_log")
public class VisitLog {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 访客标识码
     */
    private String uuid;

    /**
     * 请求接口
     */
    private String uri;

    /**
     * 请求方式
     */
    private String method;

    /**
     * 请求参数
     */
    private String param;

    /**
     * 访问行为
     */
    private String behavior;

    /**
     * 访问内容
     */
    private String content;

    /**
     * 备注
     */
    private String remark;

    /**
     * ip
     */
    private String ip;

    /**
     * ip来源
     */
    private String ipSource;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 请求耗时（毫秒）
     */
    private Integer times;

    /**
     * 操作时间
     */
    private Date createTime;

    /**
     * user-agent用户代理
     */
    private String userAgent;

    private static final long serialVersionUID = 1L;

    public VisitLog(String uuid, String uri, String method, String behavior, String content, String remark, String userAgent, String ip, String ipSource, int times, String param, String os, String browser) {
        this.uuid = uuid;
        this.uri = uri;
        this.method = method;
        this.behavior = behavior;
        this.content = content;
        this.remark = remark;
        this.userAgent = userAgent;
        this.ip = ip;
        this.ipSource = ipSource;
        this.times = times;
        this.param = param;
        this.os = os;
        this.browser = browser;
        this.createTime = new Date();
    }
}
