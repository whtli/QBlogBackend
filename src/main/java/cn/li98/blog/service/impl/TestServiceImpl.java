package com.demo.service.impl;

import com.demo.mapper.TestMapper;
import com.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author : whtli
 */
@Service
public class TestServiceImpl implements TestService {
    @Autowired
//    TestMapper testMapper;

    @Override
    public Map<String, String> getInfo() {
//        return testMapper.getInfo();
        return null;
    }
}
