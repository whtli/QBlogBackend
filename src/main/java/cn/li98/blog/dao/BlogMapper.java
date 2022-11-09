package cn.li98.blog.dao;

import cn.li98.blog.model.Blog;
import cn.li98.blog.model.BlogWithBLOBs;

public interface BlogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BlogWithBLOBs record);

    int insertSelective(BlogWithBLOBs record);

    BlogWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BlogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(BlogWithBLOBs record);

    int updateByPrimaryKey(Blog record);
}