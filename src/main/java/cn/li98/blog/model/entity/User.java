package cn.li98.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author: whtli
 * @date: 2022/11/09
 * @description: User
 */
@Data
@Accessors(chain = true)
@TableName("user")
public class User implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "昵称不能为空")
    private String username;

    // @JsonIgnore
    private String password;

    private String nickname;

    private String avatar;
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    private Date createTime;

    private Date updateTime;

    private String role;

    @TableField(exist = false)
    private List<Menu> menuList;

    private static final long serialVersionUID = 1L;

}