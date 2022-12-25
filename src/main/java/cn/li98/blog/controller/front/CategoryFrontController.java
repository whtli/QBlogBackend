package cn.li98.blog.controller.front;

import cn.li98.blog.common.Result;
import cn.li98.blog.model.entity.Blog;
import cn.li98.blog.model.entity.Category;
import cn.li98.blog.service.BlogService;
import cn.li98.blog.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author: whtli
 * @date: 2022/12/24
 * @description: 前端分类接口
 */
@RestController
@RequestMapping("/front/category")
public class CategoryFrontController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/getCategoryDetail")
    public Result getCategoryDetail() {
        List<Category> categoryList = categoryService.list();
        List<Integer> blogCount = new LinkedList<>();
        for (Category category : categoryList) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("category_id", category.getId());
            List<Blog> blogList = blogService.list(queryWrapper);
            blogCount.add(blogList.size());
        }
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("categoryList", categoryList);
        data.put("blogCount", blogCount);
        return Result.succ("查询成功", data);
    }
}
