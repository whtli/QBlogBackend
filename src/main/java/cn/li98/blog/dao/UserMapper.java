package cn.li98.blog.dao;

import cn.li98.blog.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @AUTHOR: whtli
 * @DATE: 2022/11/09
 * @DESCRIPTION: UserMapper
 */
@Repository
@Mapper
public interface UserMapper {
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