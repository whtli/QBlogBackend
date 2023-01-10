package cn.li98.blog.service.impl;

import cn.li98.blog.dao.VisitLogMapper;
import cn.li98.blog.model.entity.VisitLog;
import cn.li98.blog.service.VisitLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author: whtli
 * @date: 2023/01/09
 * @description: 前端访问日志业务实现层
 */
@Service
public class VisitLogServiceImpl extends ServiceImpl<VisitLogMapper, VisitLog> implements VisitLogService {
}
