package cn.li98.blog.dao;

import cn.li98.blog.model.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogMapper extends BaseMapper<Blog> {

}