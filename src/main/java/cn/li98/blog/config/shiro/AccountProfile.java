package cn.li98.blog.config.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * @AUTHOR: whtli
 * @DATE: 2022/11/09
 * @DESCRIPTION:
 */

@Data
public class AccountProfile implements Serializable {

    private Long id;

    private String username;

    private String avatar;

    private String email;

}