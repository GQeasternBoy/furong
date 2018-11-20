package com.xueqiu.logaop.general.service;

import com.xueqiu.logaop.general.bean.LogEntity;
import org.springframework.stereotype.Service;

/**
 * @Author:ggq
 * @Date:2018/11/20
 * @Description:日志服务
 */
@Service
public interface LogService {

    int createLog(LogEntity logEntity);

    int updateLog(LogEntity logEntity);
}
