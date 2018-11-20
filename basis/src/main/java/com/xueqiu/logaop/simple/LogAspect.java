package com.xueqiu.logaop.simple;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by 郭根权 on 2018/11/19.
 */
@Component
@Aspect
public class LogAspect {

    @Pointcut("@annotation(com.xueqiu.logaop.simple.LogAnnotation)")
    public void myPointCut() {
    }

    @AfterReturning(returning = "result",value = "myPointCut()")
    public void addLog(JoinPoint joinPoint,Object result){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //从切面织入点通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        //获取织入点所在的方法
        Method method = signature.getMethod();
        String operatorContext = "";//定义操作内容
        String operatorType = "";//定义操作方式

        //获取注解的操作方式
        if(method != null && !"".equals(method)){
            //获取自定义注解操作
            LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
            //获取用户的操作方式
            operatorType = logAnnotation.operateType();
            operatorContext = logAnnotation.operateContext();
        }

        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = className + "." + method.getName();
        //获取请求方式
        String requestMethod = request.getMethod();
        //获取请求URL
        String URL = request.getRequestURI().toString();
        //获取请求的ip
        String IP = request.getRemoteAddr();
        //获取userid
        String userid = request.getParameter("userid");
        //获取子系统id
        String subsystemid = request.getParameter("subsystemid");
        //生成UUID
        String uuid = UUID.randomUUID().toString();
        //获取请求的参数
        String argsName[] = ((CodeSignature)joinPoint.getSignature()).getParameterNames();
        Map<String, Object> paramMap = new HashMap<>();
        if (argsName.length > 0){
            paramMap = getParam(joinPoint,argsName,methodName);
        }
        String detail = JSON.toJSONString(paramMap);

        // 日志实体类封装
//        LogEntity logEntity = new LogEntity();
//        logEntity.setUserid(userid);
//        logEntity.setUuid(uuid);
//        logEntity.setIp(IP);
//        logEntity.setStatus(status);
//        logEntity.setOperatetype(operatetype);
//        logEntity.setOperatecontent(operatecontent);
//        logEntity.setDetail(detail);
//        logEntity.setSubsystemid(subsystemid);
//
//        logAddService.addLogInfo(logEntity);

    }

    public static Map<String,Object> getParam(JoinPoint joinPoint,String argsName[],String methodName){
        Map<String,Object> detailMap = new HashMap();
        Map<String,Object> map = new HashMap();
        //获取参数值
        Object args[] = joinPoint.getArgs();
        //获取参数名
        argsName = ((CodeSignature)joinPoint.getSignature()).getParameterNames();
        for (int i = 0;i < argsName.length;i++){
            map.put(argsName[i],args[i]);
        }
        detailMap.put("method",methodName);
        detailMap.put("params",map);
        return detailMap;
    }
}