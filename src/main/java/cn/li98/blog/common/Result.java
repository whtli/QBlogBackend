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
        return succ(Constant.CODE_SUCCESSFUL, "操作成功", data);
    }

    public static Result succ(String message, Object data){
        return succ(Constant.CODE_SUCCESSFUL, message, data);
    }

    public static Result succ(Integer code, String message, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMessage(message);
        r.setData(data);
        return r;
    }

    public static Result fail(String message) {
        return fail(Constant.CODE_PARAM_ERROR, message, null);
    }

    public static Result fail(String message, Object data) {
        return fail(Constant.CODE_PARAM_ERROR, message, data);
    }

    public static Result fail(Integer code, String message, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMessage(message);
        r.setData(data);
        return r;
    }
}
