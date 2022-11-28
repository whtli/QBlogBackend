package cn.li98.blog.service;

import cn.li98.blog.model.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @AUTHOR: whtli
 * @DATE: 2022/11/28
 * @DESCRIPTION: 分类业务层
 */
public interface CategoryService extends IService<Category> {
    int createCategory(Category category);

    int updateCategory(Category category);
}
