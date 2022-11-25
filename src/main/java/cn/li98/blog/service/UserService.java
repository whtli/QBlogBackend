package cn.li98.blog.service;

import cn.li98.blog.model.User;
import cn.li98.blog.model.dto.LoginDTO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author : whtli
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
     * 根据名字获取用户信息
     *
     * @param username
     * @return User
     */
    User getByName(String username);

    /**
     * 登录
     *
     * @param loginDTO
     * @return
     */
    User login(LoginDTO loginDTO);
}
