package cn.li98.blog.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: whtli
 * @date: 2022/12/17
 * @description: 字典实体类
 */
@TableName("sys_dict")
@Data
public class Dict implements Serializable {
    /**
     * 名称
     */
    private String name;

    /**
     * 内容
     */
    private String value;

    /**
     * 类型
     */
    private String type;

    private static final long serialVersionUID = 1L;
}