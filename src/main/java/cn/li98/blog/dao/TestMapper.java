package cn.li98.blog.dao;

import cn.li98.blog.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
@Mapper
public interface TestMapper {
    List<User> getInfo();
}
