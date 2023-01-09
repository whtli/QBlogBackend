package cn.li98.blog.controller.admin;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.li98.blog.common.Constant;
import cn.li98.blog.common.Result;
import cn.li98.blog.common.annotation.OperationLogger;
import cn.li98.blog.model.entity.Blog;
import cn.li98.blog.model.entity.Category;
import cn.li98.blog.model.entity.Tag;
import cn.li98.blog.model.dto.BlogWriteDTO;
import cn.li98.blog.service.BlogService;
import cn.li98.blog.service.CategoryService;
import cn.li98.blog.service.TagService;
import cn.li98.blog.utils.QiniuUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;

/**
 * @author: whtli
 * @date: 2022/11/10
 * @description: 博客控制层
 */
@Slf4j
@RestController
@RequestMapping("/admin/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private QiniuUtils qiniuUtils;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 向七牛云服务器中上传一张图片
     *
     * @param multipartFile 图片文件
     * @return 上传成功后返回的图片地址作为data
     */
    @OperationLogger("向七牛云服务器中上传图片")
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
     * @param url 图片在七牛云中的url（回显在前端）
     * @return 图片名（带路径）作为data
     */
    @OperationLogger("删除七牛云中的指定图片")
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
     * @param form BlogWriteDTO实体类，包含博客和Tag列表两个属性
     * @return 成功则"发布成功"作为data
     */
    @OperationLogger("创建/修改博客")
    @PostMapping("/submitBlog")
    public Result submitBlog(@RequestBody BlogWriteDTO form) {
        Blog blog = form.getBlog();
        List<Object> tags = form.getTags();
        // tagList是遍历前端发送的所有标签并根据类型进行转换处理之后真正要使用的标签列表
        List<Tag> tagList = new ArrayList<>();
        for (Object t : tags) {
            if (t instanceof Integer) {
                // 选择了已存在的标签
                Tag tag = tagService.getById(((Integer) t).longValue());
                tagList.add(tag);
            } else if (t instanceof String) {
                // 直接输入的标签名，此时需要判断标签是否已存在
                // 查询标签是否已存在
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.eq("tag_name", (String) t);
                if (tagService.getOne(wrapper) != null) {
                    return Result.fail("不可新增已存在的标签");
                }
                // 不存在则添加新标签
                Tag tag = new Tag();
                tag.setTagName((String) t);
                tagService.createTag(tag);
                tagList.add(tag);
            } else {
                return Result.fail("标签不正确");
            }
        }

        // 验证字段
        if (StrUtil.isEmpty(blog.getTitle()) || StrUtil.isEmpty(blog.getDescription()) || StrUtil.isEmpty(blog.getContent())) {
            return Result.fail("参数有误");
        }
        int flag = 0;
        int tagCount = 0;
        try {
            if (blog.getId() == null) {
                flag = blogService.createBlog(blog);
            } else {
                flag = blogService.updateBlog(blog);
            }
            // 关联博客和标签(维护blog_tag表)，博客与tagList中的所有标签是一对多的关系
            for (Tag t : tagList) {
                tagCount += tagService.saveBlogTag(blog.getId(), t.getId());
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        if (flag == 1 && tagCount == tagList.size()) {
            flushRedis();
            return Result.succ("博客发布成功");
        }
        return Result.fail("博客发布失败");
    }

    /**
     * 博客可见性更改
     *
     * @param blogId 博客id
     * @return Result
     */
    @OperationLogger("更新博客可见性状态")
    @PostMapping("/changeBlogStatusById")
    public Result changeBlogStatusById(@RequestParam Long blogId) {
        int res = blogService.changeBlogStatusById(blogId);
        if (res == 1) {
            // 修改成功后清空redis中的博客缓存
            flushRedis();
            return Result.succ("博客可见性更改成功", res);
        }
        return Result.fail("博客可见性更改失败", res);
    }

    /**
     * 导入博客到数据库
     *
     * @param file Markdown博客文件或Excel文件
     * @return Result
     * @throws IOException    IO异常
     * @throws ParseException 时间转换异常
     */
    @OperationLogger("导入博客到数据库")
    @PostMapping("/uploadBlog")
    public Result uploadBlog(@RequestParam MultipartFile file) throws IOException, ParseException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);

        if (Constant.FILE_TYPE_MD.equals(type)) {
            // 是md文件，需要把规定格式的博客内容读取并拆分处理之后得到博客类对象才可以插入到数据库
            Blog blog = blogService.fileToBlog(file);
            int flag = blogService.createBlog(blog);
            if (flag == 1) {
                flushRedis();
                return Result.succ(originalFilename + " 导入成功", blog);
            }
        } else if (Constant.FILE_TYPE_XLSX.equals(type) || Constant.FILE_TYPE_XLS.equals(type)) {
            // 逐行读取记录，每行是一个博客，列名对应数据库字段名
            InputStream inputStream = file.getInputStream();
            ExcelReader reader = ExcelUtil.getReader(inputStream);
            // 通过javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
            List<Blog> list = reader.readAll(Blog.class);
            int count = 0;
            for (int i = 0; i < list.size(); i++) {
                try {
                    count += blogService.createBlog(list.get(i));
                } catch (Exception e) {
                    return Result.fail("博客导入失败，停止继续导入，失败行数为：" + i, e.getMessage());
                }
            }
            flushRedis();
            if (count == list.size()) {
                return Result.succ(originalFilename + " 导入成功", list);
            } else {
                return Result.fail("导入成功的博客记录数与文件内记录数不匹配，未知原因");
            }
        } else {
            return Result.fail("博客文件类型错误，应为.md或.xlsx或.xls文件");
        }
        return Result.fail(originalFilename + " 导入失败");
    }

    /**
     * 删除博客，逻辑删除，对应字段deleted
     * 删除操作变为修改deleted字段的操作
     * 1为逻辑删除，0（数据库字段默认值）为未删除
     *
     * @param id 博客id（唯一）
     * @return 被逻辑删除的博客id作为data
     */
    @OperationLogger("逻辑删除博客")
    @DeleteMapping("/deleteBlogById")
    public Result deleteBlogById(@RequestParam Long id) {
        log.info("blog to delete : " + id);
        boolean delete = blogService.removeById(id);
        if (delete) {
            flushRedis();
            return Result.succ("博客删除成功", id);
        } else {
            return Result.fail("博客删除失败", id);
        }
    }

    /**
     * 批量删除博客，逻辑删除
     *
     * @param ids 多个博客id
     * @return 被逻辑删除的多个博客id列表
     */
    @OperationLogger("批量删除博客")
    @DeleteMapping("/deleteBlogBatchByIds")
    public Result deleteBlogBatchByIds(@RequestParam String ids) {
        String[] list = ids.split(",");
        List<Long> idList = new ArrayList<>();
        for (String id : list) {
            idList.add(Long.valueOf(id));
        }
        int deletedBlogCount = 0;
        for (Long id : idList) {
            if (blogService.removeById(id)) {
                deletedBlogCount++;
            } else {
                return Result.fail("ID为 " + id + " 的博客删除失败，后续删除停止", id);
            }
        }
        if (deletedBlogCount == idList.size()) {
            return Result.succ("批量删除成功", idList);
        }
        return Result.fail("批量删除失败");
    }

    /**
     * 修改、阅读操作对应的根据指定id查询博客的接口
     * 可以根据指定的唯一id查询对应的博客、博客所属的分类、博客拥有的标签
     *
     * @param blogId 博客id（唯一）
     * @return Result
     */
    @OperationLogger("根据指定id查询博客")
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
     * 获取博客列表
     * 可以实现无参数和多参数的分页查询
     * 将分页列表和总记录数作为键值对存储到Map中
     * 分页列表用于前端的当前页展示
     * 总记录数用于前端展示博客总数，这个数值是当前数据库中未被删除的博客总数，是所有分页中的博客个数的和
     *
     * @return 成功则Map作为data
     */
    @OperationLogger("获取博客列表")
    @PostMapping("/getBlogs")
    public Result getBlogs(@RequestBody Map<String, Object> params) {

        Map<String, Object> data = new HashMap<>(2);
        IPage<Blog> pageData = blogService.getBlogList(params);
        if (pageData.getTotal() == 0 && pageData.getRecords().isEmpty()) {
            data.put("pageData", pageData);
            data.put("total", pageData.getTotal());
            return Result.succ("未查找到相应博客", data);
        }

        List<Category> categoryList = categoryService.list();
        List<Blog> list = pageData.getRecords();
        for (int i = 0; i < list.size(); i++) {
            for (Category category : categoryList) {
                if (list.get(i).getCategoryId().equals(category.getId())) {
                    list.get(i).setCategoryName(category.getCategoryName());
                }
            }
        }

        pageData.setRecords(list);
        data.put("pageData", pageData);
        data.put("total", pageData.getTotal());
        return Result.succ("查询成功", data);
    }

    /**
     * 获取所有分类和标签以供前端选择使用
     *
     * @return 所有分类和所有标签
     */
    @OperationLogger("获取所有分类和标签")
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
     * 删除redis缓存中对应指定键值的内容
     */
    private void flushRedis() {
        Long total = (Long) redisTemplate.opsForValue().get(Constant.PAGE_NUMBER_OF_PUBLISHED_BLOGS);
        if (total != null) {
            int count = (int) Math.ceil(total * 1.0 / 10);
            log.info("可见博客的总页数 ================= : {}", count);
            for (int i = 1; i <= count; i++) {
                redisTemplate.delete(Constant.GUEST_BLOG_KEY + "_" + i);
            }
            redisTemplate.delete(Constant.PAGE_NUMBER_OF_PUBLISHED_BLOGS);
        }
    }
}
