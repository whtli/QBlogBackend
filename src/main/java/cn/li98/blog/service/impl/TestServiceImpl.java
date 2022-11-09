package cn.li98.blog.service.impl;

import cn.li98.blog.dao.TestMapper;
import cn.li98.blog.dao.UserMapper;
import cn.li98.blog.model.User;
import cn.li98.blog.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author : whtli
 */
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getInfo() {
        return userMapper.getInfo();
    }
}
