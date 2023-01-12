package cn.li98.blog.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.li98.blog.common.Result;
import cn.li98.blog.dao.BlogMapper;
import cn.li98.blog.dao.TagMapper;
import cn.li98.blog.model.entity.Blog;
import cn.li98.blog.model.entity.Tag;
import cn.li98.blog.service.TagService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: whtli
 * @date: 2022/11/28
 * @description: 博客标签业务实现层
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Resource
    private TagMapper tagMapper;

    @Resource
    private BlogMapper blogMapper;

    /**
     * 创建标签业务实现层
     *
     * @param tag 标签实体类，无id
     * @return 创建成功返回1
     */
    public int createTag(Tag tag) {
        return tagMapper.insert(tag);
    }

    /**
     * 编辑标签业务实现层
     *
     * @param tag 标签实体类，有id
     * @return 修改成功返回1
     */
    public int updateTag(Tag tag) {
        return tagMapper.updateById(tag);
    }

    /**
     * 新增与修改标签的通用方法，通过判断id的有无来区分新增还是修改
     *
     * @param tag 标签实体类
     * @return Result
     */
    @Override
    public Result submitTag(Tag tag) {
        // 验证标签名是否为空
        if (StrUtil.isEmpty(tag.getTagName())) {
            return Result.fail("标签名不可为空");
        }
        // 验证分类名是否已经存在
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("tag_name", tag.getTagName());
        List<Tag> tagList = list(queryWrapper);
        for (Tag item : tagList) {
            if (item.getTagName().equals(tag.getTagName())) {
                return Result.fail("标签名 “" + tag.getTagName() + "” 已存在！");
            }
        }
        int flag = 0;
        try {
            if (tag.getId() == null) {
                flag = createTag(tag);
            } else {
                flag = updateTag(tag);
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        if (flag == 1) {
            return Result.succ("标签发布成功");
        }
        return Result.fail("标签发布失败");
    }

    /**
     * 建立博客和标签的映射关系到blog_tag表
     *
     * @param blogId 博客id
     * @param tagId  标签id
     * @return 关联成功返回1
     */
    @Override
    public int saveBlogTag(Long blogId, Long tagId) {
        int res = tagMapper.saveBlogTag(blogId, tagId);
        if (res != 1) {
            throw new PersistenceException("维护博客标签关联表失败");
        }
        return res;
    }

    /**
     * 根据博客id查询其拥有的标签列表
     *
     * @param blogId 博客id
     * @return 标签列表
     */
    @Override
    public List<Tag> getTagsByBlogId(Long blogId) {
        return tagMapper.getTagsByBlogId(blogId);
    }

    /**
     * 根据标签id查询使用了这个标签的博客
     *
     * @param tagId 标签id
     * @return 博客列表
     */
    @Override
    public List<Blog> getBlogsByTagId(Long tagId) {
        List<Integer> list = tagMapper.getBlogsByTagId(tagId);
        if (list.isEmpty()) {
            return null;
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("id", list);
        queryWrapper.orderByDesc("create_time");

        List<Blog> blogList = blogMapper.selectList(queryWrapper);
        return blogList;
    }

    /**
     * 在创建或更新博客之前检查当前博客所带的标签
     * 若所带标签已经存在则存入标签列表
     * 若所带标签不存在于数据库中则创建新标签然后存入标签列表
     *
     * @param tags 前端传来的选择的和手动输入的标签组成的列表
     * @return 当前博客对应的标签列表
     */
    @Override
    public List<Tag> checkTagsBeforeSubmitBlog(List<Object> tags) {
        List<Tag> tagList = new LinkedList<>();
        for (Object t : tags) {
            if (t instanceof Integer) {
                // 选择了已存在的标签
                Tag tag = getById(((Integer) t).longValue());
                tagList.add(tag);
            } else if (t instanceof String) {
                // 直接输入的标签名，此时需要判断标签是否已存在
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.eq("tag_name", (String) t);
                if (getOne(wrapper) != null) {
                    log.error("不可新增已存在的标签");
                    return null;
                }
                // 不存在则添加新标签
                Tag tag = new Tag();
                tag.setTagName((String) t);
                tagMapper.insert(tag);
                tagList.add(tag);
            }
        }
        return tagList;
    }

}
