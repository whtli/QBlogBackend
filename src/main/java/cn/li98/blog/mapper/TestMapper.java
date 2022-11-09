package cn.li98.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;


@Repository
@Mapper
public interface TestMapper {
    Map<String, String> getInfo();
}
