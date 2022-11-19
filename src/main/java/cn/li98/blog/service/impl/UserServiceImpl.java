package cn.li98.blog.service.impl;

import cn.li98.blog.dao.UserMapper;
import cn.li98.blog.model.User;
import cn.li98.blog.model.dto.LoginDTO;
import cn.li98.blog.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @AUTHOR: whtli
 * @DATE: 2022/11/10
 * @DESCRIPTION:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
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

    @Override
    public User login(LoginDTO loginDTO) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username", loginDTO.getUsername());
        wrapper.eq("password", loginDTO.getPassword()); // TODO：加密判断
        User one = getOne(wrapper);
        if (one != null) {
            return one;
        }
        return null;
    }
}
