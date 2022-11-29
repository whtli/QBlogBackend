package cn.li98.blog.controllor.admin;

import cn.hutool.core.util.StrUtil;
import cn.li98.blog.common.Result;
import cn.li98.blog.model.Tag;
import cn.li98.blog.service.TagService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: whtli
 * @date: 2022/11/29
 * @description: 标签接口
 */
@Slf4j
@RestController
@RequestMapping("/admin/tag")
public class TagControllor {
    @Autowired
    TagService tagService;

    /**
     * 分页查询，获取分类列表
     *
     * @param pageNum  页码
     * @param pageSize 每页分类数量
     * @return 成功则Map作为data
     */
    @GetMapping("/getTags")
    public Result getTags(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        // 新建一个分页规则，pageNum代表当前页码，pageSize代表每页数量
        Page page = new Page(pageNum, pageSize);
        // 借助Page实现分页查询，借助QueryWrapper实现多参数查询
        IPage pageData = tagService.page(page, queryWrapper);
        if (pageData.getTotal() == 0 && pageData.getRecords().isEmpty()) {
            return Result.fail("查询失败，未查找到相应分类");
        }
        Map<String, Object> data = new HashMap<>(2);
        data.put("pageData", pageData);
        data.put("total", pageData.getTotal());
        return Result.succ("查询成功", data);
    }

    /**
     * 删除分类
     *
     * @param id 分类id（唯一）
     * @return 被删除的分类id作为data
     */
    @DeleteMapping("/deleteTagById")
    public Result deleteTagById(@RequestParam Long id) {
        log.info("tag to delete : " + id);
        boolean delete = tagService.removeById(id);
        if (delete) {
            return Result.succ("分类删除成功", id);
        } else {
            return Result.fail("分类删除失败", id);
        }
    }

    /**
     * 新增分类
     *
     * @param tag 分类实体类
     * @return Result
     */
    @PostMapping("/addTag")
    public Result addTag(@Validated @RequestBody Tag tag) {
        return submitTag(tag);
    }

    /**
     * 修改分类
     *
     * @param tag 分类实体类
     * @return Result
     */
    @PutMapping("/editTag")
    public Result updateTag(@Validated @RequestBody Tag tag) {
        return submitTag(tag);
    }

    /**
     * 新增与修改分类的通用方法，通过判断id的有无来区分新增还是修改
     *
     * @param tag 分类实体类
     * @return Result
     */
    public Result submitTag(Tag tag) {
        // 验证字段
        if (StrUtil.isEmpty(tag.getTagName())) {
            return Result.fail("分类名不可为空");
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
            return Result.succ("分类发布成功");
        }
        return Result.fail("分类发布失败");
    }
}
