package cn.li98.blog.controller.guest;

import cn.li98.blog.common.Constant;
import cn.li98.blog.common.Result;
import cn.li98.blog.common.annotation.OperationLogger;
import cn.li98.blog.model.Blog;
import cn.li98.blog.model.Category;
import cn.li98.blog.model.dto.BlogDisplayDTO;
import cn.li98.blog.service.BlogService;
import cn.li98.blog.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

/**
 * @author: whtli
 * @date: 2022/12/13
 * @description: 访客访问博客的接口
 */
@Slf4j
@RestController
@RequestMapping("/admin/guest")
public class BlogGuestController {
    @Autowired
    BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取访客可见的博客列表
     *
     * @param title      博客标题
     * @param categoryId 博客分类id
     * @return 成功则Map作为data
     */
    @OperationLogger("获取博客列表")
    @GetMapping("/getBlogList")
    public Result getBlogList(@RequestParam(value = "title", defaultValue = "") String title,
                              @RequestParam(value = "categoryId", defaultValue = "") Long categoryId) {
        /*
        // 1. 尝试从redis缓存中获取指定键值对应的数据
        Map<Object, Object> data = redisTemplate.opsForHash().entries(Constant.GUEST_BLOG_KEY + "_" + pageNum);
        // 2. 如果redis中无对应的数据
        if (data.size() == 0) {
            // 3. 从数据库取出数据
            QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
            // 3.1 将查询参数以键值对的形式存放到QueryWrapper中
            if (!StringUtils.isEmpty(title) && !StringUtils.isBlank(title)) {
                queryWrapper.like("title", title);
            }
            if (categoryId != null) {
                queryWrapper.eq("category_id", categoryId);
            }
            // 3.2 博客的可见性需指定为“可见”，用户只能看到公开的博客
            queryWrapper.eq("published", true);
            // 3.3 根据创建时间查询逆序的列表结果，越新发布的博客越容易被看到
            queryWrapper.orderByDesc("create_time");
            // 新建一个分页规则，pageNum代表当前页码，pageSize代表每页数量
            Page page = new Page(pageNum, 10);
            // 借助Page实现分页查询，借助QueryWrapper实现多参数查询
            IPage pageData = blogService.page(page, queryWrapper);
            if (pageData.getTotal() == 0 && pageData.getRecords().isEmpty()) {
                return Result.fail("查询失败，未查找到相应博客");
            }

            data.put("pageData", pageData);
            data.put("total", pageData.getTotal());
            // 4. 缓存到redis
            redisTemplate.opsForHash().putAll(Constant.GUEST_BLOG_KEY + "_" + pageNum, data);
        }
        return Result.succ("查询成功", data);
        */

        List<BlogDisplayDTO> blogDisplayList = new LinkedList<>();
        // 1. 尝试从redis缓存中获取指定键值对应的数据
        List<Blog> list = redisTemplate.opsForList().range(Constant.GUEST_BLOG_KEY, 0, -1);
        // 2. 如果redis中无对应的数据
        if (list.isEmpty()) {
            // 3. 从数据库取出数据
            QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
            // 3.1 将查询参数以键值对的形式存放到QueryWrapper中
            if (!StringUtils.isEmpty(title) && !StringUtils.isBlank(title)) {
                queryWrapper.like("title", title);
            }
            if (categoryId != null) {
                queryWrapper.eq("category_id", categoryId);
            }
            // 3.2 博客的可见性需指定为“可见”，用户只能看到公开的博客
            queryWrapper.eq("published", true);
            // 3.3 根据创建时间查询逆序的列表结果，越新发布的博客越容易被看到
            queryWrapper.orderByDesc("create_time");
            // 3.4 查询符合条件的博客列表
            List<Blog> blogList = blogService.list(queryWrapper);
            if (blogList.size() == 0) {
                return Result.fail("查询失败，未查找到相应博客");
            }
            List<Category> categoryList = categoryService.list();

            for (Blog blog : blogList) {
                BlogDisplayDTO item = new BlogDisplayDTO();
                BeanUtils.copyProperties(blog, item);
                for (Category category : categoryList){
                    if (blog.getCategoryId() == category.getId()) {
                        item.setCategoryName(category.getCategoryName());
                        blogDisplayList.add(item);
                    }
                }
            }
            // 4. 缓存到redis
            redisTemplate.opsForList().rightPush(Constant.GUEST_BLOG_KEY, blogDisplayList);
        } else {
            blogDisplayList = (List<BlogDisplayDTO>) list.get(0);
        }
        return Result.succ("查询成功", blogDisplayList);
    }
}
