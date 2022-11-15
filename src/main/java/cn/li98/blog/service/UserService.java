package cn.li98.blog.service;

import cn.li98.blog.model.User;

/**
 * @author : whtli
 */
public interface UserService {
    /**
     * 根据ID获取用户信息
     *
     * @param id
     * @return User
     */
    User getUserById(Long id);

    /**
     * 根据名字获取用户信息
     *
     * @param username
     * @return User
     */
    User getByName(String username);
}
