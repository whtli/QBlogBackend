package cn.li98.blog.controllor.admin;

import cn.li98.blog.common.Result;
import cn.li98.blog.model.Blog;
import cn.li98.blog.service.BlogService;
import cn.li98.blog.utils.QiniuUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @AUTHOR: whtli
 * @DATE: 2022/11/10
 * @DESCRIPTION:
 */
@Slf4j
@RestController
@RequestMapping("/admin/blog")
public class BlogControllor {
    private static final String CREATE = "create";
    private static final String UPDATE = "update";
    @Autowired
    BlogService blogService;

    @Autowired
    QiniuUtils qiniuUtils;

    @GetMapping("/list")
    public Result getBlogList() {
        List<Blog> blogList = new ArrayList<Blog>();
        blogList = blogService.list();
        System.out.println(blogList);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("blogList", blogList);
        return Result.succ(20000, "success", data);
    }

    @PostMapping("/addImage")
    public Result addImage(@RequestParam(value = "image") MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            log.error("未选择图片");
            return Result.fail("请选择图片");
        }
        log.info("准备上传到七牛云");
        Map<String, String> uploadImagesUrl = qiniuUtils.uploadImage(multipartFile);
        log.info("成功返回图像地址 : " + uploadImagesUrl.get("imageUrl"));
        return Result.succ(20000, "上传成功", uploadImagesUrl);
    }

    @PostMapping("/deleteImg")
    public Result deleteImg(@RequestHeader("Img-Delete") String url) {
        if (url.isEmpty()) {
            return Result.fail("无此图片");
        }
        //删除云服务器文件
        boolean flag = qiniuUtils.delete(url);
        String filename = url.split("com/")[1];
        if (flag) {
            log.info("图片删除成功: " + filename);
            return Result.succ(20000, "图片删除成功", filename);
        }
        log.error("图片删除失败 : " + filename);
        return Result.fail("图片删除失败", filename);
    }

    @PostMapping("/submitBlog")
    public Result submitBlog(@RequestBody Blog blog) {
        return submit(blog, "create");
    }

    private Result submit(Blog blog, String type) {
        // 验证字段
        if (StringUtils.isEmpty(blog.getTitle()) || StringUtils.isEmpty(blog.getDescription()) || StringUtils.isEmpty(blog.getContent()) || blog.getWords() == null || blog.getWords() < 0) {
            return Result.fail("参数有误");
        }
        Object category = blog.getCategoryId();
        if (category == null) {
            blog.setCategoryId(1L);
        }
        // TODO: 分类、标签等功能判断新增等功能

        if (blog.getReadTime() == null || blog.getReadTime() < 0) {
            // 粗略计算阅读时长
            blog.setReadTime((int) Math.round(blog.getWords() / 200.0));
        }
        if (blog.getViews() == null || blog.getViews() < 0) {
            blog.setViews(0);
        }
        Date date = new Date();
        if (CREATE.equals(type)) {
            blog.setCreateTime(date);
            blog.setUpdateTime(date);
            blog.setUserId(1L);
            try {
                blogService.createBlog(blog);
            } catch (Exception e) {
                log.error(e.toString());
            }
            return Result.succ("发布成功");
        } else if (UPDATE.equals(type)) {
            blog.setUpdateTime(date);
            try {
                blogService.updateBlog(blog);
            } catch (Exception e) {
                log.error(e.toString());
            }
            return Result.succ("更新成功");
        } else {
            return Result.fail("失败");
        }
    }
}
