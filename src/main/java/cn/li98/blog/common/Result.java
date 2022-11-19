package cn.li98.blog.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @AUTHOR: whtli
 * @DATE: 2022/11/09
 * @DESCRIPTION: 接口统一返回包装类
 */
@Getter
@Setter
@ToString
public class Result implements Serializable {
    private Integer code;
    private String message;
    private Object data;

    public static Result succ(Object data) {
        return succ(20000, "操作成功", data);
    }

    public static Result succ(int code, String message, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMessage(message);
        r.setData(data);
        return r;
    }

    public static Result fail(String message) {
        return fail(40000, message, null);
    }

    public static Result fail(String message, Object data) {
        return fail(40000, message, data);
    }

    public static Result fail(int code, String message, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMessage(message);
        r.setData(data);
        return r;
    }
}
