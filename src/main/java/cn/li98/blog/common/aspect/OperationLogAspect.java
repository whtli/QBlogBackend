package cn.li98.blog.common.aspect;

import cn.li98.blog.common.annotation.OperationLogger;
import cn.li98.blog.model.entity.OperationLog;
import cn.li98.blog.service.OperationLogService;
import cn.li98.blog.utils.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author: whtli
 * @date: 2022/12/08
 * @description: AOP记录操作日志
 */
@Component
@Aspect
public class OperationLogAspect {
    @Autowired
    OperationLogService operationLogService;

    @Autowired
    UserAgentUtils userAgentUtils;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(operationLogger)")
    public void logPointcut(OperationLogger operationLogger) {
    }

    /**
     * 配置环绕通知
     *
     * @param joinPoint       切入点
     * @param operationLogger 注解OperationLogger对象
     * @return joinPoint.proceed()
     * @throws Throwable
     */
    @Around("logPointcut(operationLogger)")
    public Object logAround(ProceedingJoinPoint joinPoint, OperationLogger operationLogger) throws Throwable {
        currentTime.set(System.currentTimeMillis());
        Object result = joinPoint.proceed();
        int times = (int) (System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        // 新建一个当前操作的日志对象并填充
        OperationLog operationLog = handleLog(joinPoint, operationLogger, times);
        // 将操作日志保持到数据库
        operationLogService.save(operationLog);
        return result;
    }

    /**
     * 获取HttpServletRequest请求对象，并设置OperationLog对象属性
     *
     * @param joinPoint       切入点
     * @param operationLogger 注解OperationLogger对象
     * @param times           操作所用时间
     * @return
     */
    private OperationLog handleLog(ProceedingJoinPoint joinPoint, OperationLogger operationLogger, int times) {
        // 从token中获取操作用户的名称
        String username = TokenUtils.getCurrentUser().getUsername();
        System.out.println("username: -----------  " + username);
        // 获取操作描述
        String description = operationLogger.value();

        // 获取请求内容中的属性
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 获取请求属性
        HttpServletRequest request = attributes.getRequest();
        // 获取请求接口
        String uri = request.getRequestURI();
        // 获取请求方式
        String method = request.getMethod();
        // 获取用户代理方式
        String userAgent = request.getHeader("User-Agent");
        // 借助IpAddressUtils工具类获取用户ip
        String ip = IpAddressUtils.getIpAddress(request);
        // 借助IpAddressUtils工具获取ip来源
        String ipSource = IpAddressUtils.getCityInfo(ip);
        // 借助AopUtils工具类获取请求参数
        Map<String, Object> requestParams = AopUtils.getRequestParams(joinPoint);
        String param = JacksonUtils.writeValueAsString(requestParams);
        // String param = StringUtils.substring(JacksonUtils.writeValueAsString(requestParams), 0, 2000);
        // 借助UserAgentUtils工具类获取操作系统和浏览器信息
        Map<String, String> userAgentMap = userAgentUtils.parseOsAndBrowser(userAgent);
        String os = userAgentMap.get("os");
        String browser = userAgentMap.get("browser");
        // 创建日志对象
        OperationLog log = new OperationLog(username, description, uri, method, userAgent, ip, ipSource, times, param, os, browser);
        return log;
    }
}
