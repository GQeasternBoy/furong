package com.xueqiu.logaop.general;

import com.xueqiu.logaop.general.bean.LogEntity;
import com.xueqiu.logaop.general.service.LogService;
import com.xueqiu.utils.DateUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author:ggq
 * @Date:2018/11/20
 * @Description:日志切入点
 */
@Component
@Aspect
public class SystemLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

    private static final ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal<>("ThreadLocal beginTime");

    private static final ThreadLocal<LogEntity> logEntityThreadLocal = new NamedThreadLocal<>("ThreadLocal Log");

    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private LogService logService;

    @Pointcut("@annotation(com.xueqiu.logaop.general.ControllerLogAnnotation)")
    public void controllerAspect(){}

    @Pointcut("@annotation(**com.xueqiu.**.service.*.*(..)")
    public void serviveAspect(){}

    @Before(value = "controllerAspect()")
    public void beforeAspect(JoinPoint joinPoint){
        Date beginTime = new Date();
        beginTimeThreadLocal.set(beginTime);

        if (logger.isDebugEnabled()){
            logger.debug("------->开始时间{},请求URL:{}",beginTime,request.getRequestURI());
        }
    }

    @After("controllerAspect()")
    public void afterAspect(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        Map<String, String[]> requestParams = request.getParameterMap();

        String methodDescription = getControllerMethodDescription(joinPoint);

        Date operateDate = beginTimeThreadLocal.get();
        long beginTime = operateDate.getTime();
        long endTime = System.currentTimeMillis();
        String requestURI = request.getRequestURI();
        if (logger.isDebugEnabled()){
            logger.debug("------>计时结束{},URL:{},耗时:{},最大内存:{}m,已分配内存:{}m,已分配内存中剩余空间:{}m",
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(endTime),
                    requestURI, DateUtils.formatDateTime(endTime-beginTime),
                    Runtime.getRuntime().maxMemory()/1024/1024,
                    Runtime.getRuntime().totalMemory()/1024/1024,
                    Runtime.getRuntime().freeMemory()/1024/1024);
        }

        LogEntity logEntity = new LogEntity();
        logEntity.setLogId(UUID.randomUUID().toString().replaceAll("-",""));
        logEntity.setType("info");
        logEntity.setTitle(methodDescription);
        logEntity.setRemoteAddr(request.getRemoteAddr());
        logEntity.setRequestUrl(requestURI);
        logEntity.setMethod(request.getMethod());
        logEntity.setParams(requestParams);
        logEntity.setOperateDate(operateDate);
        logEntity.setTimeout(DateUtils.formatDateTime(endTime-beginTime));

        //1.直接执行保存操作
        logService.createLog(logEntity);

        //2.异步保存日志
        //new SaveLogThread(logEntity,logService).start();

        //3.线程池保存日志
        threadPoolExecutor.execute(new SaveLogThread(logEntity,logService));
        logEntityThreadLocal.set(logEntity);
    }

    @AfterThrowing(pointcut = "controllerAspect()",throwing = "e")
    public void afterThrowing(JoinPoint joinPoint,Exception e){
        LogEntity logEntity = logEntityThreadLocal.get();
        if(logEntity != null){
            logEntity.setType("error");
            logEntity.setException(e.toString());
        }
        new UpdateLogThread(logEntity,logService).start();
    }

    public static String getControllerMethodDescription(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        ControllerLogAnnotation annotation = method.getAnnotation(ControllerLogAnnotation.class);
        String description = annotation.description();
        return description;
    }

    private static class SaveLogThread implements Runnable{

        private LogEntity logEntity;

        private LogService logService;

        public SaveLogThread(LogEntity logEntity,LogService logService){
            this.logEntity = logEntity;
            this.logService = logService;
        }

        @Override
        public void run() {
            logService.createLog(logEntity);
        }
    }

    private static class UpdateLogThread extends Thread{

        private LogEntity logEntity;

        private LogService logService;

        public UpdateLogThread(LogEntity logEntity,LogService logService){
            this.logEntity = logEntity;
            this.logService = logService;
        }

        @Override
        public void run() {
            logService.updateLog(logEntity);
        }
    }
}
