package cn.li98.blog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @AUTHOR: whtli
 * @DATE: 2022/11/28
 * @DESCRIPTION:
 */
@Data
@TableName("category")
public class Category {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "分类名不能为空")
    String categoryName;
}
