package cn.li98.blog.controller.admin;

import cn.li98.blog.common.Result;
import cn.li98.blog.annotation.OperationLogger;
import cn.li98.blog.model.entity.Blog;
import cn.li98.blog.model.entity.Category;
import cn.li98.blog.service.BlogService;
import cn.li98.blog.service.CategoryService;
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
 * @date: 2022/11/10
 * @description: 分类控制层
 */
@Slf4j
@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    private BlogService blogService;

    /**
     * 分页查询，获取分类列表
     *
     * @param pageNum  页码
     * @param pageSize 每页分类数量
     * @return 成功则Map作为data
     */
    @OperationLogger("获取分类列表")
    @GetMapping("/getCategoryList")
    public Result getCategoryList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        // 新建一个分页规则，pageNum代表当前页码，pageSize代表每页数量
        Page page = new Page(pageNum, pageSize);
        // 借助Page实现分页查询，借助QueryWrapper实现多参数查询
        IPage pageData = categoryService.page(page, queryWrapper);
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
    @OperationLogger("删除分类")
    @DeleteMapping("/deleteCategoryById")
    public Result deleteCategoryById(@RequestParam Long id) {
        log.info("category to delete : " + id);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("category_id", id);
        List<Blog> list = blogService.list(queryWrapper);
        if (list != null || !list.isEmpty()) {
            return Result.fail("当前分类下有" + list.size() + "个博客存在，不能直接删除分类");
        }
        boolean delete = categoryService.removeById(id);
        if (delete) {
            return Result.succ("分类删除成功", id);
        } else {
            return Result.fail("分类删除失败", id);
        }
    }

    /**
     * 新增分类
     *
     * @param category 分类实体类
     * @return Result
     */
    @OperationLogger("新增分类")
    @PostMapping("/addCategory")
    public Result addCategory(@Validated @RequestBody Category category) {
        return categoryService.submitCategory(category);
    }

    /**
     * 修改分类
     *
     * @param category 分类实体类
     * @return Result
     */
    @OperationLogger("修改分类")
    @PutMapping("/editCategory")
    public Result updateCategory(@Validated @RequestBody Category category) {
        return categoryService.submitCategory(category);
    }
}
