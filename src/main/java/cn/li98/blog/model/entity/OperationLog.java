package cn.li98.blog.model.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: whtli
 * @date: 2022/12/09
 * @description: 操作日志实体类
 */
@Data
@NoArgsConstructor
@TableName("operation_log")
public class OperationLog implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 操作者用户名
     */
    private String username;

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
     * 操作描述
     */
    private String description;

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

    public OperationLog(String username, String description, String uri, String method, String userAgent, String ip, String ipSource, int times, String param, String os, String browser) {
        this.username = username;
        this.description = description;
        this.uri = uri;
        this.method = method;
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