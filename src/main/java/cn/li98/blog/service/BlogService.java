package cn.li98.blog.service;

import cn.li98.blog.model.entity.Blog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @author: whtli
 * @date: 2022/11/10
 * @description: 博客接口业务层
 */
public interface BlogService extends IService<Blog> {

    /**
     * 新发布博客/导入博客
     *
     * @param blog
     * @return 1：创建成功；0：创建失败
     */
    int createBlog(Blog blog);

    /**
     * 更新已有博客
     *
     * @param blog
     * @return 1：更新成功；0：更新失败
     */
    int updateBlog(Blog blog);

    /**
     * 提取上传的md文件内容，赋值到Blog对象中
     *
     * @param file Markdown文件
     * @return Blog对象
     * @throws IOException    IO异常
     * @throws ParseException 时间转换异常
     */
    Blog fileToBlog(MultipartFile file) throws IOException, ParseException;

    /**
     * 博客可见性更改
     *
     * @param blogId 博客id
     * @return Result
     */
    int changeBlogStatusById(Long blogId);

    /**
     * 获取博客列表
     *
     * @param params 标题、分类id、标签id列表等参数
     * @return 符合查询条件的博客列表
     */
    IPage<Blog> getBlogList(Map<String, Object> params);
}
