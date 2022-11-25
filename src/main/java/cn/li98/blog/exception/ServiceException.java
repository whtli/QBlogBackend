package cn.li98.blog.exception;

import lombok.Getter;

/**
 * @author: whtli
 * @date: 2022/11/19
 * @description: 自定义异常，ServiceImpl层的异常可以调用
 */

@Getter
public class ServiceException extends RuntimeException{
    private Integer code;
    public ServiceException (Integer code, String msg) {
        super(msg);
        this.code = code;
    }
}
