package cn.li98.blog.service;

import cn.li98.blog.model.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * @author: whtli
 * @date: 2022/11/10
 * @description:
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
}
