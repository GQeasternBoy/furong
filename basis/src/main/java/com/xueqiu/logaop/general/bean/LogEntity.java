package com.xueqiu.logaop.general.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @Author:ggq
 * @Date:2018/11/20
 * @Description:日志实体
 */
public class LogEntity implements Serializable{

    private static final Long serialVersionUID = 1L;

    @Getter
    @Setter
    private String logId;//日志主键

    @Getter
    @Setter
    private String type;//日志类型

    @Getter
    @Setter
    private String title;//日志标题

    @Getter
    @Setter
    private String remoteAddr;//请求地址

    @Getter
    @Setter
    private String requestUrl;//请求URL

    @Getter
    @Setter
    private String method;//请求方式

    @Getter
    @Setter
    private Map<String,String[]> params;//请求参数

    @Getter
    @Setter
    private String exception;//异常

    @Getter
    @Setter
    private Date operateDate;//开始时间

    @Getter
    @Setter
    private String timeout;//结束时间

    @Getter
    @Setter
    private String userId;

}
