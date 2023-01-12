package cn.li98.blog.service;

import cn.li98.blog.common.Result;
import cn.li98.blog.model.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author: whtli
 * @date: 2022/11/28
 * @description: 分类业务层
 */
public interface CategoryService extends IService<Category> {
    /**
     * 新增与修改分类的通用方法，通过判断id的有无来区分新增还是修改
     *
     * @param category 分类实体类
     * @return Result
     */
    Result submitCategory(Category category);
}
