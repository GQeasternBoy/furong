package com.xueqiu.logaop.general;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author:ggq
 * @Date:2018/11/20
 * @Description:Service层日志拦截
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceLogAnnnotation {

    String description() default "";
}
