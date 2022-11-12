package cn.li98.blog.controllor.admin;

import cn.li98.blog.common.Result;
import cn.li98.blog.model.Blog;
import cn.li98.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @AUTHOR: whtli
 * @DATE: 2022/11/10
 * @DESCRIPTION:
 */
@RestController
@RequestMapping("/admin/blog")
public class BlogControllor {

    @Autowired
    BlogService blogService;

    @GetMapping("/list")
    public Result getBlogListAuto(){
        List<Blog> blogList = new ArrayList<Blog>();
        blogList = blogService.list();
        System.out.println(blogList);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("blogList", blogList);
        return Result.succ(20000, "success", data);
    }
}
