package cn.li98.blog.dao;

import cn.li98.blog.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author: whtli
 * @date: 2022/11/09
 * @description: 用户持久层
 */
public interface UserMapper extends BaseMapper<User> {
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
    User getUserByName(String username);
}