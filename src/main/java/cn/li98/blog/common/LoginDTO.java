package cn.li98.blog.common;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @AUTHOR: whtli
 * @DATE: 2022/11/10
 * @DESCRIPTION:
 */
@Data
public class LoginDTO implements Serializable {
    @NotBlank(message = "昵称不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

}
