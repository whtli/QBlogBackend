package cn.li98.blog.controller.front;

import cn.li98.blog.common.Result;
import cn.li98.blog.annotation.VisitLogger;
import cn.li98.blog.enums.VisitBehavior;
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
 * @description: 前端分类控制层
 */
@RestController
@RequestMapping("/front/category")
public class CategoryFrontController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BlogService blogService;

    /**
     * 获取分类信息
     * @return 分类列表及各分类下的博客数量
     */
    @VisitLogger(VisitBehavior.CATEGORY)
    @GetMapping("/getCategoryDetail")
    public Result getCategoryDetail() {
        List<Category> categoryList = categoryService.list();
        List<Integer> blogCount = new LinkedList<>();
        for (Category category : categoryList) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("category_id", category.getId());
            List<Blog> blogList = blogService.list(queryWrapper);
            int count = 0;
            if (blogList != null) {
                count = blogList.size();
            }
            blogCount.add(count);
        }
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("categoryList", categoryList);
        data.put("blogCount", blogCount);
        return Result.succ("查询成功", data);
    }
}
