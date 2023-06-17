package cn.li98.blog.service.impl;

import cn.li98.blog.utils.WordCountUtils;
import cn.li98.blog.dao.BlogMapper;
import cn.li98.blog.model.entity.Blog;
import cn.li98.blog.model.entity.Category;
import cn.li98.blog.model.entity.Tag;
import cn.li98.blog.service.BlogService;
import cn.li98.blog.service.CategoryService;
import cn.li98.blog.service.TagService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.Map;

/**
 * @author: whtli
 * @date: 2022/11/10
 * @description: 博客接口业务实现层
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
    @Resource
    private BlogMapper blogMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;
    /**
     * 为新增或修改的博客设置参数
     * 这些常规参数在新增和修改过程中具备相同的设置规则
     * 包括：类别id、阅读时长、阅读量
     *
     * @param blog
     * @return Blog
     */
    private Blog setItemsOfBlog(Blog blog) {
        if (blog.getCategoryId() == null) {
            blog.setCategoryId(1L);
        }
        // TODO: 分类、标签等功能判断新增等功能
        if (blog.getFirstPicture() == null) {
            blog.setFirstPicture("");
        }
        if (blog.getReadTime() == null || blog.getReadTime() <= 0) {
            // 计算字数，粗略计算阅读时长
            int words = WordCountUtils.count2(blog.getContent());
            int readTime = (int) Math.round(words / 200.0);
            blog.setWords(words);
            blog.setReadTime(readTime);
        }
        if (blog.getViews() == null || blog.getViews() < 0) {
            blog.setViews(0);
        }
        return blog;
    }

    /**
     * 创建博客/导入博客
     * 需要常规地设置类别id、阅读时长、阅读量参数
     * 需要单独地设置创建时间、更新时间（等于创建）、创建用户（默认为1唯一的管理员）
     *
     * @param blog
     * @return 创建成功返回1，失败返回0
     */
    @Override
    public int createBlog(Blog blog) {
        Blog newBlog = setItemsOfBlog(blog);
        if (newBlog.getCreateTime() == null) {
            Date date = new Date();
            newBlog.setCreateTime(date);
            newBlog.setUpdateTime(date);
        }
        newBlog.setUserId(1L);
        return blogMapper.createBlog(newBlog);
    }

    /**
     * 更新博客
     * 需要常规地设置类别id、阅读时长、阅读量参数
     * 需要单独地设置更新时间（当前时间）
     *
     * @param blog
     * @return 更新成功返回1，失败返回0
     */
    @Override
    public int updateBlog(Blog blog) {
        Blog newBlog = setItemsOfBlog(blog);
        Date date = new Date();
        newBlog.setUpdateTime(date);
        return blogMapper.updateBlog(blog);
    }

    /**
     * 提取上传的md文件内容，赋值到Blog对象中
     *
     * @param file Markdown文件
     * @return Blog对象
     * @throws IOException    IO异常
     * @throws ParseException 时间转换异常
     */
    @Override
    public Blog fileToBlog(MultipartFile file) throws IOException, ParseException {
        // 转成字符流
        InputStream is = file.getInputStream();
        InputStreamReader isReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isReader);
        StringBuilder content = new StringBuilder();
        String title = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        // 循环逐行读取
        while (br.ready()) {
            String line = br.readLine();
            if (line.contains("title: ")) {
                title = line.substring(7);
            }
            if (line.contains("date: ")) {
                date = sdf.parse(line.substring(6));
            }
            content.append(line);
            content.append('\n');
        }
        //关闭流
        br.close();

        // 新建博客类对象
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setFirstPicture("");
        blog.setContent(content.toString().split("date: ")[1].substring(24));
        blog.setDescription(file.getOriginalFilename());
        blog.setPublished(true);
        blog.setCommentEnabled(false);
        blog.setCreateTime(date);
        blog.setUpdateTime(date);
        blog.setViews(0);
        blog.setWords(WordCountUtils.count2(blog.getContent()));
        blog.setReadTime((int) Math.round(blog.getWords() / 200.0));
        blog.setCategoryId(1L);
        blog.setTop(false);
        blog.setPassword("");
        blog.setUserId(1L);
        blog.setDeleted(0L);
        return blog;
    }

    /**
     * 博客可见性更改
     *
     * @param blogId 博客id
     * @return 更改成功返回1，失败返回0
     */
    @Override
    public int changeBlogStatusById(Long blogId) {
        return blogMapper.changeBlogStatusById(blogId);
    }

    /**
     * 获取博客列表
     *
     * @param params 标题、分类id、标签id列表等参数
     * @return 符合查询条件的博客列表
     */
    @Override
    public IPage<Blog> getBlogList(@Param("params") Map<String, Object> params) {
        int pageNum = (int) params.get("pageNum");
        int pageSize = (int) params.get("pageSize");
        Page page = new Page(pageNum, pageSize);
        IPage<Blog> pageData = blogMapper.getBlogList(page, params);
        List<Blog> blogList = pageData.getRecords();
        List<Category> categoryList = categoryService.list();
        for (int i = 0; i < blogList.size(); i++) {
            // 查询博客所属分类
            for (Category category : categoryList) {
                if (blogList.get(i).getCategoryId().equals(category.getId())) {
                    blogList.get(i).setCategoryName(category.getCategoryName());
                }
            }
            // Category category = categoryService.getById(blogList.get(i).getCategoryId());
            // blogList.get(i).setCategoryName(category.getCategoryName());
            // 查询博客拥有的标签
            List<Tag> tagList = tagService.getTagsByBlogId(blogList.get(i).getId());
            blogList.get(i).setTagList(tagList);
        }
        pageData.setRecords(blogList);
        return pageData;
    }
}
