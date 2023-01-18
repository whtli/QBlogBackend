package cn.li98.blog.service;

import cn.li98.blog.model.entity.User;
import cn.li98.blog.model.dto.LoginDTO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author: whtli
 * @date: 2022/11/10
 * @description: 用户业务层
 */
public interface UserService extends IService<User> {
    /**
     * 根据ID获取用户信息
     *
     * @param id
     * @return User
     */
    User getUserById(Long id);

    /**
     * 登录
     *
     * @param loginDTO
     * @return
     */
    User login(LoginDTO loginDTO);
}
