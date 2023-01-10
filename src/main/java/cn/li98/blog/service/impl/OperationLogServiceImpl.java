package cn.li98.blog.service.impl;


import cn.li98.blog.dao.OperationLogMapper;
import cn.li98.blog.model.entity.OperationLog;
import cn.li98.blog.service.OperationLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author: whtli
 * @date: 2022/12/06
 * @description: 操作日志业务实现层
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {
}
