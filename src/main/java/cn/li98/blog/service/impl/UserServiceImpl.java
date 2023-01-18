package cn.li98.blog.service.impl;

import cn.hutool.crypto.SecureUtil;
import cn.li98.blog.dao.UserMapper;
import cn.li98.blog.model.entity.User;
import cn.li98.blog.model.dto.LoginDTO;
import cn.li98.blog.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: whtli
 * @date: 2022/11/10
 * @description: 用户相关功能业务实现层
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserById(Long id) {
        return userMapper.getUserById(id);
    }

    @Override
    public User login(LoginDTO loginDTO) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username", loginDTO.getUsername());
        // 密码通过md5加密后再判断
        String md5Password = SecureUtil.md5(loginDTO.getPassword());
        wrapper.eq("password", md5Password);
        User one = getOne(wrapper);
        if (one != null) {
            return one;
        }
        return null;
    }
}
