package cn.li98.blog.common.aspect;

import cn.li98.blog.common.Constant;
import cn.li98.blog.common.Result;
import cn.li98.blog.common.annotation.VisitLogger;
import cn.li98.blog.common.enums.VisitBehavior;
import cn.li98.blog.model.dto.VisitLogRemark;
import cn.li98.blog.model.entity.Blog;
import cn.li98.blog.model.entity.VisitLog;
import cn.li98.blog.service.VisitLogService;
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
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

/**
 * @author: whtli
 * @date: 2023/01/09
 * @description: AOP记录前端访问日志
 */
@Component
@Aspect
public class VisitLogAspect {
    @Autowired
    private VisitLogService visitLogService;

    @Autowired
    private UserAgentUtils userAgentUtils;

    private ThreadLocal<Long> currentTime = new ThreadLocal<>();

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(visitLogger)")
    public void logPointcut(VisitLogger visitLogger) {
    }

    /**
     * 配置环绕通知
     *
     * @param joinPoint   切入点
     * @param visitLogger 注解OperationLogger对象
     * @return joinPoint.proceed()
     * @throws Throwable
     */
    @Around("logPointcut(visitLogger)")
    public Object logAround(ProceedingJoinPoint joinPoint, VisitLogger visitLogger) throws Throwable {
        currentTime.set(System.currentTimeMillis());
        Result result = (Result) joinPoint.proceed();
        int times = (int) (System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        //获取请求对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //校验访客标识码
        String identification = checkIdentification(request);
        //记录访问日志
        VisitLog visitLog = handleLog(joinPoint, visitLogger, request, result, times, identification);
        // 将操作日志保持到数据库
        visitLogService.save(visitLog);
        return result;
    }


    /**
     * 签发UUID，并保存至数据库
     *
     * @param request
     * @return
     */
    private String saveUuid(HttpServletRequest request) {
        //获取响应对象
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //获取当前时间戳，精确到小时，防刷访客数据
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        String timestamp = Long.toString(calendar.getTimeInMillis() / 1000);
        //获取访问者基本信息
        String ip = IpAddressUtils.getIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        //根据时间戳、ip、userAgent生成UUID
        String nameUuid = timestamp + ip + userAgent;
        String uuid = UUID.nameUUIDFromBytes(nameUuid.getBytes()).toString();
        //添加访客标识码UUID至响应头
        response.addHeader("identification", uuid);
        //暴露自定义header供页面资源使用
        response.addHeader("Access-Control-Expose-Headers", "identification");
        return uuid;
    }

    /**
     * 校验访客标识码
     *
     * @param request
     * @return
     */
    private String checkIdentification(HttpServletRequest request) {
        String identification = request.getHeader("identification");
        //请求头没有uuid，签发uuid并保存到数据库和Redis
        identification = saveUuid(request);
        return identification;
    }

    /**
     * 根据访问行为，设置对应的访问内容或备注
     *
     * @param visitBehavior
     * @param requestParams
     * @param result
     * @return
     */
    private VisitLogRemark judgeBehavior(VisitBehavior visitBehavior, Map<String, Object> requestParams, Result result) {
        String remark = "";
        String content = visitBehavior.getContent();
        switch (visitBehavior) {
            case UNKNOWN:
                break;
            case INDEX:
                break;
            case CATEGORY:
                break;
            case TAG:
                break;
            case ARCHIVE:
                break;
            case ABOUT:
                break;
            case FRIEND:
                break;
            case BLOG:
                if (result.getCode().equals(Constant.CODE_SUCCESSFUL)) {
                    Map object = (Map) result.getData();
                    Blog blog = (Blog) object.get("blog");
                    Long id = blog.getId();
                    String title = blog.getTitle();
                    content = "博客标题：" + title;
                    remark = "博客ID：" + id;
                }
                break;
            case CATEGORY_DETAIL:
                if (result.getCode().equals(Constant.CODE_SUCCESSFUL)) {
                    Map object = (Map) result.getData();
                    String categoryName = (String) object.get("categoryName");
                    content = "分类名：" + categoryName;
                    Long categoryId = (Long) requestParams.get("categoryId");
                    remark = "分类ID：" + categoryId;
                }
                break;
            case TAG_DETAIL:
                if (result.getCode().equals(Constant.CODE_SUCCESSFUL)) {
                    Map object = (Map) result.getData();
                    String tagName = (String) object.get("tagName");
                    content = "标签名：" + tagName;
                    Long tagId = (Long) requestParams.get("tagId");
                    remark = "标签ID：" + tagId;
                }
                break;
            case SEARCH:
                if (result.getCode().equals(Constant.CODE_SUCCESSFUL)) {
                    String query = (String) requestParams.get("query");
                    content = query;
                    remark = "搜索内容：" + query;
                }
                break;
            case CLICK_FRIEND:
                String nickname = (String) requestParams.get("nickname");
                content = nickname;
                remark = "友链名称：" + nickname;
                break;
            default:
                break;
        }
        return new VisitLogRemark(content, remark);
    }

    /**
     * 获取HttpServletRequest请求对象，并设置OperationLog对象属性
     *
     * @param joinPoint   切入点
     * @param visitLogger 注解OperationLogger对象
     * @param times       操作所用时间
     * @return
     */
    private VisitLog handleLog(ProceedingJoinPoint joinPoint, VisitLogger visitLogger, HttpServletRequest request, Result result,
                               int times, String identification) {        // 获取请求内容中的属性
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
        // 借助UserAgentUtils工具类获取操作系统和浏览器信息
        Map<String, String> userAgentMap = userAgentUtils.parseOsAndBrowser(userAgent);
        String os = userAgentMap.get("os");
        String browser = userAgentMap.get("browser");
        VisitLogRemark visitLogRemark = judgeBehavior(visitLogger.value(), requestParams, result);
        // 创建日志对象
        VisitLog log = new VisitLog(identification, uri, method, visitLogger.value().getBehavior(), visitLogRemark.getContent(), visitLogRemark.getRemark(), userAgent, ip, ipSource, times, param, os, browser);
        return log;
    }

}
