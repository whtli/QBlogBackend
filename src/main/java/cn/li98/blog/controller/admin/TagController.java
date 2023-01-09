package cn.li98.blog.controller.admin;

import cn.hutool.core.util.StrUtil;
import cn.li98.blog.common.Result;
import cn.li98.blog.common.annotation.OperationLogger;
import cn.li98.blog.model.entity.Blog;
import cn.li98.blog.model.entity.Tag;
import cn.li98.blog.service.TagService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: whtli
 * @date: 2022/11/29
 * @description: 标签接口
 */
@Slf4j
@RestController
@RequestMapping("/admin/tag")
public class TagController {
    @Autowired
    TagService tagService;

    /**
     * 分页查询，获取标签列表
     *
     * @param pageNum  页码
     * @param pageSize 每页标签数量
     * @return 成功则Map作为data
     */
    @OperationLogger("获取标签列表")
    @GetMapping("/getTags")
    public Result getTags(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        // 新建一个分页规则，pageNum代表当前页码，pageSize代表每页数量
        Page page = new Page(pageNum, pageSize);
        // 借助Page实现分页查询，借助QueryWrapper实现多参数查询
        IPage pageData = tagService.page(page, queryWrapper);
        if (pageData.getTotal() == 0 && pageData.getRecords().isEmpty()) {
            return Result.fail("查询失败，未查找到相应标签");
        }
        Map<String, Object> data = new HashMap<>(2);
        data.put("pageData", pageData);
        data.put("total", pageData.getTotal());
        return Result.succ("查询成功", data);
    }

    /**
     * 删除标签
     *
     * @param id 标签id（唯一）
     * @return 被删除的标签id作为data
     */
    @OperationLogger("删除标签")
    @DeleteMapping("/deleteTagById")
    public Result deleteTagById(@RequestParam Long id) {
        log.info("tag to delete : " + id);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("category_id", id);
        List<Blog> blogsByTagId = tagService.getBlogsByTagId(id);
        int count = 0;
        if (blogsByTagId != null) {
            count = blogsByTagId.size();
        }
        if (count > 0) {
            return Result.fail("有" + count + "个博客正在使用当前标签，不能直接删除标签");
        }
        boolean delete = tagService.removeById(id);
        if (delete) {
            return Result.succ("标签删除成功", id);
        } else {
            return Result.fail("标签删除失败", id);
        }
    }

    /**
     * 新增标签
     *
     * @param tag 标签实体类
     * @return Result
     */
    @OperationLogger("新增标签")
    @PostMapping("/addTag")
    public Result addTag(@Validated @RequestBody Tag tag) {
        return submitTag(tag);
    }

    /**
     * 修改标签
     *
     * @param tag 标签实体类
     * @return Result
     */
    @OperationLogger("修改标签")
    @PutMapping("/editTag")
    public Result updateTag(@Validated @RequestBody Tag tag) {
        return submitTag(tag);
    }

    /**
     * 新增与修改标签的通用方法，通过判断id的有无来区分新增还是修改
     *
     * @param tag 标签实体类
     * @return Result
     */
    public Result submitTag(Tag tag) {
        // 验证标签名是否为空
        if (StrUtil.isEmpty(tag.getTagName())) {
            return Result.fail("标签名不可为空");
        }
        // 验证分类名是否已经存在
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("tag_name", tag.getTagName());
        List<Tag> list = tagService.list(queryWrapper);
        for (Tag item : list) {
            if (item.getTagName().equals(tag.getTagName())) {
                return Result.fail("标签名 “" + tag.getTagName() + "” 已存在！");
            }
        }
        int flag = 0;
        try {
            if (tag.getId() == null) {
                flag = tagService.createTag(tag);
            } else {
                flag = tagService.updateTag(tag);
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        if (flag == 1) {
            return Result.succ("标签发布成功");
        }
        return Result.fail("标签发布失败");
    }
}
