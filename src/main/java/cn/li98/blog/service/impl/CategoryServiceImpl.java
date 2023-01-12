package cn.li98.blog.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.li98.blog.common.Result;
import cn.li98.blog.dao.CategoryMapper;
import cn.li98.blog.model.entity.Category;
import cn.li98.blog.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: whtli
 * @date: 2022/11/28
 * @description: 分类业务实现层
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    /**
     * 创建分类业务实现层
     *
     * @param category 分类实体类，无id
     * @return 修改成功返回1
     */
    public int createCategory(Category category) {
        return categoryMapper.insert(category);
    }

    /**
     * 编辑分类业务实现层
     *
     * @param category 分类实体类，有id
     * @return 修改成功返回1
     */
    public int updateCategory(Category category) {
        return categoryMapper.updateById(category);
    }

    /**
     * 新增与修改分类的通用方法，通过判断id的有无来区分新增还是修改
     *
     * @param category 分类实体类
     * @return Result
     */
    @Override
    public Result submitCategory(Category category) {
        // 验证分类名是否为空
        if (StrUtil.isEmpty(category.getCategoryName())) {
            return Result.fail("分类名不可为空");
        }
        // 验证分类名是否已经存在
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("category_name", category.getCategoryName());
        List<Category> list = list(queryWrapper);
        for (Category item : list) {
            if (item.getCategoryName().equals(category.getCategoryName())) {
                return Result.fail("分类名 “" + category.getCategoryName() + "” 已存在！");
            }
        }
        int flag = 0;
        try {
            if (category.getId() == null) {
                flag = createCategory(category);
            } else {
                flag = updateCategory(category);
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
