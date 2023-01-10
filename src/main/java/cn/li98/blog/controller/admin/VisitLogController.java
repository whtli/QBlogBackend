package cn.li98.blog.controller.admin;

import cn.li98.blog.common.Result;
import cn.li98.blog.common.annotation.OperationLogger;
import cn.li98.blog.model.entity.VisitLog;
import cn.li98.blog.service.VisitLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: whtli
 * @date: 2023/01/10
 * @description: 前端访问日志控制层
 */
@RestController
@RequestMapping("/admin/log")
public class VisitLogController {
    @Autowired
    private VisitLogService visitLogService;
    /**
     * 分页查询操作日志列表
     *
     * @param date     按操作时间查询
     * @param pageNum  页码
     * @param pageSize 每页个数
     * @return
     */
    @OperationLogger("查询操作日志")
    @GetMapping("/getVisitLogList")
    public Result visitLogs(@RequestParam(defaultValue = "") String[] date,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        QueryWrapper<VisitLog> queryWrapper = new QueryWrapper<>();
        // 根据创建时间查询逆序的列表结果，越新发布的博客越容易被看到
        queryWrapper.orderByDesc("create_time");
        // 新建一个分页规则，pageNum代表当前页码，pageSize代表每页数量
        Page page = new Page(pageNum, pageSize);
        // 借助Page实现分页查询，借助QueryWrapper实现多参数查询
        IPage pageData = visitLogService.page(page, queryWrapper);
        Map<String, Object> data = new HashMap<>(2);
        data.put("pageData", pageData);
        data.put("total", pageData.getTotal());
        return Result.succ("请求成功", data);
    }

    /**
     * 按id删除操作日志
     *
     * @param id 日志id
     * @return
     */
    @OperationLogger("删除指定操作日志")
    @DeleteMapping("/deleteVisitLogById")
    public Result delete(@RequestParam Long id) {
        visitLogService.removeById(id);
        return Result.succ("删除成功");
    }
}
