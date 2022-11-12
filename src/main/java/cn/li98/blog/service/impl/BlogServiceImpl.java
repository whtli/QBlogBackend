package cn.li98.blog.service.impl;

import cn.li98.blog.dao.BlogMapper;
import cn.li98.blog.model.Blog;
import cn.li98.blog.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @AUTHOR: whtli
 * @DATE: 2022/11/10
 * @DESCRIPTION:
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
