package cn.li98.blog.dao;

import cn.li98.blog.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author: whtli
 * @date: 2022/11/09
 * @description: UserMapper
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