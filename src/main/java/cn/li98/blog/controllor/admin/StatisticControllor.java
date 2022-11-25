package cn.li98.blog.controllor.admin;

import cn.li98.blog.common.Result;
import cn.li98.blog.service.StatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author: whtli
 * @date: 2022/11/17
 * @description: 用于获取统计数据的Controllor
 */
@Slf4j
@RestController
@RequestMapping("/admin/statistic")
public class StatisticControllor {
    @Autowired
    StatisticService statisticService;

    /**
     * 获取统计数据
     *
     * @return 存放了博客分类统计数据列表和分类名列表的哈希表
     */
    @GetMapping("/getStatistic")
    public Result getStatistic() {
        Map<String, Object> map = statisticService.getBlogCountList();
        return Result.succ(map);
    }
}
