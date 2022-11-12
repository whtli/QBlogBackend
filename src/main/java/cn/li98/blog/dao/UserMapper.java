package cn.li98.blog.dao;

import cn.li98.blog.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

@Repository
@Mapper
public interface UserMapper{
    User getUserById(Long id);

    User getUserByName(String username);
}