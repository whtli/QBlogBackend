package cn.li98.blog.model.dto;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author: whtli
 * @date: 2022/11/10
 * @description:
 */
@Data
public class LoginDTO implements Serializable {
    @NotBlank(message = "昵称不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

}
