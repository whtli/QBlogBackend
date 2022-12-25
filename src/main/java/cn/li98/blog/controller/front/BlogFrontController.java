package cn.li98.blog.controller.front;

import cn.hutool.core.lang.Assert;
import cn.li98.blog.common.Constant;
import cn.li98.blog.common.Result;
import cn.li98.blog.common.annotation.OperationLogger;
import cn.li98.blog.model.entity.Blog;
import cn.li98.blog.model.entity.Category;
import cn.li98.blog.model.entity.Tag;
import cn.li98.blog.service.BlogService;
import cn.li98.blog.service.CategoryService;
import cn.li98.blog.service.TagService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: whtli
 * @date: 2022/12/13
 * @description: 访客访问博客的接口
 */
@Slf4j
@RestController
@RequestMapping("/front/blog")
public class BlogFrontController {
    @Autowired
    BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取访客可见的博客列表
     *
     * @param pageNum  页码
     * @param pageSize 页内数量
     * @return 访客可见的博客列表
     */
    @GetMapping("/getBlogList")
    public Result getBlogList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                              @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Map<String, Object> data = new HashMap<>(2);
        // 1. 尝试从redis缓存中获取指定键值对应的数据
        List<Blog> list = redisTemplate.opsForList().range(Constant.GUEST_BLOG_KEY + "_" + pageNum, 0, -1);
        // 2. 如果redis中无对应的数据
        if (list.isEmpty()) {
            // 3. 从数据库取出数据
            // 3.1 将查询参数以键值对的形式存放到QueryWrapper
            QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
            // 3.2 博客的可见性需指定为“可见”，用户只能看到公开的博客
            queryWrapper.eq("published", true);
            // 3.3 根据创建时间查询逆序的列表结果，越新发布的博客越容易被看到
            queryWrapper.orderByDesc("create_time");
            // 3.4 查询符合条件的博客列表
            Page page = new Page(pageNum, pageSize);
            IPage pageData = blogService.page(page, queryWrapper);
            if (pageData.getTotal() == 0 && pageData.getRecords().isEmpty()) {
                data.put("pageData", pageData);
                data.put("total", pageData.getTotal());
                return Result.succ("未查找到相应博客", data);
            }
            list = pageData.getRecords();
            // 3.5 处理博客列表
            List<Category> categoryList = categoryService.list();
            for (int i = 0; i < list.size(); i++) {
                for (Category category : categoryList) {
                    if (list.get(i).getCategoryId().equals(category.getId())) {
                        list.get(i).setCategoryName(category.getCategoryName());
                    }
                }
                List<Tag> tagList = tagService.getTagsByBlogId(list.get(i).getId());
                list.get(i).setTagList(tagList);
            }
            pageData.setRecords(list);
            // 4. 缓存到redis
            redisTemplate.opsForList().rightPush(Constant.GUEST_BLOG_KEY + "_" + pageNum, list);
            redisTemplate.opsForValue().set("PAGE_NUMBER_OF_PUBLISHED_BLOGS", pageData.getTotal());
            // 5. 返回给前端
            data.put("blogList", list);
            data.put("total", pageData.getTotal());
            return Result.succ("查询成功", data);
        } else if (!list.isEmpty()) {
            list = (List<Blog>) list.get(0);
            // 将查询结果填充到Map中
            data.put("blogList", list);
            data.put("total", redisTemplate.opsForValue().get("PAGE_NUMBER_OF_PUBLISHED_BLOGS"));
            return Result.succ("查询成功", data);
        }
        return Result.fail("查询失败");
    }

    /**
     * 获取所有分类和标签以供前端选择使用
     *
     * @return 所有分类和所有标签
     */
    @GetMapping("/getCategoryAndTag")
    public Result getCategoryAndTag() {
        List<Category> categoryList = categoryService.list();
        List<Tag> tagList = tagService.list();

        Map<String, Object> data = new HashMap<>(2);
        data.put("categoryList", categoryList);
        data.put("tagList", tagList);

        return Result.succ(data);
    }

    /**
     * 根据指定的唯一id查询对应的博客、博客所属的分类、博客拥有的标签
     *
     * @param blogId 博客id（唯一）
     * @return Result
     */
    @GetMapping("/getBlogInfoById")
    public Result getBlogInfoById(@RequestParam Long blogId) {
        // 查询博客
        Blog blog = blogService.getById(blogId);
        Assert.notNull(blog, "该博客不存在");
        // 查询所属分类
        Category category = categoryService.getById(blog.getCategoryId());
        // 查询拥有的标签
        List<Tag> tagList = tagService.getTagsByBlogId(blogId);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("blog", blog);
        data.put("category", category);
        data.put("tagList", tagList);
        return Result.succ("查询成功", data);
    }

    /**
     * 根据分类id查询博客列表
     *
     * @param categoryId 分类id
     * @return 指定分类中的博客列表
     */
    @GetMapping("/getBlogByCategoryId")
    public Result getBlogByCategoryId(@RequestParam Long categoryId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("category_id", categoryId);
        queryWrapper.orderByDesc("create_time");
        // 查询博客
        List<Blog> blogList = blogService.list(queryWrapper);
        // 查询分类名
        Category category = categoryService.getById(categoryId);
        String categoryName = category.getCategoryName();
        for (int i = 0; i < blogList.size(); i ++) {
            blogList.get(i).setCategoryName(categoryName);
            List<Tag> tagList = tagService.getTagsByBlogId(blogList.get(i).getId());
            blogList.get(i).setTagList(tagList);
        }

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("blogList", blogList);
        data.put("total", blogList.size());
        data.put("categoryName", categoryName);

        return Result.succ("查询成功", data);
    }

    /**
     * 根据标签id查询博客列表
     *
     * @param tagId 分类id
     * @return 指定分类中的博客列表
     */
    @GetMapping("/getBlogByTagId")
    public Result getBlogByTagId(@RequestParam Long tagId) {
        // 查询博客id列表
        List<Blog> blogList = tagService.getBlogsByTagId(tagId);
        // 查询标签名
        Tag tag = tagService.getById(tagId);
        String tagName = tag.getTagName();

        List<Category> categoryList = categoryService.list();
        for (int i = 0; i < blogList.size(); i ++) {
            for (Category category : categoryList) {
                if (blogList.get(i).getCategoryId().equals(category.getId())) {
                    blogList.get(i).setCategoryName(category.getCategoryName());
                }
            }            List<Tag> tagList = tagService.getTagsByBlogId(blogList.get(i).getId());
            blogList.get(i).setTagList(tagList);
        }

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("blogList", blogList);
        data.put("total", blogList.size());
        data.put("tagName", tagName);

        return Result.succ("查询成功", data);
    }
}

