package cn.li98.blog.controller.front;

import cn.li98.blog.common.Result;
import cn.li98.blog.model.entity.Blog;
import cn.li98.blog.model.entity.Category;
import cn.li98.blog.model.entity.Tag;
import cn.li98.blog.service.BlogService;
import cn.li98.blog.service.CategoryService;
import cn.li98.blog.service.TagService;
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
 * @description: 前端标签控制层
 */
@RestController
@RequestMapping("/front/tag")
public class TagFrontController {
    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/getTagDetail")
    public Result getTagDetail() {
        List<Tag> tagList = tagService.list();
        List<Integer> blogCount = new LinkedList<>();
        for (Tag tag : tagList) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("tag_id", tag.getId());
            List<Blog> blogList = tagService.getBlogsByTagId(tag.getId());
            int count = 0;
            if (blogList != null) {
                count = blogList.size();
            }
            blogCount.add(count);
        }
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("tagList", tagList);
        data.put("blogCount", blogCount);
        return Result.succ("查询成功", data);
    }
}
