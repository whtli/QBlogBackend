package cn.li98.blog.service.impl;

import cn.li98.blog.dao.TestMapper;
import cn.li98.blog.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author : whtli
 */
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    TestMapper testMapper;

    @Override
    public Map<String, String> getInfo() {
        return testMapper.getInfo();
    }
}
