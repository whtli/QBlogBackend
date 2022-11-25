package cn.li98.blog.controllor.admin;

import cn.hutool.core.lang.Assert;
import cn.li98.blog.common.Result;
import cn.li98.blog.model.Blog;
import cn.li98.blog.service.BlogService;
import cn.li98.blog.utils.QiniuUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @author: whtli
 * @date: 2022/11/10
 * @description:
 */
@Slf4j
@RestController
@RequestMapping("/admin/blog")
public class BlogControllor {
    @Autowired
    BlogService blogService;

    @Autowired
    QiniuUtils qiniuUtils;

    /**
     * 向七牛云服务器中上传一张图片
     *
     * @param multipartFile
     * @return 上传成功后返回的图片地址作为data
     */
    @PostMapping("/addImage")
    public Result addImage(@RequestParam(value = "image") MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            log.error("未选择图片");
            return Result.fail("请选择图片");
        }
        log.info("准备上传到七牛云");
        Map<String, String> uploadImagesUrl = qiniuUtils.uploadImage(multipartFile);
        log.info("成功返回图像地址 : " + uploadImagesUrl.get("imageUrl"));
        return Result.succ("上传成功", uploadImagesUrl);
    }

    /**
     * 删除七牛云中的指定图片
     *
     * @param url
     * @return 图片名（带路径）作为data
     */
    @PostMapping("/deleteImg")
    public Result deleteImg(@RequestHeader("Img-Delete") String url) {
        if (url.isEmpty()) {
            return Result.fail("无此图片");
        }
        //删除云服务器图片
        boolean flag = qiniuUtils.delete(url);
        String filename = url.split("com/")[1];
        if (flag) {
            log.info("图片删除成功: " + filename);
            return Result.succ("图片删除成功", filename);
        }
        log.error("图片删除失败 : " + filename);
        return Result.fail("图片删除失败", filename);
    }

    /**
     * 创建、修改博客
     *
     * @param blog
     * @return 成功则"发布成功"作为data
     */
    @PostMapping("/submitBlog")
    public Result submitBlog(@Validated @RequestBody Blog blog) {
        // 验证字段
        if (StringUtils.isEmpty(blog.getTitle()) || StringUtils.isEmpty(blog.getDescription()) || StringUtils.isEmpty(blog.getContent()) || blog.getWords() == null || blog.getWords() < 0) {
            return Result.fail("参数有误");
        }
        int flag = 0;
        try {
            if (blog.getId() == null) {
                flag = blogService.createBlog(blog);
            } else {
                flag = blogService.updateBlog(blog);
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        if (flag == 1) {
            return Result.succ("发布成功");
        }
        return Result.fail("失败");
    }

    /**
     * 删除博客，逻辑删除，对应字段deleted
     * 删除操作变为修改deleted字段的操作
     * 1为逻辑删除，0（数据库字段默认值）为未删除
     *
     * @param id
     * @return 被逻辑删除的博客id作为data
     */
    @DeleteMapping("/deleteBlogById")
    public Result deleteBlogById(@RequestParam Long id) {
        log.info("blog to delete : " + id);
        boolean delete = blogService.removeById(id);
        System.out.println("delete: " + delete);
        if (delete) {
            return Result.succ("博客删除成功", id);
        } else {
            return Result.fail("博客删除失败", id);
        }
    }


    /**
     * 批量删除博客，逻辑删除
     *
     * @param ids
     * @return 被逻辑删除的多个博客id列表
     */
    @DeleteMapping("/deleteBlogBatchByIds")
    public Result deleteBlogBatchByIds(@RequestParam String ids) {
        String[] list = ids.split(",");
        List<Long> idList = new ArrayList<>();
        for (String id : list) {
            idList.add(Long.valueOf(id));
        }
        int deletedBlogCount = 0;
        for (Long id : idList) {
            if (deleteBlogById(id).getCode() == 200) {
                deletedBlogCount++;
            } else {
                Result.fail("ID为 " + id + " 的博客删除失败，后续删除停止", id);
            }
        }
        if (deletedBlogCount == idList.size()) {
            return Result.succ("批量删除成功", idList);
        }
        return Result.fail("批量删除失败");
    }

    /**
     * 修改操作对应的根据指定id查询博客的接口
     * 可以根据指定的唯一id查询对应的博客
     *
     * @param id
     * @return 成功则Blog作为data
     */
    @GetMapping("/getBlogById")
    public Result getBlogById(@RequestParam Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客不存在");
        return Result.succ("查询成功", blog);
    }

    /**
     * 获取博客列表
     * 可以实现无参数查询和多参数查询
     * 可以实现分页查询
     * 将分页列表和总记录数作为键值对存储到Map中
     * 分页列表用于前端的当前页展示
     * 总记录数用于前端展示博客总数，这个数值是当前数据库中未被删除的博客总数，是所有分页中的博客个数的和
     *
     * @param title
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @return 成功则Map作为data
     */
    @GetMapping("/getBlogs")
    public Result getBlogs(@RequestParam(value = "title", defaultValue = "") String title,
                           @RequestParam(value = "categoryId", defaultValue = "") Long categoryId,
                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        // 将查询参数以键值对的形式存放到QueryWrapper中
        if (!StringUtils.isEmpty(title) && !StringUtils.isBlank(title)) {
            queryWrapper.like("title", title);
        }
        if (categoryId != null) {
            queryWrapper.eq("category_id", categoryId);
        }
        // 根据创建时间查询逆序的列表结果，越新发布的博客越容易被看到
        queryWrapper.orderByDesc("create_time");
        // 新建一个分页规则，pageNum代表当前页码，pageSize代表每页数量
        Page page = new Page(pageNum, pageSize);
        // 借助Page实现分页查询，借助QueryWrapper实现多参数查询
        IPage pageData = blogService.page(page, queryWrapper);
        if (pageData.getTotal() == 0 && pageData.getRecords().isEmpty()) {
            return Result.fail("查询失败，未查找到相应博客");
        }
        Map<String, Object> data = new HashMap<>(2);
        data.put("pageData", pageData);
        data.put("total", pageData.getTotal());
        return Result.succ("查询成功", data);
    }
}
