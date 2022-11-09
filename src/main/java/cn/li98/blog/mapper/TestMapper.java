package com.demo.mapper;

import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;


@Repository
@Mapper
public interface TestMapper {
    Map<String, String> getInfo();
}
