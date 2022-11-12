package cn.li98.blog.service;

import cn.li98.blog.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @author : whtli
 */
public interface UserService{
    User getUserById(Long id);

    User getByName(String username);
}
