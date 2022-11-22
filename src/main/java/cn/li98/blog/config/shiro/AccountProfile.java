package cn.li98.blog.config.shiro;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @AUTHOR: whtli
 * @DATE: 2022/11/09
 * @DESCRIPTION:
 */

@Data
public class AccountProfile implements Serializable {

    private Long id;

    private String username;

    private String nickname;

    private String avatar;

    private String email;

    private Date createTime;

    private Date updateTime;

    private String role;
}