package cn.li98.blog.service.impl;

import cn.li98.blog.dao.CategoryMapper;
import cn.li98.blog.model.entity.Category;
import cn.li98.blog.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
    public int createCategory(Category category) {
        return categoryMapper.insert(category);
    }

    /**
     * 编辑分类业务实现层
     *
     * @param category 分类实体类，有id
     * @return 修改成功返回1
     */
    @Override
    public int updateCategory(Category category) {
        return categoryMapper.updateById(category);
    }
}
