package cn.li98.blog.service.impl;

import cn.li98.blog.dao.UserMapper;
import cn.li98.blog.model.User;
import cn.li98.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : whtli
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User getUserById(Long id) {
        return userMapper.getUserById(id);
    }

    @Override
    public User getByName(String username) {
        return userMapper.getUserByName(username);
    }
}
