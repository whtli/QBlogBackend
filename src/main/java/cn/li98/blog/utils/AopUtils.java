package cn.li98.blog.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: whtli
 * @date: 2022/12/06
 * @description: AOP工具类，可用于获取请求中的参数名与参数值
 */
public class AopUtils {
    /**
     * 自定义需要忽略的参数
     */
    private static Set<String> ignoreParams = new HashSet<String>() {
        {
            add("jwt");
        }
    };

    /**
     * 获取请求参数
     *
     * @param joinPoint
     * @return
     */
    public static Map<String, Object> getRequestParams(JoinPoint joinPoint) {
        Map<String, Object> map = new LinkedHashMap<>();
        // 参数名数组
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        // 参数值数组
        Object[] args = joinPoint.getArgs();
        // 将符合条件的参数名与其对应的参数值，存到map中
        for (int i = 0; i < args.length; i++) {
            if (!isIgnoreParams(parameterNames[i]) && !isFilterObject(args[i])) {
                map.put(parameterNames[i], args[i]);
            }
        }
        return map;
    }

    /**
     * 判断是否忽略参数
     *
     * @param params
     * @return
     */
    private static boolean isIgnoreParams(String params) {
        return ignoreParams.contains(params);
    }

    /**
     * consider if the data is file, httpRequest or response
     *
     * @param o the data
     * @return if match return true, else return false
     */
    private static boolean isFilterObject(final Object o) {
        return o instanceof HttpServletRequest || o instanceof HttpServletResponse || o instanceof MultipartFile;
    }
}
