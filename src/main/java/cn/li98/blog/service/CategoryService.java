package cn.li98.blog.service;

import cn.li98.blog.model.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author: whtli
 * @date: 2022/11/28
 * @description: 分类业务层
 */
public interface CategoryService extends IService<Category> {
    /**
     * 创建分类业务层
     *
     * @param category 分类实体类，无id
     * @return 创建成功返回1
     */
    int createCategory(Category category);

    /**
     * 编辑分类业务层
     *
     * @param category 分类实体类，有id
     * @return 修改成功返回1
     */
    int updateCategory(Category category);
}
