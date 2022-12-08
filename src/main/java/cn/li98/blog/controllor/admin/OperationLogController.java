package cn.li98.blog.controllor.admin;

import cn.li98.blog.common.Result;
import cn.li98.blog.common.annotation.OperationLogger;
import cn.li98.blog.model.OperationLog;
import cn.li98.blog.service.OperationLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: whtli
 * @date: 2022/12/07
 * @description:
 */
@RestController
@RequestMapping("/admin/log")
public class OperationLogController {
    @Autowired
    OperationLogService operationLogService;

    /**
     * 分页查询操作日志列表
     *
     * @param date     按操作时间查询
     * @param pageNum  页码
     * @param pageSize 每页个数
     * @return
     */
    @OperationLogger("查询操作日志")
    @GetMapping("/getOperationLogList")
    public Result operationLogs(@RequestParam(defaultValue = "") String[] date,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        /*String startDate = null;
        String endDate = null;
        if (date.length == 2) {
            startDate = date[0];
            endDate = date[1];
        }
        String orderBy = "create_time desc";
        PageHelper.startPage(pageNum, pageSize, orderBy);
        PageInfo<OperationLog> pageInfo = new PageInfo<>(operationLogService.getOperationLogListByDate(startDate, endDate));*/
        QueryWrapper<OperationLog> queryWrapper = new QueryWrapper<>();
        // 根据创建时间查询逆序的列表结果，越新发布的博客越容易被看到
        queryWrapper.orderByDesc("create_time");
        // 新建一个分页规则，pageNum代表当前页码，pageSize代表每页数量
        Page page = new Page(pageNum, pageSize);
        // 借助Page实现分页查询，借助QueryWrapper实现多参数查询
        IPage pageData = operationLogService.page(page, queryWrapper);
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
    @OperationLogger("按id删除操作日志")
    @DeleteMapping("/deleteOperationLogById")
    public Result delete(@RequestParam Long id) {
        operationLogService.removeById(id);
        return Result.succ("删除成功");
    }
}
