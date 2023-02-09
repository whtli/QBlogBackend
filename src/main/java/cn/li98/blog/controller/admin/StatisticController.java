package cn.li98.blog.controller.admin;

import cn.li98.blog.common.Result;
import cn.li98.blog.annotation.OperationLogger;
import cn.li98.blog.service.StatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: whtli
 * @date: 2022/11/17
 * @description: 统计数据控制层
 */
@Slf4j
@RestController
@RequestMapping("/admin/data")
public class StatisticController {
    @Autowired
    StatisticService statisticService;

    /**
     * 获取统计数据
     *
     * @return 存放了博客分类统计数据列表和分类名列表的哈希表
     */
    @OperationLogger("获取统计数据")
    @GetMapping("/getStatistic")
    public Result getStatistic() {
        Map<String, Object> map = statisticService.getBlogStatistic();
        int totalPageView = statisticService.getTotalPageView();
        int todayPageView = statisticService.getTodayPageView();
        int totalUniqueVisitor = statisticService.getTotalUniqueVisitor();
        int todayUniqueVisitor = statisticService.getTodayUniqueVisitor();
        int totalComment = statisticService.getTotalComment();
        map.put("totalPageView", totalPageView);
        map.put("todayPageView", todayPageView);
        map.put("totalUniqueVisitor", totalUniqueVisitor);
        map.put("todayUniqueVisitor", todayUniqueVisitor);
        map.put("totalComment", totalComment);
        return Result.succ("统计信息获取成功", map);
    }
}
