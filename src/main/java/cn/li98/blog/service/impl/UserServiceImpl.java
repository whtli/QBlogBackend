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
 * @author: whtli
 * @date: 2022/11/10
 * @description:
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
        // TODO：加密判断
        wrapper.eq("password", loginDTO.getPassword());
        User one = getOne(wrapper);
        /*if (!SecureUtil.md5(user.getPassword()).equals(SecureUtil.md5(loginDTO.getPassword()))) {
            return Result.fail("密码不正确");
        }*/
        if (one != null) {
            return one;
        }
        return null;
    }
}
