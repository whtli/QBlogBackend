package cn.li98.blog.service.impl;

import cn.li98.blog.dao.CategoryMapper;
import cn.li98.blog.model.Category;
import cn.li98.blog.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @AUTHOR: whtli
 * @DATE: 2022/11/28
 * @DESCRIPTION: 分类业务实现层
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public int createCategory(Category category) {
        return categoryMapper.insert(category);
    }

    @Override
    public int updateCategory(Category category) {
        return categoryMapper.updateById(category);
    }
}
