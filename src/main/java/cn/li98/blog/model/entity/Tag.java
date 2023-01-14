package cn.li98.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * @author: whtli
 * @date: 2022/11/29
 * @description: 博客标签实体类
 */
@Data
@TableName("tag")
public class Tag implements Serializable {
	/**
	 * 标签id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * 标签名称
	 */
	private String tagName;
	/**
	 * 标签颜色(与Semantic UI提供的颜色对应，可选)
	 */
	private String color;

	private static final long serialVersionUID = 1L;

}
